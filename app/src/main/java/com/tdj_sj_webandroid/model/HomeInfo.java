package com.tdj_sj_webandroid.model;

import java.util.List;

public class HomeInfo {


    /**
     * menus : [{"id":1,"menuName":"发车表","menuDesc":null,"menuIcon":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/703089250168900nil.jpg","menuUrl":"/home/today.do"},{"id":2,"menuName":"核货中心","menuDesc":null,"menuIcon":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/703089142890200y52.jpg","menuUrl":"/write/center.do"},{"id":3,"menuName":"配送任务","menuDesc":null,"menuIcon":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/7030889814449002rc.jpg","menuUrl":"/task/task.do"},{"id":4,"menuName":"扫码退押","menuDesc":null,"menuIcon":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/703087929519100coa.jpg","menuUrl":"/home/retreat.do"}]
     * title : 测试1号 2区18号
     */

    private String title;
    private List<MenusBean> menus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MenusBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenusBean> menus) {
        this.menus = menus;
    }

    public static class MenusBean {
        /**
         * id : 1
         * menuName : 发车表
         * menuDesc : null
         * menuIcon : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/703089250168900nil.jpg
         * menuUrl : /home/today.do
         */

        private int id;
        private String menuName;
        private Object menuDesc;
        private String menuIcon;
        private String menuUrl;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public Object getMenuDesc() {
            return menuDesc;
        }

        public void setMenuDesc(Object menuDesc) {
            this.menuDesc = menuDesc;
        }

        public String getMenuIcon() {
            return menuIcon;
        }

        public void setMenuIcon(String menuIcon) {
            this.menuIcon = menuIcon;
        }

        public String getMenuUrl() {
            return menuUrl;
        }

        public void setMenuUrl(String menuUrl) {
            this.menuUrl = menuUrl;
        }
    }
}
