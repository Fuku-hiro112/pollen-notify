package com.fuku.pollen_notify.service;

import lombok.Getter;

public enum PollenLevel {
    FEW(0, "少ないでしょう"),
    SLIGHTLY_MANY(10, "やや多いでしょう"),
    MANY(30, "多いでしょう"),
    VERY_MANY(50, "非常に多いでしょう");

    private final int lowerBound;
    @Getter
    private final String label;

    PollenLevel(int lowerBound, String label) {
        this.lowerBound = lowerBound;
        this.label = label;
    }

    public static PollenLevel of(int pollenCount) {
        // pollenCountに対応するレベルを選ぶロジック
        if(VERY_MANY.lowerBound <= pollenCount) {
            return VERY_MANY;
        } else if (MANY.lowerBound <= pollenCount) {
            return MANY;
        } else if (SLIGHTLY_MANY.lowerBound <= pollenCount) {
            return SLIGHTLY_MANY;
        } else if (FEW.lowerBound <= pollenCount) {
            return FEW;
        } else {
            throw new IllegalArgumentException("pollenCount must be >= 0 but was " + pollenCount);
        }
    }
}