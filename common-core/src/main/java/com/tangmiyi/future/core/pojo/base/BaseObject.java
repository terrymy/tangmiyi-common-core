package com.tangmiyi.future.core.pojo.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
