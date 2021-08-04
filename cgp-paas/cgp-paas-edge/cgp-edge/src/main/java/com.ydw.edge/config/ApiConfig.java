package com.ydw.edge.config;

import com.ydw.edge.model.vo.DeviceApiVO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/5/814:55
 */
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfig {

    private List<DeviceApiVO> list;

    public List<DeviceApiVO> getList() {
        return list;
    }

    public void setList(List<DeviceApiVO> list) {
        this.list = list;
    }
}
