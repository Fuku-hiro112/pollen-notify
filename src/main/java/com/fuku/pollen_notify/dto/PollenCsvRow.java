package com.fuku.pollen_notify.dto;

import java.time.Instant;

public record PollenCsvRow(
        String cityCode,
        Instant date,
        int pollen_count
) {}