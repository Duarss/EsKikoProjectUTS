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
        // Tampilkan Toast sukses hanya ketika saveSuccessLD = true,
        // lalu reset flag agar tidak muncul lagi saat fragment re-attach.
        viewModel.saveSuccessLD.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess == true) {
                Toast.makeText(requireContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                viewModel.resetSaveSuccessFlag()
            }
        }

        // Tampilkan error jika ada, lalu clear agar tidak retrigger saat re-attach.
        viewModel.errorMessageLD.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }
    }

}