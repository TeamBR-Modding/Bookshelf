Bookshelf API <a href='http://teambrmodding.com:8111/viewType.html?buildTypeId=Bookshelf_Build&tab=buildTypeStatusDiv&branch_Bookshelf=%3Cdefault%3E'><img src="http://teambrmodding.com:8111/app/rest/builds/buildType:(id:Bookshelf_Build)/statusIcon"/></a><a href='https://minecraft.curseforge.com/projects/bookshelf-api-library'><img src='http://cf.way2muchnoise.eu/full_bookshelf-api-library_downloads.svg'><img src='http://cf.way2muchnoise.eu/versions/bookshelf-api-library_latest.svg'></a>
======

Library used by TeamBRModding mods. Contains base class helpers for energy/inventories/fluid and a simple GUI system.


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
