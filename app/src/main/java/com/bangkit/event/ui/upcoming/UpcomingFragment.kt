package com.bangkit.event.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.event.DetailActivity
import com.bangkit.event.MainViewModel
import com.bangkit.event.adapter.AdapterEvent
import com.bangkit.event.databinding.FragmentUpcomingBinding
import com.google.android.material.snackbar.Snackbar

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var eventAdapter: AdapterEvent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.apply {
            upcoming.observe(viewLifecycleOwner) { eventAdapter.submitList(it) }
            loading.observe(viewLifecycleOwner) { showLoading(it) }
            errorMessage.observe(viewLifecycleOwner) { it?.let { showSnackbar(it) } }
        }
    }

    private fun setupRecyclerView() {
        eventAdapter = AdapterEvent(requireContext()) { event ->
            startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("event", event)
            })
        }
        binding.rvEvent.layoutManager = LinearLayoutManager(context)
        binding.rvEvent.adapter = eventAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
