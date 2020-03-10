package io.opentracing.contrib.specialagent.rule.dubbo27;

import com.alibaba.dubbo.common.Constants;
import io.opentracing.contrib.specialagent.rule.CommonHelper;
import io.opentracing.contrib.specialagent.rule.GreeterService;
import io.opentracing.contrib.specialagent.rule.GreeterServiceImpl;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

public class MockServer {
    ServiceConfig<GreeterService> service;
    String linkLocalIp;

    MockServer() {
        linkLocalIp = NetUtils.getLocalAddress().getHostAddress();
        if (linkLocalIp != null) {
            // avoid dubbo's logic which might pick docker ip
            System.setProperty(Constants.DUBBO_IP_TO_BIND, linkLocalIp);
            System.setProperty(Constants.DUBBO_IP_TO_REGISTRY, linkLocalIp);
        }
        service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig("test"));
        service.setRegistry(new RegistryConfig(RegistryConfig.NO_AVAILABLE));
        service.setProtocol(new ProtocolConfig("dubbo", CommonHelper.getUnusedPort()));
        service.setFilter("traceFilter");
        service.setInterface(GreeterService.class);
        service.setRef(new GreeterServiceImpl());
    }

    void start() {
        service.export();
    }

    void stop() {
        service.unexport();
    }

    int port() {
        return service.getProtocol().getPort();
    }

    String ip() {
        return linkLocalIp != null ? linkLocalIp : "127.0.0.1";
    }


}