package com.teambr.bookshelf.collections;


public class VersionReturn {

    public String oldVersion;
    public String newVersion;
    public String status;

    public VersionReturn(String oldVersion, String newVersion, String status) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.status = status;
    }

}
