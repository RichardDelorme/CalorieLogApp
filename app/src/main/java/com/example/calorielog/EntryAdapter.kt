package com.example.calorielog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calorielog.model.FoodEntry

class EntryAdapter(private var items: List<FoodEntry>)
    : RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtName)
        val cal: TextView  = view.findViewById(R.id.txtCalories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entry, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = items[position]
        holder.name.text = entry.foodName
        holder.cal.text = holder.itemView.context.getString(
            R.string.calories_value,
            entry.calories
        )

    }

    override fun getItemCount() = items.size

    fun update(newList: List<FoodEntry>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = items.size
            override fun getNewListSize() = newList.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                items[oldItemPosition].id == newList[newItemPosition].id
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                items[oldItemPosition] == newList[newItemPosition]
        })
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

}
