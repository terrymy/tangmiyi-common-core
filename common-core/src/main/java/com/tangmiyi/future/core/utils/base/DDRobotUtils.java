package com.tangmiyi.future.core.utils.base;

import com.tangmiyi.future.core.consts.CoreConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RefreshScope
@Component
public class DDRobotUtils {

    @Value("${ali.ddrobotUat.url:https://oapi.dingtalk.com/robot/send?access_token=da09fb33e3e90e94c3e17cf4691ffce7ca31e2a91094439171f617b7ed2cb2a5}")
    private String ddrobotUatUrl;

    @Value("${ali.ddrobotProd.url:https://oapi.dingtalk.com/robot/send?access_token=2380f9c265bb353fce67145b809180fac0ac01b9c035d9664dd04222384fef42}")
    private String ddrobotProdUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.profiles.active:dev}")
    private String active;

    @Async
    public void send(String ddrobotUrl, Map<String, Object> items){
        try {
            restTemplate.postForEntity(ddrobotUrl,items,String.class);
        } catch (Exception e) {
            log.error("send dingding error:{}",ExUtils.getEDetail(e));
        }
    }

    public void sendMsg(String text) {
        String ddrobotUrl = active.equalsIgnoreCase(CoreConstants.ENV_UAT) ? ddrobotUatUrl : ddrobotProdUrl;
        Map<String, Object> items = new HashMap<>();
        items.put("msgtype", "text");
        Map<String, String> textContent = new HashMap<>();
        if(text.length() > 1000){
            textContent.put("content", text.substring(0,1000));
        }else{
            textContent.put("content", text);
        }
        items.put("text", textContent);
        Map<String, Object> atItems = new HashMap<>();
        items.put("at", atItems);
        send(ddrobotUrl,items);
    }
}
