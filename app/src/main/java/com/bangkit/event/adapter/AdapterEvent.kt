package com.bangkit.event.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.event.R
import com.bangkit.event.response.ListEventsItem
import com.bumptech.glide.Glide

class AdapterEvent(
    private val context: Context,
    private val itemClickListener: (ListEventsItem) -> Unit
) : RecyclerView.Adapter<AdapterEvent.EventViewHolder>() {

    private var events: List<ListEventsItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_adapter, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.tvEventTitle.text = event.name
        Glide.with(context).load(event.imageLogo).into(holder.ivEventPicture)
        holder.itemView.setOnClickListener { itemClickListener(event) }
    }

    override fun getItemCount(): Int = events.size

    fun submitList(newEvents: List<ListEventsItem>) {
        val diffResult = DiffUtil.calculateDiff(EventsDiffCallback(events, newEvents))
        events = newEvents
        diffResult.dispatchUpdatesTo(this)
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivEventPicture: ImageView = itemView.findViewById(R.id.ivEventPicture)
        val tvEventTitle: TextView = itemView.findViewById(R.id.tvEventTitle)
    }

    class EventsDiffCallback(
        private val oldList: List<ListEventsItem>,
        private val newList: List<ListEventsItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
