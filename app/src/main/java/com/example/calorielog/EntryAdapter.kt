package com.example.calorielog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calorielog.model.FoodEntry

class EntryAdapter(
    private var items: List<FoodEntry>
) : RecyclerView.Adapter<EntryAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.txtName)
        val cal: TextView  = v.findViewById(R.id.txtCalories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entry, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val entry = items[position]
        holder.name.text = entry.foodName
        holder.cal.text  = holder.itemView.context
            .getString(R.string.calories_value, entry.calories)
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<FoodEntry>) {
        items = newItems

        notifyDataSetChanged()
    }
}

