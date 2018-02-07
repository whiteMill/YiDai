package com.sdot.yidai.model;

/**
 * Created by Administrator on 2018/1/9.
 */

public class ExceRecordVo {

    /**
     * id : 10
     * createDate : 2018-01-09 14:46:01
     * mobile : 17826811104
     * loanAmount : 123123
     * passDate : 2018-01-09T06:46:01.000+0000
     * _links : {"self":{"href":"http://test.edairisk.com/rest/approveLoans/10"},"approveLoan":{"href":"http://test.edairisk.com/rest/approveLoans/10"},"approveLoanState":{"href":"http://test.edairisk.com/rest/approveLoans/10/approveLoanState"},"person":{"href":"http://test.edairisk.com/rest/approveLoans/10/person"}}
     */

    private String id;
    private String createDate;
    private String mobile;
    private String loanAmount;
    private String passDate;
    private String state;
    private Object _links;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPassDate() {
        return passDate;
    }

    public void setPassDate(String passDate) {
        this.passDate = passDate;
    }

    public Object get_links() {
        return _links;
    }

    public void set_links(Object _links) {
        this._links = _links;
    }
}
