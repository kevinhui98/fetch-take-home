package com.example.fetchtakehome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchtakehome.model.Item
import com.example.fetchtakehome.network.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.delay

class ItemViewModel : ViewModel() {

    private val repository = ItemRepository()
    // StateFlow that holds the grouped items
    private val _items = MutableStateFlow<Map<Int, List<Item>>>(emptyMap())
    val items: StateFlow<Map<Int, List<Item>>> = _items

    // StateFlow for loading/error states if needed
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // This represents whether a refresh is actively happening
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    init {
        fetchItems()
    }

    internal fun fetchItems() {
        viewModelScope.launch {
            delay(1000) // delay to allow network to initialize
            _isRefreshing.value = true
            try {
                _isLoading.value = true
                _items.value = repository.getGroupedItems()
            }catch (e: Exception) {
                // You can handle error state here
                Log.i("ItemViewModel","error $e")
                _items.value = emptyMap()
            } finally {
                _isLoading.value = false
                _isRefreshing.value = false
            }
        }
    }
}