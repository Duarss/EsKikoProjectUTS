package com.example.eskikoprojectuts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.eskikoprojectuts.model.Anak
import com.example.eskikoprojectuts.util.FileHelper

// fragment data
class DataListViewModel (application: Application): AndroidViewModel(application){
    val dataLD = MutableLiveData<ArrayList<Anak>>()
    val dataLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun refresh() {
        loadingLD.value = true          // progress barnya mulai muncul
        dataLoadErrorLD.value = false

        val fileHelper = FileHelper(getApplication())
        val dataString = fileHelper.readFromFile()
        val anakList = ArrayList<Anak>()

        if (dataString.isNotEmpty()){
            val lines = dataString.split("\n").filter { it.isNotBlank() }

            for (line in lines) {
                val parts = line.split("|")

                val usiaText = parts[0].trim().substringAfter(":").substringBefore("tahun").trim()
                val tinggiText = parts[1].trim().substringAfter(":").substringBefore("cm").trim()
                val beratText = parts[2].trim().substringAfter(":").substringBefore("kg").trim()

                val anak = Anak(
                    beratText.toDoubleOrNull(),
                    tinggiText.toDoubleOrNull(),
                    usiaText.toIntOrNull()
                )
                anakList.add(anak)
            }

            dataLD.value = anakList
            dataLoadErrorLD.value = false
        }

        else {
            dataLD.value = arrayListOf()
            dataLoadErrorLD.value = false
        }

    }
}