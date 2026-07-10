# ReinforcedTools

Minecraft 26.1.2向けの、Balmを基盤にしたFabric / NeoForgeマルチローダーMod開発環境です。

## 前提

- mise
- Java 25（`.mise.toml`で固定）
- IntelliJ IDEA 2025.3以降推奨

## セットアップ

```sh
mise trust
mise install
mise exec -- ./gradlew tasks
```

IDEではこのディレクトリをGradleプロジェクトとして開きます。実行構成は各ローダーのGradleタスクから生成されます。

```sh
mise exec -- ./gradlew :fabric:runClient
mise exec -- ./gradlew :neoforge:runClient
mise exec -- ./gradlew build
```

共通コードは `common/`、ローダー固有コードは `fabric/` と `neoforge/` に置きます。Mod登録や設定は共通ソースでBalm APIを利用します。
