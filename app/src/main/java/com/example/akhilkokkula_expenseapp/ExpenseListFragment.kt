package com.example.akhilkokkula_expenseapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.akhilkokkula_expenseapp.databinding.FragmentExpenseListBinding
import java.util.Date
import java.util.Calendar


class ExpenseListFragment : Fragment(), ExpenseListAdapter.OnItemClickListener {

    private var _binding: FragmentExpenseListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }


    private val expenseListViewModel: ExpenseListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)

        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(context)
        val expenseListAdapter = ExpenseListAdapter(emptyList(), this)
        binding.expenseRecyclerView.adapter = expenseListAdapter

        val addButton = binding.addExpenseBtn
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_expenseListFragment_to_addExpenseFragment)
        }

        binding.showCategorizedList.setOnClickListener {
            findNavController().navigate(R.id.action_expenseListFragment_to_categorizedListFragment)
        }

        val datePicker = binding.datePicker
        val categorySpinner = binding.categorySpinner
        var category = "All"
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println(adapterView?.getItemAtPosition(position).toString())
                category = adapterView?.getItemAtPosition(position).toString()

                val calendarSelectByCategory = Calendar.getInstance()
                calendarSelectByCategory.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
                calendarSelectByCategory.set(Calendar.HOUR_OF_DAY, 0)
                calendarSelectByCategory.set(Calendar.MINUTE, 0)
                calendarSelectByCategory.set(Calendar.SECOND, 0)
                calendarSelectByCategory.set(Calendar.MILLISECOND, 0)
                val selectedDateByCategory = calendarSelectByCategory.timeInMillis
                println(Date(selectedDateByCategory))
                if (category == "All") {
                    expenseListViewModel.getExpenses(Date(selectedDateByCategory))
                } else {
                    expenseListViewModel.getExpensesByDateAndCategory(Date(selectedDateByCategory), category)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        var selectedDate = 0L
        datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth, object : DatePicker.OnDateChangedListener {
            override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                val calendarSelect = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, monthOfYear)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                selectedDate = calendarSelect.timeInMillis
                println(selectedDate)
                println(Date(selectedDate))
                println(category)

                if (category == "All") {
                    expenseListViewModel.getExpenses(Date(selectedDate))
                } else {
                    expenseListViewModel.getExpensesByDateAndCategory(Date(selectedDate), category)
                }
            }
        })


        expenseListViewModel.expenseList.observe(viewLifecycleOwner) { expenseList ->
            println(expenseList)
            expenseListAdapter.updateExpenses(expenseList)
        }

        return binding.root
    }

    override fun onExpenseClick(expense: Expense) {
        val bundle = Bundle().apply{
            putInt("id", expense.id)
            putString("title", expense.title)
            putLong("date", expense.date.time)
            putDouble("amount", expense.amount)
            putString("category", expense.category)
        }

        findNavController().navigate(R.id.action_expenseListFragment_to_updateExpenseFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}