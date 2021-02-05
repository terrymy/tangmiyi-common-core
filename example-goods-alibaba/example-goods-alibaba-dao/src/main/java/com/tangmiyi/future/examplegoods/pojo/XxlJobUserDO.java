package com.tangmiyi.future.examplegoods.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "xxl_job_user")
@Setter
@Getter
public class XxlJobUserDO{


    @Column(name = "id")
    private Long id;

    private String username;

    private String password;

    private Integer role;

    private String permission;
}