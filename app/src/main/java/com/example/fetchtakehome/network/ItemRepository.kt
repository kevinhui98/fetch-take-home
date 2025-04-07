package com.example.fetchtakehome.network

import com.example.fetchtakehome.model.Item

class ItemRepository {
    suspend fun getGroupedItems(): Map<Int, List<Item>> {
        val items = ApiClient.fetchItems()

        return items
            .filter { !it.name.isNullOrBlank() } // Filter out null or blank names
            .sortedWith(compareBy<Item> { it.listId }.thenBy { it.name }) // Sort by listId, then name
            .groupBy { it.listId } // Group by listId
    }
}
