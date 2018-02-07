package com.sdot.yidai.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public class ApplyDateVo {
    private String sortId;
    private String sortName;
    private List<ImageVo> imageVoList;

    public ApplyDateVo(String sortId, String sortName, List<ImageVo> imageVoList) {
        this.sortId = sortId;
        this.sortName = sortName;
        this.imageVoList = imageVoList;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public List<ImageVo> getImageVoList() {
        return imageVoList;
    }

    public void setImageVoList(List<ImageVo> imageVoList) {
        this.imageVoList = imageVoList;
    }
}
