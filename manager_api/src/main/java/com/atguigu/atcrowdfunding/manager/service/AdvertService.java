package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.Page;

import java.util.Map;

public interface AdvertService{


    int insertAdvert(Advertisement advertisement);

    Page<Advertisement> pageQuery(Map<String, Object> map);

    int deleteAdvert(int id);

    int batcheDelete(Data datas);
}
