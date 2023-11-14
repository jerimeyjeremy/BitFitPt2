package com.example.bitfit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.InsertActivity

const val ARTICLE_EXTRA = "ARTICLE_EXTRA"

class FitAdapter(private val context: Context, private val foods  : List<DisplayFit>) :
    RecyclerView.Adapter<FitAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fit_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }

    override fun getItemCount() = foods.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val nameTextView = itemView.findViewById<TextView>(R.id.nameTV)
        val caloriesNumber = itemView.findViewById<TextView>(R.id.caloriesAmount)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(food : DisplayFit) {
            nameTextView.text = food.food?.toString()
            caloriesNumber.text = food.calories?.toString()
        }

        override fun onClick(v: View?) {
            val food = foods[absoluteAdapterPosition]

            val intent = Intent(context, InsertActivity::class.java)
            intent.putExtra(ARTICLE_EXTRA,food)
            context.startActivity(intent)

        }
    }
}