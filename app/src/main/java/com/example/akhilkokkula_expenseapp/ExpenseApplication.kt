package com.example.akhilkokkula_expenseapp

import android.app.Application

class ExpenseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ExpenseRepository.initialize(this)
    }
}