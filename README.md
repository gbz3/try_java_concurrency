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
