package com.tdj_sj_webandroid.model;

public class StorageManagement {


    /**
     * orderId : 6013699
     * payTime : 2019-07-27 11:22:38
     * shippHotelName : 测试001店铺
     * contactTel : 18727175309
     * level3Unit :
     * productName : 包菜
     * couponAmount : 0.0
     * productImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1906280438231596852b.jpg
     * levelType : 2
     * price : 2.7
     * avgUnit : 斤
     * sku : 9190727000023
     * level2Unit : 斤
     * specId : 78437
     * productId : 32220
     * productLicense :
     * nickName : 高山平包
     * stationShortName : 竹1
     * productDesc : 高山包菜
     * itemId : 7165706
     * unit : 袋
     * qty : 1
     * deliveredTime : 9:00
     * isForegift : 1
     * foregift : 0
     * marketPrice : 2.7
     * totalPrice : 2.7
     * avgPrice : 0.9
     * customerLineCode : 99
     * description :
     * lineName : 二汽路线1
     * orderStatus : wait_seller_send_goods
     * shippAddr : _紫贞街道长虹北路9号
     * shippName : 测试001
     * createAt : 2019-07-27 11:22:33
     * level2Value : 3
     * lineCode : B24区 5
     * itemStatus : 3
     * qtyRefunded : 0.0
     * stationName : 竹叶山
     * storeName : 雪英蔬菜商行
     * packageName :
     * driverNo : 212号
     * inStockDate : 2019-07-27 15:04:25
     * customerImg : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190709163807a6cbd49a.jpg
     * level3Value : 0
     * storeId : 363
     * hotelName : 测试001店铺
     * shippingLineId : 0
     * discountAvgPrice : 0
     * productCriteria : 1
     * shippTel : 13995566001
     */

    private double price;
    private String sku;
    private int qty;
    private String unit;
    private String productName;
    private int order_type;

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
