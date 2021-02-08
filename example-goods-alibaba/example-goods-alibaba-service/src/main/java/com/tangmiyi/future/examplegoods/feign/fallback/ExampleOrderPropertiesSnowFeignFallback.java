/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tangmiyi.future.examplegoods.feign.fallback;

import com.tangmiyi.future.core.bean.PageBean;
import com.tangmiyi.future.core.bean.ResultBean;
import com.tangmiyi.future.examplegoods.feign.ExampleOrderPropertiesSnowFeign;
import com.tangmiyi.future.exampleorder.client.service.OrderPropertiesSnowService;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowPageParam;
import com.tangmiyi.future.exampleorder.pojo.param.PropertiesSnowParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * sentinel 降级处理
 */
@Component
@Slf4j
public class ExampleOrderPropertiesSnowFeignFallback implements ExampleOrderPropertiesSnowFeign {

	@Override
	public ResultBean<Boolean> add(@Valid PropertiesSnowParam propertiesSnowParam) {
		return null;
	}

	@Override
	public ResultBean<PageBean> pageList(@Valid PropertiesSnowPageParam propertiesSnowPageParam) {
		return null;
	}
}
