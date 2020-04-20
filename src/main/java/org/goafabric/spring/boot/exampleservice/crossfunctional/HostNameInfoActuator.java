package org.goafabric.spring.boot.exampleservice.crossfunctional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HostNameInfoActuator implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        try {
            builder.withDetail("ip", InetAddress.getLocalHost().getHostAddress());
            builder.withDetail("hostname", InetAddress.getLocalHost().getHostName());
            builder.withDetail("Postgres Stats", getStatSatements());
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
        }
    }


    private static final String sql =
            "select round(total_time::numeric, 2), round((total_time/calls)::numeric, 2) as avg_time, calls, left(query, 150)" +
                    " from pg_stat_statements order by total_time desc limit 20";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getStatSatements() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("total\taverage\tcalls\tquery\n");

        final List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> result : results) {
            String line = "";
            for (Object object : result.values()) {
                line+= object.toString().replace("\n", " ") + "\t";
            }
            buffer.append(line).append("\n");
        }
        return buffer.toString();
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
