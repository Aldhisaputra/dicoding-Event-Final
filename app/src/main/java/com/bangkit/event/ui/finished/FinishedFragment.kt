package com.bangkit.event.ui.finished

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
import com.bangkit.event.databinding.FragmentFinishedBinding
import com.google.android.material.snackbar.Snackbar

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding should not be accessed when it is null")

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapterEvent: AdapterEvent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        with(mainViewModel) {
            finishedevents.observe(viewLifecycleOwner) { adapterEvent.submitList(it) }
            loading.observe(viewLifecycleOwner) { binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE }
            errorMessage.observe(viewLifecycleOwner) { it?.let { msg -> Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show() } }
        }
    }

    private fun setupRecyclerView() {
        adapterEvent = AdapterEvent(requireContext()) { event ->
            startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("event", event)
            })
        }
        binding.rvEvent.layoutManager = LinearLayoutManager(context)
        binding.rvEvent.adapter = adapterEvent
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
