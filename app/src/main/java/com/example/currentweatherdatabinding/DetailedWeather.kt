package com.example.currentweatherdatabinding

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.currentweatherdatabinding.databinding.DetailedWeatherBinding
import com.example.currentweatherdatabinding.databinding.SimpleWeatherBinding
import android.os.Build
import androidx.fragment.app.FragmentTransaction


class DetailedWeather: Fragment() {
    lateinit var bindingDetailed: DetailedWeatherBinding
    lateinit var button: Button;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.detailed_weather, container, false)
        view.setBackgroundColor(Color.RED)
        bindingDetailed  = DetailedWeatherBinding.inflate(inflater, container, false)
        bindingDetailed.weather = (this.activity as MainActivity?)?.weather ?: Weather("No city", 0f,getString(R.string.weather_clear),0f,0,R.drawable.sun,R.drawable.north)


//        button = this.requireActivity().findViewById(R.id.displayBtn)
//        button.setOnClickListener{
//            (this.activity as MainActivity?)?.onClick()!!
//        }

        return bindingDetailed.root
        //return super.onCreateView(inflater, container, savedInstanceState)
    }
}