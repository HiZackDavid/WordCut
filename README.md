<h1 align="center">🎮 WordCut</h1>
<p align="center">
  A bilingual Android word puzzle game built with Kotlin and Jetpack Compose.
</p>
<p align="center">
  <a href="https://developer.android.com/compose" alt="Android">
    <img src="https://img.shields.io/badge/Platform-Android-green?logo=android" /></a>
  <a href="https://developer.android.com/compose" alt="Jetpack compose">
    <img src="https://img.shields.io/badge/UI_Toolkit-Jetpack_Compose-4285F4?logo=jetpackcompose" /></a>
  <a href="https://kotlinlang.org" alt="Kotlin">
    <img src="https://img.shields.io/badge/Language-Kotlin-purple?logo=kotlin" /></a>
  <a href="https://www.etsmtl.ca/etudes/cours/log450" alt="ÉTS">
    <img src="https://img.shields.io/badge/ÉTS-LOG450-red" /></a>
  <hr />
</p>

<div align="center">

🎓 École de technologie supérieure (ÉTS)

📱 LOG450 – Conception d’applications mobiles

</div>

# 📖 Overview

**WordCut** is a mobile word puzzle game in which the player progressively transforms a word into shorter valid words.

Each round begins with a randomly selected word containing at least five letters. The player must create a new valid word using only letters from the current word while removing between one and three letters at each step.

The challenge is not simply to find shorter words: the scoring system rewards both efficient letter removal and rearranging the order of the remaining letters.

WordCut currently supports **French and English dictionaries** stored locally in the application, allowing the game to run without requiring a network connection.

# 🎮 Gameplay

A game starts by selecting a random word from the currently selected dictionary.

For each turn, the player:

1. Creates a new word using letters available in the current word.
2. Removes between **1 and 3 letters**.
3. Submits the word.
4. The application verifies that:
   - the word exists in the selected dictionary;
   - the submitted letters all come from the current word;
   - no letter is used more times than it appears in the source word;
   - between 1 and 3 letters were removed.
5. If valid, the submitted word becomes the source word for the next turn.

The player can enter letters using the custom on-screen keyboard and remove entered letters before submitting.

# 🏆 Scoring

WordCut rewards the player based on how aggressively the word is shortened.

| Letters removed | Points |
| --------------- | -----: |
| 1 letter        |      3 |
| 2 letters       |      2 |
| 3 letters       |      1 |

An additional **2-point bonus** is awarded when the remaining letters are rearranged instead of preserving their original order.

For example:

```text
MATELAS
   ↓
METAL
   ↓
LAME
   ↓
AME
```

The game evaluates each submitted row independently and displays the earned score alongside it.

# ⏱️ Timed Gameplay

Each game currently starts with a **120-second countdown**.

The timer:

- starts when a new game is created;
- pauses while the initial game-information dialog is displayed;
- prevents additional input once time expires;
- resets whenever the player restarts the game or changes dictionaries.

# 🌍 Dictionaries

WordCut currently includes two bundled dictionaries:

- French
- English

The player can switch dictionaries from the game interface.

Changing the dictionary automatically starts a new game using a random word from the selected language.

Dictionary files are stored locally under:

```text
app/src/main/assets/dictionaries/
├── francais.txt
└── english.txt
```

The dictionary data source:

- loads words from Android assets;
- normalizes words to uppercase;
- caches loaded dictionaries in memory;
- verifies submitted words;
- selects random starting words of at least five letters.

Because dictionaries are packaged with the application, gameplay does not depend on an external API or internet connection.

# ✨ Features

- 🎲 Random starting-word generation
- French dictionary support
- English dictionary support
- 🔤 Custom Compose keyboard
- ⌫ Letter deletion
- ✅ Dictionary-based word validation
- 🧮 Multi-part scoring system
- ⏱️ Two-minute countdown timer
- 🔄 Game restart
- 🌍 Runtime dictionary selection
- 🚩 Language flags
- ℹ️ In-app game instructions
- 📱 Fully implemented with Jetpack Compose
- 💉 Dependency injection with Hilt
- 🧱 Layered architecture separating UI, domain, and data logic

# 🏗️ Architecture

WordCut follows a layered architecture inspired by Clean Architecture principles.

```text
UI
│
├── Screens
├── Layouts
├── Components
├── UI Models
└── ViewModels
        │
        ▼
Domain
│
├── Models
├── Use Cases
├── Repository Interfaces
└── Utilities
        │
        ▼
Data
│
├── Repository Implementations
└── Data Sources
        │
        ▼
Local dictionary assets
```

The goal is to keep game rules independent from Android UI code and from the source used to retrieve dictionary words.

# 🧠 Domain Layer

The domain layer contains the core game rules.

## Models

### `GameState`

Represents the current game state, including:

- original starting word;
- active/current word;
- submitted and active rows;
- current row;
- available letter counts;
- game-over state.

### `GameRow`

Represents one row in the progression:

- entered letters;
- maximum active letter count;
- whether the row has been committed;
- scoring values.

### `Dictionary`

Represents an available dictionary independently of how it is stored.

The model supports different possible sources:

```kotlin
sealed interface DictionarySource {
    data class Asset(val fileName: String) : DictionarySource
    data class DeviceFile(val filePath: String) : DictionarySource
    data class Url(val url: String) : DictionarySource
}
```

Only asset-backed dictionaries are currently used, but the domain model leaves room for other dictionary sources later.

# ⚙️ Use Cases

Game actions are implemented as individual use cases.

## `StartGameUseCase`

- retrieves a random word from the selected dictionary;
- ensures starting words contain at least five letters;
- creates the initial game rows;
- initializes the remaining-letter counts.

## `TypeLetterUseCase`

- adds a letter to the active row;
- prevents unavailable letters from being entered;
- prevents using a letter more times than it exists in the current word;
- respects the maximum size of the active row.

## `DeleteLetterUseCase`

- removes the most recently entered letter;
- restores that letter to the available keyboard counts.

## `SubmitWordUseCase`

Contains the primary validation and scoring rules.

A submitted word must:

- contain only letters from the source word;
- respect the multiplicity of those letters;
- remove between 1 and 3 letters;
- exist in the selected dictionary.

After validation, the use case calculates the score and creates the next playable row.

# 💾 Data Layer

The data layer abstracts access to dictionary content.

## `WordRepository`

The domain depends only on this interface:

```kotlin
interface WordRepository {
    suspend fun getRandomWord(dictionaryId: String): String
    suspend fun isValidWord(dictionaryId: String, word: String): Boolean
}
```

This keeps the game logic independent from the storage implementation.

## `LocalWordRepository`

The current implementation delegates dictionary operations to an asset-backed data source.

## `AssetDictionaryDataSource`

Loads dictionary files from:

```text
assets/dictionaries/
```

Loaded dictionaries are cached using an in-memory map so the same file does not need to be parsed repeatedly during gameplay.

# 🖥️ UI Layer

The interface is built entirely with **Jetpack Compose**.

The UI layer includes:

```text
ui/
├── components/
│   ├── cells/
│   └── dialogs/
├── layouts/
├── models/
├── screens/
├── theme/
└── viewmodels/
```

## `GameScreen`

The main screen coordinates:

- the game board;
- custom keyboard;
- timer;
- restart action;
- dictionary picker;
- information dialog.

The screen observes `GameUiState` exposed by `GameViewModel` and forwards user interactions back to the ViewModel.

## `GameViewModel`

`GameViewModel` orchestrates the application's game flow.

It:

- starts and resets games;
- maintains the selected dictionary;
- executes game use cases;
- manages the two-minute countdown;
- pauses gameplay when necessary;
- exposes immutable UI state through `StateFlow`.

```text
GameScreen
    │
    ▼
GameViewModel
    │
    ├── StartGameUseCase
    ├── TypeLetterUseCase
    ├── DeleteLetterUseCase
    └── SubmitWordUseCase
            │
            ▼
      WordRepository
            │
            ▼
 AssetDictionaryDataSource
```

# 💉 Dependency Injection

WordCut uses **Hilt** for dependency injection.

`WordCutApp` is annotated with:

```kotlin
@HiltAndroidApp
```

while `MainActivity` uses:

```kotlin
@AndroidEntryPoint
```

`AppModule` provides the repository and game use cases required by `GameViewModel`.

This keeps object creation outside of the UI and makes dependencies explicit.

# 🛠️ Tech Stack

| Technology           | Purpose                           |
| -------------------- | --------------------------------- |
| Kotlin               | Application language              |
| Jetpack Compose      | Declarative UI                    |
| Material 3           | UI components                     |
| ViewModel            | UI state management               |
| Kotlin Coroutines    | Timer and asynchronous operations |
| StateFlow            | Reactive UI state                 |
| Hilt                 | Dependency injection              |
| KSP                  | Hilt annotation processing        |
| FlagKit Compose      | Dictionary/language flags         |
| Android AssetManager | Local dictionary loading          |

# 📁 Project Structure

```text
app/src/main/
├── assets/
│   └── dictionaries/
│       ├── english.txt
│       └── francais.txt
│
├── java/com.example.wordcut/
│   ├── data/
│   │   ├── datasources/
│   │   │   ├── AssetDictionaryDataSource.kt
│   │   │   └── LocalWordDataSource.kt
│   │   └── repositories/
│   │       └── LocalWordRepository.kt
│   │
│   ├── di/
│   │   └── AppModule.kt
│   │
│   ├── domain/
│   │   ├── models/
│   │   ├── repositories/
│   │   ├── usecases/
│   │   └── utils/
│   │
│   ├── ui/
│   │   ├── components/
│   │   ├── layouts/
│   │   ├── models/
│   │   ├── screens/
│   │   ├── theme/
│   │   └── viewmodels/
│   │
│   ├── utils/
│   │
│   ├── MainActivity.kt
│   └── WordCutApp.kt
│
└── res/
```

# 🚀 Getting Started

## Requirements

You will need:

- Android Studio
- JDK 11+
- Android SDK 24 or newer
- Gradle wrapper included with the project

The application currently uses:

```text
Minimum SDK: 24
Target SDK: 36
Compile SDK: 36
```

## Clone the Repository

```bash
git clone https://github.com/HiZackDavid/WordCut.git
cd WordCut
```

## Build the Project

On Windows:

```powershell
.\gradlew.bat build
```

On macOS/Linux:

```bash
./gradlew build
```

You can also open the project directly in Android Studio and allow Gradle to synchronize the dependencies.

## Run the Application

1. Open WordCut in Android Studio.
2. Start an Android emulator or connect a physical Android device.
3. Run the `app` configuration.
4. The game will automatically initialize when the application starts.

No API key, backend service, or network configuration is required.

# 📦 Main Dependencies

The current project includes:

- AndroidX Core KTX
- Jetpack Compose
- Material 3
- Android Lifecycle / ViewModel
- Hilt
- Hilt Navigation Compose
- KSP
- FlagKit Compose

Dependency versions are managed using a Gradle version catalog in:

```text
gradle/libs.versions.toml
```

# 💡 Design Decisions

## Local Dictionaries

Word validation is intentionally backed by local asset files rather than a remote service.

This provides:

- offline gameplay;
- predictable word validation;
- fast dictionary lookup after initial loading;
- no external API dependency.

## Repository Abstraction

Although dictionaries are currently loaded from assets, the domain layer uses `WordRepository` instead of directly accessing Android resources.

This makes it possible to introduce other dictionary sources later without changing the core game rules.

The existing `DictionarySource` model already anticipates possibilities such as:

- local device files;
- remote URL-based dictionaries.

## Use-Case-Oriented Game Logic

Typing, deleting, starting, and submitting words are represented by separate use cases instead of being implemented directly inside `GameViewModel`.

This keeps the ViewModel focused on orchestration and state management.

# 🔮 Possible Improvements

The current architecture leaves room for several future additions:

- additional dictionaries and languages;
- user-imported dictionary files;
- remote dictionary sources;
- persistent high scores;
- difficulty settings;
- configurable game duration;
- game history;
- statistics;
- animations and richer game-over feedback;
- automated tests for game rules and scoring;
- accessibility improvements.
