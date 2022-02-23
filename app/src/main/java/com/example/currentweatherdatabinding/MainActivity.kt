package com.example.currentweatherdatabinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.example.currentweatherdatabinding.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import java.util.*
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import com.google.gson.Gson
import com.google.gson.JsonParser


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.displayBtn).setOnClickListener {
            onClick(it)
        }


        binding  = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.weather = Weather("Not Stated", 0f,"Clear",0f,0,R.drawable.sun,R.drawable.north)
    }
    suspend fun loadWeather() {

        val API_KEY = resources.getString(R.string.API_KEY)
        var city = findViewById<EditText>(R.id.input).text.toString()
        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${API_KEY}&units=metric";

        val stream: InputStream

        try {
            stream = URL(weatherURL).getContent() as InputStream
        } catch (e: Exception){
            binding.weather = Weather("Something went wrong", 0f,"Clear",0f,0,R.drawable.sun,R.drawable.north)
            return
        }


        // JSON отдаётся одной строкой,
        val data = Scanner(stream).nextLine()
        Log.d("mytag", data)

        val parser = JsonParser.parseString(data).asJsonObject

        val type = parser.get("weather").asJsonArray[0].asJsonObject.get("main").asString
        val temp = parser.get("main").asJsonObject.get("temp").toString().toFloat()
        val wSpeed = parser.get("wind").asJsonObject.get("speed").toString().toFloat()
        val wDir = parser.get("wind").asJsonObject.get("deg").toString().toInt()
        var typeImg: Int = 0
        var wDirImg: Int = 0

        when (type) {
            "Clouds" -> typeImg = R.drawable.cloudy
            "Clear" -> typeImg = R.drawable.sun
            "Rain" -> typeImg = R.drawable.rainy
            "Snow" -> typeImg = R.drawable.snowy
        }

        when (wDir / 90) {
            0 -> wDirImg = R.drawable.north
            1 -> wDirImg = R.drawable.east
            2 -> wDirImg = R.drawable.south
            3 -> wDirImg = R.drawable.west
        }

        var newWeather = Weather(city, temp, type, wSpeed, wDir, typeImg, wDirImg)

        binding.weather = newWeather

    }
    public fun onClick(v: View) {
        // Используем IO-диспетчер вместо Main (основного потока)
        GlobalScope.launch (Dispatchers.IO) {
            loadWeather()
        }
    }
}