package com.example.currentweatherdatabinding

import android.graphics.Color
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // получаем ссылку на текстовое поле в каждом элементе списка
    val tv = itemView.findViewById<TextView>(R.id.city)

    init {
        tv.setOnClickListener { _ ->
            Toast.makeText(tv.context, tv.text, Toast.LENGTH_SHORT).show()
            //tv.setBackgroundColor(Color.parseColor(tv.text as String))
        }
    }

    fun bindTo(city: String) {
//        tv.setBackgroundColor(city)
        // вывод кода цвета в 16-ричном виде
        tv.text = city
    }
}
