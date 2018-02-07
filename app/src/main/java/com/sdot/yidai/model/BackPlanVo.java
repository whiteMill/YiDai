package com.sdot.yidai.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/21.
 */

public class BackPlanVo implements Serializable{
            /**
             * id : 16
             * label : null
             * createdAt : 2017-12-21T07:34:48.000+0000
             * lastModifiedAt : 2017-12-21T07:34:48.000+0000
             * act : null
             * notice : null
             * currentUserCanActList : [{"id":2,"label":"读取","actCode":"read","iconClass":null,"btnClass":null,"sort":20,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null}]
             * fileObject : null
             * wechatFiles : []
             * stateActList : {}
             * fileCategoryTree : null
             * filePackage : null
             * topcategory : -
             * subcategory : -
             * principal : 0.0
             * term : 1
             * interest : 87.5
             * fees : 87.5
             * isOverdue : null
             * overdueDays : 0
             * overdueAmount : 0.0
             * accountSettle : null
             * leftAmount : null
             * debtorRepaymentDate : 2018-01-26 10:01:03
             * debtorExtentedRepaymentDate : null
             * creditorRepaymentDate : null
             * information : null
             * orderSn : null
             * loanSn : LBT17122110010066
             * planSn : 20171221153447582-1
             * debtorMobile : null
             * debtorName : 倪咯
             * _links : {"self":{"href":"http://test.edairisk.com/rest/creditrepayplans/16"},"creditrepayplan":{"href":"http://test.edairisk.com/rest/creditrepayplans/16"},"serviceUser":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/serviceUser"},"state":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/state"},"files":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/files"},"creditrepayment":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/creditrepayment"},"logs":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/logs"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/createdByDepartment"},"loan":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/loan"},"creditcard":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/creditcard"},"lastAct":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/lastAct"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/lastModifiedBy"},"workflow":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/workflow"},"createdBy":{"href":"http://test.edairisk.com/rest/creditrepayplans/16/createdBy"}}
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
            private String principal;
            private String term;
            private String interest;
            private String fees;
            private Object isOverdue;
            private String overdueDays;
            private String overdueAmount;
            private Object accountSettle;
            private String leftAmount;
            private String debtorRepaymentDate;
            private Object debtorExtentedRepaymentDate;
            private Object creditorRepaymentDate;
            private Object information;
            private Object orderSn;
            private String loanSn;
            private String planSn;
            private Object debtorMobile;
            private String debtorName;
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

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public Object getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Object isOverdue) {
        this.isOverdue = isOverdue;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Object getAccountSettle() {
        return accountSettle;
    }

    public void setAccountSettle(Object accountSettle) {
        this.accountSettle = accountSettle;
    }

    public String getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(String leftAmount) {
        this.leftAmount = leftAmount;
    }

    public String getDebtorRepaymentDate() {
        return debtorRepaymentDate;
    }

    public void setDebtorRepaymentDate(String debtorRepaymentDate) {
        this.debtorRepaymentDate = debtorRepaymentDate;
    }

    public Object getDebtorExtentedRepaymentDate() {
        return debtorExtentedRepaymentDate;
    }

    public void setDebtorExtentedRepaymentDate(Object debtorExtentedRepaymentDate) {
        this.debtorExtentedRepaymentDate = debtorExtentedRepaymentDate;
    }

    public Object getCreditorRepaymentDate() {
        return creditorRepaymentDate;
    }

    public void setCreditorRepaymentDate(Object creditorRepaymentDate) {
        this.creditorRepaymentDate = creditorRepaymentDate;
    }

    public Object getInformation() {
        return information;
    }

    public void setInformation(Object information) {
        this.information = information;
    }

    public Object getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Object orderSn) {
        this.orderSn = orderSn;
    }

    public String getLoanSn() {
        return loanSn;
    }

    public void setLoanSn(String loanSn) {
        this.loanSn = loanSn;
    }

    public String getPlanSn() {
        return planSn;
    }

    public void setPlanSn(String planSn) {
        this.planSn = planSn;
    }

    public Object getDebtorMobile() {
        return debtorMobile;
    }

    public void setDebtorMobile(Object debtorMobile) {
        this.debtorMobile = debtorMobile;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
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
