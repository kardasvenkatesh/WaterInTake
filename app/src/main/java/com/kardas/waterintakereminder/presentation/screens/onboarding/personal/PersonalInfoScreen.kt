package com.kardas.waterintakereminder.presentation.screens.onboarding.personal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kardas.waterintakereminder.R
import com.kardas.waterintakereminder.presentation.components.AppButton
import com.kardas.waterintakereminder.presentation.components.AppTextField
import com.kardas.waterintakereminder.presentation.screens.onboarding.OnboardingViewModel

@Composable
fun PersonalInfoScreen(
    viewModel: OnboardingViewModel,
    onNavigateToWorkStyle: () -> Unit
) {
    val state by viewModel.onboardingState.collectAsState()
    
    // Convert float to string for text field. Handle 0f case
    val weightStr = if (state.weightKg > 0) state.weightKg.toString() else ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_personal_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        AppTextField(
            value = state.name,
            onValueChange = viewModel::updateName,
            label = stringResource(id = R.string.hint_name),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        AppTextField(
            value = weightStr,
            onValueChange = viewModel::updateWeight,
            label = stringResource(id = R.string.hint_weight),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        
        // TODO: Add Gender selection using custom RadioButton group or Dropdown
        
        Spacer(modifier = Modifier.weight(1f))
        
        AppButton(
            text = stringResource(id = R.string.btn_next),
            onClick = onNavigateToWorkStyle,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.name.isNotBlank() && state.weightKg > 0f
        )
    }
}
