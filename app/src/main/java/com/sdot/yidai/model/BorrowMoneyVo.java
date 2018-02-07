package com.sdot.yidai.model;

/**
 * Created by Administrator on 2017/11/8.
 */

public class BorrowMoneyVo {
    private String no;
    private String borrowMoney;
    private String lastMoney;
    private String timeLine;

    public BorrowMoneyVo(String no, String borrowMoney, String lastMoney, String timeLine) {
        this.no = no;
        this.borrowMoney = borrowMoney;
        this.lastMoney = lastMoney;
        this.timeLine = timeLine;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(String borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public String getLastMoney() {
        return lastMoney;
    }

    public void setLastMoney(String lastMoney) {
        this.lastMoney = lastMoney;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }
}
