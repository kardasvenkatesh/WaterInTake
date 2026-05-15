# 📱 DroidStack — The Ultimate Android Template

DroidStack is a production-ready, clean-architecture Android template designed for rapid development. Built with the latest modern stack, it provides a solid foundation for both beginners and professional freelance developers.

![Android](https://img.shields.io/badge/Platform-Android-brightgreen.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg)
![License](https://img.shields.io/badge/License-MIT-purple.svg)

---

## 🚀 Key Features

- **Architecture**: Simple MVVM with Clean Architecture principles.
- **UI Toolkit**: 100% Jetpack Compose with Material 3.
- **Dependency Injection**: Hilt (Dagger) for seamless DI.
- **Persistence**: Room Database + DataStore Preferences.
- **Navigation**: Type-safe Navigation Compose with a centralized graph.
- **Networking**: Pre-configured for future Retrofit integration (optional).
- **Asynchronous**: Kotlin Coroutines & Flow for reactive state management.
- **Image Loading**: Coil for high-performance image loading.
- **Theming**: Premium dark/light mode support with Material 3 Dynamic Color (optional).
- **Quality**: ProGuard/R8 rules pre-configured for release.
- **AI Ready**: Includes a custom `droidstack` skill and official [Google Android Skills](https://github.com/android/skills) to guide AI coding assistants (like Antigravity or Gemini) to follow expert Android patterns.
- **Agentic Setup**: Pre-configured with `.agents/skills` to provide zero-config support for agentic development.

---

## 🤖 AI-Native Development (Agentic Support)

This project is built for the future of development. It includes **Agent Skills**, which are modular instructions that your AI coding assistant (Antigravity, Gemini, etc.) automatically reads to help you build faster and better.

### Included Skills:
- **`droidstack`**: Custom skill for this template's architecture (Simple MVVM, No UseCases).
- **Official Android Skills**: Includes Google's official expertise for:
    - **AGP Upgrades**: Seamless Gradle plugin updates.
    - **Compose Migrations**: Best practices for modern UI.
    - **R8 Analyzer**: Troubleshooting release builds.
    - **Edge-to-Edge**: Perfecting immersive layouts.
    - **Play Billing**: Handling Google Play transactions.

These skills are stored in the `.agents/skills/` directory. They **do not** increase your app's APK size and are only used to help the developer work more efficiently with AI.

```text
com.template.app/
├── data/           # Data Sources (Room, DataStore, Repository Implementation)
├── domain/         # Business Rules (Models, Repository Interfaces)
├── presentation/   # UI (Screens, ViewModels, Components, Navigation, Theme)
├── di/             # Dependency Injection Modules
└── util/           # Helpers, Extensions, Constants
```

---

## 🛠️ How to Use

> [!TIP]
> **Beginner ho?** Agar aap non-coder hain toh pehle [**BEGINNER_GUIDE.md**](BEGINNER_GUIDE.md) dekhein.

1. **Clone the repository**:
   ```bash
   git clone https://github.com/ThecodingShef/DroidStack-Template.git
   ```

2. **Personalize**:
   - Replace `com.template.app` with your package name (Global search & replace).
   - Rename the app in `res/values/strings.xml` (`app_name`).
   - Customize colors in `presentation/theme/Color.kt`.

3. **Start Building**:
   - Add new features in `presentation/screens/`.
   - Define entities in `data/local/entity/`.
   - Update navigation in `presentation/navigation/Screen.kt`.

---

## 🤖 AI-First Development

This project is optimized for AI-assisted coding. The `AGENTS.md` file provides strict rules for AI agents to:
- Prevent hardcoded strings.
- Ensure proper import management.
- Maintain architecture integrity.
- Follow the custom design system.

---

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Created with ❤️ by **thecodingshef**
