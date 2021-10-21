package com.tdj_sj_webandroid.model;

import java.util.List;

public class HomeInfo {


    /**
     * menus : [{"id":1,"menuName":"发车表","menuDesc":null,"menuIcon":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/703089250168900nil.jpg","menuUrl":"/home/today.do"},{"id":2,"menuName":"核货中心","menuDesc":null,"menuIcon":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/703089142890200y52.jpg","menuUrl":"/write/center.do"},{"id":3,"menuName":"配送任务","menuDesc":null,"menuIcon":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/7030889814449002rc.jpg","menuUrl":"/task/task.do"},{"id":4,"menuName":"扫码退押","menuDesc":null,"menuIcon":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/703087929519100coa.jpg","menuUrl":"/home/retreat.do"}]
     * title : 测试1号 2区18号
     */

    private String title;
    private List<MenusBean> menus;
    private List<NoticeList> noticeList;
    private int phase;

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public List<NoticeList> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NoticeList> noticeList) {
        this.noticeList = noticeList;
    }

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
        private int homeGroup;
        private int type;
        private String homeGroupName;

        public String getHomeGroupName() {
            return homeGroupName;
        }

        public void setHomeGroupName(String homeGroupName) {
            this.homeGroupName = homeGroupName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getHomeGroup() {
            return homeGroup;
        }

        public void setHomeGroup(int homeGroup) {
            this.homeGroup = homeGroup;
        }

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

    public static  class NoticeList{

        /**
         * end_status : 0
         * img : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/201213134646d8eb9d2c.png
         * creator : 超级管理员/admin
         * create_time : 2020-12-14 09:22:37
         * pub_time : 2020-12-13 13:46:51
         * end_time : null
         * entity_id : 17
         * title : 11
         * content : 22
         * content_img : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/201213134634fc690982.png,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/20121313463422aa562b.jpg
         * update_time : 2020-12-13 13:47:14
         * site : 3
         * link_url : /user/driverNoticeDetail.do?entityId=
         * status : 0
         */

        private int end_status;
        private String img;
        private String creator;
        private String create_time;
        private String pub_time;
        private Object end_time;
        private int entity_id;
        private String title;
        private String content;
        private String content_img;
        private String update_time;
        private int site;
        private String link_url;
        private int status;

        public int getEnd_status() {
            return end_status;
        }

        public void setEnd_status(int end_status) {
            this.end_status = end_status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPub_time() {
            return pub_time;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }

        public Object getEnd_time() {
            return end_time;
        }

        public void setEnd_time(Object end_time) {
            this.end_time = end_time;
        }

        public int getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent_img() {
            return content_img;
        }

        public void setContent_img(String content_img) {
            this.content_img = content_img;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getSite() {
            return site;
        }

        public void setSite(int site) {
            this.site = site;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
