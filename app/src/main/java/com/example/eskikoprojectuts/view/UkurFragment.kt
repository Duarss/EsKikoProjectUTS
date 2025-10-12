package com.example.eskikoprojectuts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.eskikoprojectuts.R
import com.example.eskikoprojectuts.databinding.FragmentUkurBinding
import com.example.eskikoprojectuts.viewmodel.ListViewModel


class UkurFragment : Fragment() {
    private lateinit var binding: FragmentUkurBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUkurBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        binding.btnTambah.setOnClickListener {
            val berat = binding.txtBerat.text.toString()
            val tinggi = binding.txtTinggi.text.toString()
            val usia = binding.txtUsia.text.toString()

            viewModel.simpanData(berat, tinggi, usia)
        }
    }


}