package com.atguigu.atcrowdfunding.util;

import com.atguigu.atcrowdfunding.bean.Permission;

public class AjaxResult {
    private boolean success;
    private String message;
    Page page;
    Object data;
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
