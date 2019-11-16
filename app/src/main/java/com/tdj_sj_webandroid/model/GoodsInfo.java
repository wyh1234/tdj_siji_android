package com.tdj_sj_webandroid.model;

import java.util.List;

public class GoodsInfo {

    /**
     * total : 267
     * goodsList : [{"addressEntityId":18810,"amountRefund":0,"avgPrice":3.7,"avgUnit":"袋","carNumber":"","cityName":"","commission":5.55,"createDate":"2019-11-15 22:48:08.0","customerLineCode":70,"customerName":"江西汤一绝","deliveredTime":"7:00","discountAvgPrice":3.7,"driverName":"","driverTel":"","expectDeliveredDate":"2019-11-16 00:00:00.0","expectDeliveredEarliestTime":"7:00","expectDeliveredLatestTime":"8:00","foregift":0,"itemId":13605903,"itemStatus":5,"lastName":"江西汤一绝","level2Unit":"袋","level2Value":10,"level3Unit":"克","level3Value":250,"levelType":3,"lineCode":"G91区65","lineName":"江岸区二七路线","name":"湘西外婆菜(食得迷外婆菜)","orderAmount":111,"orderId":11463216,"orderNo":"66011184786845491205","orderQty":3,"orderStatus":"trade_success","packageName":"","price":37,"productCriteria":"1","productId":65304,"productNo":"9191115498958","qtyRefund":0,"shippingLineId":"19337","site":-1,"specId":111485,"stationId":0,"stationName":"汉口北配送中心","storeContactName":"彭成玲","storeName":"成玲冻品批发商行","storeTel":"15623873687","truckTime":"1","unit":"件","productImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1906071728073270d996.jpg","driver_id":"371","driver_name":null,"driver_tel":null,"customer_id":21271,"checkTime":"","checkUserId":-1,"checkStatus":-1,"check_customer_id":"-1","check_customer_code":"","driver_name_code":"商辉","inDriverId":"124","inDriverName":"商辉","inDriverTel":"15827239920","status":null}]
     */

    private int total;
    private int tab1;
    private int tab2;
    private int tab3;
    private int tab4;
    private List<GoodsListBean> goodsList;

    public int getTab1() {
        return tab1;
    }

    public void setTab1(int tab1) {
        this.tab1 = tab1;
    }

    public int getTab2() {
        return tab2;
    }

    public void setTab2(int tab2) {
        this.tab2 = tab2;
    }

    public int getTab3() {
        return tab3;
    }

    public void setTab3(int tab3) {
        this.tab3 = tab3;
    }

    public int getTab4() {
        return tab4;
    }

    public void setTab4(int tab4) {
        this.tab4 = tab4;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean {
        /**
         * addressEntityId : 18810
         * amountRefund : 0
         * avgPrice : 3.7
         * avgUnit : 袋
         * carNumber :
         * cityName :
         * commission : 5.55
         * createDate : 2019-11-15 22:48:08.0
         * customerLineCode : 70
         * customerName : 江西汤一绝
         * deliveredTime : 7:00
         * discountAvgPrice : 3.7
         * driverName :
         * driverTel :
         * expectDeliveredDate : 2019-11-16 00:00:00.0
         * expectDeliveredEarliestTime : 7:00
         * expectDeliveredLatestTime : 8:00
         * foregift : 0
         * itemId : 13605903
         * itemStatus : 5
         * lastName : 江西汤一绝
         * level2Unit : 袋
         * level2Value : 10
         * level3Unit : 克
         * level3Value : 250
         * levelType : 3
         * lineCode : G91区65
         * lineName : 江岸区二七路线
         * name : 湘西外婆菜(食得迷外婆菜)
         * orderAmount : 111.0
         * orderId : 11463216
         * orderNo : 66011184786845491205
         * orderQty : 3
         * orderStatus : trade_success
         * packageName :
         * price : 37.0
         * productCriteria : 1
         * productId : 65304
         * productNo : 9191115498958
         * qtyRefund : 0.0
         * shippingLineId : 19337
         * site : -1
         * specId : 111485
         * stationId : 0
         * stationName : 汉口北配送中心
         * storeContactName : 彭成玲
         * storeName : 成玲冻品批发商行
         * storeTel : 15623873687
         * truckTime : 1
         * unit : 件
         * productImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1906071728073270d996.jpg
         * driver_id : 371
         * driver_name : null
         * driver_tel : null
         * customer_id : 21271
         * checkTime :
         * checkUserId : -1
         * checkStatus : -1
         * check_customer_id : -1
         * check_customer_code :
         * driver_name_code : 商辉
         * inDriverId : 124
         * inDriverName : 商辉
         * inDriverTel : 15827239920
         * status : null
         */

        private int addressEntityId;
        private int amountRefund;
        private double avgPrice;
        private String avgUnit;
        private String carNumber;
        private String cityName;
        private double commission;
        private String createDate;
        private int customerLineCode;
        private String customerName;
        private String deliveredTime;
        private double discountAvgPrice;
        private String driverName;
        private String driverTel;
        private String expectDeliveredDate;
        private String expectDeliveredEarliestTime;
        private String expectDeliveredLatestTime;
        private int foregift;
        private int itemId;
        private int itemStatus;
        private String lastName;
        private String level2Unit;
        private int level2Value;
        private String level3Unit;
        private int level3Value;
        private int levelType;
        private String lineCode;
        private String lineName;
        private String name;
        private double orderAmount;
        private int orderId;
        private String orderNo;
        private int orderQty;
        private String orderStatus;
        private String packageName;
        private double price;
        private String productCriteria;
        private int productId;
        private String productNo;
        private double qtyRefund;
        private String shippingLineId;
        private int site;
        private int specId;
        private int stationId;
        private String stationName;
        private String storeContactName;
        private String storeName;
        private String storeTel;
        private String truckTime;
        private String unit;
        private String productImage;
        private String driver_id;
        private Object driver_name;
        private Object driver_tel;
        private int customer_id;
        private String checkTime;
        private int checkUserId;
        private int checkStatus;
        private String check_customer_id;
        private String check_customer_code;
        private String driver_name_code;
        private String inDriverId;
        private String inDriverName;
        private String inDriverTel;
        private Object status;

        public int getAddressEntityId() {
            return addressEntityId;
        }

        public void setAddressEntityId(int addressEntityId) {
            this.addressEntityId = addressEntityId;
        }

        public int getAmountRefund() {
            return amountRefund;
        }

        public void setAmountRefund(int amountRefund) {
            this.amountRefund = amountRefund;
        }

        public double getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(double avgPrice) {
            this.avgPrice = avgPrice;
        }

        public String getAvgUnit() {
            return avgUnit;
        }

        public void setAvgUnit(String avgUnit) {
            this.avgUnit = avgUnit;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public double getCommission() {
            return commission;
        }

        public void setCommission(double commission) {
            this.commission = commission;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getCustomerLineCode() {
            return customerLineCode;
        }

        public void setCustomerLineCode(int customerLineCode) {
            this.customerLineCode = customerLineCode;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getDeliveredTime() {
            return deliveredTime;
        }

        public void setDeliveredTime(String deliveredTime) {
            this.deliveredTime = deliveredTime;
        }

        public double getDiscountAvgPrice() {
            return discountAvgPrice;
        }

        public void setDiscountAvgPrice(double discountAvgPrice) {
            this.discountAvgPrice = discountAvgPrice;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverTel() {
            return driverTel;
        }

        public void setDriverTel(String driverTel) {
            this.driverTel = driverTel;
        }

        public String getExpectDeliveredDate() {
            return expectDeliveredDate;
        }

        public void setExpectDeliveredDate(String expectDeliveredDate) {
            this.expectDeliveredDate = expectDeliveredDate;
        }

        public String getExpectDeliveredEarliestTime() {
            return expectDeliveredEarliestTime;
        }

        public void setExpectDeliveredEarliestTime(String expectDeliveredEarliestTime) {
            this.expectDeliveredEarliestTime = expectDeliveredEarliestTime;
        }

        public String getExpectDeliveredLatestTime() {
            return expectDeliveredLatestTime;
        }

        public void setExpectDeliveredLatestTime(String expectDeliveredLatestTime) {
            this.expectDeliveredLatestTime = expectDeliveredLatestTime;
        }

        public int getForegift() {
            return foregift;
        }

        public void setForegift(int foregift) {
            this.foregift = foregift;
        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public int getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(int itemStatus) {
            this.itemStatus = itemStatus;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getLevel2Unit() {
            return level2Unit;
        }

        public void setLevel2Unit(String level2Unit) {
            this.level2Unit = level2Unit;
        }

        public int getLevel2Value() {
            return level2Value;
        }

        public void setLevel2Value(int level2Value) {
            this.level2Value = level2Value;
        }

        public String getLevel3Unit() {
            return level3Unit;
        }

        public void setLevel3Unit(String level3Unit) {
            this.level3Unit = level3Unit;
        }

        public int getLevel3Value() {
            return level3Value;
        }

        public void setLevel3Value(int level3Value) {
            this.level3Value = level3Value;
        }

        public int getLevelType() {
            return levelType;
        }

        public void setLevelType(int levelType) {
            this.levelType = levelType;
        }

        public String getLineCode() {
            return lineCode;
        }

        public void setLineCode(String lineCode) {
            this.lineCode = lineCode;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getOrderQty() {
            return orderQty;
        }

        public void setOrderQty(int orderQty) {
            this.orderQty = orderQty;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getProductCriteria() {
            return productCriteria;
        }

        public void setProductCriteria(String productCriteria) {
            this.productCriteria = productCriteria;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public double getQtyRefund() {
            return qtyRefund;
        }

        public void setQtyRefund(double qtyRefund) {
            this.qtyRefund = qtyRefund;
        }

        public String getShippingLineId() {
            return shippingLineId;
        }

        public void setShippingLineId(String shippingLineId) {
            this.shippingLineId = shippingLineId;
        }

        public int getSite() {
            return site;
        }

        public void setSite(int site) {
            this.site = site;
        }

        public int getSpecId() {
            return specId;
        }

        public void setSpecId(int specId) {
            this.specId = specId;
        }

        public int getStationId() {
            return stationId;
        }

        public void setStationId(int stationId) {
            this.stationId = stationId;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getStoreContactName() {
            return storeContactName;
        }

        public void setStoreContactName(String storeContactName) {
            this.storeContactName = storeContactName;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreTel() {
            return storeTel;
        }

        public void setStoreTel(String storeTel) {
            this.storeTel = storeTel;
        }

        public String getTruckTime() {
            return truckTime;
        }

        public void setTruckTime(String truckTime) {
            this.truckTime = truckTime;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public Object getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(Object driver_name) {
            this.driver_name = driver_name;
        }

        public Object getDriver_tel() {
            return driver_tel;
        }

        public void setDriver_tel(Object driver_tel) {
            this.driver_tel = driver_tel;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public int getCheckUserId() {
            return checkUserId;
        }

        public void setCheckUserId(int checkUserId) {
            this.checkUserId = checkUserId;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getCheck_customer_id() {
            return check_customer_id;
        }

        public void setCheck_customer_id(String check_customer_id) {
            this.check_customer_id = check_customer_id;
        }

        public String getCheck_customer_code() {
            return check_customer_code;
        }

        public void setCheck_customer_code(String check_customer_code) {
            this.check_customer_code = check_customer_code;
        }

        public String getDriver_name_code() {
            return driver_name_code;
        }

        public void setDriver_name_code(String driver_name_code) {
            this.driver_name_code = driver_name_code;
        }

        public String getInDriverId() {
            return inDriverId;
        }

        public void setInDriverId(String inDriverId) {
            this.inDriverId = inDriverId;
        }

        public String getInDriverName() {
            return inDriverName;
        }

        public void setInDriverName(String inDriverName) {
            this.inDriverName = inDriverName;
        }

        public String getInDriverTel() {
            return inDriverTel;
        }

        public void setInDriverTel(String inDriverTel) {
            this.inDriverTel = inDriverTel;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }
    }
}
