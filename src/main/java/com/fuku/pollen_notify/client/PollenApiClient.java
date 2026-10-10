package com.fuku.pollen_notify.client;
import com.fuku.pollen_notify.dto.PollenCsvRow;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;

import java.time.Instant;
import java.time.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;

@Component
public class PollenApiClient {

    private final RestClient restClient;

    // base-url を注入して RestClient を組み立てる（@Value か設定クラス。後述）
    public PollenApiClient(@Value("${pollen-api.base-url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    /** citycode を指定して CSV 文字列を取得する */
    public String fetchRawCsv(String cityCode, String startDate) {
        return restClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParam("citycode", cityCode)
                            .queryParam("start", startDate)
                            .build())
                .retrieve()
                .body(String.class);   // ← CSVをそのまま文字列で受ける
    }

    // CSV文字列 → List<PollenCsvRow>
    public List<PollenCsvRow> parse(String csv) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()              // 1行目をヘッダとして扱う（Step 0で確認した形に合わせる）
                .setSkipHeaderRecord(true)
                .build();

        try (CSVParser parser = CSVParser.parse(csv, format)) {
            List<PollenCsvRow> rows = new ArrayList<>();
            for (CSVRecord csvRecord : parser) {
                String cityCode = csvRecord.get("citycode");        // 列名 or インデックス
                int count       = Integer.parseInt(csvRecord.get("pollen"));
                Instant at      = OffsetDateTime.parse(csvRecord.get("date")).toInstant();/* 日時文字列をInstantへ変換（DateTimeFormatter） */
                rows.add(new PollenCsvRow(cityCode, at, count));
            }
            return rows;
        }
    }
}