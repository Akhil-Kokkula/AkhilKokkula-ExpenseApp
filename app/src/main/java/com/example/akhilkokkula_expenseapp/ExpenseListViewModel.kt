package com.example.akhilkokkula_expenseapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class ExpenseListViewModel : ViewModel() {
    private val _expenseList = MutableLiveData<List<Expense>>()
    val expenseList: LiveData<List<Expense>> = _expenseList
    private val expenseRepository = ExpenseRepository.get()

    fun getExpenses(date: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            _expenseList.postValue(expenseRepository.getExpenses(date))
        }
    }

    fun getExpensesByDateAndCategory(date: Date, category : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _expenseList.postValue(expenseRepository.getExpensesByDateAndCategory(date, category))
        }
    }

     fun insertExpense(expenseName: String, date: Date, expenseAmt: Double, category: String) {
        val expense = Expense(title = expenseName, date = date, amount = expenseAmt, category = category)
        expenseRepository.insertExpense(expense)
    }

    fun updateExpense(id: Int, expenseName: String, date: Date, expenseAmt: Double, category: String) {
        val expense = Expense(id = id, title = expenseName, date = date, amount = expenseAmt, category = category)
        expenseRepository.updateExpense(expense)
    }

}