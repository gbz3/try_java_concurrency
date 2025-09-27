# try_java_concurrency

## 0. SDKMAN

[SDKMAN](https://sdkman.io/)

```
$ sudo apt install unzip zip
$ curl -s "https://get.sdkman.io" | bash
$ source "/home/kashiba/.sdkman/bin/sdkman-init.sh"
$ sdk version

SDKMAN!
script: 5.20.0
native: 0.7.14 (linux aarch64)

$ sdk list java
...
$ sdk install java 25-open
...
$ sdk current java
Using java version 25-open
$ java --version
openjdk 25 2025-09-16
OpenJDK Runtime Environment (build 25+36-3489)
OpenJDK 64-Bit Server VM (build 25+36-3489, mixed mode, sharing)
```

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
$ java -XX:StartFlightRecording=filename=recording.jfr,settings=VThreadEvents.jfc JFRVirtualThreadDemo.java
$ jfr print --events jdk.VirtualThreadStart,jdk.VirtualThreadEnd,jdk.VirtualThreadPinned,jdk.VirtualThreadSubmitFailed recording.jfr
jdk.VirtualThreadStart {
  startTime = 10:38:34.659 (2025-09-27)
  javaThreadId = 29
  eventThread = "" (javaThreadId = 29, virtual)
}

jdk.VirtualThreadEnd {
  startTime = 10:38:34.660 (2025-09-27)
  javaThreadId = 29
  eventThread = "" (javaThreadId = 29, virtual)
}

jdk.VirtualThreadStart {
  startTime = 10:38:34.660 (2025-09-27)
  javaThreadId = 32
  eventThread = "" (javaThreadId = 32, virtual)
}

jdk.VirtualThreadEnd {
  startTime = 10:38:35.176 (2025-09-27)
  javaThreadId = 32
  eventThread = "" (javaThreadId = 32, virtual)
}

jdk.VirtualThreadStart {
  startTime = 10:38:35.178 (2025-09-27)
  javaThreadId = 34
  eventThread = "" (javaThreadId = 34, virtual)
}

jdk.VirtualThreadEnd {
  startTime = 10:38:35.680 (2025-09-27)
  javaThreadId = 34
  eventThread = "" (javaThreadId = 34, virtual)
}

```

## 3.Javaにおける最新の並行処理の仕組み

### フィボナッチ計算（FixedPool）

```
[1]$ java FibonacciNumberWithTraditionalThreadPool.java  # 応答なし（ダンプ取得後に Ctrl+C）

[2]$ jps
6851 SourceLauncher
7049 Jps
[2]$ jcmd 6851 Thread.dump_to_file -format=json threaddump.json
6851:
Created ****/try_java_concurrency/threaddump.json

```

### フィボナッチ計算（ForkJoinPool）

```
$ java FibonacciNumberWithForkJoinPool.java
Fibonacci number is: 6765
```
