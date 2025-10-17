package com.example.eskikoprojectuts.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.eskikoprojectuts.R
import com.example.eskikoprojectuts.databinding.FragmentProfileBinding
import java.util.Calendar

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    // Inisialisasi SharedPreferences
    private val PREF_FILE = "child_profile"
    private val NAME_KEY = "profile_name"
    private val BOD_KEY = "profile_bod"
    private val GENDER_KEY = "profile_gender"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load profile data
        loadProfile()

        // Tanggal lahir via DatePicker
        binding.txtInputEditBoD.setOnClickListener {
            showDatePicker()
        }

        // Simpan ke SharedPreferences
        binding.btnSimpan.setOnClickListener {
            saveProfile()
        }
    }

    private fun loadProfile() {
        val prefs = requireContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val name = prefs.getString(NAME_KEY, "") ?: ""
        val bod = prefs.getString(BOD_KEY, "") ?: ""
        val gender = prefs.getString(GENDER_KEY, "") ?: ""

        binding.txtInputEditName.setText(name)
        binding.txtInputEditBoD.setText(bod)

        when (gender) {
            "Laki-Laki" -> binding.btnRadioMale.isChecked = true
            "Perempuan" -> binding.btnRadioFemale.isChecked = true
            else -> binding.btnRadioGenders.clearCheck()
        }
    }

    @SuppressLint("UseKtx")
    private fun saveProfile() {
        val name = binding.txtInputEditName.text.toString()
        val bod = binding.txtInputEditBoD.text.toString()
        val gender = when (binding.btnRadioGenders.checkedRadioButtonId) {
            binding.btnRadioMale.id -> "Laki-Laki"
            binding.btnRadioFemale.id -> "Perempuan"
            else -> ""
        }

        // Validasi
        if (name.isEmpty()) {
            binding.txtInputEditName.error = "Nama tidak boleh kosong"
            return
        }

        if (bod.isEmpty()) {
            binding.txtInputEditBoD.error = "Tanggal Lahir tidak boleh kosong"
            return
        }

        if (gender.isEmpty()) {
            Toast.makeText(requireContext(), "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
            return
        }

        val prefs = requireContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(NAME_KEY, name)
            .putString(BOD_KEY, bod)
            .putString(GENDER_KEY, gender)
            .apply()

        Toast.makeText(requireContext(), "Profil tersimpan", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("DefaultLocale")
    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)

        DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val dateString = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            binding.txtInputEditBoD.setText(dateString)
        }, y, m, d).show()
    }
}