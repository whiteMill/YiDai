package com.sdot.yidai.model;

/**
 * Created by Administrator on 2017/11/3.
 */

public class NoticeVo {

    /**
     * id : 1
     * label : null
     * createdAt : 2017-12-08T03:17:29.000+0000
     * lastModifiedAt : 2017-12-08T03:17:29.000+0000
     * title : 开户进度通知
     * content : 尊敬的用户你好 ，开户成功
     * _links : {"self":{"href":"http://test.edairisk.com/rest/appMessages/1"},"appMessage":{"href":"http://test.edairisk.com/rest/appMessages/1"},"toUser":{"href":"http://test.edairisk.com/rest/appMessages/1/toUser"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/appMessages/1/lastModifiedBy"},"createdBy":{"href":"http://test.edairisk.com/rest/appMessages/1/createdBy"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/appMessages/1/createdByDepartment"},"product":{"href":"http://test.edairisk.com/rest/appMessages/1/product"}}
     */

    private String id;
    private String label;
    private String createdAt;
    private String lastModifiedAt;
    private String title;
    private String pushTime;
    private String content;
    private Boolean reading;
    private Object _links;
    private String toPage;
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getToPage() {
        return toPage;
    }

    public void setToPage(String toPage) {
        this.toPage = toPage;
    }

    public Boolean getReading() {
        return reading;
    }

    public void setReading(Boolean reading) {
        this.reading = reading;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object get_links() {
        return _links;
    }

    public void set_links(Object _links) {
        this._links = _links;
    }


}
