package com.fuku.pollen_notify;
import com.fuku.pollen_notify.service.PollenIngestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class IngestRunner implements CommandLineRunner {

    private final PollenIngestService service;

    public IngestRunner(PollenIngestService service) {   // 依存注入
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));/*LocalDate.of(2026,3,1)*/
        service.ingest("27123", nowDate);
    }
}