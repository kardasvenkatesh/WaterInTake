package com.kardas.waterintakereminder.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.kardas.waterintakereminder.presentation.components.AppButton
import com.kardas.waterintakereminder.presentation.components.AppCard
import com.kardas.waterintakereminder.presentation.components.EmptyStateView
import com.kardas.waterintakereminder.presentation.components.LoadingView
import com.kardas.waterintakereminder.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLogWater: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.home_title)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val s = state) {
                is UiState.Loading -> LoadingView()
                is UiState.Success -> DashboardContent(
                    data = s.data,
                    onQuickAdd = viewModel::quickAddWater,
                    onCustomAdd = onNavigateToLogWater
                )
                is UiState.Empty -> EmptyStateView(
                    title = stringResource(id = R.string.home_empty_title),
                    subtitle = stringResource(id = R.string.home_empty_desc)
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
private fun DashboardContent(
    data: DashboardData,
    onQuickAdd: (Int) -> Unit,
    onCustomAdd: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.home_greeting, data.userName),
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(8.dp))
    
    Text(
        text = stringResource(id = R.string.home_streak_title, data.streakDays),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(32.dp))

    // Progress Ring Mockup using CircularProgressIndicator
    val progress = if (data.targetMl > 0) data.consumedMl.toFloat() / data.targetMl.toFloat() else 0f
    
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(240.dp)) {
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            strokeWidth = 16.dp,
        )
        CircularProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 16.dp,
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${data.consumedMl} ml",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "of ${data.targetMl} ml",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    Spacer(modifier = Modifier.height(48.dp))

    Text(
        text = stringResource(id = R.string.home_quick_add),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        QuickAddButton(amount = 100, onClick = { onQuickAdd(100) })
        QuickAddButton(amount = 250, onClick = { onQuickAdd(250) })
        QuickAddButton(amount = 500, onClick = { onQuickAdd(500) })
    }

    Spacer(modifier = Modifier.height(16.dp))

    AppButton(
        text = stringResource(id = R.string.home_custom_amount_btn),
        onClick = onCustomAdd,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun QuickAddButton(amount: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(text = "+${amount}ml")
    }
}
