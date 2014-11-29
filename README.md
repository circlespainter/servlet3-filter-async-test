# Servlet 3 filter chain async mode test

Derived from a (very useful) post here [Micha Kops / hasCode.com Blog] and its sample code.

Filter chain in async mode (servlet spec 3.0+) seems broken in most popular servlet containers.

* Tomcat
** https://issues.apache.org/bugzilla/show_bug.cgi?id=57284
* Jetty
** https://bugs.eclipse.org/bugs/show_bug.cgi?id=433321
** https://bugs.eclipse.org/bugs/show_bug.cgi?id=453594
** https://bugs.eclipse.org/bugs/show_bug.cgi?id=453609
** https://bugs.eclipse.org/bugs/show_bug.cgi?id=446563

## Getting started

Adjust `build.gradle` to choose container versions and between Tomcat and Jetty, then `gradle run`.

## Servlet request processing

### Normal flow with 2 filters

Processing happens in the same container thread:

```
CONTAINER -> Filter1 (doFilter):
  ...
  chain.doFilter() -> Filter2 (doFilter):
    ...
    chain.doFilter() -> Servlet (service):
      ...
      (Servlet returns)
    ...
    (Filter2 returns)
  ...
  (Filter1 returns)
-> Request is committed to container and response is returned to client
```

### Flow when request is put in async mode by filter 1

Main container thread:

```
CONTAINER -> Filter1 (doFilter):
  ...
  chain.doFilter() -> Filter2 (doFilter):
    ...
    request.startAsync()
    -> dispatch rest of processing to some other thread
    (Filter2 returns)
  ...
  (Filter1 returns)
(Request processing is still outstanding)
```

In some other thread:

```
 Filter2 (runnable):
  chain.doFilter() -> Servlet (service):
    ...
    (Servlet returns)
  ...
  request.complete() -> Request is committed to container and response is returned to client
  ...
  (Filter2's runnable returns and the separate thread ends)
```

The normal control flow is broken as the first filter could return before the second one gets even a chance to run and request
processing ends. The case where the servlet starts the async context is similar.

If a processing flow similar to the sync one has to be kept in place, this has to be performed collaboratively by filters through
book-keeping especially coded for such a purpose.

Unfortunately both Jetty and Tomcat containers have problems when `chain.doFilter()` is called in a different thread after putting
request processing in async mode. Tomcat simply crashes with NPE (7.0.56, 7.0.57 and 8.0.15), while Jetty loses some request fields
(`contextPath`, `servletContext` and `requestURI` become `null` in 8.1.15.v20140411, 8.1.16.v20140903, 9.2.4.v20141103 and
9.2.5.v20141112).

### A simpler but still async case

In this case a ThreadFilter executes first in the chain and has the sole purpose of dispatching the rest of the request processing
on another thread.

In this way the whole application filter chain runs in the same (async) context with a normal control flow:

```
Thread 2: Filter 1 -> Filter 2 -> Servlet -> Filter 2 -> Filter 1
```

In this case there's no reason for special bookeeping.

Main container thread:

```
CONTAINER -> ThreadFilter (doFilter):
  -> dispatch rest of processing to some other thread
  (ThreadFilter returns)
(Request processing is still outstanding)
```

In some other thread:

```
 Filter2 (runnable):
  chain.doFilter() -> Servlet (service):
    ...
    (Servlet returns)
  ...
  request.complete() -> Request is committed to container and response is returned to client
  ...
  (Filter2's runnable returns and the separate thread ends)
```

Of course, unfortunately, the same container issues related to async execution apply.

## Dispatching requests for async execution in Spring Boot (with or without Actuator)

Spring Boot runs Spring Framework in an embedded container (Tomcat or Jetty) and provides an auto-configuration framework
based on sensible defaults and classpath inspection (among other things) in order to minimize the need to write (and
maintain) setup code and/or XML configuration.

Spring Boot and Actuator use servlet filters to perform several tasks (e.g. security, performance measurement ecc.) and this
means it is plagued by the above issues with containers under async mode (e.g. the metrics filter assumes requestURI not to be
`null`).

Spring supports controller methods returning a `Callable` or `Future` though, which will execute under a Spring asynchronous task
by leveraging Servlet support for async request processing dispatch. Spring filters support some special bookkeeping needed for
this particular case.

Here are some possible strategies (and their shortcomings) to dispatch Spring processing to a custom async execution context:

* Starting the async context in a special outmost (i.e. first) filter -> hits bugs in containers
  * Jetty could be patched by wrapping the request but unfortunately the `doFilter` implementation in (at least) 8.1.5 to 9.2.4
    assumes either that the request is not wrapped or that the unwrapped one can be found in an internal threadlocal, which is
    clearly not true when dispatching to a different thread as it is the case when in async mode.
* Starting the async context in a DispatcherServlet (Springs Web MVC central servlet) initiating Spring special async bookkeeping
  -> Spring request processing invoked by the servlet assumes that the bookkeeping hasn't already been started, so it would need
  to be "patched" with customized beans
* Starting the async context in the controller method handler -> has a more limited scope and requires adapting several to support
  Spring's async bookkeeping

----

   [Micha Kops / hasCode.com Blog]:http://www.hascode.com/2013/07/embedding-jetty-or-tomcat-in-your-java-application/
