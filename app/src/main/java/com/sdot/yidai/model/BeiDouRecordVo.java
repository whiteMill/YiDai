package com.sdot.yidai.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BeiDouRecordVo {

    /**
     * id : 38
     * label : null
     * createdAt : 2017-11-23T07:15:36.000+0000
     * lastModifiedAt : 2017-11-23T07:15:36.000+0000
     * act : null
     * notice : null
     * currentUserCanActList : [{"id":1,"label":"创建","actCode":"create","iconClass":"fa fa-star","btnClass":"pull-left  btn btn-success btn-sm  m-r-sm m-b-sm btn-addon","sort":10,"importance":false,"actGroup":"UPDATE","messageType":null,"subcategory":null,"topcategory":null},{"id":2,"label":"读取","actCode":"read","iconClass":null,"btnClass":null,"sort":20,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null}]
     * fileObject : null
     * wechatFiles : []
     * stateActList : {}
     * fileCategoryTree : null
     * filePackage : null
     * topcategory : -
     * subcategory : -
     * repaymentMethodCode : LEND_REPAY_ON_DEMAND
     * principal : 1234
     * periodCode : null
     * periodNumber : null
     * debtorInterest : null
     * storeInterest : null
     * creditorInterest : null
     * creditorRepaymentDay : null
     * debtorRepaymentDay : null
     * debtorExtentedRepaymentDays : null
     * description : null
     * comment : null
     * information : {"id":"BB3D6430B6C5419DAB72E21E1EBCAB55","mobile":"15356799999","loanStartDate":"20171123","loanEndDate":"20171223","loanAmount":1234,"payType":0,"accountType":"2","loanAccount":"666655698745125910","accountName":"大风","accountBank":"光大银行","jgCode":"0039"}
     * creditorLoanSn : BB3D6430B6C5419DAB72E21E1EBCAB55
     * serviceUserName : null
     * loanSn : null
     * debtorName : null
     * loanType : null
     * debtorRealityAmount : null
     * oneTimeFee : null
     * retreatFee : null
     * _links : {"self":{"href":"http://test.edairisk.com/rest/loans/38"},"loan":{"href":"http://test.edairisk.com/rest/loans/38"},"creditcard":{"href":"http://test.edairisk.com/rest/loans/38/creditcard"},"files":{"href":"http://test.edairisk.com/rest/loans/38/files"},"serviceUser":{"href":"http://test.edairisk.com/rest/loans/38/serviceUser"},"punishinterestrule":{"href":"http://test.edairisk.com/rest/loans/38/punishinterestrule"},"creditrepayplans":{"href":"http://test.edairisk.com/rest/loans/38/creditrepayplans"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/loans/38/createdByDepartment"},"lastAct":{"href":"http://test.edairisk.com/rest/loans/38/lastAct"},"debtorPerson":{"href":"http://test.edairisk.com/rest/loans/38/debtorPerson"},"debtorEnterprise":{"href":"http://test.edairisk.com/rest/loans/38/debtorEnterprise"},"workflow":{"href":"http://test.edairisk.com/rest/loans/38/workflow"},"logs":{"href":"http://test.edairisk.com/rest/loans/38/logs"},"debtorReceiveBankcard":{"href":"http://test.edairisk.com/rest/loans/38/debtorReceiveBankcard"},"debtorUser":{"href":"http://test.edairisk.com/rest/loans/38/debtorUser"},"createdBy":{"href":"http://test.edairisk.com/rest/loans/38/createdBy"},"ordercdd":{"href":"http://test.edairisk.com/rest/loans/38/ordercdd"},"state":{"href":"http://test.edairisk.com/rest/loans/38/state"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/loans/38/lastModifiedBy"}}
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
    private String repaymentMethodCode;
    private String principal;
    private Object periodCode;
    private Object periodNumber;
    private Object debtorInterest;
    private Object storeInterest;
    private Object creditorInterest;
    private Object creditorRepaymentDay;
    private Object debtorRepaymentDay;
    private Object debtorExtentedRepaymentDays;
    private Object description;
    private Object comment;
    private String information;
    private String creditorLoanSn;
    private Object serviceUserName;
    private Object loanSn;
    private Object debtorName;
    private Object loanType;
    private Object debtorRealityAmount;
    private Object oneTimeFee;
    private Object retreatFee;
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

    public String getRepaymentMethodCode() {
        return repaymentMethodCode;
    }

    public void setRepaymentMethodCode(String repaymentMethodCode) {
        this.repaymentMethodCode = repaymentMethodCode;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Object getPeriodCode() {
        return periodCode;
    }

    public void setPeriodCode(Object periodCode) {
        this.periodCode = periodCode;
    }

    public Object getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(Object periodNumber) {
        this.periodNumber = periodNumber;
    }

    public Object getDebtorInterest() {
        return debtorInterest;
    }

    public void setDebtorInterest(Object debtorInterest) {
        this.debtorInterest = debtorInterest;
    }

    public Object getStoreInterest() {
        return storeInterest;
    }

    public void setStoreInterest(Object storeInterest) {
        this.storeInterest = storeInterest;
    }

    public Object getCreditorInterest() {
        return creditorInterest;
    }

    public void setCreditorInterest(Object creditorInterest) {
        this.creditorInterest = creditorInterest;
    }

    public Object getCreditorRepaymentDay() {
        return creditorRepaymentDay;
    }

    public void setCreditorRepaymentDay(Object creditorRepaymentDay) {
        this.creditorRepaymentDay = creditorRepaymentDay;
    }

    public Object getDebtorRepaymentDay() {
        return debtorRepaymentDay;
    }

    public void setDebtorRepaymentDay(Object debtorRepaymentDay) {
        this.debtorRepaymentDay = debtorRepaymentDay;
    }

    public Object getDebtorExtentedRepaymentDays() {
        return debtorExtentedRepaymentDays;
    }

    public void setDebtorExtentedRepaymentDays(Object debtorExtentedRepaymentDays) {
        this.debtorExtentedRepaymentDays = debtorExtentedRepaymentDays;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCreditorLoanSn() {
        return creditorLoanSn;
    }

    public void setCreditorLoanSn(String creditorLoanSn) {
        this.creditorLoanSn = creditorLoanSn;
    }

    public Object getServiceUserName() {
        return serviceUserName;
    }

    public void setServiceUserName(Object serviceUserName) {
        this.serviceUserName = serviceUserName;
    }

    public Object getLoanSn() {
        return loanSn;
    }

    public void setLoanSn(Object loanSn) {
        this.loanSn = loanSn;
    }

    public Object getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(Object debtorName) {
        this.debtorName = debtorName;
    }

    public Object getLoanType() {
        return loanType;
    }

    public void setLoanType(Object loanType) {
        this.loanType = loanType;
    }

    public Object getDebtorRealityAmount() {
        return debtorRealityAmount;
    }

    public void setDebtorRealityAmount(Object debtorRealityAmount) {
        this.debtorRealityAmount = debtorRealityAmount;
    }

    public Object getOneTimeFee() {
        return oneTimeFee;
    }

    public void setOneTimeFee(Object oneTimeFee) {
        this.oneTimeFee = oneTimeFee;
    }

    public Object getRetreatFee() {
        return retreatFee;
    }

    public void setRetreatFee(Object retreatFee) {
        this.retreatFee = retreatFee;
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
