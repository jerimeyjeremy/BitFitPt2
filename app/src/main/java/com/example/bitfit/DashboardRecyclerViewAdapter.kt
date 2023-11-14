package com.example.bitfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitfit.R.id
import com.example.bitfit.R.layout

class DashboardRecyclerViewAdapter (
    private val dashboard: List<Dashboard>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<DashboardRecyclerViewAdapter.BookViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout.fragment_dashboard, parent, false)
        return BookViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class BookViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mAverageFill: TextView = mView.findViewById<View>(id.averageFILL) as TextView
        val mMinFill: TextView = mView.findViewById<View>(id.minFILL) as TextView
        val mMaxFill: TextView = mView.findViewById<View>(id.maxFILL) as TextView
        val mAverageTV: TextView = mView.findViewById<View>(id.averageTV) as TextView
        val mMinTV: TextView = mView.findViewById<View>(id.minTV) as TextView
        val mMaxTV: TextView = mView.findViewById<View>(id.maxTV) as TextView
        val clearButton: Button = mView.findViewById<View>(id.clearBT) as Button
        var mItem: Dashboard? = null

        /*override fun toString(): String {
            return mBookTitle.toString() + " '" + mBookAuthor.text + "'"
        }*/
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = dashboard[position]

        holder.mItem = book
        holder.mAverageTV.text = book.avgCalories.toString()
        holder.mMinTV.text = book.minCalories.toString()
        holder.mMaxTV.text = book.maxCalories.toString()

        holder.mView.setOnClickListener {
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }
        }
    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return dashboard.size
    }

}