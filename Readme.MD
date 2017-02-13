Bookshelf API [![Build Status](http://teambrmodding.com:8080/job/Bookshelf%20API%201.8.9/badge/icon)](http://teambrmodding.com:8080/job/Bookshelf%20API%201.8.9/)
======

Library of useful functions used by TeamBR

Adding to Project
=================

Add the following code to build.gradle.

```
repositories {
    maven {
        url "http://www.teambrmodding.com/maven2/"
    }
}

dependencies {
    compile group: "com.teambr.bookshelf", name: "Bookshelf", version: "+", classifier: "dev"
}
```

License
=======
<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" property="dct:title">Bookshelf API</span> by <a xmlns:cc="http://creativecommons.org/ns#" href="http://minecraft.curseforge.com/projects/bookshelf-api-library" property="cc:attributionName" rel="cc:attributionURL">Team BR Modding</a> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.<br />Based on a work at <a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/TeamBR-Modding/Bookshelf" rel="dct:source">https://github.com/TeamBR-Modding/Bookshelf</a>.
