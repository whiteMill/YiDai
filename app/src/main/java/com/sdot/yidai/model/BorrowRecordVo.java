package com.sdot.yidai.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class BorrowRecordVo implements Serializable{

    /**
     * id : 5FEC19917C4C4F3AA37CBE3D1D91AB87
     * mobile : 15356799999
     * loanStartDate : 20171116
     * loanEndDate : 20171216
     * loanAmount : 1000
     * payType : 1
     * payPlans : [{"term":1,"planCorpus":1000,"planLoanDate":"20171216"}]
     * accountType : 2
     * loanAccount : 666655698745125910
     * accountName : 大风
     * accountBank : 光大银行
     * jgCode : 0039
     */

    private String levelId;
    private String id;
    private String mobile;
    private String loanStartDate;
    private String loanEndDate;
    private String loanAmount;
    private String payType;
    private String accountType;
    private String loanAccount;
    private String accountName;
    private String accountBank;
    private String jgCode;
    private List<PayPlansBean> payPlans;

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(String loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public String getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(String loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getJgCode() {
        return jgCode;
    }

    public void setJgCode(String jgCode) {
        this.jgCode = jgCode;
    }

    public List<PayPlansBean> getPayPlans() {
        return payPlans;
    }

    public void setPayPlans(List<PayPlansBean> payPlans) {
        this.payPlans = payPlans;
    }

    public static class PayPlansBean implements Serializable{
        /**
         * term : 1
         * planCorpus : 1000
         * planLoanDate : 20171216
         */

        private String term;
        private String planCorpus;
        private String planLoanDate;

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getPlanCorpus() {
            return planCorpus;
        }

        public void setPlanCorpus(String planCorpus) {
            this.planCorpus = planCorpus;
        }

        public String getPlanLoanDate() {
            return planLoanDate;
        }

        public void setPlanLoanDate(String planLoanDate) {
            this.planLoanDate = planLoanDate;
        }
    }
}
