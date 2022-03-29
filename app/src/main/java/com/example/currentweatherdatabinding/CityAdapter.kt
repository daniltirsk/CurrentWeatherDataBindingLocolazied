package com.example.currentweatherdatabinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


internal class CityAdapter(private val inflater: LayoutInflater) :
// DIFF_CALLBACK сравнивает элементы списка
    ListAdapter<String, CityViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        // создаём новый View с использованием отдельной разметки (может быть динамической)
        val row: View = inflater.inflate(R.layout.city, parent, false)
        return CityViewHolder(row)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldCity: String, newCity: String): Boolean {
                    return oldCity == newCity
                }

                override fun areContentsTheSame(oldCity: String, newCity: String): Boolean {
                    return areItemsTheSame(oldCity, newCity)
                }
            }
    }
}
