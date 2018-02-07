package com.sdot.yidai.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/15.
 */

public class CreditcardVo implements Serializable{

            /**
             * id : 8
             * label : null
             * createdAt : 2017-11-15T06:34:19.000+0000
             * lastModifiedAt : 2017-11-15T06:34:19.000+0000
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
             * creditcardIdentity : 15356799999
             * creditcardAddition : 9714966
             * creditGrant : 10000
             * creditBalance : 10000
             * creditFrozen : null
             * creditTemporary : null
             * creditTemporaryDate : null
             * description : null
             * sort : 104
             * expiryDate : null
             * allowDebtorLoan : null
             * allowLoanNumber : null
             * _links : {"self":{"href":"http://test.edairisk.com/rest/creditcards/8"},"creditcard":{"href":"http://test.edairisk.com/rest/creditcards/8"},"loans":{"href":"http://test.edairisk.com/rest/creditcards/8/loans"},"creditadjusts":{"href":"http://test.edairisk.com/rest/creditcards/8/creditadjusts"},"createdBy":{"href":"http://test.edairisk.com/rest/creditcards/8/createdBy"},"user":{"href":"http://test.edairisk.com/rest/creditcards/8/user"},"creditrepayments":{"href":"http://test.edairisk.com/rest/creditcards/8/creditrepayments"},"lastAct":{"href":"http://test.edairisk.com/rest/creditcards/8/lastAct"},"person":{"href":"http://test.edairisk.com/rest/creditcards/8/person"},"files":{"href":"http://test.edairisk.com/rest/creditcards/8/files"},"workflow":{"href":"http://test.edairisk.com/rest/creditcards/8/workflow"},"logs":{"href":"http://test.edairisk.com/rest/creditcards/8/logs"},"state":{"href":"http://test.edairisk.com/rest/creditcards/8/state"},"product":{"href":"http://test.edairisk.com/rest/creditcards/8/product"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/creditcards/8/createdByDepartment"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/creditcards/8/lastModifiedBy"},"enterprise":{"href":"http://test.edairisk.com/rest/creditcards/8/enterprise"}}
             */

            private int id;
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
            private String creditcardIdentity;
            private int creditcardAddition;
            private String creditGrant;
            private String creditBalance;
            private Object creditFrozen;
            private Object creditTemporary;
            private Object creditTemporaryDate;
            private Object description;
            private int sort;
            private Object expiryDate;
            private Object allowDebtorLoan;
            private Object allowLoanNumber;
            private Object _links;
            private Object currentUserCanActList;
            private Object wechatFiles;

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

    public String getCreditcardIdentity() {
        return creditcardIdentity;
    }

    public void setCreditcardIdentity(String creditcardIdentity) {
        this.creditcardIdentity = creditcardIdentity;
    }

    public int getCreditcardAddition() {
        return creditcardAddition;
    }

    public void setCreditcardAddition(int creditcardAddition) {
        this.creditcardAddition = creditcardAddition;
    }

    public String getCreditGrant() {
        return creditGrant;
    }

    public void setCreditGrant(String creditGrant) {
        this.creditGrant = creditGrant;
    }

    public String getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(String creditBalance) {
        this.creditBalance = creditBalance;
    }

    public Object getCreditFrozen() {
        return creditFrozen;
    }

    public void setCreditFrozen(Object creditFrozen) {
        this.creditFrozen = creditFrozen;
    }

    public Object getCreditTemporary() {
        return creditTemporary;
    }

    public void setCreditTemporary(Object creditTemporary) {
        this.creditTemporary = creditTemporary;
    }

    public Object getCreditTemporaryDate() {
        return creditTemporaryDate;
    }

    public void setCreditTemporaryDate(Object creditTemporaryDate) {
        this.creditTemporaryDate = creditTemporaryDate;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Object expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Object getAllowDebtorLoan() {
        return allowDebtorLoan;
    }

    public void setAllowDebtorLoan(Object allowDebtorLoan) {
        this.allowDebtorLoan = allowDebtorLoan;
    }

    public Object getAllowLoanNumber() {
        return allowLoanNumber;
    }

    public void setAllowLoanNumber(Object allowLoanNumber) {
        this.allowLoanNumber = allowLoanNumber;
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
