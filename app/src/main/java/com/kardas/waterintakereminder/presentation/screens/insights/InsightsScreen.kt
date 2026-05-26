package com.kardas.waterintakereminder.presentation.screens.insights

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kardas.waterintakereminder.R
import com.kardas.waterintakereminder.presentation.components.AppCard
import com.kardas.waterintakereminder.presentation.components.EmptyStateView
import com.kardas.waterintakereminder.presentation.components.LoadingView
import com.kardas.waterintakereminder.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(
    viewModel: InsightsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.insights_title)) }
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
                is UiState.Success -> InsightsContent(data = s.data)
                is UiState.Empty -> EmptyStateView(
                    title = stringResource(id = R.string.insights_empty_title),
                    subtitle = stringResource(id = R.string.insights_empty_desc)
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
private fun InsightsContent(data: InsightsData) {
    Text(
        text = stringResource(id = R.string.insights_weekly_summary),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.insights_avg_daily, "${data.avgDailyMl}ml"),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.insights_best_day, "${data.bestDayMl}ml"),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    
    Spacer(modifier = Modifier.height(32.dp))
    
    Text(
        text = stringResource(id = R.string.insights_smart_insights),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.fillMaxWidth()
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    // Mock insights for MVP
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.insights_mock_afternoon),
            modifier = Modifier.padding(16.dp)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.insights_mock_consistency),
            modifier = Modifier.padding(16.dp)
        )
    }
}
