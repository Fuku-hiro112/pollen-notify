package com.fuku.pollen_notify.service;

import com.fuku.pollen_notify.client.PollenApiClient;
import com.fuku.pollen_notify.dto.PollenCsvRow;
import com.fuku.pollen_notify.entity.ObservationPoint;
import com.fuku.pollen_notify.entity.PollenMeasurement;
import com.fuku.pollen_notify.repository.ObservationPointRepository;
import com.fuku.pollen_notify.repository.PollenMeasurementRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PollenIngestService {

    private final PollenApiClient client;
    private final ObservationPointRepository pointRepo;
    private final PollenMeasurementRepository measurementRepo;

    // コンストラクタインジェクション（@Autowired不要：引数1コンストラクタは自動注入）
    public PollenIngestService(PollenApiClient client,
                               ObservationPointRepository pointRepo,
                               PollenMeasurementRepository measurementRepo) {
        this.client = client;
        this.pointRepo = pointRepo;
        this.measurementRepo = measurementRepo;
    }

    @Transactional
    public void ingest(String cityCode, String startDate) throws IOException {
        String csv = client.fetchRawCsv(cityCode, startDate);     // 1. 取得
        List<PollenCsvRow> rows = client.parse(csv);   // 2. パース
        ObservationPoint point = pointRepo.findByExternalId(cityCode)
                .orElseThrow(() -> new IllegalStateException("観測地点が見つかりません: " + cityCode));

        List<PollenMeasurement> entities = rows.stream()      // ← stream はここ
                .filter(this::isValid)                        //    4. -9999を弾く
                .filter(row -> !measurementRepo.existsByObservationPointAndMeasuredAt(point, row.date()))
                .map(row -> toEntity(row, point)) //    5. 変換
                .toList();

        measurementRepo.saveAll(entities);          // 6. 保存
    }

    // 以下は private の補助メソッド（流れから呼ばれる部品）
    private boolean isValid(PollenCsvRow row) { return row.pollen_count() >= 0; }

    // 例：マッパー的なメソッド
    PollenMeasurement toEntity(PollenCsvRow row, ObservationPoint point) {
        return PollenMeasurement.of(point, row.date(), row.pollen_count());
    }
}