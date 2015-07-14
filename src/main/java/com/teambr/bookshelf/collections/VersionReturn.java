package com.teambr.bookshelf.collections;


public class VersionReturn {

    public String oldVersion;
    public String newVersion;
    public String status;
    public String updateLoc;

    public VersionReturn(String oldVersion, String newVersion, String status, String updateLoc) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.status = status;
        this.updateLoc = updateLoc;
    }

}
