# ShefStack — Android Template by thecodingshef

## Project Identity
- Template Name : ShefStack
- Created by    : thecodingshef
- Language      : Kotlin
- UI Toolkit    : Jetpack Compose + Material 3
- Architecture  : Clean Architecture + Simple MVVM
- Min SDK       : 26 (Android 8.0)
- Target SDK    : 35
- Package       : com.shefstack.android

---

## Architecture — Layers (Never Mix Them)

```
presentation/  → UI only (Screens, ViewModels, Components)
domain/        → Business rules & contracts (Models, Repository interfaces)
data/          → Data sources only (Room, DataStore, RepositoryImpl)
di/            → Dependency Injection only (Hilt Modules)
util/          → Helpers, Extensions, Constants
```

### Layer Communication Rules
- Screen → ViewModel only (never Repository directly)
- ViewModel → Repository interface only (never DAO directly)
- Repository → DAO / DataStore only

---

## Tech Stack (Do Not Add Libraries Without Asking)

| Category      | Library                           |
|---------------|-----------------------------------|
| UI            | Jetpack Compose, Material 3       |
| Navigation    | Navigation Compose                |
| DI            | Hilt                              |
| Database      | Room + KSP                        |
| Preferences   | DataStore                         |
| Background    | WorkManager                       |
| Image Loading | Coil                              |
| Logging       | Timber                            |
| Async         | Kotlin Coroutines + Flow          |
| Serialization | Kotlin Serialization              |

---

## Coding Rules

### General
- One feature per prompt — never mix multiple features in one response
- Modify only files needed for the requested feature
- Do not refactor unrelated code
- Do not delete existing code unless explicitly asked
- Add KDoc comments on all public functions and classes
- Keep functions small and single-purpose
- Use descriptive variable and function names
- Avoid complex one-liners — prefer readable multi-line code

### Strings & Resources
- No hardcoded strings — always use strings.xml
- No hardcoded colors — always use theme tokens from Color.kt
- No hardcoded dimensions — always use dp / sp values

### Beginner Friendliness
- Add inline comments explaining WHY not just WHAT
- Add // TODO: comments wherever the developer needs to customize
- Keep code readable over clever

---

## Import Rules — Read Before Every File

### Rule 1: No Fully-Qualified Inline Names
```kotlin
// WRONG
com.shefstack.android.presentation.components.AppButton()

// CORRECT
import com.shefstack.android.presentation.components.AppButton
AppButton()
```

### Rule 2: No Hallucinated Imports
- Never import from com.example.*, com.demo.*, or any package that does not exist in this project
- Only import packages that actually exist in the codebase

### Rule 3: Verify Custom Components Before Using
- Before using any custom component, check if it exists in presentation/components/
- If it exists → import and use it
- If it does not exist → create it in presentation/components/ first, then use it
- Never assume a component exists

### Rule 4: Default to Material 3
- Always use standard Material 3 Jetpack Compose components by default
- Only use custom components if they already exist in presentation/components/ or are explicitly requested

### Rule 5: Internal Package
- All internal imports must start with: com.shefstack.android

### Rule 6: No Hardcoded Strings
- Never use hardcoded strings in Composable files.
- Always use `stringResource(id = R.string.string_name)`.
- If a string is missing, add it to `res/values/strings.xml` first.
```kotlin
// WRONG
Text(text = "Hello World")

// CORRECT
Text(text = stringResource(id = R.string.hello_world))
```

---

## UiState Rules — Mandatory for Every Screen

```kotlin
sealed class UiState<out T> {
    object Idle    : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Empty   : UiState<Nothing>()
}
```

Every screen composable must handle all 5 states:
- Idle    → show nothing or default UI
- Loading → show LoadingView() or ShimmerEffect()
- Success → show actual content
- Error   → show ErrorView() with retry option
- Empty   → show EmptyStateView() with action button

---

## Folder Structure — Always Follow This

```
com.shefstack.android/
├── data/
│   ├── local/
│   │   ├── dao/
│   │   ├── entity/
│   │   └── converter/
│   ├── repository/
│   └── datastore/
├── domain/
│   ├── model/
│   └── repository/
├── presentation/
│   ├── screens/
│   │   └── [featurename]/
│   │       ├── FeatureScreen.kt
│   │       ├── FeatureViewModel.kt
│   │       └── FeatureUiState.kt
│   ├── components/
│   ├── navigation/
│   └── theme/
├── di/
└── util/
```

New screen rule: Every new screen gets its own folder inside presentation/screens/

---

## Prompt Format — Use This Every Time

```
Following AGENTS.md, implement [feature name].

User should be able to:
- [action 1]
- [action 2]
- [action 3]

Constraints:
- Modify only files needed for this feature
- Do not refactor unrelated code
- Do not add new libraries unless required
- Add comments explaining the code
- After changes, list: files changed + how to test
```

## Error Fix Format

```
Following AGENTS.md, fix this error only.

Error:
[PASTE ERROR HERE]

Constraints:
- Fix only the root cause
- Do not change unrelated files
- Explain what was wrong in simple words
```

---

## After Every Response — AI Must Show

1. Files Changed:
   - List every file that was created or modified

2. How to Test:
   - Step by step: what to tap, what should appear

3. What to Customize:
   - List all TODO comments added and what they mean

---

## What This Template Includes (Do Not Re-Create)

- Material 3 Theme (Color.kt, Type.kt, Shape.kt, Theme.kt)
- Navigation setup (Screen.kt, AppNavGraph.kt)
- Room database base setup (AppDatabase.kt)
- DataStore preferences (AppPreferences.kt)
- Hilt DI modules (AppModule, DatabaseModule, RepositoryModule)
- Reusable components:
  - AppButton (Primary, Secondary, Text variants)
  - AppTextField (with error state + password toggle)
  - AppCard (clickable + non-clickable)
  - AppTopBar (with back button + actions)
  - AppBottomBar (with nav items)
  - LoadingView + ShimmerEffect
  - EmptyStateView
  - ErrorView
- UiState sealed class (util/UiState.kt)
- Extensions (util/Extensions.kt)
- Constants (util/Constants.kt)
- NotificationHelper (util/NotificationHelper.kt)

---

## Do Not Ever
- Add Firebase unless explicitly asked
- Add Retrofit unless explicitly asked
- Add any paid or analytics SDK
- Create duplicate components that already exist
- Use lateinit var in ViewModels (use StateFlow instead)
- Use LiveData (use StateFlow + Flow only)
- Use findViewById (Compose only)
- Write business logic inside a Screen composable
- Write UI logic inside a Repository
