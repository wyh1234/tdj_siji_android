package com.tdj_sj_webandroid.model;

public class ConfirmPlan {
    private int index;
    private boolean isConf;

    public boolean isConf() {
        return isConf;
    }

    public void setConf(boolean conf) {
        isConf = conf;
    }

    public ConfirmPlan(int index, boolean isConf) {
        this.index = index;
        this.isConf = isConf;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
