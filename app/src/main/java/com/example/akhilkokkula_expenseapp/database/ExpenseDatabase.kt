package com.example.akhilkokkula_expenseapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.akhilkokkula_expenseapp.Expense

@Database(entities = [Expense::class], version = 1)
@TypeConverters(ExpenseTypeConverters::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}