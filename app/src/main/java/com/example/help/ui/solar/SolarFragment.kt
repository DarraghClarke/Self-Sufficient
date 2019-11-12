package com.example.help.ui.solar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.help.R

class SolarFragment : Fragment() {

    private lateinit var solarViewModel: SolarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        solarViewModel =
            ViewModelProviders.of(this).get(SolarViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_solar, container, false)
        val textView: TextView = root.findViewById(R.id.text_solar)
        solarViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}