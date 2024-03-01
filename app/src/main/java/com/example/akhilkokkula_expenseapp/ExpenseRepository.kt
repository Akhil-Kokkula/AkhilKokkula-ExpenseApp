package com.example.akhilkokkula_expenseapp

import android.content.Context
import androidx.room.Room
import com.example.akhilkokkula_expenseapp.database.ExpenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

private const val DATABASE_NAME = "expense-database"

class ExpenseRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val db: ExpenseDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            ExpenseDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    suspend fun getExpenses(date: Date): List<Expense> = db.expenseDao().getExpenses(date)

    suspend fun getExpensesByDateAndCategory(date: Date, category: String) : List<Expense> = db.expenseDao().getExpensesByDateAndCategory(date, category)

    suspend fun getCategorizedExpenses(): List<CategorizedExpense> = db.expenseDao().getCategorizedExpenses()

    fun updateExpense(expense: Expense) {
        coroutineScope.launch {
            db.expenseDao().updateExpense(expense)
        }
    }

    fun insertExpense(expense: Expense) {
        coroutineScope.launch {
            db.expenseDao().insertExpense(expense)
        }
    }

    companion object {
        private var INSTANCE: ExpenseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ExpenseRepository(context)
            }
        }

        fun get(): ExpenseRepository {
            return INSTANCE
                ?: throw IllegalStateException("ExpenseRepository must be initialized")
        }
    }
}
