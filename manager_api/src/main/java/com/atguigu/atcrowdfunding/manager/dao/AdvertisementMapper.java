package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.Order;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);
    List<Advertisement> queryPage1(Map<String,Object>map);
    int queryCount(Map<String,Object>map);
    int batchDelete(@Param(value = "datas") Data datas);
}