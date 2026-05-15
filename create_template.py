import os

files = {}

files["build.gradle.kts"] = """plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}
"""

files["app/build.gradle.kts"] = """plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.template.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.template.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // TODO: Add signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    
    // Accompanist Permissions
    implementation(libs.accompanist.permissions)

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Coil
    implementation(libs.coil.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Timber
    implementation(libs.timber)
}
"""

files["gradle/libs.versions.toml"] = """[versions]
agp = "8.4.0"
kotlin = "1.9.23"
coreKtx = "1.13.1"
lifecycleRuntimeKtx = "2.7.0"
activityCompose = "1.9.0"
composeBom = "2024.05.00"
navigationCompose = "2.7.7"
hilt = "2.51.1"
hiltNavigationCompose = "1.2.0"
room = "2.6.1"
datastore = "1.1.1"
workManager = "2.9.0"
hiltWork = "1.2.0"
accompanist = "0.34.0"
splashscreen = "1.0.1"
coil = "2.6.0"
coroutines = "1.8.0"
timber = "5.0.1"
ksp = "1.9.23-1.0.20"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }

hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }
androidx-hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "hiltNavigationCompose" }

androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }

androidx-datastore-preferences = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastore" }

androidx-work-runtime-ktx = { group = "androidx.work", name = "work-runtime-ktx", version.ref = "workManager" }
androidx-hilt-work = { group = "androidx.hilt", name = "hilt-work", version.ref = "hiltWork" }

accompanist-permissions = { group = "com.google.accompanist", name = "accompanist-permissions", version.ref = "accompanist" }

androidx-core-splashscreen = { group = "androidx.core", name = "core-splashscreen", version.ref = "splashscreen" }

coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }

kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }

timber = { group = "com.jakewharton.timber", name = "timber", version.ref = "timber" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
"""

files["app/src/main/java/com/template/app/MainApplication.kt"] = """package com.template.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Main application class.
 */
@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Timber for logging
        Timber.plant(Timber.DebugTree())
    }
}
"""

files["app/src/main/java/com/template/app/MainActivity.kt"] = """package com.template.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.template.app.presentation.navigation.AppNavGraph
import com.template.app.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle splash screen transition
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        // Edge-to-edge layout
        enableEdgeToEdge()
        
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph()
                }
            }
        }
    }
}
"""

files["app/src/main/java/com/template/app/presentation/theme/Color.kt"] = """package com.template.app.presentation.theme

import androidx.compose.ui.graphics.Color

// TEMPLATE COLORS
// TODO: Replace with your brand colors

val Primary = Color(0xFF1E6B9E)
val PrimaryVariant = Color(0xFF0D3B6E)
val Secondary = Color(0xFF27AE60)

val BackgroundLight = Color(0xFFF8FAFB)
val SurfaceLight = Color(0xFFFFFFFF)
val OnBackgroundLight = Color(0xFF1A1A2E)

val BackgroundDark = Color(0xFF121212)
val SurfaceDark = Color(0xFF1E1E1E)
val OnBackgroundDark = Color(0xFFE0E0E0)

val SuccessColor = Color(0xFF2ECC71)
val ErrorColor = Color(0xFFE74C3C)
val WarningColor = Color(0xFFF39C12)
"""

files["app/src/main/java/com/template/app/presentation/theme/Type.kt"] = """package com.template.app.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Typography scale
// TODO: Replace with your app font

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)
"""

files["app/src/main/java/com/template/app/presentation/theme/Theme.kt"] = """package com.template.app.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = OnBackgroundLight,
    onSurface = OnBackgroundLight
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = OnBackgroundDark,
    onSurface = OnBackgroundDark
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Dynamic color disabled by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
"""

files["app/src/main/java/com/template/app/presentation/navigation/Screen.kt"] = """package com.template.app.presentation.navigation

/**
 * Sealed class defining all app screens and routes.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Settings : Screen("settings")
    // TODO: Add your screens here
}
"""

files["app/src/main/java/com/template/app/presentation/navigation/AppNavGraph.kt"] = """package com.template.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.template.app.presentation.screens.splash.SplashScreen
import com.template.app.presentation.screens.home.HomeScreen
import com.template.app.presentation.screens.settings.SettingsScreen

/**
 * Main navigation graph for the application.
 */
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // TODO: Add your screen routes here
    }
}
"""

files["app/src/main/java/com/template/app/utils/UiState.kt"] = """package com.template.app.utils

/**
 * Generic sealed class for managing UI state.
 */
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}
"""

files["app/src/main/java/com/template/app/utils/Extensions.kt"] = """package com.template.app.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Context extensions.
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Modifier extensions.
 */
fun Modifier.clickableWithRipple(onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(),
        onClick = onClick
    )
}
"""

files["app/src/main/java/com/template/app/utils/Constants.kt"] = """package com.template.app.utils

/**
 * App-wide constants.
 */
object Constants {
    const val DATABASE_NAME = "template_database"
    const val PREFERENCES_NAME = "template_preferences"
    
    // TODO: Add your constants here
}
"""

files["app/src/main/java/com/template/app/presentation/components/AppButton.kt"] = """package com.template.app.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Primary filled button.
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(text = text)
        }
    }
}

/**
 * Secondary outlined button.
 */
@Composable
fun AppSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
        } else {
            Text(text = text)
        }
    }
}

/**
 * Text button.
 */
@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(text = text)
    }
}
"""

files["app/src/main/java/com/template/app/presentation/components/AppTextField.kt"] = """package com.template.app.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Standard text field with optional error state.
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    errorMessage: String? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage != null,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            singleLine = true
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
"""

files["app/src/main/java/com/template/app/presentation/components/AppCard.kt"] = """package com.template.app.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Reusable AppCard.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val cardModifier = modifier.padding(8.dp)
    val shape = RoundedCornerShape(12.dp)
    val colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    val elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = cardModifier,
            shape = shape,
            colors = colors,
            elevation = elevation
        ) {
            content()
        }
    } else {
        Card(
            modifier = cardModifier,
            shape = shape,
            colors = colors,
            elevation = elevation
        ) {
            content()
        }
    }
}
"""

files["app/src/main/java/com/template/app/presentation/components/LoadingView.kt"] = """package com.template.app.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Full screen loading indicator.
 */
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
"""

files["app/src/main/java/com/template/app/presentation/components/EmptyStateView.kt"] = """package com.template.app.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Reusable empty state view.
 */
@Composable
fun EmptyStateView(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Info,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        if (buttonText != null && onButtonClick != null) {
            Spacer(modifier = Modifier.height(24.dp))
            AppButton(
                text = buttonText,
                onClick = onButtonClick
            )
        }
    }
}
"""

files["app/src/main/java/com/template/app/data/local/entity/PlaceholderEntity.kt"] = """package com.template.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Placeholder Room Entity.
 */
@Entity(tableName = "placeholder_table")
data class PlaceholderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
"""

files["app/src/main/java/com/template/app/data/local/dao/PlaceholderDao.kt"] = """package com.template.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.template.app.data.local.entity.PlaceholderEntity
import kotlinx.coroutines.flow.Flow

/**
 * Placeholder Room DAO.
 */
@Dao
interface PlaceholderDao {
    @Query("SELECT * FROM placeholder_table")
    fun getAll(): Flow<List<PlaceholderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PlaceholderEntity)
}
"""

files["app/src/main/java/com/template/app/data/local/AppDatabase.kt"] = """package com.template.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.template.app.data.local.dao.PlaceholderDao
import com.template.app.data.local.entity.PlaceholderEntity

/**
 * Room App Database.
 * TODO: Add your entities here
 */
@Database(
    entities = [PlaceholderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val placeholderDao: PlaceholderDao
}
"""

files["app/src/main/java/com/template/app/di/DatabaseModule.kt"] = """package com.template.app.di

import android.content.Context
import androidx.room.Room
import com.template.app.data.local.AppDatabase
import com.template.app.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    // TODO: Add your DAOs here
    @Provides
    @Singleton
    fun providePlaceholderDao(db: AppDatabase) = db.placeholderDao
}
"""

files["app/src/main/java/com/template/app/domain/model/PlaceholderModel.kt"] = """package com.template.app.domain.model

/**
 * Placeholder Domain Model.
 */
data class PlaceholderModel(
    val id: Int,
    val name: String
)
"""

files["app/src/main/java/com/template/app/domain/repository/PlaceholderRepository.kt"] = """package com.template.app.domain.repository

import com.template.app.domain.model.PlaceholderModel
import kotlinx.coroutines.flow.Flow

/**
 * Placeholder Repository Interface.
 */
interface PlaceholderRepository {
    fun getPlaceholders(): Flow<List<PlaceholderModel>>
    suspend fun addPlaceholder(name: String)
}
"""

files["app/src/main/java/com/template/app/data/repository/PlaceholderRepositoryImpl.kt"] = """package com.template.app.data.repository

import com.template.app.data.local.dao.PlaceholderDao
import com.template.app.data.local.entity.PlaceholderEntity
import com.template.app.domain.model.PlaceholderModel
import com.template.app.domain.repository.PlaceholderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of PlaceholderRepository.
 */
class PlaceholderRepositoryImpl @Inject constructor(
    private val dao: PlaceholderDao
) : PlaceholderRepository {

    override fun getPlaceholders(): Flow<List<PlaceholderModel>> {
        return dao.getAll().map { list ->
            list.map { PlaceholderModel(it.id, it.name) }
        }
    }

    override suspend fun addPlaceholder(name: String) {
        dao.insert(PlaceholderEntity(name = name))
    }
}
"""

files["app/src/main/java/com/template/app/domain/usecase/PlaceholderUseCase.kt"] = """package com.template.app.domain.usecase

// TEMPLATE: Not heavily using UseCases per request, 
// keeping this as a simple placeholder if needed later.

class PlaceholderUseCase {
    // Add logic here if you want to use UseCases
}
"""

files["app/src/main/java/com/template/app/di/RepositoryModule.kt"] = """package com.template.app.di

import com.template.app.data.repository.PlaceholderRepositoryImpl
import com.template.app.domain.repository.PlaceholderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlaceholderRepository(
        impl: PlaceholderRepositoryImpl
    ): PlaceholderRepository
}
"""

files["app/src/main/java/com/template/app/di/AppModule.kt"] = """package com.template.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.template.app.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES_NAME)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}
"""

files["app/src/main/java/com/template/app/presentation/screens/splash/SplashScreen.kt"] = """package com.template.app.presentation.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        // Handle logic for determining destination
        delay(1500)
        onNavigateToHome()
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Android Template",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
"""

files["app/src/main/java/com/template/app/presentation/screens/home/HomeViewModel.kt"] = """package com.template.app.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.app.domain.model.PlaceholderModel
import com.template.app.domain.repository.PlaceholderRepository
import com.template.app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PlaceholderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<PlaceholderModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<PlaceholderModel>>> = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getPlaceholders()
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "An unexpected error occurred")
                }
                .collect { items ->
                    if (items.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(items)
                    }
                }
        }
    }

    // TODO: Example function
    fun addData(name: String) {
        viewModelScope.launch {
            repository.addPlaceholder(name)
        }
    }
}
"""

files["app/src/main/java/com/template/app/presentation/screens/home/HomeScreen.kt"] = """package com.template.app.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.template.app.presentation.components.EmptyStateView
import com.template.app.presentation.components.LoadingView
import com.template.app.presentation.components.AppCard
import com.template.app.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    com.template.app.presentation.components.AppTextButton(
                        text = "Settings",
                        onClick = onNavigateToSettings
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addData("Item \${System.currentTimeMillis()}") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val s = state) {
                is UiState.Loading -> LoadingView()
                is UiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(s.data) { item ->
                            AppCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
                is UiState.Empty -> EmptyStateView(title = "No Items", subtitle = "Click + to add an item")
                is UiState.Error -> EmptyStateView(title = "Error", subtitle = s.message)
            }
        }
    }
}
"""

files["app/src/main/java/com/template/app/presentation/screens/settings/SettingsScreen.kt"] = """package com.template.app.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "App Settings")
            // TODO: Add more settings here
        }
    }
}
"""

files["app/src/main/AndroidManifest.xml"] = """<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- <uses-permission android:name="android.permission.INTERNET" /> -->
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

    <application
        android:name=".MainApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="AndroidTemplate"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.TemplateApp">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.TemplateApp.Starting">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
"""

files["app/proguard-rules.pro"] = """# Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep @dagger.hilt.android.HiltAndroidApp class * { *; }
-keep @dagger.hilt.android.AndroidEntryPoint class * { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *

# DataStore
-keep class androidx.datastore.** { *; }

# WorkManager
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.CoroutineWorker
-keep class androidx.work.** { *; }

# Coil
-keep class coil.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Models
# TODO: Keep your data models
-keep class com.template.app.domain.model.** { *; }
"""

files[".gitignore"] = """# Keys & secrets (NEVER commit these)
local.properties
*.jks
*.keystore
keystore.properties
google-services.json

# Build outputs
/build
/app/build
.gradle/

# IDE
.idea/
*.iml
.DS_Store

# Release
release/
"""

files["AGENTS.md"] = """# Android Project Template

This is an Android project template.
Tech: Kotlin, Jetpack Compose, Material 3, MVVM, Hilt, Room, DataStore, WorkManager.

## Rules:
- Follow Clean Architecture principles (Presentation, Domain, Data layers)
- One feature at a time
- No unnecessary libraries
- Keep code beginner-friendly with comments
- Use UiState sealed class for all screen states
- Follow existing folder structure
"""

files["README.md"] = """## Android Project Template

### How to use this template:
1. Clone this repo
2. Find & Replace "com.template.app" with your package name
3. Find & Replace "AndroidTemplate" with your app name
4. Update theme/Color.kt with your brand colors
5. Update theme/Type.kt with your font
6. Add your screens in presentation/screens/
7. Add your routes in navigation/Screen.kt
8. Start building!

### What's included:
- build.gradle.kts (project & app levels)
- libs.versions.toml
- MainApplication.kt & MainActivity.kt
- theme files (Color.kt, Type.kt, Theme.kt)
- Navigation graph
- UiState and Extensions
- Reusable UI Components
- Room Database setup
- Hilt DI setup
- SplashScreen, HomeScreen, SettingsScreen
- Proguard rules & AndroidManifest
- AGENTS.md for AI instructions
"""

files["app/src/main/res/values/themes.xml"] = """<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.TemplateApp" parent="android:Theme.Material.Light.NoActionBar">
        <item name="android:statusBarColor">@android:color/transparent</item>
        <item name="android:navigationBarColor">@android:color/transparent</item>
    </style>
    
    <style name="Theme.TemplateApp.Starting" parent="Theme.SplashScreen">
        <!-- Set the splash screen background -->
        <item name="windowSplashScreenBackground">#1E6B9E</item>
        <item name="postSplashScreenTheme">@style/Theme.TemplateApp</item>
    </style>
</resources>
"""

files["settings.gradle.kts"] = """pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\\\.android.*")
                includeGroupByRegex("com\\\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AndroidTemplate"
include(":app")
"""

import os
for path, content in files.items():
    directory = os.path.dirname(path)
    if directory and not os.path.exists(directory):
        os.makedirs(directory, exist_ok=True)
    with open(path, "w") as f:
        f.write(content)
print("done")
