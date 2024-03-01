package com.example.akhilkokkula_expenseapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.akhilkokkula_expenseapp.databinding.FragmentCategorizedListBinding

class CategorizedListFragment : Fragment() {
    private var _binding: FragmentCategorizedListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val categorizedListViewModel: CategorizedListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategorizedListBinding.inflate(inflater, container, false)

        binding.categorizedListRecyclerView.layoutManager = LinearLayoutManager(context)
        val categorizedListAdapter = CategorizedListAdapter(emptyList())
        binding.categorizedListRecyclerView.adapter = categorizedListAdapter

        categorizedListViewModel.getCategorizedExpenses()

        categorizedListViewModel.categorizedExpenseList.observe(viewLifecycleOwner) { categorizedExpenseList ->
            println(categorizedExpenseList)
            categorizedListAdapter.updatedCategorizedList(categorizedExpenseList)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}