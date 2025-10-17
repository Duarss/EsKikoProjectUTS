package com.example.eskikoprojectuts.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.eskikoprojectuts.model.Anak
import com.example.eskikoprojectuts.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// fragment ukur
class ListViewModel(application: Application) : AndroidViewModel(application) {

    // Pesan error (opsional, agar bisa tampilkan toast error saat validasi/exception)
    val errorMessageLD = MutableLiveData<String?>()

    // Flag sukses simpan. Di-observe di Fragment untuk menampilkan Toast sekali,
    // lalu direset ke false lagi agar tidak retrigger saat re-attach observer.
    val saveSuccessLD = MutableLiveData<Boolean>(false)

    fun simpanData(berat: String, tinggi: String, usia: String) {
        val fileHelper = FileHelper(getApplication())

        if (berat.isEmpty() || tinggi.isEmpty() || usia.isEmpty()) {
            errorMessageLD.value = "Semua data wajib diisi"
            return
        }

        try {
            val jsonOld = fileHelper.readFromFile()
            val sType = object : TypeToken<MutableList<Anak>>() {}.type

            val listOld: MutableList<Anak> = try {
                if (jsonOld.trimStart().startsWith("[")) {
                    Gson().fromJson(jsonOld, sType)
                } else {
                    mutableListOf() // jika bukan array atau kosong, mulai list baru
                }
            } catch (_: Exception) {
                mutableListOf()
            }

            val newData = Anak(
                weight = berat.toDouble(),
                height = tinggi.toDouble(),
                usia = usia.toInt()
            )
            listOld.add(newData)

            val jsonNew = Gson().toJson(listOld)
            fileHelper.writeToFile(jsonNew)

            // Trigger sukses -> Fragment akan menampilkan Toast, lalu kita reset flag-nya
            saveSuccessLD.value = true
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            errorMessageLD.value = "Data gagal disimpan: ${e.message}"
        }
    }

    // Dipanggil Fragment setelah Toast sukses ditampilkan, agar tidak retrigger
    fun resetSaveSuccessFlag() {
        saveSuccessLD.value = false
    }

    // Dipanggil Fragment setelah Toast error ditampilkan, agar tidak retrigger
    fun clearError() {
        errorMessageLD.value = null
    }
}
