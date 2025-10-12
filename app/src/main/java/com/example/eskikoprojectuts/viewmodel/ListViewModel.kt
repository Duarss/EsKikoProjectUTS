package com.example.eskikoprojectuts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ListViewModel (application: Application): AndroidViewModel(application) {
    val messageLD = MutableLiveData<String>()


}