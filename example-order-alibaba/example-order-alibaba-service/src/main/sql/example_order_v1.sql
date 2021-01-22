CREATE TABLE `properties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `key` varchar(100) NOT NULL COMMENT '配置文件key',
  `value` varchar(100) NOT NULL COMMENT '配置文件value',
  `application` varchar(100) NOT NULL COMMENT '客户端code',
  `profile` varchar(50) NOT NULL COMMENT '环境激活',
  `lable` varchar(50) NOT NULL COMMENT '分支',
  `common` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否公用，1是，0否',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统配置属性表';

CREATE TABLE `properties_snow` (
  `id` bigint(20) NOT NULL COMMENT '主键，雪花算法',
  `key` varchar(100) NOT NULL COMMENT '配置文件key',
  `value` varchar(100) NOT NULL COMMENT '配置文件value',
  `application` varchar(100) NOT NULL COMMENT '客户端code',
  `profile` varchar(50) NOT NULL COMMENT '环境激活',
  `lable` varchar(50) NOT NULL COMMENT '分支',
  `common` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否公用，1是，0否',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置属性表';