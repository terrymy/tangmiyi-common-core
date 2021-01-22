package com.tangmiyi.future.exampleorder.pojo.param;

import com.tangmiyi.future.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel
@Setter
@Getter
public class PropertiesSnowPageParam extends BaseParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否公用，1是，0否", required = true)
    @NotNull(message = "是否公用不能为空")
    private Integer common;
}
