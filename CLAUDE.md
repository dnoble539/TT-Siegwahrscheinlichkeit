# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

Windows: use `gradlew.bat`. Bash/git-bash: use `./gradlew`.

- Build: `gradlew.bat build`
- Run all tests: `gradlew.bat test`
- Run a single test class: `gradlew.bat test --tests "tischtennis.MatchTest"`
- Run the app: `gradlew.bat run` (the `application` plugin is configured, `mainClass = 'Application'`)

Requires a JDK 21 (Gradle toolchain, auto-detected from `~/.jdks/` etc.).

Test reports land in `build/reports/tests/test/index.html`.

## External dependency: probability-math (MathLib)

The probability engine (`math` package: `Probability`, `MathFunctions`,
`StochasticFunctions`, `Polynom`) lives in the separate `probability-math`
GitHub repo (published as `MathLib`) and is consumed via
[JitPack](https://jitpack.io) as `com.github.dnoble539:MathLib:v1.1.0` (see
`maven { url 'https://jitpack.io' }` in `repositories` and the `implementation`
line in `build.gradle`). JitPack builds the tagged commit on demand — no sibling
checkout or local publish is required. To pick up a new library release, bump
the version string in the `implementation` line to the new git tag from the
`probability-math` repo.

Do not vendor a copy of the `math` code back into this repo. The `magic` /
`finance` EV calculator that used to live here moved to the `magic-arena-ev`
repo.

## Architecture

Table tennis match win probability from TTR (Tischtennis-Rating) point
difference. German domain vocabulary throughout: TTR, Satz (set), Ballwechsel
(rally), Verlängerung (deuce/overtime). All arithmetic uses `BigDecimal` /
`MathContext.DECIMAL32` — don't switch to `double`.

- `Match.chanceToWinMatchGiven(Probability)` is meant to compose rally-level
  probabilities (`calculateProbabilityForLoosingSetGivenMatchWinPercentage`) into
  a full best-of-N match probability via `StochasticFunctions`.
- **Known pre-existing defect** (carried over from the monorepo, pinned by
  `MatchTest`): `chanceToWinMatchGiven` passes `(n, m)` with `m > n` to
  `StochasticFunctions.probabilityForMOrLessHitsInNTries`, violating that
  method's `tries <= hits` guard, so it throws `IllegalArgumentException` for
  realistic parameters. `Application` catches this and reports the calculation as
  not yet functional.
- `Match.chanceToWinBallwechselGivenMatchWinPercentage` and
  `TtrCalculator.calculateWinPercentage` are unimplemented stubs (`return null`)
  — the TTR-difference → per-rally-probability formula from the README is not yet
  wired up. `Player.chanceToWinVs` depends on that stub.
