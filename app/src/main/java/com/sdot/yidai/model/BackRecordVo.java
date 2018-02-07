package com.sdot.yidai.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class BackRecordVo implements Serializable{

    /**
     * id : C57C8A5622DC43BCAA3B751E10B149BC
     * loanUuid : 8580F7F514D9490EB0C3383C9470D554
     * mobile : 15356799999
     * payAmount : 1015.0
     * remainAmount : 0.0
     * payDate : 20171116153700057
     * payAccount : 666655698745125910
     * payPlans : [{"planCorpus":1000,"planInterest":15,"penatly":0,"interestStart":"20171116","interestEnd":"20171215","isOverdue":0}]
     * jgCode : 0039
     */

    private String id;
    private String loanUuid;
    private String mobile;
    private double payAmount;
    private double remainAmount;
    private String payDate;
    private String payAccount;
    private String jgCode;
    private List<PayPlansBean> payPlans;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanUuid() {
        return loanUuid;
    }

    public void setLoanUuid(String loanUuid) {
        this.loanUuid = loanUuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(double remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
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
         * planCorpus : 1000
         * planInterest : 15
         * penatly : 0
         * interestStart : 20171116
         * interestEnd : 20171215
         * isOverdue : 0
         */

        private String planCorpus;
        private String planInterest;
        private String penatly;
        private String interestStart;
        private String interestEnd;
        private String isOverdue;

        public String getPlanCorpus() {
            return planCorpus;
        }

        public void setPlanCorpus(String planCorpus) {
            this.planCorpus = planCorpus;
        }

        public String getPlanInterest() {
            return planInterest;
        }

        public void setPlanInterest(String planInterest) {
            this.planInterest = planInterest;
        }

        public String getPenatly() {
            return penatly;
        }

        public void setPenatly(String penatly) {
            this.penatly = penatly;
        }

        public String getInterestStart() {
            return interestStart;
        }

        public void setInterestStart(String interestStart) {
            this.interestStart = interestStart;
        }

        public String getInterestEnd() {
            return interestEnd;
        }

        public void setInterestEnd(String interestEnd) {
            this.interestEnd = interestEnd;
        }

        public String getIsOverdue() {
            return isOverdue;
        }

        public void setIsOverdue(String isOverdue) {
            this.isOverdue = isOverdue;
        }
    }
}
