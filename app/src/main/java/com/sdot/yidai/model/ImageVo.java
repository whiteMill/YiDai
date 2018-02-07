package com.sdot.yidai.model;

/**
 * Created by Administrator on 2017/11/14.
 */

public class ImageVo {
    private String id;
    private String path;

    public ImageVo(String id, String path) {
        this.id = id;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
