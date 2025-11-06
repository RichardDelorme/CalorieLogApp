package com.example.calorielog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DayTotalAdapter(private var items: List<DayTotal>)
    : RecyclerView.Adapter<DayTotalAdapter.VH>() {

    inner class VH(v: View): RecyclerView.ViewHolder(v) {
        val date: TextView = v.findViewById(R.id.txtDay)
        val total: TextView = v.findViewById(R.id.txtDayTotal)
    }

    override fun onCreateViewHolder(p: ViewGroup, vt: Int): VH {
        val v = LayoutInflater.from(p.context).inflate(R.layout.item_day_total, p, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val dt = items[pos]
        h.date.text = dt.date
        h.total.text = h.itemView.context.getString(R.string.total_calories_value, dt.total)
    }

    override fun getItemCount() = items.size

    fun update(newItems: List<DayTotal>) {
        items = newItems
        notifyDataSetChanged()
    }
}
