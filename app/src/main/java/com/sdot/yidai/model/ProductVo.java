package com.sdot.yidai.model;


/**
 * Created by Administrator on 2017/12/13.
 */

public class ProductVo {

    public int getItemType() {
        return itemType;
    }

    /**
     * state : ADOPT
     * reamin : null
     * total : null
     * apply : true
     * cardId : null
     */

    public static final int PASS = 1;
    public static final int UNPASS = 2;

    private String cardSort;
    private String state;
    private String reamin;
    private String total;
    private boolean apply;
    private String cardId;
    private int itemType;

    public ProductVo(String cardSort, String state, String reamin, String total, boolean apply, String cardId, int itemType) {
        this.cardSort = cardSort;
        this.state = state;
        this.reamin = reamin;
        this.total = total;
        this.apply = apply;
        this.cardId = cardId;
        this.itemType = itemType;
    }

    public ProductVo(int itemType) {
        this.itemType = itemType;
    }

    public ProductVo(String cardSort, String state, String reamin, String total, boolean apply, String cardId) {
        this.cardSort = cardSort;
        this.state = state;
        this.reamin = reamin;
        this.total = total;
        this.apply = apply;
        this.cardId = cardId;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getCardSort() {
        return cardSort;
    }

    public void setCardSort(String cardSort) {
        this.cardSort = cardSort;
    }

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
