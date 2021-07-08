package com.tdj_sj_webandroid.model;

import java.util.List;

public class StockListBean {

    private List<String> inStockData;
    private Integer qty;
    private String isMultiple;


    public List<String> getInStockData() {
        return inStockData;
    }

    public void setInStockData(List<String> inStockData) {
        this.inStockData = inStockData;
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
