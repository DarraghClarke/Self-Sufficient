package com.example.help.ui.rain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RainViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is rain Fragment"
    }
    val text: LiveData<String> = _text
}