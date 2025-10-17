package com.example.eskikoprojectuts.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.eskikoprojectuts.model.Anak
import com.example.eskikoprojectuts.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// ---- Event wrapper sederhana untuk one-time message ----
class Event<out T>(private val content: T) {
    private var hasBeenHandled = false
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) null else {
            hasBeenHandled = true
            content
        }
    }
    fun peekContent(): T = content
}

// fragment ukur
class ListViewModel(application: Application) : AndroidViewModel(application) {

    // Ubah dari String biasa -> Event<String> agar one-time
    val saveMessageEvent = MutableLiveData<Event<String>>()
    val errorMessageEvent = MutableLiveData<Event<String>>() // optional, kalau mau toast error juga

    fun simpanData(berat: String, tinggi: String, usia: String) {
        val fileHelper = FileHelper(getApplication())

        val w = berat.trim()
        val h = tinggi.trim()
        val u = usia.trim()

        if (w.isEmpty() || h.isEmpty() || u.isEmpty()) {
            errorMessageEvent.value = Event("Semua data wajib diisi")
            return
        }

        try {
            val weight = w.toDouble()
            val height = h.toDouble()
            val age = u.toInt()

            val jsonOld = fileHelper.readFromFile()
            val sType = object : TypeToken<MutableList<Anak>>() {}.type

            val listOld: MutableList<Anak> = try {
                if (jsonOld.trimStart().startsWith("[")) {
                    Gson().fromJson(jsonOld, sType)
                } else {
                    mutableListOf() // jika bukan array, mulai list baru
                }
            } catch (_: Exception) {
                mutableListOf()
            }

            val newData = Anak(weight = weight, height = height, usia = age)
            listOld.add(newData)

            val jsonNew = Gson().toJson(listOld)
            fileHelper.writeToFile(jsonNew)

            // Kirim event satu-kali SAJA untuk sukses simpan
            saveMessageEvent.value = Event("Data berhasil disimpan")
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            errorMessageEvent.value = Event("Data gagal disimpan: ${e.message}")
        }
    }
}
