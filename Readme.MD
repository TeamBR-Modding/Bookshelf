BR-Lib
======

Library of useful functions used by TeamBR

Adding to Project
=================

Add the following code to build.gradle.

```
repositories {
    maven {
        url "http://www.beyondrealitypack.com/maven2/"
    }
}

dependencies {
    compile group: "com.dyonovan.brlib", name: "BR-Lib", version: "+", classifier: "dev"
}
```
