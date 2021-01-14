package com.tangmiyi.future.exampleorder.pojo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class MqPropertiesDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String key;

    private String value;
}
