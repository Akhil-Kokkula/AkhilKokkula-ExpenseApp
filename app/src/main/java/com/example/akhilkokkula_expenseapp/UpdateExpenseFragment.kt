package com.example.akhilkokkula_expenseapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.akhilkokkula_expenseapp.databinding.FragmentUpdateExpenseBinding
import java.util.Date
import java.util.Calendar



class UpdateExpenseFragment : Fragment() {

    private var _binding: FragmentUpdateExpenseBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val expenseListViewModel: ExpenseListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id")
        val expenseName = arguments?.getString("title")
        val date = arguments?.getLong("date")
        val expenseAmt = arguments?.getDouble("amount")
        val category = arguments?.getString("category")

        binding.updateExpenseName.setText(expenseName)
        binding.updateExpenseAmt.setText(expenseAmt.toString())
        val categoryArray = resources.getStringArray(R.array.category_array).toList()
        binding.updateCategorySpinner2.setSelection(categoryArray.indexOf(category))

        val calendar = Calendar.getInstance().apply{timeInMillis = date!!}
        binding.updateDateSelector.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        binding.updateExpenseBtn.setOnClickListener {
            if (binding.updateExpenseName.text.toString() == "") {
                Toast.makeText(requireContext(), "Please enter an expense name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.updateExpenseAmt.text.toString() == "" || binding.updateExpenseAmt.text.toString().toDouble() <= 0.0) {
                Toast.makeText(requireContext(), "Please enter a valid expense amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.updateCategorySpinner2.selectedItem.toString() == "" || binding.updateCategorySpinner2.selectedItem.toString() == "All") {
                Toast.makeText(requireContext(), "Please enter a valid category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val datePicker = binding.updateDateSelector
            var selectedDate = 0L
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

            println("Updating expense")
            println(Date(selectedDate))
            println(binding.updateExpenseAmt.text.toString().toDouble())
            expenseListViewModel.updateExpense(
                id = id!!,
                expenseName = binding.updateExpenseName.text.toString(),
                date = Date(selectedDate),
                category = binding.updateCategorySpinner2.selectedItem.toString(),
                expenseAmt = binding.updateExpenseAmt.text.toString().toDouble()
            )

            findNavController().navigate(R.id.action_updateExpenseFragment_to_expenseListFragment)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}