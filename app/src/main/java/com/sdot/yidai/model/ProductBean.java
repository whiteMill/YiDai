package com.sdot.yidai.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ProductBean {
    /**
     * id : 4
     * label : 面单白条
     * createdAt : 2017-12-14T02:33:06.000+0000
     * lastModifiedAt : 2017-12-14T02:33:19.000+0000
     * act : null
     * notice : null
     * currentUserCanActList : [{"id":2,"label":"读取","actCode":"read","iconClass":null,"btnClass":null,"sort":20,"importance":false,"actGroup":"READ","messageType":null,"subcategory":"-","topcategory":"-"}]
     * fileObject : null
     * wechatFiles : []
     * stateActList : {"ENABLED":{"id":2,"label":"有效","sort":100,"stateCode":"ENABLED","done":false,"acts":[]},"DISABLED":{"id":3,"label":"无效","sort":200,"stateCode":"DISABLED","done":false,"acts":[]}}
     * fileCategoryTree : {}
     * filePackage : null
     * topcategory : -
     * subcategory : -
     * storeInterest : 0.04
     * debtorInterest : 0.21
     * storeAmountMin : 100000
     * storeAmountMax : 1000000
     * storeRepaymentDay : 4
     * debtorRepaymentDay : 9
     * autoRiskmodelFailValue : null
     * autoRiskmodelSuccessValue : null
     * description : 5465465456465
     * comment : null
     * sort : 1
     * _links : {"self":{"href":"http://test.edairisk.com/rest/products/4"},"product":{"href":"http://test.edairisk.com/rest/products/4"},"department":{"href":"http://test.edairisk.com/rest/products/4/department"},"logs":{"href":"http://test.edairisk.com/rest/products/4/logs"},"repaymentAccount":{"href":"http://test.edairisk.com/rest/products/4/repaymentAccount"},"capitalproduct":{"href":"http://test.edairisk.com/rest/products/4/capitalproduct"},"lastAct":{"href":"http://test.edairisk.com/rest/products/4/lastAct"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/products/4/lastModifiedBy"},"orderwdsjshes":{"href":"http://test.edairisk.com/rest/products/4/orderwdsjshes"},"workflow":{"href":"http://test.edairisk.com/rest/products/4/workflow"},"files":{"href":"http://test.edairisk.com/rest/products/4/files"},"punishinterestrule":{"href":"http://test.edairisk.com/rest/products/4/punishinterestrule"},"state":{"href":"http://test.edairisk.com/rest/products/4/state"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/products/4/createdByDepartment"},"createdBy":{"href":"http://test.edairisk.com/rest/products/4/createdBy"}}
     */

    private String id;
    private String label;
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
    private String storeInterest;
    private String debtorInterest;
    private String storeAmountMin;
    private String storeAmountMax;
    private String storeRepaymentDay;
    private String debtorRepaymentDay;
    private Object autoRiskmodelFailValue;
    private Object autoRiskmodelSuccessValue;
    private String description;
    private String loanRate;
    private Object comment;
    private String sort;
    private Object _links;
    private Object currentUserCanActList;
    private Object wechatFiles;

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate;
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

    public String getStoreInterest() {
        return storeInterest;
    }

    public void setStoreInterest(String storeInterest) {
        this.storeInterest = storeInterest;
    }

    public String getDebtorInterest() {
        return debtorInterest;
    }

    public void setDebtorInterest(String debtorInterest) {
        this.debtorInterest = debtorInterest;
    }

    public String getStoreAmountMin() {
        return storeAmountMin;
    }

    public void setStoreAmountMin(String storeAmountMin) {
        this.storeAmountMin = storeAmountMin;
    }

    public String getStoreAmountMax() {
        return storeAmountMax;
    }

    public void setStoreAmountMax(String storeAmountMax) {
        this.storeAmountMax = storeAmountMax;
    }

    public String getStoreRepaymentDay() {
        return storeRepaymentDay;
    }

    public void setStoreRepaymentDay(String storeRepaymentDay) {
        this.storeRepaymentDay = storeRepaymentDay;
    }

    public String getDebtorRepaymentDay() {
        return debtorRepaymentDay;
    }

    public void setDebtorRepaymentDay(String debtorRepaymentDay) {
        this.debtorRepaymentDay = debtorRepaymentDay;
    }

    public Object getAutoRiskmodelFailValue() {
        return autoRiskmodelFailValue;
    }

    public void setAutoRiskmodelFailValue(Object autoRiskmodelFailValue) {
        this.autoRiskmodelFailValue = autoRiskmodelFailValue;
    }

    public Object getAutoRiskmodelSuccessValue() {
        return autoRiskmodelSuccessValue;
    }

    public void setAutoRiskmodelSuccessValue(Object autoRiskmodelSuccessValue) {
        this.autoRiskmodelSuccessValue = autoRiskmodelSuccessValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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
