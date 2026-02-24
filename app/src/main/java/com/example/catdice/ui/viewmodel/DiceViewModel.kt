package com.example.catdice.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catdice.data.DiceRepository
import com.example.catdice.domain.RollResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiceViewModel @Inject constructor(
    private val repository: DiceRepository
) : ViewModel() {

    private val _historyList = MutableStateFlow<List<RollResult>>(emptyList())
    val historyList: StateFlow<List<RollResult>> = _historyList.asStateFlow()

    var diceCount by mutableIntStateOf(2)
    var dice1Result by mutableIntStateOf(1)
    var dice2Result by mutableIntStateOf(6)
    var isRolling by mutableStateOf(false)

    init {
        viewModelScope.launch {
            repository.historyFlow.collect { savedList ->
                _historyList.value = savedList
            }
        }
    }

    fun addRollResult(dice1: Int, dice2: Int?, total: Int) {
        val newResult = RollResult(dice1, dice2, total)
        val currentList = _historyList.value.toMutableList()

        currentList.add(0, newResult)

        if (currentList.size > 5) {
            currentList.removeAt(currentList.lastIndex)
        }

        _historyList.value = currentList
        saveToDataStore(currentList)
    }

    private fun saveToDataStore(list: List<RollResult>) {
        viewModelScope.launch {
            repository.saveHistory(list)
        }
    }
}