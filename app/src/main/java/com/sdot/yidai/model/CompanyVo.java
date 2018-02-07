package com.sdot.yidai.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/14.
 */

public class CompanyVo implements Serializable {
    /**
     * id : 29
     * label : null
     * createdAt : 2017-12-14T06:44:33.000+0000
     * lastModifiedAt : 2017-12-14T06:44:33.000+0000
     * act : null
     * notice : null
     * currentUserCanActList : [{"id":1,"label":"创建","actCode":"create","iconClass":null,"btnClass":null,"sort":10,"importance":false,"actGroup":"UPDATE","messageType":null,"subcategory":null,"topcategory":null},{"id":2,"label":"读取","actCode":"read","iconClass":null,"btnClass":null,"sort":20,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null},{"id":3,"label":"更新","actCode":"update","iconClass":null,"btnClass":null,"sort":30,"importance":false,"actGroup":"UPDATE","messageType":null,"subcategory":null,"topcategory":null},{"id":4,"label":"删除","actCode":"delete","iconClass":null,"btnClass":null,"sort":40,"importance":false,"actGroup":"UPDATE","messageType":null,"subcategory":null,"topcategory":null},{"id":5,"label":"自己的列表","actCode":"listOwn","iconClass":null,"btnClass":null,"sort":50,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null},{"id":6,"label":"部门的列表","actCode":"listOwnDepartment","iconClass":null,"btnClass":null,"sort":60,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null},{"id":7,"label":"部门和下属部门的列表","actCode":"listOwnDepartmentAndChildren","iconClass":null,"btnClass":null,"sort":70,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null}]
     * companyName : 监控
     * companyPersonName : 进口
     * companyAddress : 北京市北京市东城区进口
     * companyNumber : 556
     * _links : {"self":{"href":"http://test.edairisk.com/rest/companies/29"},"company":{"href":"http://test.edairisk.com/rest/companies/29"},"state":{"href":"http://test.edairisk.com/rest/companies/29/state"},"user":{"href":"http://test.edairisk.com/rest/companies/29/user"},"createdBy":{"href":"http://test.edairisk.com/rest/companies/29/createdBy"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/companies/29/createdByDepartment"},"logs":{"href":"http://test.edairisk.com/rest/companies/29/logs"},"lastAct":{"href":"http://test.edairisk.com/rest/companies/29/lastAct"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/companies/29/lastModifiedBy"}}
     */

    private String id;
    private Object label;
    private String createdAt;
    private String lastModifiedAt;
    private Object act;
    private Object notice;
    private String companyName;
    private String companyPersonName;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private String companyAddress;
    private String companyNumber;
    private Object _links;
    private Object currentUserCanActList;

    public String getCompanyProvince() {
        return companyProvince;
    }

    public void setCompanyProvince(String companyProvince) {
        this.companyProvince = companyProvince;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyDistrict() {
        return companyDistrict;
    }

    public void setCompanyDistrict(String companyDistrict) {
        this.companyDistrict = companyDistrict;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Object getAct() {
        return act;
    }

    public void setAct(Object act) {
        this.act = act;
    }

    public Object getNotice() {
        return notice;
    }

    public void setNotice(Object notice) {
        this.notice = notice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPersonName() {
        return companyPersonName;
    }

    public void setCompanyPersonName(String companyPersonName) {
        this.companyPersonName = companyPersonName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Object get_links() {
        return _links;
    }

    public void set_links(Object _links) {
        this._links = _links;
    }

    public Object getCurrentUserCanActList() {
        return currentUserCanActList;
    }

    public void setCurrentUserCanActList(Object currentUserCanActList) {
        this.currentUserCanActList = currentUserCanActList;
    }
}
