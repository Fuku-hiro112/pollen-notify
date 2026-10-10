package com.fuku.pollen_notify.service;

public class NotificationDecision {
    private final PollenLevel notifyFromLevel = PollenLevel.SLIGHTLY_MANY;

    public boolean shouldNotify(int pollenCount) {
        return PollenLevel.of(pollenCount).compareTo(notifyFromLevel) >= 0;
    }
}