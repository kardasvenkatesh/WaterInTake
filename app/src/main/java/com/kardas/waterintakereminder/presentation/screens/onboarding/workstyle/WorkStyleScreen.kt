package com.kardas.waterintakereminder.presentation.screens.onboarding.workstyle

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
import com.kardas.waterintakereminder.presentation.components.AppCard
import com.kardas.waterintakereminder.presentation.screens.onboarding.OnboardingViewModel

@Composable
fun WorkStyleScreen(
    viewModel: OnboardingViewModel,
    onNavigateToSchedule: () -> Unit
) {
    val state by viewModel.onboardingState.collectAsState()
    
    val workStyles = listOf(
        stringResource(id = R.string.workstyle_desk),
        stringResource(id = R.string.workstyle_active),
        stringResource(id = R.string.workstyle_student),
        stringResource(id = R.string.workstyle_workout)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_workstyle_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        workStyles.forEach { style ->
            AppCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                onClick = { viewModel.updateWorkStyle(style) }
            ) {
                Text(
                    text = style,
                    modifier = Modifier.padding(16.dp),
                    color = if (state.workStyle == style) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    fontWeight = if (state.workStyle == style) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        AppButton(
            text = stringResource(id = R.string.btn_next),
            onClick = onNavigateToSchedule,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.workStyle.isNotBlank()
        )
    }
}
