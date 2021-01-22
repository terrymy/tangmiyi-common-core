package com.tangmiyi.future.exampleorder.service.impl;


import com.alicp.jetcache.anno.Cached;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tangmiyi.future.core.bean.PageBean;
import com.tangmiyi.future.core.utils.base.BeanUtils;
import com.tangmiyi.future.core.utils.base.IdGeneratorSnowflakeUtils;
import com.tangmiyi.future.exampleorder.dao.PropertiesSnowMapper;
import com.tangmiyi.future.exampleorder.pojo.PropertiesSnowDO;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowPageParam;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowParam;
import com.tangmiyi.future.exampleorder.service.PropertiesSnowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PropertiesServiceSnowImpl implements PropertiesSnowService {

    @Resource
    private PropertiesSnowMapper propertiesSnowMapper;

    @Override
    @Cached
    public PageBean findPageList(PropertiesSnowPageParam propertiesSnowPageParam) {
        Page<PropertiesSnowDO> page = PageHelper.startPage(propertiesSnowPageParam.getPageNo(), propertiesSnowPageParam.getPageSize());
        List<PropertiesSnowDO> propertiesSnowDOS = propertiesSnowMapper.selectPageList(propertiesSnowPageParam);
        PageBean pageBean = new PageBean();
        pageBean.setTotal(page.getTotal());
        pageBean.setList(propertiesSnowDOS);
        return pageBean;
    }

    @Override
    public void insert(PropertiesSnowParam propertiesSnowParam) {
        PropertiesSnowDO propertiesSnowDO = new PropertiesSnowDO();
        BeanUtils.copyBean(propertiesSnowParam,propertiesSnowDO);
        propertiesSnowDO.setId(IdGeneratorSnowflakeUtils.genId());
        propertiesSnowMapper.insertSelective(propertiesSnowDO);
    }
}
