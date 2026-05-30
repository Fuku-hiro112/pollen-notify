# pollen-notify

## 概要

指定した花粉飛散量がしきい値を超過したときに通知する Spring Boot アプリケーション。私生活で実用しつつ、**Spring Boot / JPA / DDD / テスト** を体系的に学ぶための題材として開発中。各 Phase 1〜5 で「動く貧弱なコード」を一旦完成させ、Phase 4 で DDD パターン (集約・値オブジェクト・ドメインサービス) で再設計する学習リファクタを行う。AI (Claude Code) はメンター/レビュアー/設計相談役として活用し、実装の代行は行わない方針で進めている。

## 使用技術

- Java 21
- Spring Boot 3.x
- Spring Data JPA + PostgreSQL 16 (ローカルは Docker Compose)
- Gradle (Kotlin DSL)
- Flyway (DB マイグレーション)
- JUnit 5 / Mockito / AssertJ (Phase 3 以降)
- 外部 API: ウェザーニューズ「ポールンロボ」花粉飛散数データ
- 通知: Discord Webhook (Phase 2)、LINE Messaging API (Phase 5 任意)

## 機能 (フェーズ別)

| Phase | 目的 | 主な学習対象 |
|---|---|---|
| 1 (進行中) | Spring Boot 雛形 + 花粉 API 取得・DB 保存 | Spring 基礎、JPA、外部 API 連携 |
| 2 | 定期実行 + しきい値判定 + Discord 通知 | バッチ、通知ポート抽象化 |
| 3 | ユーザー設定の REST API 管理 | API 設計、認証、テスト |
| 4 (本命) | DDD リファクタ | 集約、値オブジェクト、ドメインサービス |
| 5 | フロント or デプロイ (任意) | — |

## このプロジェクトでの AI 駆動開発の進め方

本リポジトリは「AI に作らせる」のではなく **「AI に指導してもらう」スタイル** を実践している。`CLAUDE.md` と `.claude/skills/` に行動ルールを明文化し、Claude Code がそれを必ず守る設計。

### 指示スタイル

- **依頼するのは**: 設計判断の選択肢提示 (A 案 vs B 案 のトレードオフ)、コードレビュー、概念解説 (例: 「JPA の遅延ロードとは」「集約境界の決め方」)、テスト観点出し、詰まった時の "ヒント" (答えではなく方向性)
- **避けるのは**: 「〇〇機能を実装して」「全部書いて」「最後まで作って」のような丸投げ
- **例外的に Claude が書いて OK**: Initializr 相当の雛形、build.gradle / application.yml、HTML テンプレートの装飾、シェルスクリプト等の学習対象外の周辺コード

### プロジェクト固有 Skill による品質担保

[`.claude/skills/long-explanation-html/SKILL.md`](.claude/skills/long-explanation-html/SKILL.md) として明文化:

> 100 行を超える説明・設計議論・学習資料を出力するときは、必ず単一 HTML ファイルとして `docs/` 配下に作成する。専門用語にはホバーで説明が出る tooltip、概念には図解 (SVG / Mermaid / HTML図)、学習ポイントの強調、目次・自己チェックを含めること。

これにより、長文の解説が再利用可能な学習資料として `docs/` 配下に蓄積される。

### 学習ログ (docs/)

各 Phase の判断記録・概念解説・JPA リファレンスを HTML で残している。ブラウザで開けば tooltip 付きで読める。

- [2026-05-25_phase1-user-implementation.html](docs/2026-05-25_phase1-user-implementation.html) — Phase 1 ユーザー実装ガイド
- [2026-05-26_design-criteria-entity.html](docs/2026-05-26_design-criteria-entity.html) — Entity 設計判断基準
- [2026-05-27_jpa-annotations-reference.html](docs/2026-05-27_jpa-annotations-reference.html) — JPA アノテーションリファレンス

### Git 運用 (CLAUDE.md / Git Flow)

- `main` / `develop` / `feature/*` の Git Flow を採用
- Claude は feature/* ブランチ作成 → main/develop 直接コミット禁止
- ターン終了時の auto-commit / セッション終了時の develop マージは hook で自動化

## ディレクトリ構成

```
.
├── .claude/
│   ├── settings.json                       Claude Code 共有設定 (公開)
│   └── skills/long-explanation-html/       長文説明 HTML 化ルール
├── docs/                                   学習ログ・設計判断 (HTML)
├── src/
│   └── main/
│       ├── java/com/fuku/pollen_notify/
│       │   └── entity/                     ObservationPoint, PollenMeasurement 等
│       └── resources/
│           ├── application.yml
│           └── db/migration/V1__init.sql   Flyway マイグレーション
├── docker-compose.yml                      PostgreSQL 16 ローカル起動
├── .env.example                            環境変数テンプレ (.env は git ignore)
├── build.gradle.kts
└── CLAUDE.md                               プロジェクト固有の Claude ルール
```

## セットアップ

### 前提

- JDK 21
- Docker / Docker Compose

### 手順

```bash
# 1. 環境変数を準備 (DB の dev デフォルト値が入っている)
cp .env.example .env

# 2. PostgreSQL を起動
docker compose up -d

# 3. アプリ起動
./gradlew bootRun
```

DB の状態確認:

```bash
docker compose logs db
docker compose ps
```

## ライセンス

MIT
