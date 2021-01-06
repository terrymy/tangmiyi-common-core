package com.tangmiyi.future.exampleorder.pojo.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * 类说明: valid测试实体
 **/
@Getter
@Setter
public class TestValidParam {

    @NotNull
    private String inputString;

    @AssertTrue
    private Boolean inputTrue;

    @AssertFalse
    private Boolean inputFalse;

    @Min(1)
    private Integer inputMin;

    @Max(100)
    private Integer inputMax;
}