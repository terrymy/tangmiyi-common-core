package com.tangmiyi.future.core.pojo.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseDO extends BaseObject {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String genID;

    private Date createdAt;

    private Date updatedAt;

}
