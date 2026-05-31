---
name: long-explanation-html
description: 本プロジェクトで100行を超える説明・解説・設計議論・学習資料を出力する際は、必ず単一HTMLファイルとして `docs/` 配下に作成する。専門用語にはホバーで説明が出るtooltip、概念には図解（SVG/Mermaid/HTML図）、学習ポイントの強調、目次・自己チェックを含めること。
---

# Long Explanation HTML — 長文説明HTML化Skill

## 発動条件

以下のいずれかに該当する場合、本Skillを自動適用する：

1. ユーザーへの説明・解説が **100行を超える** と見込まれる時
2. 設計判断の比較（A案/B案/C案 のような多軸比較）
3. 概念解説（DDD、JPA、Spring等の学習対象用語の説明）
4. Phase完了レビュー / 学習ふりかえり資料
5. ユーザーが「分かりやすくまとめて」「資料として欲しい」と要求した時

## 出力先

カテゴリ別サブフォルダに振り分ける：

```
docs/
├── index.html                            … 全ドキュメント目次（新規追加時は要更新）
├── guides/                               … 実装手順・進め方・全体像
├── references/                           … リファレンス資料（API・アノテーション等）
├── design-decisions/                     … 設計判断記録（ADR的）
└── plans/                                … 修正・実装プラン（チェックリスト系）
```

ファイル命名：`YYYY-MM-DD_<topic-kebab>.html`

例：
- `docs/guides/2026-05-25_phase1-user-implementation.html`
- `docs/references/2026-05-27_jpa-annotations-reference.html`
- `docs/design-decisions/2026-05-26_design-criteria-entity.html`
- `docs/plans/2026-05-30_entity-fix-plan.html`

### カテゴリ判定基準

| カテゴリ | 該当するもの |
|---|---|
| **guides** | Phase の進め方、レイヤー全体像、初学者向け解説 |
| **references** | アノテーション/API/ライブラリの逆引き、用語集 |
| **design-decisions** | A案/B案/C案の比較と採用理由（後から「なぜこう決めたか」を思い出すため） |
| **plans** | 特定の修正・実装の手順チェックリスト（完了で役目を終える） |

### 制約

- 日付プレフィックス：作成日（時系列で並ぶように）。更新時は維持
- 単一HTMLファイル（CSS/JSは inline。外部依存ゼロでブラウザで即開ける）
- 新規ドキュメント作成時は **`docs/index.html` も更新**して目次に追加する

## 必須要素

### 1. HTML構造
- `<!DOCTYPE html>` から始まる完全なHTML5文書
- `<meta charset="UTF-8">` 必須
- `lang="ja"` 指定
- タイトルバーに `<title>` を設定

### 2. 専門用語のtooltip
- `<abbr title="...">` または `<span class="term" data-tooltip="...">` を用いる
- ホバーで日本語の簡単な説明が表示されること
- 対象例：JPA、DDD、Entity、Aggregate、UPSERT、Flyway、Bean、IoC、DI、ORM 等

### 3. 図解
最低1つは以下のいずれかで図を入れる：
- インラインSVG（フロー図、構造図、ER図、レイヤー図）
- HTML+CSSによる箱・矢印図
- Mermaid（CDNを使う場合のみ。基本は依存ゼロ優先）
- 表（比較表は積極的に表で）

### 4. 視覚的強調
カラーパレット（学習目的を反映）：
- 🟢 概念/理解必須：緑系
- 🔵 実装/手順：青系
- 🟡 注意/落とし穴：黄系
- 🔴 やってはいけない：赤系
- 🟣 学習Tips：紫系

クラス名規約：
- `.concept` `.implementation` `.warning` `.danger` `.tip`

### 5. 目次（TOC）
- 説明が3セクション以上ある場合は目次を冒頭に配置
- アンカーリンクで各セクションへジャンプ可能に

### 6. 自己チェック（学習資料の場合）
- 末尾に「理解度チェック」セクション
- 3〜5問の問い（答えは `<details>` で折りたたみ）

### 7. レスポンシブ
- `max-width: 900px; margin: 0 auto;` でPC表示時に読みやすく
- フォントサイズは最低16px

### 8. チェックリスト（plans/ カテゴリ必須、他カテゴリは任意）

「完了したらチェックを入れる」運用が想定されるリストは、必ず<strong>インタラクティブなチェックボックス</strong>にする：

- `<ul class="checklist">` でマークアップ
- JS で各 `<li>` の先頭に `<input type="checkbox">` を自動挿入
- 状態は `localStorage` に保存し、リロードしても保持
- localStorage キー：`<page-key>:<section-id>:<list-index>:<item-index>`
  - 例：`pollen-notify:entity-fix-plan:observation-fix:0:3`
- 進捗バー（`X / Y 完了`）とリセットボタンをリストの直前に自動表示
- チェック済みアイテムは取り消し線＋グレーアウト

実装は `docs/plans/2026-05-30_entity-fix-plan.html` 末尾のIIFEスクリプトをそのまま流用可。`PAGE_KEY` 定数だけ変更すれば動く。

## スタイル基本テンプレ

```html
<style>
  body { font-family: -apple-system, "Segoe UI", "Hiragino Kaku Gothic ProN", sans-serif;
         max-width: 900px; margin: 0 auto; padding: 2rem; line-height: 1.7; color: #222; }
  h1 { border-bottom: 3px solid #2563eb; padding-bottom: 0.5rem; }
  h2 { border-left: 5px solid #2563eb; padding-left: 0.6rem; margin-top: 2.5rem; }
  .term { border-bottom: 1px dotted #2563eb; cursor: help; }
  .concept { background: #ecfdf5; border-left: 4px solid #10b981; padding: 1rem; margin: 1rem 0; border-radius: 4px; }
  .implementation { background: #eff6ff; border-left: 4px solid #3b82f6; padding: 1rem; margin: 1rem 0; border-radius: 4px; }
  .warning { background: #fef3c7; border-left: 4px solid #f59e0b; padding: 1rem; margin: 1rem 0; border-radius: 4px; }
  .danger { background: #fee2e2; border-left: 4px solid #ef4444; padding: 1rem; margin: 1rem 0; border-radius: 4px; }
  .tip { background: #f5f3ff; border-left: 4px solid #8b5cf6; padding: 1rem; margin: 1rem 0; border-radius: 4px; }
  table { border-collapse: collapse; width: 100%; margin: 1rem 0; }
  th, td { border: 1px solid #d1d5db; padding: 0.5rem 0.8rem; text-align: left; }
  th { background: #f3f4f6; }
  code { background: #f3f4f6; padding: 0.1rem 0.4rem; border-radius: 3px; font-size: 0.9em; }
  pre { background: #1e293b; color: #f1f5f9; padding: 1rem; border-radius: 6px; overflow-x: auto; }
  pre code { background: transparent; color: inherit; padding: 0; }
  .toc { background: #f9fafb; border: 1px solid #e5e7eb; padding: 1rem 1.5rem; border-radius: 6px; }
  .toc ol { margin: 0.3rem 0; }
  details { background: #f9fafb; padding: 0.8rem 1rem; border-radius: 4px; margin: 0.5rem 0; }
  summary { cursor: pointer; font-weight: bold; }
  .diagram { margin: 1.5rem 0; text-align: center; }
</style>
```

## CLAUDE.md趣旨を踏まえた追加要件（Claude独自拡張）

本プロジェクトは **学習が第一目的** であるため：

1. **「Claudeが書いた答え」と「ユーザーが自分で書くべき箇所」を視覚的に区別**
   - 自分で書くべき箇所には 🖊️ アイコン or 専用クラス `.user-task`
2. **学習リソースの埋め込み**
   - 公式ドキュメントへのリンクを各概念解説の末尾に置く
3. **間違えやすいポイントを明示**
   - `.danger` クラスで「Phase 1でやりがちな失敗」を強調
4. **次のアクションを明示**
   - 末尾に「次にやること」セクションを必ず置く
5. **Phase間の連続性**
   - Phase 1の説明にPhase 4で何が起こるかを軽く触れる（DDDリファクタへの伏線）

## 適用しないケース

- 短い回答（30行未満の質問への応答）
- コード差分の説明（diff/Edit説明は通常テキストで可）
- エラーメッセージの即時解説（即応性優先）
- ユーザーが「テキストで答えて」と明示した時

## 既存HTMLドキュメントの更新

- 内容を更新する場合：日付プレフィックスは維持しつつ、文末に「更新履歴」セクションを追加
- 大幅な作り直しが必要な場合：新規ファイルを作成し、旧ファイルに「→ 新版へ」リンクを追加
