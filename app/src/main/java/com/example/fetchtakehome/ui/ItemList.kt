package com.example.fetchtakehome.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fetchtakehome.model.Item
import androidx.compose.foundation.layout.Arrangement

@Composable
fun ItemList(groupedItems: Map<Int, List<Item>>) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        groupedItems.forEach { (listId, itemsForList) ->
            // Group Header
            item {
                Text(
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
            }

            val rows = itemsForList.chunked(3)
            items(rows.size) { rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val rowItems = rows[rowIndex]
                    rowItems.forEach { item ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Text(
                                text = item.name.orEmpty(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    // Fill space if only 1 item in last row
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            // Spacer between groups
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
