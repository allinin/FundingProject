package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.manager.dao.AdvertisementMapper;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    public int insertAdvert(Advertisement advertisement) {
        return advertisementMapper.insert(advertisement);
    }

    @Override
    public Page<Advertisement> pageQuery(Map<String, Object> map) {
        Page<Advertisement> advertisementPage=new Page<Advertisement>((Integer) map.get("pageno"),(Integer)map.get("pagesize"));
        map.put("startIndex",advertisementPage.getStartIndex());
        List<Advertisement> advertisements=advertisementMapper.queryPage1(map);
        int count=advertisementMapper.queryCount(map);
        advertisementPage.setDatas(advertisements);
        advertisementPage.setTotalsize(count);
        return advertisementPage;
    }

    @Override
    public int deleteAdvert(int id) {
        return advertisementMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batcheDelete(Data datas) {
        return advertisementMapper.batchDelete(datas);
    }
}
