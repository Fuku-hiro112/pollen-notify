package com.fuku.pollen_notify.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.time.Instant;

@Entity
@Table(name = "pollen_measurements",uniqueConstraints = @UniqueConstraint(columnNames = {"observation_point_id", "measured_at"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class PollenMeasurement {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pollen_measurements_id_seq"
        )
    @SequenceGenerator(
            name = "pollen_measurements_id_seq",
            sequenceName = "pollen_measurements_id_seq",
            allocationSize = 1
        )
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "observation_point_id", nullable = false)
    private ObservationPoint observationPoint;

    @Column(name = "measured_at",nullable=false)
    private Instant measuredAt;

    @Column(name = "pollen_count",nullable = false)
    private int pollenCount;

    @Column(name = "created_at" ,nullable=false)
    private Instant createdAt;

    public static PollenMeasurement of(ObservationPoint observationPoint, Instant at, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("pollenCount must be >= 0 but was " + count);
        }
        PollenMeasurement pollenMeasurement = new PollenMeasurement();
        pollenMeasurement.observationPoint = observationPoint;
        pollenMeasurement.measuredAt       = at;
        pollenMeasurement.pollenCount      = count;
        return pollenMeasurement;
    }
}
