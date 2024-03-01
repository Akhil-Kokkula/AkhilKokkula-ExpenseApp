package com.example.akhilkokkula_expenseapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.akhilkokkula_expenseapp.databinding.CategorizedItemBinding

private const val TAG = "CategorizedListAdapter"

class CategorizedListHolder(
    private val binding: CategorizedItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(categorizedExpense: CategorizedExpense) {
        binding.categoryTitle.text = categorizedExpense.category
        binding.amountInfoCategory.text = categorizedExpense.totalAmount.toString()
    }
}

class CategorizedListAdapter(
    private var categorizedExpenses: List<CategorizedExpense>
) : RecyclerView.Adapter<CategorizedListHolder>() {

    fun updatedCategorizedList(updatedList: List<CategorizedExpense>) {
        categorizedExpenses = updatedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorizedListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategorizedItemBinding.inflate(inflater, parent, false)
        return CategorizedListHolder(binding)
    }

    override fun onBindViewHolder(holder: CategorizedListHolder, position: Int) {
        val expense = categorizedExpenses[position]
        holder.bind(expense)
    }

    override fun getItemCount() = categorizedExpenses.size
}