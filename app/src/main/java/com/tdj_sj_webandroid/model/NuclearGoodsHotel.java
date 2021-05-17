package com.tdj_sj_webandroid.model;

import java.util.List;

public class NuclearGoodsHotel {


    /**
     * date : 2019-11-16
     * order_num : 10
     * type : 1
     * user : {"stationName":"汉口北配送中心","driverNo":"65号司机"}
     * order_list : [{"code":"G91区65-6","yrk":0,"qt":0,"ch":2,"key_desc":6,"num":21,"name":"(美冒)","zc":0,"drk":0,"ds":19,"customer":10123},{"code":"G91区65-11","yrk":0,"qt":0,"ch":1,"key_desc":11,"num":6,"name":"(桥头排骨二七路店)","zc":0,"drk":0,"ds":5,"customer":31338},{"code":"G91区65-17","yrk":0,"qt":0,"ch":0,"key_desc":17,"num":4,"name":"(煎饼王子  上东汇店)","zc":0,"drk":0,"ds":4,"customer":11165},{"code":"G91区65-25","yrk":0,"qt":0,"ch":1,"key_desc":25,"num":14,"name":"(爽口锅仔)","zc":0,"drk":1,"ds":12,"customer":13493},{"code":"G91区65-35","yrk":0,"qt":0,"ch":0,"key_desc":35,"num":12,"name":"(渔家小厨私房菜)","zc":0,"drk":0,"ds":12,"customer":14431},{"code":"G91区65-37","yrk":0,"qt":0,"ch":0,"key_desc":37,"num":7,"name":"(聚小面)","zc":0,"drk":0,"ds":7,"customer":14632},{"code":"G91区65-50","yrk":0,"qt":0,"ch":0,"key_desc":50,"num":6,"name":"(聚小面)","zc":0,"drk":0,"ds":6,"customer":33129},{"code":"G91区65-55","yrk":0,"qt":0,"ch":0,"key_desc":55,"num":12,"name":"(螺狮粉)","zc":0,"drk":0,"ds":12,"customer":23296},{"code":"G91区65-58","yrk":0,"qt":0,"ch":0,"key_desc":58,"num":7,"name":"(攀多拉厨房)","zc":0,"drk":0,"ds":7,"customer":20016},{"code":"G91区65-70","yrk":0,"qt":0,"ch":0,"key_desc":70,"num":1,"name":"(江西汤一绝)","zc":0,"drk":0,"ds":1,"customer":21271}]
     * nums : 24
     */

    private String date;
    private int order_num;
    private int type;
    private UserBean user;
    private int nums;
    private int tab1;
    private int tab2;
    private int tab3;
    private List<OrderListBean> order_list;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public List<OrderListBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderListBean> order_list) {
        this.order_list = order_list;
    }

    public static class UserBean {
        /**
         * stationName : 汉口北配送中心
         * driverNo : 65号司机
         */

        private String stationName;
        private String driverNo;

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getDriverNo() {
            return driverNo;
        }

        public void setDriverNo(String driverNo) {
            this.driverNo = driverNo;
        }
    }

    public static class OrderListBean {
        /**
         * code : G91区65-6
         * yrk : 0
         * qt : 0
         * ch : 2
         * key_desc : 6
         * num : 21
         * name : (美冒)
         * zc : 0
         * drk : 0
         * ds : 19
         * customer : 10123
         */

        private String code;
        private int yrk;
        private int qt;
        private int ch;
        private int key_desc;
        private int num;
        private String name;
        private int zc;
        private int drk;
        private int ds;
        private int customer;
        private String customerName;
        private String autoCode;
        private int isEdited;
        private int isC;    //0普通客户 1.个人客户  2.大客户

        public int getIsC() {
            return isC;
        }

        public void setIsC(int isC) {
            this.isC = isC;
        }

        public int getIsEdited() {
            return isEdited;
        }

        public void setIsEdited(int isEdited) {
            this.isEdited = isEdited;
        }

        public String getAutoCode() {
            return autoCode;
        }

        public void setAutoCode(String autoCode) {
            this.autoCode = autoCode;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getYrk() {
            return yrk;
        }

        public void setYrk(int yrk) {
            this.yrk = yrk;
        }

        public int getQt() {
            return qt;
        }

        public void setQt(int qt) {
            this.qt = qt;
        }

        public int getCh() {
            return ch;
        }

        public void setCh(int ch) {
            this.ch = ch;
        }

        public int getKey_desc() {
            return key_desc;
        }

        public void setKey_desc(int key_desc) {
            this.key_desc = key_desc;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getZc() {
            return zc;
        }

        public void setZc(int zc) {
            this.zc = zc;
        }

        public int getDrk() {
            return drk;
        }

        public void setDrk(int drk) {
            this.drk = drk;
        }

        public int getDs() {
            return ds;
        }

        public void setDs(int ds) {
            this.ds = ds;
        }

        public int getCustomer() {
            return customer;
        }

        public void setCustomer(int customer) {
            this.customer = customer;
        }
    }
}
