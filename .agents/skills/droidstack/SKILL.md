---
name: droidstack
description: Orchestrates Android development tasks for the ShefStack template. Use when implementing new features, fixing bugs, or managing architecture in this repository.
---

# DroidStack Skill

This skill provides comprehensive instructions for building and maintaining the ShefStack Android template. It ensures consistency across architecture, coding style, and resource management.

## When to use this skill

- Use this when implementing a new feature or screen.
- Use this when refactoring existing code or fixing bugs.
- Use this when adding new dependencies or updating the tech stack.

## Architecture Guidelines (Simple MVVM)

Always follow the 3-layer architecture:
- **presentation/**: UI only (Screens, ViewModels, Components, Navigation).
- **domain/**: Business rules and contracts (Models, Repository interfaces).
- **data/**: Data sources only (Room, DataStore, Repository Implementation).

### Layer Communication
- **Screen** → **ViewModel** only.
- **ViewModel** → **Repository interface** only.
- **Repository** → **DAO / DataStore** only.

## Coding Rules

### 1. No Hardcoded Strings
- Never use hardcoded strings in Composables.
- Always use `stringResource(id = R.string.name)`.
- Add new strings to `res/values/strings.xml` first.

### 2. Import Management
- **No Fully-Qualified Inline Names**: Use standard imports at the top of the file.
- **No Hallucinated Imports**: Only import packages that exist in the codebase.
- **Package Consistency**: Internal imports must start with `com.template.app`.

### 3. Component Usage
- Always prefer Material 3 native components.
- Check `presentation/components/` before creating a new component.

### 4. UI State Management
Every screen must handle all 5 `UiState` cases:
- `Idle`: Default/blank state.
- `Loading`: Show `LoadingView`.
- `Success`: Show content.
- `Error`: Show `EmptyStateView` with the error message.
- `Empty`: Show `EmptyStateView` with an action button.

## Folder Structure

```text
com.template.app/
├── data/           # local/ (dao, entity), repository/, datastore/
├── domain/         # model/, repository/
├── presentation/   # screens/[feature]/, components/, navigation/, theme/
└── di/             # Hilt modules
```

---
Created by **thecodingshef**
