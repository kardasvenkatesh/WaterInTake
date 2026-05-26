package com.kardas.waterintakereminder.presentation.screens.logwater

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kardas.waterintakereminder.R
import com.kardas.waterintakereminder.presentation.components.AppButton
import com.kardas.waterintakereminder.presentation.components.AppTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogWaterScreen(
    viewModel: LogWaterViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val amount by viewModel.customAmount.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.log_water_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
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
                .padding(24.dp)
        ) {
            AppTextField(
                value = amount,
                onValueChange = viewModel::updateAmount,
                label = stringResource(id = R.string.log_water_custom_amount),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            AppButton(
                text = stringResource(id = R.string.btn_save),
                onClick = { viewModel.saveWaterLog(onSuccess = onNavigateBack) },
                modifier = Modifier.fillMaxWidth(),
                enabled = amount.isNotBlank() && (amount.toIntOrNull() ?: 0) > 0
            )
        }
    }
}
