package com.tdj_sj_webandroid.model;

import java.util.List;

public class CheckListBean {

    private List<String> checkList;
    private Integer qty;
    private String isMultiple;


    public List<String> getInStockData() {
        return checkList;
    }

    public void setInStockData(List<String> inStockData) {
        this.checkList = inStockData;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getIsMultiple() {
        return isMultiple;
    }

    public void setIsMultiple(String isMultiple) {
        this.isMultiple = isMultiple;
    }
}
