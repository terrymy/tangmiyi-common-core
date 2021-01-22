package com.tangmiyi.future.exampleorder.dao;

import com.tangmiyi.future.exampleorder.pojo.PropertiesSnowDO;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowPageParam;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PropertiesSnowMapper extends Mapper<PropertiesSnowDO> {

    List<PropertiesSnowDO> selectPageList(@Param("propertiesSnowPageParam") PropertiesSnowPageParam propertiesSnowPageParam);
}