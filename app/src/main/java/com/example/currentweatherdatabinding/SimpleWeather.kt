package com.example.currentweatherdatabinding
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.currentweatherdatabinding.databinding.SimpleWeatherBinding
import java.util.*

class SimpleWeather: Fragment() {
    lateinit var bindingSimple: SimpleWeatherBinding
    lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val locale: Locale = resources.configuration.locale

        val view = inflater.inflate(R.layout.simple_weather, container, false)
        view.setBackgroundColor(Color.BLUE)
        bindingSimple  = SimpleWeatherBinding.inflate(inflater, container, false)

        bindingSimple.weather = (this.activity as MainActivity?)?.weather ?: Weather("No city", 0f,getString(R.string.weather_clear),0f,0,R.drawable.sun,R.drawable.north)

//        button = this.requireActivity().findViewById(R.id.displayBtn)
//        button.setOnClickListener{
//            (this.activity as MainActivity?)?.onClick()
//        }


        return bindingSimple.root
        //return super.onCreateView(inflater, container, savedInstanceState)
    }
}