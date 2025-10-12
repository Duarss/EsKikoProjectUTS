package com.example.eskikoprojectuts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.eskikoprojectuts.util.FileHelper

class ListViewModel (application: Application): AndroidViewModel(application) {
    val messageLD = MutableLiveData<String>()

    fun simpanData(berat: String, tinggi: String, usia: String){
        val fileHelper = FileHelper(getApplication())

        if (berat.isEmpty() || tinggi.isEmpty() || usia.isEmpty()) {
            messageLD.value = "Semua data wajib diisi"
            return
        }

        try{
            val data = "Usia: $usia tahun | Tinggi: $tinggi cm | Berat: $berat kg"
            fileHelper.writeToFile(data)
            messageLD.value = "Data berhasil disimpan"
        }
        catch (e: Exception) {
            messageLD.value = "Data gagal disimpan: ${e.message}"
        }
    }
}