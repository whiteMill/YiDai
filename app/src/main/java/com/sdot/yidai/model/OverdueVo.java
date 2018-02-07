package com.sdot.yidai.model;

/**
 * Created by Administrator on 2018/1/10.
 */

public class OverdueVo {

    /**
     * id : 3
     * historyAmount : 1000
     * overdueAmount : 1000
     * penalSum : 250
     * overdueDays : 5
     * createdAt : 2018-01-10 12:10:55
     * finishAt : null
     * status : 0
     * _links : {"self":{"href":"http://test.edairisk.com/rest/loanoverdues/3"},"loanoverdue":{"href":"http://test.edairisk.com/rest/loanoverdues/3"},"creditrepayment":{"href":"http://test.edairisk.com/rest/loanoverdues/3/creditrepayment"},"loan":{"href":"http://test.edairisk.com/rest/loanoverdues/3/loan"}}
     */

    private String id;
    private String historyAmount;
    private String overdueAmount;
    private String penalSum;
    private String overdueDays;
    private String createdAt;
    private Object finishAt;
    private String status;
    private Object _links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHistoryAmount() {
        return historyAmount;
    }

    public void setHistoryAmount(String historyAmount) {
        this.historyAmount = historyAmount;
    }

    public String getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public String getPenalSum() {
        return penalSum;
    }

    public void setPenalSum(String penalSum) {
        this.penalSum = penalSum;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(Object finishAt) {
        this.finishAt = finishAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object get_links() {
        return _links;
    }

    public void set_links(Object _links) {
        this._links = _links;
    }
}
