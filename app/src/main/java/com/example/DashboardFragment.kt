package com.example.fitbitpt2

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var db: CalorieDatabase
    private lateinit var adapter: CalorieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = CalorieDatabase.getDatabase(requireContext())

        val totalView = view.findViewById<TextView>(R.id.tvTotalCalories)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        adapter = CalorieAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            val entries = db.calorieDao().getAll()
            val total = entries.sumOf { it.calories }
            totalView.text = "Total Calories: $total"
            adapter.updateData(entries)
        }
    }
}
