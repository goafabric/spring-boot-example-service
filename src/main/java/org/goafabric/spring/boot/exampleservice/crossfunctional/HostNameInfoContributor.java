package org.goafabric.spring.boot.exampleservice.crossfunctional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Slf4j
public class HostNameInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        try {
            builder.withDetail("ip", InetAddress.getLocalHost().getHostAddress());
            builder.withDetail("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
        }
    }
}