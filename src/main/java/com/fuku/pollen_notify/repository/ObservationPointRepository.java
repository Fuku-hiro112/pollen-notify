package com.fuku.pollen_notify.repository;

import com.fuku.pollen_notify.entity.ObservationPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObservationPointRepository
        extends JpaRepository<ObservationPoint, Long> {
    Optional<ObservationPoint> findByExternalId(String externalId);
}