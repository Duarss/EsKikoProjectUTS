package com.example.eskikoprojectuts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.eskikoprojectuts.model.Anak
import com.example.eskikoprojectuts.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// fragment data
class DataListViewModel (application: Application): AndroidViewModel(application) {
    val dataLD = MutableLiveData<ArrayList<Anak>>()
    val dataLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun refresh() {
        loadingLD.value = true  // progress barnya mulai muncul
        dataLoadErrorLD.value = false

        val context = getApplication<Application>().applicationContext
        val fileHelper = FileHelper(context)
        val jsonString = fileHelper.readFromFile()

        if (jsonString.isNotEmpty()) {
            try {
                val sType = object : TypeToken<List<Anak>>() {}.type
                val result = Gson().fromJson<List<Anak>>(jsonString, sType)
                dataLD.value = result as ArrayList<Anak>
                dataLoadErrorLD.value = false
            } catch (e: Exception) {
                dataLoadErrorLD.value = true
            }
        } else {
            dataLoadErrorLD.value = true
        }

        loadingLD.value = false
    }

}

