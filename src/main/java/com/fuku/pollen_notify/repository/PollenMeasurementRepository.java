package com.fuku.pollen_notify.repository;

import com.fuku.pollen_notify.entity.PollenMeasurement;
import com.fuku.pollen_notify.entity.ObservationPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface PollenMeasurementRepository
        extends JpaRepository<PollenMeasurement, Long> {
    boolean existsByObservationPointAndMeasuredAt(ObservationPoint point, Instant measuredAt);
}