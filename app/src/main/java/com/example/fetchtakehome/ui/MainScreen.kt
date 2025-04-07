package com.example.fetchtakehome.ui

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fetchtakehome.viewmodel.ItemViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import android.util.Log
//import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults


import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(viewModel: ItemViewModel = viewModel()) {
    val groupedItems by viewModel.items.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    Log.i("MainScreen", "$groupedItems")

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    PullToRefreshLayout(
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.fetchItems() }
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (groupedItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Failed to load data.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchItems() }) {
                        Text("Retry")
                    }
                }
            }
        } else {
            ItemList(groupedItems = groupedItems)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshLayout(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullToRefresh(
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                onRefresh = onRefresh
            )
    ) {
        content()

        PullToRefreshDefaults.Indicator(
            isRefreshing = isRefreshing,
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

