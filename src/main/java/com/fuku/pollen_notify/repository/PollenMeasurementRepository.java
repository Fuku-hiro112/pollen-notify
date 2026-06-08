package com.fuku.pollen_notify.repository;

import com.fuku.pollen_notify.entity.PollenMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollenMeasurementRepository
        extends JpaRepository<PollenMeasurement, Long> {
    // 中身は空でも動く（次章のメソッドはあとで足す）
}
