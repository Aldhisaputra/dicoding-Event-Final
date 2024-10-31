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
import com.bangkit.event.entity.EntityEvent
import com.bumptech.glide.Glide

class AdapterEventFavorite(
    private val context: Context,
    private val itemClickListener: (EntityEvent) -> Unit
) : RecyclerView.Adapter<AdapterEventFavorite.EventViewHolder>() {

    private var adapterFavorite: List<EntityEvent> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_adapter, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = adapterFavorite[position]
        holder.bind(event)
        holder.itemView.setOnClickListener { itemClickListener(event) }
    }

    override fun getItemCount(): Int = adapterFavorite.size

    fun submitList(newEvents: List<EntityEvent>) {
        val diffResult = DiffUtil.calculateDiff(EventDiffCallback(adapterFavorite, newEvents))
        adapterFavorite = newEvents
        diffResult.dispatchUpdatesTo(this)
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivEventPicture: ImageView = itemView.findViewById(R.id.ivEventPicture)
        private val tvEventTitle: TextView = itemView.findViewById(R.id.tvEventTitle)

        fun bind(event: EntityEvent) {
            tvEventTitle.text = event.name
            Glide.with(itemView.context).load(event.imageLogo).into(ivEventPicture)
        }
    }

    class EventDiffCallback(
        private val oldList: List<EntityEvent>,
        private val newList: List<EntityEvent>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldList[oldPos].id == newList[newPos].id
        }

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldList[oldPos] == newList[newPos]
        }
    }
}
