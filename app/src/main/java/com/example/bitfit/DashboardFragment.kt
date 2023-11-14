package com.example.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment(), OnListFragmentInteractionListener {

    private val articles = mutableListOf<DisplayFit>()
    private lateinit var fitRecyclerView: RecyclerView
    private lateinit var fitAdapter: FitAdapter
    private lateinit var averageTextView: TextView
    private lateinit var minTextView: TextView
    private lateinit var maxTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        averageTextView = view.findViewById<TextView>(R.id.averageTV)
        minTextView = view.findViewById<TextView>(R.id.minTV)
        maxTextView = view.findViewById<TextView>(R.id.maxTV)
        val clearButtonView : Button = view.findViewById(R.id.clearBT)

        lifecycleScope.launch{
            (activity?.application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFit(
                        entity.name.toString(),
                        entity.calories
                    )
                }.also { mappedList ->
                    update(mappedList)
                }
            }
        }

        clearButtonView.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (activity?.application as FoodApplication).db.foodDao().deleteAll()
            }
        }

        return view
    }

    private fun update(foods: List<DisplayFit>) {
        if (foods.isEmpty()) {
            averageTextView.text = "No Data"
            minTextView.text = "No Data"
            maxTextView.text = "No Data"
            return
        }

        var min : Double = Double.MAX_VALUE
        var max : Double = Double.MIN_VALUE
        var sum : Double = 0.0

        for (food in foods) {
            food.calories.let { num ->
                sum += num!!
                if (num < min) min = num
                if (num > max) max = num
            }
        }

        var avg : Double = sum / foods.size


        averageTextView.text = String.format("%.2f", avg)
        minTextView.text = min.toString()
        maxTextView.text = max.toString()

    }


    override fun onItemClick(item: Dashboard) {
        TODO("Not yet implemented")
    }

}
