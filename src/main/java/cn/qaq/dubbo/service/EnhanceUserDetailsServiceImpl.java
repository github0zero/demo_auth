package cn.qaq.dubbo.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

@DubboService(version = "1.0")
@Component
public class EnhanceUserDetailsServiceImpl implements EnhanceUserDetailsService {

    @Override
    public JSONObject newAccessToken() {
        JSONObject r = new JSONObject();
        r.put("app_name","demo_auth");
        r.put("token","123asf");
        return r;
    }
}
