# try_java_concurrency

## 1. 導入部

### スレッドはいくつ生成できるか？

```
$ java ThreadLimitTest.java
[2.233s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 2040k, guardsize: 0k, detached.
[2.233s][warning][os,thread] Failed to start the native thread for java.lang.Thread "Thread-9172"
Reached thread limit: 9172
java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
        at java.base/java.lang.Thread.start0(Native Method)
        at java.base/java.lang.Thread.start(Thread.java:1390)
        at ThreadLimitTest.main(ThreadLimitTest.java:13)
        at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)
        at java.base/java.lang.reflect.Method.invoke(Method.java:565)
        at jdk.compiler/com.sun.tools.javac.launcher.SourceLauncher.execute(SourceLauncher.java:254)
        at jdk.compiler/com.sun.tools.javac.launcher.SourceLauncher.run(SourceLauncher.java:138)
        at jdk.compiler/com.sun.tools.javac.launcher.SourceLauncher.main(SourceLauncher.java:76)
^C
```

## 2.仮想スレッドを理解する

### JFR

```
$ java -version
openjdk version "24.0.1" 2025-04-15
OpenJDK Runtime Environment Homebrew (build 24.0.1)
OpenJDK 64-Bit Server VM Homebrew (build 24.0.1, mixed mode, sharing)
$ java -XX:StartFlightRecording=filename=recording.jfr,settings=VThreadEvents.jfc JFRVirtualThreadDemo.java
$ jfr print --events jdk.VirtualThreadStart,jdk.VirtualThreadEnd,jdk.VirtualThreadPinned,jdk.VirtualThreadSubmitFailed recording.jfr 
jdk.VirtualThreadStart {
  startTime = 09:28:34.414 (2025-09-27)
  javaThreadId = 34
  eventThread = "" (javaThreadId = 34, virtual)
}

jdk.VirtualThreadEnd {
  startTime = 09:28:34.414 (2025-09-27)
  javaThreadId = 34
  eventThread = "" (javaThreadId = 34, virtual)
}

jdk.VirtualThreadStart {
  startTime = 09:28:34.415 (2025-09-27)
  javaThreadId = 37
  eventThread = "" (javaThreadId = 37, virtual)
}

jdk.VirtualThreadEnd {
  startTime = 09:28:34.916 (2025-09-27)
  javaThreadId = 37
  eventThread = "" (javaThreadId = 37, virtual)
}

jdk.VirtualThreadStart {
  startTime = 09:28:34.917 (2025-09-27)
  javaThreadId = 39
  eventThread = "" (javaThreadId = 39, virtual)
}

jdk.VirtualThreadEnd {
  startTime = 09:28:35.419 (2025-09-27)
  javaThreadId = 39
  eventThread = "" (javaThreadId = 39, virtual)
}

```
