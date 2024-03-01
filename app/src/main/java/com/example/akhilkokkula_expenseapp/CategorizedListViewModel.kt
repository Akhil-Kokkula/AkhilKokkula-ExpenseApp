package com.example.akhilkokkula_expenseapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategorizedListViewModel : ViewModel() {
    private val _categorizedExpenseList = MutableLiveData<List<CategorizedExpense>>()
    val categorizedExpenseList: LiveData<List<CategorizedExpense>> = _categorizedExpenseList
    private val expenseRepository = ExpenseRepository.get()

    fun getCategorizedExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            _categorizedExpenseList.postValue(expenseRepository.getCategorizedExpenses())
        }
        println(_categorizedExpenseList)
    }
}