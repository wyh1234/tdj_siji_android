package com.tdj_sj_webandroid.model;

import java.util.List;

public class ScannerHistory {


    /**
     * startTime : 2019-12-29 01:01:01
     * endTime : 2020-01-01 01:01:01
     * ps : 20
     * pn : 1
     * driverId : 487
     * createStartTime : 2019-12-29 01:01:01
     * createEndTime : 2020-01-01 01:01:01
     * total : 9
     * items : [{"entityId":null,"qrCodeId":null,"type":null,"price":0.04,"createTime":null,"itemId":null,"inTime":null,"settingId":null,"driverId":null,"storeId":2716,"status":null,"storeName":"测试环境店铺","storePhone":null,"storeUser":null,"storeNick":null,"scannerHouse":null,"updateTime":null,"productName":null,"productId":null,"productPrice":null,"productUnit":null,"productUnitType":null,"lineCode":null,"customerLineCode":null,"customerId":null,"rowSize":1,"orderItem":null,"driverName":null,"driverPhone":null,"isFree":null,"scannerHouseId":null,"scannerHouseType":null,"websiteId":0,"note":null},{"entityId":null,"qrCodeId":null,"type":null,"price":0.15,"createTime":null,"itemId":null,"inTime":null,"settingId":null,"driverId":null,"storeId":2716,"status":null,"storeName":"测试环境店铺","storePhone":null,"storeUser":null,"storeNick":null,"scannerHouse":"南湖接货仓1","updateTime":null,"productName":null,"productId":null,"productPrice":null,"productUnit":null,"productUnitType":null,"lineCode":null,"customerLineCode":null,"customerId":null,"rowSize":1,"orderItem":null,"driverName":null,"driverPhone":null,"isFree":null,"scannerHouseId":null,"scannerHouseType":null,"websiteId":0,"note":null},{"entityId":null,"qrCodeId":null,"type":null,"price":0.04,"createTime":null,"itemId":null,"inTime":null,"settingId":null,"driverId":null,"storeId":2716,"status":null,"storeName":"测试环境店铺","storePhone":null,"storeUser":null,"storeNick":null,"scannerHouse":"接","updateTime":null,"productName":null,"productId":null,"productPrice":null,"productUnit":null,"productUnitType":null,"lineCode":null,"customerLineCode":null,"customerId":null,"rowSize":1,"orderItem":null,"driverName":null,"driverPhone":null,"isFree":null,"scannerHouseId":null,"scannerHouseType":null,"websiteId":0,"note":null},{"entityId":null,"qrCodeId":null,"type":null,"price":4.44,"createTime":null,"itemId":null,"inTime":null,"settingId":null,"driverId":null,"storeId":2716,"status":null,"storeName":"测试环境店铺","storePhone":null,"storeUser":null,"storeNick":null,"scannerHouse":"武泰闸配送中心","updateTime":null,"productName":null,"productId":null,"productPrice":null,"productUnit":null,"productUnitType":null,"lineCode":null,"customerLineCode":null,"customerId":null,"rowSize":6,"orderItem":null,"driverName":null,"driverPhone":null,"isFree":null,"scannerHouseId":null,"scannerHouseType":null,"websiteId":0,"note":null}]
     */

    private String startTime;
    private String endTime;
    private String ps;
    private String pn;
    private int driverId;
    private String createStartTime;
    private String createEndTime;
    private int total;
    private int orderNum;
    private List<ItemsBean> items;

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * entityId : null
         * qrCodeId : null
         * type : null
         * price : 0.04
         * createTime : null
         * itemId : null
         * inTime : null
         * settingId : null
         * driverId : null
         * storeId : 2716
         * status : null
         * storeName : 测试环境店铺
         * storePhone : null
         * storeUser : null
         * storeNick : null
         * scannerHouse : null
         * updateTime : null
         * productName : null
         * productId : null
         * productPrice : null
         * productUnit : null
         * productUnitType : null
         * lineCode : null
         * customerLineCode : null
         * customerId : null
         * rowSize : 1
         * orderItem : null
         * driverName : null
         * driverPhone : null
         * isFree : null
         * scannerHouseId : null
         * scannerHouseType : null
         * websiteId : 0
         * note : null
         */

        private Object entityId;
        private Object qrCodeId;
        private Object type;
        private double price;
        private Object createTime;
        private Object itemId;
        private Object inTime;
        private Object settingId;
        private Object driverId;
        private int storeId;
        private Object status;
        private String storeName;
        private String storePhone;
        private Object storeUser;
        private Object storeNick;
        private String scannerHouse;
        private Object updateTime;
        private Object productName;
        private Object productId;
        private Object productPrice;
        private Object productUnit;
        private Object productUnitType;
        private Object lineCode;
        private Object customerLineCode;
        private Object customerId;
        private int rowSize;
        private Object orderItem;
        private Object driverName;
        private Object driverPhone;
        private Object isFree;
        private Object scannerHouseId;
        private Object scannerHouseType;
        private int websiteId;
        private Object note;

        public Object getEntityId() {
            return entityId;
        }

        public void setEntityId(Object entityId) {
            this.entityId = entityId;
        }

        public Object getQrCodeId() {
            return qrCodeId;
        }

        public void setQrCodeId(Object qrCodeId) {
            this.qrCodeId = qrCodeId;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getItemId() {
            return itemId;
        }

        public void setItemId(Object itemId) {
            this.itemId = itemId;
        }

        public Object getInTime() {
            return inTime;
        }

        public void setInTime(Object inTime) {
            this.inTime = inTime;
        }

        public Object getSettingId() {
            return settingId;
        }

        public void setSettingId(Object settingId) {
            this.settingId = settingId;
        }

        public Object getDriverId() {
            return driverId;
        }

        public void setDriverId(Object driverId) {
            this.driverId = driverId;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStorePhone() {
            return storePhone;
        }

        public void setStorePhone(String storePhone) {
            this.storePhone = storePhone;
        }

        public Object getStoreUser() {
            return storeUser;
        }

        public void setStoreUser(Object storeUser) {
            this.storeUser = storeUser;
        }

        public Object getStoreNick() {
            return storeNick;
        }

        public void setStoreNick(Object storeNick) {
            this.storeNick = storeNick;
        }

        public String getScannerHouse() {
            return scannerHouse;
        }

        public void setScannerHouse(String scannerHouse) {
            this.scannerHouse = scannerHouse;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getProductName() {
            return productName;
        }

        public void setProductName(Object productName) {
            this.productName = productName;
        }

        public Object getProductId() {
            return productId;
        }

        public void setProductId(Object productId) {
            this.productId = productId;
        }

        public Object getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(Object productPrice) {
            this.productPrice = productPrice;
        }

        public Object getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(Object productUnit) {
            this.productUnit = productUnit;
        }

        public Object getProductUnitType() {
            return productUnitType;
        }

        public void setProductUnitType(Object productUnitType) {
            this.productUnitType = productUnitType;
        }

        public Object getLineCode() {
            return lineCode;
        }

        public void setLineCode(Object lineCode) {
            this.lineCode = lineCode;
        }

        public Object getCustomerLineCode() {
            return customerLineCode;
        }

        public void setCustomerLineCode(Object customerLineCode) {
            this.customerLineCode = customerLineCode;
        }

        public Object getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Object customerId) {
            this.customerId = customerId;
        }

        public int getRowSize() {
            return rowSize;
        }

        public void setRowSize(int rowSize) {
            this.rowSize = rowSize;
        }

        public Object getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(Object orderItem) {
            this.orderItem = orderItem;
        }

        public Object getDriverName() {
            return driverName;
        }

        public void setDriverName(Object driverName) {
            this.driverName = driverName;
        }

        public Object getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(Object driverPhone) {
            this.driverPhone = driverPhone;
        }

        public Object getIsFree() {
            return isFree;
        }

        public void setIsFree(Object isFree) {
            this.isFree = isFree;
        }

        public Object getScannerHouseId() {
            return scannerHouseId;
        }

        public void setScannerHouseId(Object scannerHouseId) {
            this.scannerHouseId = scannerHouseId;
        }

        public Object getScannerHouseType() {
            return scannerHouseType;
        }

        public void setScannerHouseType(Object scannerHouseType) {
            this.scannerHouseType = scannerHouseType;
        }

        public int getWebsiteId() {
            return websiteId;
        }

        public void setWebsiteId(int websiteId) {
            this.websiteId = websiteId;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }
    }
}
