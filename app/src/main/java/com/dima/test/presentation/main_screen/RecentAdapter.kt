package com.dima.test.presentation.main_screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dima.test.R
import com.dima.test.domain.model.NumberInfo

class RecentAdapter(
    private val data: List<NumberInfo>,
    private val onClick: (NumberInfo) -> Unit
) : RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {

    inner class RecentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val previewTv: TextView = itemView.findViewById(R.id.previewTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        return RecentViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recent, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.apply {
            var text = data[position].fact
            if(text.length < 40){
                text.plus("...")
            } else {
                text = "${text.subSequence(0, 40)}..."
            }
            previewTv.text = text
            itemView.setOnClickListener {
                onClick(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size
}