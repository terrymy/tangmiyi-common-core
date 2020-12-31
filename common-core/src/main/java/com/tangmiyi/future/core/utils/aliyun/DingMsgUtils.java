package com.tangmiyi.future.core.utils.aliyun;

import brave.Tracer;
import com.tangmiyi.future.core.consts.CoreConstants;
import com.tangmiyi.future.core.utils.base.DDRobotUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@Order(3)
public class DingMsgUtils {

	@Autowired
	private Tracer tracer;

	@Autowired
	private DDRobotUtils ddRobotUtil;

	@Value("${spring.application.name:default}")
	private String serviceName;

	@Value("${spring.profiles.active:dev}")
	private String active;

	public void sendDingMsg(String msg) {
		if (active.equals(CoreConstants.ENV_UAT) || active.equals(CoreConstants.ENV_PROD)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("environment: ");
			stringBuilder.append(active);
			stringBuilder.append("\n");
			stringBuilder.append("TraceId: ");
			if(Objects.nonNull(tracer) && Objects.nonNull(tracer.currentSpan())){
				stringBuilder.append(tracer.currentSpan().context().traceIdString());
			}
			stringBuilder.append("\n");
			stringBuilder.append("ServiceName: ");
			stringBuilder.append(serviceName);
			stringBuilder.append("\n");
			stringBuilder.append("Content: ");
			stringBuilder.append(msg);
			ddRobotUtil.sendMsg(stringBuilder.toString());
		}
	}
}
