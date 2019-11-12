package com.example.help.ui.solar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SolarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is solar Fragment"
    }
    val text: LiveData<String> = _text
}