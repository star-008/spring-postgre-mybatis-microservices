package com.tianwen.springcloud.scoreapi.entity.export;

/**
 * Created by kimchh on 11/23/2018.
 */
public class ExportedFile {

    private String name;
    private String contentType;
    private String path;
    private String key;

    public ExportedFile() {

    }

    public ExportedFile(String name, String contentType) {
        this.name = name;
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
