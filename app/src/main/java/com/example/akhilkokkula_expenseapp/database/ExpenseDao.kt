package com.example.akhilkokkula_expenseapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.akhilkokkula_expenseapp.CategorizedExpense
import com.example.akhilkokkula_expenseapp.Expense
import java.util.Date

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

    @Query("SELECT * FROM expenses WHERE date = :date")
    suspend fun getExpenses(date: Date): List<Expense>

    @Query("SELECT * FROM expenses WHERE date = :date AND category = :category")
    suspend fun getExpensesByDateAndCategory(date: Date, category: String): List<Expense>

    @Query("SELECT category, SUM(amount) AS totalAmount FROM expenses GROUP BY category")
    suspend fun getCategorizedExpenses(): List<CategorizedExpense>

}