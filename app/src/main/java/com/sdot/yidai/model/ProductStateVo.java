package com.sdot.yidai.model;

import java.io.Serializable;

/**
 * Created by windMill on 2017/11/28.
 */

public class ProductStateVo implements Serializable{

    /**
     * total : 10000
     * remain : 10000
     * consume : 0
     * sjsh : {"state":"ENABLED","reamin":10000,"total":10000,"apply":true,"cardId":10}
     * wdxyd : {"state":null,"reamin":null,"total":null,"apply":false,"cardId":null}
     * rzzl : {"state":null,"reamin":null,"total":null,"apply":false,"cardId":null}
     */

 /*   private String total;
    private String remain;
    private String consume;*/
    private SjshVo sjsh;
    private WdxydVo wdxyd;
    private RzzlVo rzzl;
    private MdbtVo mdbt;
    private CompanyMDBT companyMDBT;

    public CompanyMDBT getCompanyMDBT() {
        return companyMDBT;
    }

    public void setCompanyMDBT(CompanyMDBT companyMDBT) {
        this.companyMDBT = companyMDBT;
    }

    public MdbtVo getMdbt() {
        return mdbt;
    }

    public void setMdbt(MdbtVo mdbt) {
        this.mdbt = mdbt;
    }

    public SjshVo getSjsh() {
        return sjsh;
    }

    public void setSjsh(SjshVo sjsh) {
        this.sjsh = sjsh;
    }

    public WdxydVo getWdxyd() {
        return wdxyd;
    }

    public void setWdxyd(WdxydVo wdxyd) {
        this.wdxyd = wdxyd;
    }

    public RzzlVo getRzzl() {
        return rzzl;
    }

    public void setRzzl(RzzlVo rzzl) {
        this.rzzl = rzzl;
    }

    public static class SjshVo implements Serializable{
        /**
         * state : ENABLED
         * reamin : 10000
         * total : 10000
         * apply : true
         * cardId : 10
         */

        private String state;
        private String reamin;
        private String total;
        private boolean apply;
        private String cardId;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getReamin() {
            return reamin;
        }

        public void setReamin(String reamin) {
            this.reamin = reamin;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public boolean isApply() {
            return apply;
        }

        public void setApply(boolean apply) {
            this.apply = apply;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }
    }

    public static class WdxydVo implements Serializable{
        /**
         * state : null
         * reamin : null
         * total : null
         * apply : false
         * cardId : null
         */

        private String  state;
        private String reamin;
        private String total;
        private boolean apply;
        private String cardId;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getReamin() {
            return reamin;
        }

        public void setReamin(String reamin) {
            this.reamin = reamin;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public boolean isApply() {
            return apply;
        }

        public void setApply(boolean apply) {
            this.apply = apply;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }
    }

    public static class RzzlVo implements Serializable{
        /**
         * state : null
         * reamin : null
         * total : null
         * apply : false
         * cardId : null
         */

        private String state;
        private String reamin;
        private String total;
        private boolean apply;
        private String cardId;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getReamin() {
            return reamin;
        }

        public void setReamin(String reamin) {
            this.reamin = reamin;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public boolean isApply() {
            return apply;
        }

        public void setApply(boolean apply) {
            this.apply = apply;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }
    }

    public static class MdbtVo implements Serializable{
        /**
         * state : null
         * reamin : null
         * total : null
         * apply : false
         * cardId : null
         */

        private String state;
        private String reamin;
        private String total;
        private boolean apply;
        private String cardId;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getReamin() {
            return reamin;
        }

        public void setReamin(String reamin) {
            this.reamin = reamin;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public boolean isApply() {
            return apply;
        }

        public void setApply(boolean apply) {
            this.apply = apply;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }
    }

    public static class CompanyMDBT implements Serializable{
        private String state;
        private String reamin;
        private String total;
        private boolean apply;
        private String cardId;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getReamin() {
            return reamin;
        }

        public void setReamin(String reamin) {
            this.reamin = reamin;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public boolean isApply() {
            return apply;
        }

        public void setApply(boolean apply) {
            this.apply = apply;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }
    }
}
