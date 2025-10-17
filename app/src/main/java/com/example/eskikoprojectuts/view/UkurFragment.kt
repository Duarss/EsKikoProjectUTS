package com.example.eskikoprojectuts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        binding.btnTambah.setOnClickListener {
            val berat = binding.txtBerat.text.toString()
            val tinggi = binding.txtTinggi.text.toString()
            val usia = binding.txtUsia.text.toString()

            viewModel.simpanData(berat, tinggi, usia)

            binding.txtBerat.text?.clear()
            binding.txtTinggi.text?.clear()
            binding.txtUsia.text?.clear()
        }

        observeViewModel()
    }

    fun observeViewModel(){
        // Tampilkan toast hanya saat event baru diterima (one-time)
        viewModel.saveMessageEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.errorMessageEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

}