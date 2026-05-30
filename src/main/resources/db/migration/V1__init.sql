-- =========================================================================
-- V1: 花粉通知アプリ 初期スキーマ
-- =========================================================================

-- -------------------------------------------------------------------------
-- 観測地点マスタ
--   external_id: ポールンロボAPIの citycode（市区町村コード）
-- -------------------------------------------------------------------------
CREATE TABLE observation_points (
    id           BIGSERIAL    PRIMARY KEY,
    external_id  VARCHAR(64)  NOT NULL UNIQUE,
    name         VARCHAR(128) NOT NULL,
    latitude     NUMERIC(9, 6),
    longitude    NUMERIC(9, 6),
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- -------------------------------------------------------------------------
-- 花粉観測値
--   不変データとして扱うため updated_at は持たない
--   (observation_point_id, measured_at) で自然キーUNIQUE
-- -------------------------------------------------------------------------
CREATE TABLE pollen_measurements (
    id                    BIGSERIAL   PRIMARY KEY,
    observation_point_id  BIGINT      NOT NULL
        REFERENCES observation_points(id),
    measured_at           TIMESTAMPTZ NOT NULL,
    pollen_count          INTEGER     NOT NULL,
    created_at            TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_measurement UNIQUE (observation_point_id, measured_at)
);

-- 「地点Xの最新N件」を高速に取得するためのインデックス
CREATE INDEX idx_measurement_point_time
    ON pollen_measurements (observation_point_id, measured_at DESC);
