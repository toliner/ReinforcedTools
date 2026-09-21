# Repository Guidelines

## Project Structure & Module Organization

This is a Minecraft 26.1.2 multi-loader mod built with Balm. Shared gameplay code lives in `common/src/main/java/dev/toliner/reinforcedtools`; keep item definitions, tool behavior, and data generation there. Loader entry points and metadata belong in `fabric/` and `neoforge/` under their respective `src/main` trees. Fabric GameTests are in `fabric/src/gametest`; reusable GameTest assertions are in `common/src/main/java/.../gametest`.

`ReinforcedToolsDataGenerator` produces recipes, models, tags, translations, and the GameTest structure. Generated output is placed under `build/generated-resources`; do not hand-edit it. Version and mod metadata are defined in `gradle.properties`, and dependency versions are centralized in `gradle/libs.versions.toml`.

## Build, Test, and Development Commands

Use the pinned Java toolchain through mise:

```sh
mise exec -- ./gradlew build
mise exec -- ./gradlew :fabric:runClient
mise exec -- ./gradlew :neoforge:runClient
mise exec -- ./gradlew :fabric:test :neoforge:runGameTestServer
```

`build` compiles both loaders, generates data, and packages JARs. Run each client task when a loader-specific change needs manual verification. The final command executes the Fabric and NeoForge GameTest suites; run it for gameplay, recipe, or generated-data changes.

## Coding Style & Naming Conventions

Write Java with four-space indentation, braces on the same line, and explicit, readable names. Use `PascalCase` for types, `camelCase` for methods and fields, and lowercase snake_case for resource IDs (for example, `reinforced_diamond_pickaxe`). Keep loader-specific integration thin; add cross-loader behavior to `common` first. No formatter or linter is configured, so match the surrounding code and avoid unrelated reformatting.

## Testing Guidelines

Add or update GameTests for observable behavior. Use descriptive test names such as `repairKitRestoresQuarterDurability`; place Fabric registrations in `fabric/src/gametest` and share assertions through the common GameTest package. Verify both loaders with the combined GameTest command above. There is no stated coverage threshold.

## Commit & Pull Request Guidelines

Use short, imperative commit subjects, following existing history: `Implement reinforced tools and GameTests` or `Change license to MPL-2.0`. Keep each commit focused. PRs should explain player-visible changes, list the Gradle commands run, link related issues, and include screenshots for visual or UI changes. Update `CHANGELOG.md` and generated assets when a release-facing feature changes.
