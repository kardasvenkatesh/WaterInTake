package com.template.app.presentation.screens.home

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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.template.app.R
import com.template.app.presentation.components.EmptyStateView
import com.template.app.presentation.components.LoadingView
import com.template.app.presentation.components.AppCard
import com.template.app.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.home_title)) }
            )
        },
        floatingActionButton = {
            val itemPrefix = stringResource(id = R.string.item_prefix)
            FloatingActionButton(onClick = { viewModel.addData(itemPrefix.format(System.currentTimeMillis())) }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_item))
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
                is UiState.Empty -> EmptyStateView(
                    title = stringResource(id = R.string.no_data_title),
                    subtitle = stringResource(id = R.string.no_data_desc)
                )
                is UiState.Error -> EmptyStateView(
                    title = stringResource(id = R.string.error_title),
                    subtitle = s.message.ifEmpty { stringResource(id = R.string.unknown_error) }
                )
            }
        }
    }
}
