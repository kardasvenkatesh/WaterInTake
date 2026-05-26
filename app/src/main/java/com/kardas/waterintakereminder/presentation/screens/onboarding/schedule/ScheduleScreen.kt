package com.kardas.waterintakereminder.presentation.screens.onboarding.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kardas.waterintakereminder.R
import com.kardas.waterintakereminder.presentation.components.AppButton
import com.kardas.waterintakereminder.presentation.components.AppTextField
import com.kardas.waterintakereminder.presentation.screens.onboarding.OnboardingViewModel

@Composable
fun ScheduleScreen(
    viewModel: OnboardingViewModel,
    onNavigateToHome: () -> Unit
) {
    val state by viewModel.onboardingState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_schedule_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // TODO: Replace with TimePickerDialog
        AppTextField(
            value = state.wakeUpTime,
            onValueChange = viewModel::updateWakeUpTime,
            label = stringResource(id = R.string.schedule_wake_up),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // TODO: Replace with TimePickerDialog
        AppTextField(
            value = state.bedtime,
            onValueChange = viewModel::updateBedtime,
            label = stringResource(id = R.string.schedule_bedtime),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        AppButton(
            text = stringResource(id = R.string.btn_finish),
            onClick = {
                viewModel.finishOnboarding(onComplete = onNavigateToHome)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.wakeUpTime.isNotBlank() && state.bedtime.isNotBlank()
        )
    }
}
