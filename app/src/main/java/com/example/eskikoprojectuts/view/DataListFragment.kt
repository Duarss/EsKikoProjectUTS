package com.example.eskikoprojectuts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eskikoprojectuts.R
import com.example.eskikoprojectuts.databinding.FragmentDataListBinding
import com.example.eskikoprojectuts.viewmodel.ListViewModel

class DataListFragment : Fragment() {
    private lateinit var binding: FragmentDataListBinding
    private lateinit var viewModel: ListViewModel
    private val dataListAdapter = DataListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}