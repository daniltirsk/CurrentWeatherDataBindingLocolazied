package com.example.currentweatherdatabinding

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.alertdialogdemo.MyDialog
import com.example.currentweatherdatabinding.databinding.DetailedWeatherBinding
import com.example.currentweatherdatabinding.databinding.SimpleWeatherBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import java.util.*
import com.google.gson.JsonParser


class MainActivity : AppCompatActivity() {
    lateinit var fm: FragmentManager
    lateinit var ft: FragmentTransaction
    lateinit var Detailed: Fragment
    lateinit var Simple: Fragment
    lateinit var cities: MutableList<String>
    public lateinit var weather: Weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fm = supportFragmentManager
        ft = fm.beginTransaction()
        Detailed = DetailedWeather()
        Simple = SimpleWeather()

        weather = Weather("No city", 0f,getString(R.string.weather_clear),0f,0,R.drawable.sun,R.drawable.north)


        MyDialog(this).show(supportFragmentManager, "choice")

        val rv = findViewById<RecyclerView>(R.id.input)
        val cityAdapter = CityAdapter(LayoutInflater.from(this))
        // добавляем данные в список для отображения


        cities = mutableListOf();
        for (city in getResources().getStringArray(R.array.cities)){
            cities.add(city);
        }
        cityAdapter.submitList(cities);

        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = cityAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv)

        snapHelper.getSnapPosition(rv)


        rv.attachSnapHelperWithListener(snapHelper,
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE
        ) { position ->
            updateWeatherDisplay(cities[position])
        }

        updateWeatherDisplay(cities[0])

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.menu)

        toolbar.setOnMenuItemClickListener { item -> setLocale(this,
            item.toString().lowercase(Locale.getDefault())
        ) }
    }
    fun loadWeather(city: String): Weather {

        val API_KEY = resources.getString(R.string.API_KEY)
        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${API_KEY}&units=metric";
        var newWeather: Weather
        val stream: InputStream

        try {
            stream = URL(weatherURL).getContent() as InputStream
        } catch (e: Exception){
            newWeather = Weather("Something went wrong", 0f,getString(R.string.weather_clear),0f,0,R.drawable.sun,R.drawable.north)
            return newWeather
        }


        // JSON отдаётся одной строкой,
        val data = Scanner(stream).nextLine()
        Log.d("mytag", data)

        val parser = JsonParser.parseString(data).asJsonObject

        val type = parser.get("weather").asJsonArray[0].asJsonObject.get("main").asString
        val temp = parser.get("main").asJsonObject.get("temp").toString().toFloat()
        val wSpeed = parser.get("wind").asJsonObject.get("speed").toString().toFloat()
        val wDir = parser.get("wind").asJsonObject.get("deg").toString().toInt()
        var typeString: Int = 0
        var typeImg: Int = 0
        var wDirImg: Int = 0

        when (type) {
            "Clouds" -> {
                typeImg = R.drawable.cloudy
                typeString = R.string.weather_clouds
            }
            "Clear" -> {
                typeImg = R.drawable.sun
                typeString = R.string.weather_clear
            }
            "Rain" -> {
                typeImg = R.drawable.rainy
                typeString = R.string.weather_rain
            }
            "Snow" -> {
                typeImg = R.drawable.snowy
                typeString = R.string.weather_snow
            }
        }

        when (wDir / 90) {
            0 -> wDirImg = R.drawable.north
            1 -> wDirImg = R.drawable.east
            2 -> wDirImg = R.drawable.south
            3 -> wDirImg = R.drawable.west
        }

        newWeather = Weather(city, temp, getString(typeString), wSpeed, wDir, typeImg, wDirImg)

        return newWeather
    }
    public fun updateWeatherDisplay(city:String) {
        // Используем IO-диспетчер вместо Main (основного потока)
        GlobalScope.launch (Dispatchers.IO) {
            weather = loadWeather(city)

            ft = fm.beginTransaction()
            if (Simple.isAdded){
                Simple = SimpleWeather()
                ft.replace(R.id.container_fragm, Simple)
                ft.commit()
            }
            if (Detailed.isAdded){
                Detailed = DetailedWeather()
                ft.replace(R.id.container_fragm, Detailed)
                ft.commit()
            }
        }
    }

    fun addCity(v: View){
        var cityName = findViewById<TextView>(R.id.cityInput).text.toString().replaceFirstChar { c->c.uppercase() }

        val rv = findViewById<RecyclerView>(R.id.input)
        val cityAdapter = CityAdapter(LayoutInflater.from(this))

        cities.add(0, cityName);
        cityAdapter.submitList(cities);
        rv.adapter = cityAdapter
        updateWeatherDisplay(cities[0])
    }

    fun setLocale(activity: Activity, languageCode: String?): Boolean {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.getConfiguration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())
        val intent = intent
        activity.finish()
        activity.startActivity(intent)
//        activity.recreate()

        return true
    }
}