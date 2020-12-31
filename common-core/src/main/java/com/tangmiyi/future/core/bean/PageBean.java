package com.tangmiyi.future.core.bean;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "list", notes = "结果集", example = "[{id:1}]")
    private T list;

    @ApiModelProperty(name = "total", notes = "总条数", example = "999")
    private Long total;
}
