package com.example.akhilkokkula_expenseapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.akhilkokkula_expenseapp.databinding.ExpenseItemBinding

private const val TAG = "ExpenseListAdapter"

class ExpenseHolder(
    private val binding: ExpenseItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(expense: Expense) {
        binding.expenseTitle.text = expense.title
        binding.dateInfo.text = expense.date.toString()
        binding.amountInfo.text = expense.amount.toString()
        binding.categoryInfo.text = expense.category
    }
}

class ExpenseListAdapter(
    private var expenses: List<Expense>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ExpenseHolder>() {

    interface OnItemClickListener {
        fun onExpenseClick(expense: Expense)
    }

    fun updateExpenses(updatedList: List<Expense>) {
        expenses = updatedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExpenseItemBinding.inflate(inflater, parent, false)
        return ExpenseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense)

        holder.itemView.setOnClickListener {
            listener.onExpenseClick(expense)
        }
    }

    override fun getItemCount() = expenses.size
}