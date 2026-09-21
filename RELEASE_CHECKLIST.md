# Mod Release Checklist

ReinforcedTools を公開する前に確認するチェックリストです。

## リリース内容

- [ ] `gradle.properties` の `version` をリリース番号に更新する。
- [ ] `CHANGELOG.md` に、このバージョンで追加・変更・修正した内容を記載する。
- [ ] プレイヤー向けREADMEを更新する。
  - [ ] Modの概要と追加要素を説明する。
  - [ ] 対応Minecraftバージョン（現在は26.1.2）を記載する。
  - [ ] Fabric / NeoForgeの両方に対応することを記載する。
  - [ ] 必須依存ModのBalmと、導入方法を記載する。
  - [ ] GitHub Issues・配布ページ・ソースへのリンクを記載する。
- [ ] `gradle.properties` の `description` を実際のMod内容に更新する。
- [ ] `homepage`、`sources`、`issues` を公開URLで設定する。
- [ ] MPL-2.0の適用範囲と、同梱する第三者ライブラリのライセンスを確認する。

## 動作確認

- [ ] クリーンな環境で `./gradlew build` を実行する。
- [ ] Fabric: `./gradlew :fabric:test` を実行する。
- [ ] NeoForge: `./gradlew :neoforge:runGameTestServer` を実行する。
- [ ] Fabric / NeoForgeのクライアントで手動確認する。
  - [ ] 35種類のReinforced Toolがクリエイティブタブに表示される。
  - [ ] 各素材・各ツール種のレシピをクラフトできる。
  - [ ] ネザライト強化用の鍛冶台レシピを確認する。
  - [ ] Repair Kitが、同素材の損傷済み強化ツールだけをオフハンド使用で25%修理する。
  - [ ] Repair Kitの消費、耐久値上限、エンチャント、採掘可否を確認する。
- [ ] Fabric / NeoForgeの専用サーバーで起動し、接続・クラフト・修理を確認する。
- [ ] 既存ワールドへ導入する場合、起動・アイテム取得・削除時の影響を確認する。

## 配布物

- [ ] `fabric/build/libs/reinforced_tools-fabric-<MC>-<version>.jar` を用意する。
- [ ] `neoforge/build/libs/reinforced_tools-neoforge-<MC>-<version>.jar` を用意する。
- [ ] `-sources.jar` と `-javadoc.jar` は必要に応じて添付する。
- [ ] 配布対象に開発用・テスト用・未分類のJARを含めない。
- [ ] 各JARを実際にModフォルダへ入れて起動確認する。

## 公開ページ

- [ ] プロジェクト名、短い説明、詳細説明を用意する。
- [ ] アイコンと、ツール・レシピ・Repair Kitが分かるスクリーンショットを用意する。
- [ ] 対応ゲームバージョンとローダー（Fabric、NeoForge）を各ファイルへ正しく設定する。
- [ ] Balmを必須依存関係として指定する。
- [ ] リリース種別（初回はBeta推奨）と変更履歴を設定する。
- [ ] 不具合報告先をGitHub Issuesに統一する。

## GitHub

- [ ] `main` または `master` に公開対象のコミットだけが含まれることを確認する。
- [ ] リリースタグ（例: `v0.1.0`）を作成する。
- [ ] GitHub Releaseを作成し、Fabric版・NeoForge版JARと変更履歴を添付する。
- [ ] リリースページ、README、配布ページのバージョン表記を一致させる。

## 公開後

- [ ] Modrinth / CurseForgeの公開ページと実際のダウンロードファイルを確認する。
- [ ] 新規インストール手順を別環境で再確認する。
- [ ] リリース後のクラッシュ報告・依存関係解決・ローダータグの誤りを確認する。
- [ ] 次回版の作業を開始する前に、既知の問題をIssueへ記録する。
