package com.sdot.yidai.model;


import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/14.
 */

public class OrderMdbtVo implements Serializable{
            /**
             * id : 9
             * label : null
             * createdAt : 2017-12-14T09:25:22.000+0000
             * lastModifiedAt : 2017-12-14T09:25:22.000+0000
             * act : null
             * notice : null
             * currentUserCanActList : [{"id":1,"label":"创建","actCode":"create","iconClass":null,"btnClass":null,"sort":10,"importance":false,"actGroup":"UPDATE","messageType":null,"subcategory":null,"topcategory":null},{"id":2,"label":"读取","actCode":"read","iconClass":null,"btnClass":null,"sort":20,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null},{"id":3,"label":"更新","actCode":"update","iconClass":null,"btnClass":null,"sort":30,"importance":false,"actGroup":"UPDATE","messageType":null,"subcategory":null,"topcategory":null},{"id":5,"label":"自己的列表","actCode":"listOwn","iconClass":null,"btnClass":null,"sort":50,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null}]
             * fileObject : null
             * wechatFiles : []
             * stateActList : {}
             * fileCategoryTree : null
             * filePackage : null
             * topcategory : -
             * subcategory : -
             * application : null
             * applyAmount : 320000
             * comfirmAmount : null
             * applyPeriodNumber : null
             * realName : 哦哟
             * applyMobile : 13011112222
             * applyIdentityNo : 342202198208057469
             * applyEnterpriseName : 兔兔
             * applyEnterpriseReigionName : 北京市北京市东城区
             * applyEnterpriseAddress : null
             * applyReferrerRealName : 兔兔
             * applyDayPickExpress : 1000
             * applyDayPatchExpress : 1000
             * applyWayBillFee : null
             * comment : null
             * applyEnterpriseProvince : 北京市
             * applyEnterpriseCity : 北京市
             * applyEnterpriseDistrict : 东城区
             * applyEnterpriseTown : null
             * applyPeriodCode : null
             * isDistribution : null
             * serviceName : null
             * applyPurchasePrice : 1
             * agentBrand : 其他
             * stateAdopt : false
             * stateEnable : false
             * _links : {"self":{"href":"http://test.edairisk.com/rest/ordermdbts/9"},"ordermdbt":{"href":"http://test.edairisk.com/rest/ordermdbts/9"},"workflow":{"href":"http://test.edairisk.com/rest/ordermdbts/9/workflow"},"company":{"href":"http://test.edairisk.com/rest/ordermdbts/9/company"},"enterprise":{"href":"http://test.edairisk.com/rest/ordermdbts/9/enterprise"},"debtorAccount":{"href":"http://test.edairisk.com/rest/ordermdbts/9/debtorAccount"},"user":{"href":"http://test.edairisk.com/rest/ordermdbts/9/user"},"logs":{"href":"http://test.edairisk.com/rest/ordermdbts/9/logs"},"createdBy":{"href":"http://test.edairisk.com/rest/ordermdbts/9/createdBy"},"files":{"href":"http://test.edairisk.com/rest/ordermdbts/9/files"},"lastAct":{"href":"http://test.edairisk.com/rest/ordermdbts/9/lastAct"},"person":{"href":"http://test.edairisk.com/rest/ordermdbts/9/person"},"state":{"href":"http://test.edairisk.com/rest/ordermdbts/9/state"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/ordermdbts/9/lastModifiedBy"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/ordermdbts/9/createdByDepartment"},"product":{"href":"http://test.edairisk.com/rest/ordermdbts/9/product"},"debtorReceiveAccount":{"href":"http://test.edairisk.com/rest/ordermdbts/9/debtorReceiveAccount"},"serviceUser":{"href":"http://test.edairisk.com/rest/ordermdbts/9/serviceUser"}}
             */

            private String id;
            private Object label;
            private String createdAt;
            private String lastModifiedAt;
            private Object act;
            private Object notice;
            private Object fileObject;
            private Object stateActList;
            private Object fileCategoryTree;
            private Object filePackage;
            private String topcategory;
            private String subcategory;
            private Object application;
            private String applyAmount;
            private Object comfirmAmount;
            private Object applyPeriodNumber;
            private String realName;
            private String applyMobile;
            private String applyIdentityNo;
            private String applyEnterpriseName;
            private String applyEnterpriseReigionName;
            private Object applyEnterpriseAddress;
            private String applyReferrerRealName;
            private String applyDayPickExpress;
            private String applyDayPatchExpress;
            private Object applyWayBillFee;
            private Object comment;
            private String applyEnterpriseProvince;
            private String applyEnterpriseCity;
            private String applyEnterpriseDistrict;
            private Object applyEnterpriseTown;
            private Object applyPeriodCode;
            private Object isDistribution;
            private Object serviceName;
            private String applyPurchasePrice;
            private String agentBrand;
            private boolean stateAdopt;
            private boolean stateEnable;
            private Object _links;
            private Object currentUserCanActList;
            private Object wechatFiles;

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

    public Object getFileObject() {
        return fileObject;
    }

    public void setFileObject(Object fileObject) {
        this.fileObject = fileObject;
    }

    public Object getStateActList() {
        return stateActList;
    }

    public void setStateActList(Object stateActList) {
        this.stateActList = stateActList;
    }

    public Object getFileCategoryTree() {
        return fileCategoryTree;
    }

    public void setFileCategoryTree(Object fileCategoryTree) {
        this.fileCategoryTree = fileCategoryTree;
    }

    public Object getFilePackage() {
        return filePackage;
    }

    public void setFilePackage(Object filePackage) {
        this.filePackage = filePackage;
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

    public Object getApplication() {
        return application;
    }

    public void setApplication(Object application) {
        this.application = application;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Object getComfirmAmount() {
        return comfirmAmount;
    }

    public void setComfirmAmount(Object comfirmAmount) {
        this.comfirmAmount = comfirmAmount;
    }

    public Object getApplyPeriodNumber() {
        return applyPeriodNumber;
    }

    public void setApplyPeriodNumber(Object applyPeriodNumber) {
        this.applyPeriodNumber = applyPeriodNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getApplyMobile() {
        return applyMobile;
    }

    public void setApplyMobile(String applyMobile) {
        this.applyMobile = applyMobile;
    }

    public String getApplyIdentityNo() {
        return applyIdentityNo;
    }

    public void setApplyIdentityNo(String applyIdentityNo) {
        this.applyIdentityNo = applyIdentityNo;
    }

    public String getApplyEnterpriseName() {
        return applyEnterpriseName;
    }

    public void setApplyEnterpriseName(String applyEnterpriseName) {
        this.applyEnterpriseName = applyEnterpriseName;
    }

    public String getApplyEnterpriseReigionName() {
        return applyEnterpriseReigionName;
    }

    public void setApplyEnterpriseReigionName(String applyEnterpriseReigionName) {
        this.applyEnterpriseReigionName = applyEnterpriseReigionName;
    }

    public Object getApplyEnterpriseAddress() {
        return applyEnterpriseAddress;
    }

    public void setApplyEnterpriseAddress(Object applyEnterpriseAddress) {
        this.applyEnterpriseAddress = applyEnterpriseAddress;
    }

    public String getApplyReferrerRealName() {
        return applyReferrerRealName;
    }

    public void setApplyReferrerRealName(String applyReferrerRealName) {
        this.applyReferrerRealName = applyReferrerRealName;
    }

    public String getApplyDayPickExpress() {
        return applyDayPickExpress;
    }

    public void setApplyDayPickExpress(String applyDayPickExpress) {
        this.applyDayPickExpress = applyDayPickExpress;
    }

    public String getApplyDayPatchExpress() {
        return applyDayPatchExpress;
    }

    public void setApplyDayPatchExpress(String applyDayPatchExpress) {
        this.applyDayPatchExpress = applyDayPatchExpress;
    }

    public Object getApplyWayBillFee() {
        return applyWayBillFee;
    }

    public void setApplyWayBillFee(Object applyWayBillFee) {
        this.applyWayBillFee = applyWayBillFee;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getApplyEnterpriseProvince() {
        return applyEnterpriseProvince;
    }

    public void setApplyEnterpriseProvince(String applyEnterpriseProvince) {
        this.applyEnterpriseProvince = applyEnterpriseProvince;
    }

    public String getApplyEnterpriseCity() {
        return applyEnterpriseCity;
    }

    public void setApplyEnterpriseCity(String applyEnterpriseCity) {
        this.applyEnterpriseCity = applyEnterpriseCity;
    }

    public String getApplyEnterpriseDistrict() {
        return applyEnterpriseDistrict;
    }

    public void setApplyEnterpriseDistrict(String applyEnterpriseDistrict) {
        this.applyEnterpriseDistrict = applyEnterpriseDistrict;
    }

    public Object getApplyEnterpriseTown() {
        return applyEnterpriseTown;
    }

    public void setApplyEnterpriseTown(Object applyEnterpriseTown) {
        this.applyEnterpriseTown = applyEnterpriseTown;
    }

    public Object getApplyPeriodCode() {
        return applyPeriodCode;
    }

    public void setApplyPeriodCode(Object applyPeriodCode) {
        this.applyPeriodCode = applyPeriodCode;
    }

    public Object getIsDistribution() {
        return isDistribution;
    }

    public void setIsDistribution(Object isDistribution) {
        this.isDistribution = isDistribution;
    }

    public Object getServiceName() {
        return serviceName;
    }

    public void setServiceName(Object serviceName) {
        this.serviceName = serviceName;
    }

    public String getApplyPurchasePrice() {
        return applyPurchasePrice;
    }

    public void setApplyPurchasePrice(String applyPurchasePrice) {
        this.applyPurchasePrice = applyPurchasePrice;
    }

    public String getAgentBrand() {
        return agentBrand;
    }

    public void setAgentBrand(String agentBrand) {
        this.agentBrand = agentBrand;
    }

    public boolean isStateAdopt() {
        return stateAdopt;
    }

    public void setStateAdopt(boolean stateAdopt) {
        this.stateAdopt = stateAdopt;
    }

    public boolean isStateEnable() {
        return stateEnable;
    }

    public void setStateEnable(boolean stateEnable) {
        this.stateEnable = stateEnable;
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

    public Object getWechatFiles() {
        return wechatFiles;
    }

    public void setWechatFiles(Object wechatFiles) {
        this.wechatFiles = wechatFiles;
    }
}
