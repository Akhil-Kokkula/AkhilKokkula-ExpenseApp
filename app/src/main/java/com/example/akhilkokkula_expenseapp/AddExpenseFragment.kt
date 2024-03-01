package com.example.akhilkokkula_expenseapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.akhilkokkula_expenseapp.databinding.FragmentAddExpenseBinding
import java.util.Date
import java.util.Calendar

class AddExpenseFragment : Fragment() {
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val expenseListViewModel: ExpenseListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addExpenseBtn2.setOnClickListener() {
            if (binding.setExpenseName.text.toString() == "") {
                Toast.makeText(requireContext(), "Please enter an expense name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            println(binding.setExpenseAmt.text.toString().toDouble())
            if (binding.setExpenseAmt.text.toString().toDouble() <= 0.0) {
                Toast.makeText(requireContext(), "Please enter a valid expense amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val categorySpinner = binding.categorySpinner2
            if (categorySpinner.selectedItem.toString() == "" || categorySpinner.selectedItem.toString() == "All") {
                Toast.makeText(requireContext(), "Please enter a category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var selectedDate = 0L
            val datePicker = binding.dateSelector
            val calendarSelect = Calendar.getInstance()
            calendarSelect.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            calendarSelect.set(Calendar.HOUR_OF_DAY, 0)
            calendarSelect.set(Calendar.MINUTE, 0)
            calendarSelect.set(Calendar.SECOND, 0)
            calendarSelect.set(Calendar.MILLISECOND, 0)
            selectedDate = calendarSelect.timeInMillis
            println(selectedDate)
            println(Date(selectedDate))


            if (selectedDate == 0L) {
                Toast.makeText(requireContext(), "Please enter a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            expenseListViewModel.insertExpense(
                expenseName = binding.setExpenseName.text.toString(),
                date = Date(selectedDate),
                category = categorySpinner.selectedItem.toString(),
                expenseAmt = binding.setExpenseAmt.text.toString().toDouble()
            )
            findNavController().navigate(R.id.action_addExpenseFragment_to_expenseListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}