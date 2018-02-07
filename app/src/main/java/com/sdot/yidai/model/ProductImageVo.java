package com.sdot.yidai.model;

/**
 * Created by Administrator on 2017/11/20.
 */

public class ProductImageVo {

    /**
     * id : 103
     * label : null
     * createdAt : 2017-11-20T02:02:25.000+0000
     * lastModifiedAt : 2017-11-20T02:02:25.000+0000
     * originalFileName : 151114155843498.jpg
     * newFileName : db87092e-db14-421d-8cef-c08f558888f1.jpg
     * fileSize : 52723
     * fileType : JPEG
     * smallImage : db87092e-db14-421d-8cef-c08f558888f1_198.jpg
     * middleImage : db87092e-db14-421d-8cef-c08f558888f1_720.jpg
     * largeImage : db87092e-db14-421d-8cef-c08f558888f1.jpg
     * uploadType : image
     * topcategory : 资产证明类
     * subcategory : 营业执照
     * appCode : null
     * actCode : null
     * client : null
     * imei : null
     * ip : 115.192.87.87
     * _links : {"self":{"href":"http://test.edairisk.com/rest/orderwdsjshFiles/103"},"orderwdsjshFile":{"href":"http://test.edairisk.com/rest/orderwdsjshFiles/103"},"entity":{"href":"http://test.edairisk.com/rest/orderwdsjshFiles/103/entity"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/orderwdsjshFiles/103/createdByDepartment"},"act":{"href":"http://test.edairisk.com/rest/orderwdsjshFiles/103/act"},"uploaderDepartmenttype":{"href":"http://test.edairisk.com/rest/orderwdsjshFiles/103/uploaderDepartmenttype"},"log":{"href":"http://test.edairisk.com/rest/orderwdsjshFiles/103/log"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/orderwdsjshFiles/103/lastModifiedBy"},"createdBy":{"href":"http://test.edairisk.com/rest/orderwdsjshFiles/103/createdBy"}}
     */

    private int id;
    private Object label;
    private String createdAt;
    private String lastModifiedAt;
    private String originalFileName;
    private String newFileName;
    private int fileSize;
    private String fileType;
    private String smallImage;
    private String middleImage;
    private String largeImage;
    private String uploadType;
    private String topcategory;
    private String subcategory;
    private Object appCode;
    private Object actCode;
    private Object client;
    private Object imei;
    private String ip;
    private Object _links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getLabel() {
        return label;
    }

    public void setLabel(Object label) {
        this.label = label;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(String lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getMiddleImage() {
        return middleImage;
    }

    public void setMiddleImage(String middleImage) {
        this.middleImage = middleImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getTopcategory() {
        return topcategory;
    }

    public void setTopcategory(String topcategory) {
        this.topcategory = topcategory;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Object getAppCode() {
        return appCode;
    }

    public void setAppCode(Object appCode) {
        this.appCode = appCode;
    }

    public Object getActCode() {
        return actCode;
    }

    public void setActCode(Object actCode) {
        this.actCode = actCode;
    }

    public Object getClient() {
        return client;
    }

    public void setClient(Object client) {
        this.client = client;
    }

    public Object getImei() {
        return imei;
    }

    public void setImei(Object imei) {
        this.imei = imei;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Object get_links() {
        return _links;
    }

    public void set_links(Object _links) {
        this._links = _links;
    }
}
