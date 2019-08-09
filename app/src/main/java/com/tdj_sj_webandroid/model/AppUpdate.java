package com.tdj_sj_webandroid.model;

/**
 * Created by Administrator on 2018/5/3.
 */

public class AppUpdate {

    /**
     * data : {"id":8,"type":1,"version":"123123","url":"http://a1.zukehouse.com/uploads/file/addb1c0eb4d0f360033e204835f7ae6b.jpg","message":"12312313","created_at":"2018-05-03 12:04:19","updated_at":"2018-05-03 12:04:19"}
     * code : 0
     * msg : success
     */

    private DataBean data;
    private int err;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * id : 8
         * type : 1
         * version : 123123
         * url : http://a1.zukehouse.com/uploads/file/addb1c0eb4d0f360033e204835f7ae6b.jpg
         * message : 12312313
         * created_at : 2018-05-03 12:04:19
         * updated_at : 2018-05-03 12:04:19
         */

        private int id;
        private int type;
        private String version;
        private String url;
        private String remark;
        private String createTime;
        private String eifTime;
        private double size;
        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEifTime() {
            return eifTime;
        }

        public void setEifTime(String eifTime) {
            this.eifTime = eifTime;
        }



        public double getSize() {
            return size;
        }

        public void setSize(double size) {
            this.size = size;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
}
