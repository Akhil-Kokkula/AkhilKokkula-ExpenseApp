package com.example.akhilkokkula_expenseapp

import androidx.room.PrimaryKey
import androidx.room.Entity
import java.util.Date


@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: Date,
    val amount: Double,
    val category: String
)

data class CategorizedExpense(
    var category: String,
    var totalAmount: Double
)
