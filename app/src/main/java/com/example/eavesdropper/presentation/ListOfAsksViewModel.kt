package com.example.eavesdropper.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eavesdropper.domain.entity.Ask

class ListOfAsksViewModel : ViewModel() {

    private val initialListOfAsks = mutableListOf<Ask>().apply {
        repeat(30) {
            add(
                Ask(
                    id = it,
                    question = "Question $it",
                    answer = "Answer $it"
                )
            )
        }
    }

    private val _asks = MutableLiveData<List<Ask>>(initialListOfAsks)
    val asks: LiveData<List<Ask>> = _asks

    fun delete(ask: Ask) {
        val modifiedList = _asks.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(ask)
        _asks.value = modifiedList
    }
}