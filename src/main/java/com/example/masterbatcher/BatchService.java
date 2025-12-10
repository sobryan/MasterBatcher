package com.example.masterbatcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BatchService {

    private final JdbcTemplate jdbc;

    @Value("${queue.batch-size:500}")
    private int batchSize;

    public BatchService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Transactional
    public int processBatch() {
        String sql = """
            SELECT id, payload
            FROM queue_table
            WHERE status = 0
            FETCH FIRST ? ROWS ONLY
            FOR UPDATE WITH RS USE AND KEEP UPDATE LOCKS
            SKIP LOCKED DATA
            """;

        List<Map<String, Object>> rows = jdbc.queryForList(sql, batchSize);

        System.out.println(Thread.currentThread().getName() + " claimed " + rows.size() + " rows");

        for (Map<String, Object> row : rows) {
            Long id = (Long) row.get("id");
            String payload = (String) row.get("payload");

            transform(payload);

            jdbc.update("UPDATE queue_table SET status = 1 WHERE id = ?", id);
        }

        return rows.size();
    }

    private void transform(String payload) {
        // TODO: implement transformation logic
    }
}
