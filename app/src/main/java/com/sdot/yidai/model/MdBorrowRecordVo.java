package com.sdot.yidai.model;


import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/20.
 */

public class MdBorrowRecordVo implements Serializable{

            /**
             * id : 116
             * label : null
             * createdAt : 2017-12-21T02:01:00.000+0000
             * lastModifiedAt : 2017-12-21T02:01:00.000+0000
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
             * repaymentMethodCode : BEFORE_INTEREST_AFTER_PRINCIPAL
             * useDate : 2017-12-28 10:01:03
             * applyDate : 2017-12-21 10:01:03
             * orderNo : LBT17122110010066
             * principal : 5000
             * totalInterest : 262.5
             * overdue : 0
             * remainAmount : 0
             * periodCode : DAY
             * periodNumber : 3
             * debtorInterest : null
             * storeInterest : null
             * creditorInterest : null
             * creditorRepaymentDay : null
             * debtorRepaymentDay : null
             * debtorExtentedRepaymentDays : null
             * description : null
             * comment : null
             * information : null
             * creditorLoanSn : null
             * serviceUserName : null
             * loanSn : null
             * debtorName : 倪咯
             * loanType : ORDERMDBT
             * debtorRealityAmount : null
             * oneTimeFee : null
             * retreatFee : null
             * _links : {"self":{"href":"http://test.edairisk.com/rest/loans/116"},"loan":{"href":"http://test.edairisk.com/rest/loans/116"},"debtorReceiveBankcard":{"href":"http://test.edairisk.com/rest/loans/116/debtorReceiveBankcard"},"workflow":{"href":"http://test.edairisk.com/rest/loans/116/workflow"},"files":{"href":"http://test.edairisk.com/rest/loans/116/files"},"logs":{"href":"http://test.edairisk.com/rest/loans/116/logs"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/loans/116/createdByDepartment"},"lastAct":{"href":"http://test.edairisk.com/rest/loans/116/lastAct"},"creditrepayplans":{"href":"http://test.edairisk.com/rest/loans/116/creditrepayplans"},"creditcard":{"href":"http://test.edairisk.com/rest/loans/116/creditcard"},"debtorUser":{"href":"http://test.edairisk.com/rest/loans/116/debtorUser"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/loans/116/lastModifiedBy"},"state":{"href":"http://test.edairisk.com/rest/loans/116/state"},"debtorEnterprise":{"href":"http://test.edairisk.com/rest/loans/116/debtorEnterprise"},"ordercdd":{"href":"http://test.edairisk.com/rest/loans/116/ordercdd"},"serviceUser":{"href":"http://test.edairisk.com/rest/loans/116/serviceUser"},"createdBy":{"href":"http://test.edairisk.com/rest/loans/116/createdBy"},"debtorPerson":{"href":"http://test.edairisk.com/rest/loans/116/debtorPerson"},"punishinterestrule":{"href":"http://test.edairisk.com/rest/loans/116/punishinterestrule"}}
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
            private String useDate;
            private String applyDate;
            private String orderNo;
            private String principal;
            private String totalInterest;
            private String overdue;
            private String remainAmount;
            private String periodCode;
            private String periodNumber;
            private Object debtorInterest;
            private Object storeInterest;
            private Object creditorInterest;
            private Object creditorRepaymentDay;
            private Object debtorRepaymentDay;
            private Object debtorExtentedRepaymentDays;
            private Object description;
            private Object comment;
            private Object information;
            private Object creditorLoanSn;
            private Object serviceUserName;
            private Object loanSn;
            private String debtorName;
            private String loanType;
            private Object debtorRealityAmount;
            private Object oneTimeFee;
            private Object retreatFee;
            private Object _links;
            private Object currentUserCanActList;
            private Object wechatFiles;
            private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getPeriodCode() {
        return periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public String getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(String periodNumber) {
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

    public Object getInformation() {
        return information;
    }

    public void setInformation(Object information) {
        this.information = information;
    }

    public Object getCreditorLoanSn() {
        return creditorLoanSn;
    }

    public void setCreditorLoanSn(Object creditorLoanSn) {
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

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
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
