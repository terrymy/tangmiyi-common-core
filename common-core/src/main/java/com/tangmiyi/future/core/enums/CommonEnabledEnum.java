package com.tangmiyi.future.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 所有yes or no公用枚举
 */
@Getter
@AllArgsConstructor
public enum CommonEnabledEnum {


	/**
	 * 是
	 */
	ENABLED( 1),

	/**
	 * 否
	 */
	DISABLED( 0);

	public Integer value;
}

