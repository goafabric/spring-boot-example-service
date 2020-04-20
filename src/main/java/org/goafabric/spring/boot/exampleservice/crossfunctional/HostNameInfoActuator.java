package org.goafabric.spring.boot.exampleservice.crossfunctional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HostNameInfoActuator implements InfoContributor {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${management.database.monitoring.enabled}")
    private Boolean databaseMonitoringEnabled;

    @Override
    public void contribute(Info.Builder builder) {
        addHostInfo(builder);
        addDatabaseInfo(builder);
    }

    private void addHostInfo(Info.Builder builder) {
        try {
            builder.withDetail("ip", InetAddress.getLocalHost().getHostAddress());
            builder.withDetail("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void addDatabaseInfo(Info.Builder builder) {
        if (databaseMonitoringEnabled) {
            builder.withDetail("pg_stat_statements", "")
                    .withDetail("total    average    calls    query", "")
                    .withDetails(getStatStatements());
        }
    }

    private static final String sql =
            "select round(total_time::numeric, 2), round((total_time/calls)::numeric, 2) as avg_time, calls, left(query, 150)" +
                    " from pg_stat_statements order by total_time desc limit 20";

    private Map<String, Object> getStatStatements() {
        final Map<String, Object> map = new LinkedHashMap<>();
        jdbcTemplate.queryForList(sql).stream().forEach(result -> {
            final StringBuffer line = new StringBuffer();
            result.values().stream().forEach(object -> line.append(object.toString().replace("\n", " ") + "    "));
            map.put("line " + map.size(), line.toString());
        });
        return map;
    }
}
