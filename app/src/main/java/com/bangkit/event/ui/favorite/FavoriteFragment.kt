package com.bangkit.event.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.event.DetailActivity
import com.bangkit.event.R
import com.bangkit.event.adapter.AdapterEventFavorite
import com.bangkit.event.repository.RepositoryEvent

class FavoriteFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterEventFavorite: AdapterEventFavorite
    private lateinit var repositoryEvent: RepositoryEvent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        recyclerView = view.findViewById(R.id.rvEvent)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapterEventFavorite = AdapterEventFavorite(requireContext()) { favoriteEvent ->
            Log.d("FavoriteFragment", "Navigating to DetailActivity with: $favoriteEvent")
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("event", favoriteEvent)
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapterEventFavorite

        repositoryEvent = RepositoryEvent.getInstance(requireContext())

        loadFavoriteEvents()

        return view
    }

    private fun loadFavoriteEvents() {
        repositoryEvent.getAllFavoriteEvent().observe(viewLifecycleOwner) { events ->
            adapterEventFavorite.submitList(events)
        }
    }
}
