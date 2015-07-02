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

License
=======
<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" property="dct:title">BR-Lib</span> by <a xmlns:cc="http://creativecommons.org/ns#" href="https://github.com/TeamBR-Modding/BRLib" property="cc:attributionName" rel="cc:attributionURL">Dyonovan, PaulJoda</a> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.<br />Based on a work at <a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/TeamBR-Modding/BRLib" rel="dct:source">https://github.com/TeamBR-Modding/BRLib</a>.
