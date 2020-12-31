package com.tangmiyi.future.core.pojo.param;

import com.tangmiyi.future.core.pojo.base.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseParam extends BaseObject {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公共参数:当前页,默认为1")
    private Integer pageNo = 1;
    @ApiModelProperty(value = "公共参数:每页多少条,默认为20")
    private Integer pageSize = 20;
    @ApiModelProperty(value = "公共参数:开始时间,格式:yyyy-MM-dd HH:mm:ss或者毫秒时间戳")
    private Date startTime;
    @ApiModelProperty(value = "公共参数:结束时间,格式:yyyy-MM-dd HH:mm:ss或者毫秒时间戳")
    private Date endTime;
}
