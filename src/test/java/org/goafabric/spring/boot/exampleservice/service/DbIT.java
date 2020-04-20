package org.goafabric.spring.boot.exampleservice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class DbIT {
    @Autowired
    private DataSource dataSource;

    private static final String sql =
            "select round(total_time::numeric, 2), round((total_time/calls)::numeric, 2) as avg_time, calls, left(query, 150)" +
            " from pg_stat_statements order by total_time desc limit 20";

    @Test
    public void test() {
        System.out.println(getStatStements());
    }

    private String getStatStements() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        StringBuffer buffer = new StringBuffer();
        buffer.append("total\taverage\tcalls\tquery\n");

        final List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> result : results) {
            String line = "";
            for (Object object : result.values()) {
                final String value = object.toString().replace("\n", " ");
                line+= value.toString() + "\t";
            }
            buffer.append(line).append("\n");
        }
        return buffer.toString();
    }
}
