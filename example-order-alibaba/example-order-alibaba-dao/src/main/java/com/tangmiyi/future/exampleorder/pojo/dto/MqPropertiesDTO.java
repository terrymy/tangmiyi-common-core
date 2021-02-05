package com.tangmiyi.future.exampleorder.pojo.dto;

import com.tangmiyi.future.core.pojo.base.BaseObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class MqPropertiesDTO extends BaseObject {

    private Long id;

    private String key;

    private String value;
}
