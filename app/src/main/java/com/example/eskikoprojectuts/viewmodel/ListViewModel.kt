package com.example.eskikoprojectuts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.eskikoprojectuts.model.Anak
import com.example.eskikoprojectuts.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// fragment ukur
class ListViewModel (application: Application): AndroidViewModel(application) {
    val messageLD = MutableLiveData<String>()

    fun simpanData(berat: String, tinggi: String, usia: String){
        val fileHelper = FileHelper(getApplication())

        if (berat.isEmpty() || tinggi.isEmpty() || usia.isEmpty()) {
            messageLD.value = "Semua data wajib diisi"
            return
        }

        try{
            val jsonOld = fileHelper.readFromFile()
            val sType = object : TypeToken<MutableList<Anak>>() {}.type
            val listOld: MutableList<Anak> =
                if (jsonOld.isNotEmpty()) Gson().fromJson(jsonOld, sType) else mutableListOf()


            val newData = Anak(usia = usia.toInt(), height = tinggi.toDouble(), weight =  berat.toDouble())
            listOld.add(newData)

            val jsonNew = Gson().toJson(listOld)
            fileHelper.writeToFile(jsonNew)

            messageLD.value = "Data berhasil disimpan"
        }
        catch (e: Exception) {
            messageLD.value = "Data gagal disimpan: ${e.message}"
        }
    }
}