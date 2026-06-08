package com.fuku.pollen_notify.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "observation_points")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ObservationPoint {
    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "observation_points_id_seq"
        )
    @SequenceGenerator(
            name = "observation_points_id_seq",
            sequenceName = "observation_points_id_seq",
            allocationSize = 1
        )
    @Column
    private Long id;
    
    @Column(name = "external_id",nullable = false,unique = true,length = 64)
    private String externalId;
    
    @Column(nullable = false,length = 128)
    private String name;
    
    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;
    
    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;
    
    @Column(name = "created_at",nullable=false)
    private Instant createdAt;
    
    @Column(name = "updated_at",nullable=false)
    private Instant updatedAt;
}