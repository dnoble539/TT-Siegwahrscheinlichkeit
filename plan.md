# Repo-Split: probability-math + zwei Fachdomänen-Repos

## Ziel

Das `math`-Package als eigenständige Java-Library auslagern und `tischtennis`
sowie `magic` in getrennte Repositories überführen, die diese Library
konsumieren. Nutzung rein privat, auf dem eigenen Rechner.

## Status der Umsetzung (2026-09-10)

Umgesetzt, rein lokal, kein GitHub. Ergebnis:

- `C:\Users\User\IdeaProjects\probability-math` — Branch `main`, Commit
  "Initial math library". `gradlew.bat build` grün (3 Testklassen), zusätzlich
  `publishToMavenLocal` → `~/.m2/.../com/stats/probability-math/1.0/`.
- `C:\Users\User\IdeaProjects\magic-arena-ev` — Branch `main`, Commit
  "Initial magic arena EV calculator". `gradlew.bat build` grün,
  `gradlew.bat run` liefert `Der Gewinn ... 175.30114867671125000000000` —
  zahlenidentisch zum Vor-Split-Stand (die Textdifferenz zum Anker war reines
  Konsolen-Encoding).
- `TT-Siegwahrscheinlichkeit` (dieses Repo) — Branch `split-into-repos` erstellt,
  committet und per `--no-ff` nach `main` gemergt, Branch gelöscht. **Nicht
  gepusht.** `math`/`magic`/`finance` entfernt, neuer TT-`Application`,
  `MatchTest` als Characterization-Test.

Abweichungen vom Plan unten:

- **Java-Toolchain 19 statt 21** — kein JDK 21 auf dem Rechner; installiert und
  von Gradle automatisch erkannt ist `~/.jdks/corretto-19.0.2` (das ist auch das
  JDK, mit dem das Monorepo bisher gebaut wurde).
- **Kein GitHub** — die drei Repos bleiben lokal. Schritte zum Anlegen/Pushen
  entfallen.
- **`includeBuild` dauerhaft aktiv** (nicht auskommentiert), da keine CI.
- `Match.chanceToWinMatchGiven` wirft für realistische Parameter eine
  `IllegalArgumentException` (vorbestehender, aus dem Monorepo übernommener
  Bug: `m > n` an `probabilityForMOrLessHitsInNTries`). `Application` fängt das
  ab und meldet die Berechnung als "noch nicht funktionsfähig"; `MatchTest`
  pinnt dieses Verhalten.

## Brauche ich JitPack?

**Nein.** JitPack löst das Problem "andere Leute / andere Rechner sollen meine
Library aus dem Internet ziehen können, ohne dass ich einen Publishing-Server
betreibe". Für rein private Nutzung auf einem Rechner ist das unnötig. Ebenso
entfallen `jitpack.yml`, `com.github.*`-Koordinaten, Git-Tags als Pflicht und
jegliche CI.

Stattdessen: **Gradle Composite Build** als Hauptmechanismus, `publishToMavenLocal`
als optionaler Einfrier-Weg.

### Weg 1 (empfohlen): Composite Build

In jedem App-Repo in `settings.gradle`:

```groovy
includeBuild '../probability-math'
```

Gradle ersetzt die deklarierte Dependency transparent durch das lokale
Nachbarprojekt. Kein Publishen, keine Versions-Pflege, Änderungen an der Library
sind sofort im App-Build sichtbar. Einzige Voraussetzung: `probability-math/`
liegt als Schwesterordner neben dem App-Repo.

### Weg 2 (optional): publishToMavenLocal

```
gradlew.bat publishToMavenLocal
```

Schreibt das JAR nach `~/.m2/repository/com/stats/probability-math/<version>/`.
App-Repos lesen es über `mavenLocal()`. Nützlich, wenn man einen festen
Library-Stand einfrieren will, der sich beim Weiterarbeiten an den math-Quellen
nicht mitändert. Erfordert ein erneutes `publishToMavenLocal` nach jeder
Library-Änderung.

## Versionierung — so einfach wie nötig

- **Eine** `version`-Zeile in `probability-math/build.gradle`. Der bestehende Wert
  `1.0` bleibt einfach stehen.
- Beim Composite Build wird die Version ohnehin ignoriert (Gradle substituiert
  nach `group:name`, nicht nach Version). Sie zählt nur für den optionalen
  `publishToMavenLocal`-Weg.
- Hochzählen nur dann, wenn man bewusst ein altes JAR in `~/.m2` behalten will.
  Kein SemVer-Regelwerk, kein Changelog, keine Pflicht-Tags.
- `git tag` höchstens als persönliches Lesezeichen, wenn man später den
  Library-Stand zu einem bestimmten Zeitpunkt wiederfinden möchte. Nicht nötig.
- `group` bleibt `com.stats` (kein Umbenennen auf `io.github.*`), das spart
  Änderungen und ist für lokale Nutzung bedeutungslos.

## Ziel-Repositories

| Repo | Typ | Inhalt | Hängt ab von |
|---|---|---|---|
| `probability-math` | Library (`java-library`) | `src/main/java/math/**`, `src/test/java/math/**` | JDK |
| `tt-siegwahrscheinlichkeit` | App (bestehendes Repo umbauen) | `src/main/java/tischtennis/**` + neuer TT-Einstiegspunkt | `probability-math` |
| `magic-arena-ev` | App | `src/main/java/magic/**`, `src/main/java/finance/**`, `Application.java` (aktuelle magic-Variante), `src/test/java/magic/**` | `probability-math` |

`finance` wandert mit ins Magic-Repo (einziger Consumer). Erst bei weiterem
Bedarf eine zweite Library.

## Abhängigkeitsgraph

```
math       -> JDK
finance    -> JDK
tischtennis-> math
magic      -> math, finance
Application-> magic, math.Probability
```

Azyklisch, keine Rückwärtskante aus `math` in eine Fachdomäne. Mockito ist als
Test-Dependency deklariert, wird aber nirgends verwendet — beim Split weglassen.

## Phase A — Vorbereitung (im aktuellen Repo)

1. `gradlew.bat run` ausführen, kompletten `LimitedChampionShip`-Output in eine
   Datei speichern (Regressionsanker für später).
2. `gradlew.bat test` ausführen, bestätigen dass alle 4 Testklassen grün sind.
3. In `.gitignore` die Zeile `gradle/` entfernen (sie schließt fälschlich
   `gradle/wrapper/` aus).
4. Prüfen, dass `gradle/wrapper/gradle-wrapper.jar` + `.properties` getrackt
   werden (`git status --ignored`), sonst `git add -f`.

## Phase B — Repo `probability-math` aufsetzen

5. Leeren Ordner `probability-math/` anlegen (Schwesterordner zum aktuellen
   Repo), darin `git init`.
6. Kopieren aus dem alten Repo: `gradlew`, `gradlew.bat`, `gradle/wrapper/**`.
7. `.gitignore` anlegen: `.idea/`, `build/`, `.gradle/`, `out/`.
8. `LICENSE` + `README.md` anlegen (kurz, privat — reicht ein Einzeiler).
9. `src/main/java/math/` anlegen, die 4 Dateien `MathFunctions.java`,
   `Polynom.java`, `Probability.java`, `StochasticFunctions.java` hineinkopieren
   (`package math;` bleibt).
10. `src/test/java/math/` anlegen, `MathFunctionsTest.java`, `PolynomTest.java`,
    `StochasticFunctionsTest.java` hineinkopieren.
11. `settings.gradle`: `rootProject.name = 'probability-math'`.
12. `build.gradle` schreiben:
    ```groovy
    plugins {
        id 'java-library'
        id 'maven-publish'   // nur für den optionalen publishToMavenLocal-Weg
    }

    group = 'com.stats'
    version = '1.0'

    java {
        toolchain { languageVersion = JavaLanguageVersion.of(19) }
        withSourcesJar()
    }

    repositories { mavenCentral() }

    dependencies {
        testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
        testImplementation 'org.junit.jupiter:junit-jupiter-params:5.10.2'
        testImplementation 'org.assertj:assertj-core:3.25.2'
    }

    test { useJUnitPlatform() }

    publishing {
        publications { maven(MavenPublication) { from components.java } }
    }
    ```
13. `gradlew.bat build` — muss grün sein, 3 Testklassen laufen.
14. `git add . && git commit -m "Initial math library"`.
15. Optional: GitHub-Repo anlegen und pushen (nur als Backup/Sync, nicht für
    Distribution nötig).

## Phase C — Repo `magic-arena-ev` aufsetzen

16. Ordner `magic-arena-ev/` anlegen (Schwesterordner), `git init`.
17. `gradlew`, `gradlew.bat`, `gradle/wrapper/**`, `.gitignore`, `LICENSE`,
    `README.md` anlegen/kopieren.
18. Kopieren: kompletter Ordner `src/main/java/magic/` (11 Dateien).
19. Kopieren: kompletter Ordner `src/main/java/finance/` (3 Dateien).
20. Kopieren: `src/main/java/Application.java` (aktuelle, magic-verdrahtete
    Version) nach `src/main/java/`.
21. Kopieren: `src/test/java/magic/MagicEventTest.java`.
22. `settings.gradle`:
    ```groovy
    rootProject.name = 'magic-arena-ev'
    includeBuild '../probability-math'
    ```
23. `build.gradle` schreiben:
    ```groovy
    plugins { id 'application' }

    group = 'com.stats'
    version = '1.0'

    java {
        toolchain { languageVersion = JavaLanguageVersion.of(19) }
    }

    repositories {
        mavenCentral()
        mavenLocal()   // Fallback, falls man doch ohne Composite Build baut
    }

    dependencies {
        implementation 'com.stats:probability-math:1.0'
        testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
        testImplementation 'org.junit.jupiter:junit-jupiter-params:5.10.2'
        testImplementation 'org.assertj:assertj-core:3.25.2'
    }

    application { mainClass = 'Application' }

    test { useJUnitPlatform() }
    ```
24. `gradlew.bat test` — `MagicEventTest` muss grün sein.
25. `gradlew.bat run` — Output mit dem Regressionsanker aus Schritt 1
    vergleichen (muss identisch sein).
26. `CLAUDE.md` anlegen mit nur den Magic-/Finance-/Application-Abschnitten,
    Build-Kommandos angepasst.
27. `git add . && git commit`. Optional GitHub-Repo + push.

## Phase D — Repo `tt-siegwahrscheinlichkeit` (bestehendes Repo umbauen)

28. Auf `main` neuen Branch `split-into-repos` anlegen.
29. Löschen: `src/main/java/math/`, `src/main/java/magic/`,
    `src/main/java/finance/`, `src/test/java/magic/`, `src/test/java/math/`.
30. `src/main/java/Application.java` durch einen neuen, TT-spezifischen
    Einstiegspunkt ersetzen (der bisherige rief Magic auf), der
    `tischtennis`-Klassen nutzt.
31. `settings.gradle`:
    ```groovy
    rootProject.name = 'tt-siegwahrscheinlichkeit'
    includeBuild '../probability-math'
    ```
32. `build.gradle` umschreiben:
    ```groovy
    plugins { id 'application' }

    group = 'com.stats'
    version = '1.0'

    java {
        toolchain { languageVersion = JavaLanguageVersion.of(19) }
    }

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        implementation 'com.stats:probability-math:1.0'
        testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
        testImplementation 'org.junit.jupiter:junit-jupiter-params:5.10.2'
        testImplementation 'org.assertj:assertj-core:3.25.2'
    }

    application { mainClass = '...' }   // neuer TT-Einstiegspunkt

    test { useJUnitPlatform() }
    ```
33. `gradlew.bat build` — kompiliert gegen die Library.
34. Optional: Characterization-Tests für
    `Match.chanceToWinMatchGiven(Probability)` ergänzen (`tischtennis` hat
    bisher keine Tests). `gradlew.bat test` grün.
35. `CLAUDE.md` auf den Tischtennis-Teil reduzieren; Architektur-Abschnitt zu
    `magic`/`finance`/`math` entfernen, Hinweis auf die externe Library ergänzen.
36. Branch mergen.

## Phase E — Abschluss

37. Nach erfolgreicher Verifikation (alle drei Repos bauen, `run`-Output von
    `magic-arena-ev` deckt sich mit dem Anker): die drei Repos sind
    eigenständig. Keine CI, kein Release-Prozess.
38. In beiden App-READMEs einen Zweizeiler notieren: "probability-math muss als
    Schwesterordner ausgecheckt sein" bzw. "alternativ `gradlew.bat
    publishToMavenLocal` in probability-math".

## Stolpersteine

- `gradle/` in `.gitignore` **vor** dem Kopieren fixen, sonst fehlt der Wrapper
  in den neuen Repos.
- Ohne gepinnte Toolchain baut es lokal anders als erwartet (System-`java` ist
  1.8, Gradle 8.14 läuft über ein anderes JDK) — in allen drei Repos
  `JavaLanguageVersion.of(19)` setzen.
- Default-Package `Application.java` lässt sich nicht importieren — unkritisch,
  jedes App-Repo bekommt seinen eigenen Einstiegspunkt.
- Composite Build braucht `probability-math/` als Nachbarordner; fehlt er,
  greift Gradle auf `mavenLocal()` zurück und braucht dort ein publiziertes JAR.
- `SNAPSHOT` vermeiden; feste Version, für lokale Iteration den Composite Build
  nutzen statt Versionen hochzuzählen.
- `tischtennis` hat null Tests und
  `Match.chanceToWinBallwechselGivenMatchWinPercentage` /
  `TtrCalculator.calculateWinPercentage` sind `return null`-Stubs — der Split
  behebt das nicht.
