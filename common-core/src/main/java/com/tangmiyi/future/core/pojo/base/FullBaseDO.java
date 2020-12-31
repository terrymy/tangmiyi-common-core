package com.tangmiyi.future.core.pojo.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullBaseDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private Long createrId;

    private String creater;

    private Long updateId;

    private String updater;
}
