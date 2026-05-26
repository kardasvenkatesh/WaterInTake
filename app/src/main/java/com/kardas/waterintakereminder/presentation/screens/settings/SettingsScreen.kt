package com.kardas.waterintakereminder.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kardas.waterintakereminder.R
import com.kardas.waterintakereminder.domain.model.UserProfile
import com.kardas.waterintakereminder.presentation.components.AppCard
import com.kardas.waterintakereminder.presentation.components.EmptyStateView
import com.kardas.waterintakereminder.presentation.components.LoadingView
import com.kardas.waterintakereminder.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.settings_title)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (val s = state) {
                is UiState.Loading -> LoadingView()
                is UiState.Success -> SettingsContent(
                    profile = s.data,
                    onRecalculateGoal = viewModel::recalculateGoal
                )
                is UiState.Empty -> EmptyStateView(
                    title = stringResource(id = R.string.empty_profile_title),
                    subtitle = stringResource(id = R.string.empty_profile_desc)
                )
                is UiState.Error -> EmptyStateView(
                    title = stringResource(id = R.string.error_title),
                    subtitle = s.message.ifEmpty { stringResource(id = R.string.unknown_error) }
                )
            }
        }
    }
}

@Composable
private fun SettingsContent(
    profile: UserProfile,
    onRecalculateGoal: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.settings_profile),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(8.dp))
    
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = profile.name.ifBlank { "User" }, style = MaterialTheme.typography.titleMedium)
            Text(text = "${profile.weightKg} kg | ${profile.workStyle}", style = MaterialTheme.typography.bodyMedium)
        }
    }
    
    Spacer(modifier = Modifier.height(24.dp))
    
    Text(
        text = stringResource(id = R.string.settings_hydration_goal_section),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(8.dp))
    
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            SettingsRow(
                title = stringResource(id = R.string.settings_daily_goal),
                value = "${profile.dailyGoalMl} ml"
            )
            Divider()
            SettingsRow(
                title = stringResource(id = R.string.settings_recalculate_goal),
                value = "",
                onClick = onRecalculateGoal
            )
        }
    }
    
    Spacer(modifier = Modifier.height(24.dp))
    
    Text(
        text = stringResource(id = R.string.settings_reminders),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(8.dp))
    
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            SettingsRow(
                title = stringResource(id = R.string.settings_wake_up_time),
                value = profile.wakeUpTime
            )
            Divider()
            SettingsRow(
                title = stringResource(id = R.string.settings_bedtime),
                value = profile.bedtime
            )
        }
    }
}

@Composable
private fun SettingsRow(title: String, value: String, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        if (value.isNotBlank()) {
            Text(text = value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
