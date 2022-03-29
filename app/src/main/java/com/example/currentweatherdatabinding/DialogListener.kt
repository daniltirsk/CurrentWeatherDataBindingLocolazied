package com.example.currentweatherdatabinding

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.currentweatherdatabinding.DetailedWeather
import com.example.currentweatherdatabinding.R
import com.example.currentweatherdatabinding.SimpleWeather


class DialogListener: DialogInterface.OnClickListener {
    lateinit var fm: FragmentManager
    lateinit var ft: FragmentTransaction
    lateinit var Detailed: Fragment
    lateinit var Simple: Fragment
    override fun onClick(dialog: DialogInterface?, choice: Int) {
        val lw: ListView = (dialog as AlertDialog).listView
        val checkedItem: Int = lw.checkedItemPosition
        fm = (dialog.ownerActivity as MainActivity?)?.fm!!
        when (checkedItem){
            1 -> {
                var Simple = SimpleWeather()
                (dialog.ownerActivity as MainActivity?)?.Simple = Simple
                ft = fm.beginTransaction()
                fm.beginTransaction().add(R.id.container_fragm, Simple)
                    .commit()
            }
            0 -> {
                var Detailed = DetailedWeather()
                (dialog.ownerActivity as MainActivity?)?.Detailed = Detailed
                ft = fm.beginTransaction()
                fm.beginTransaction().add(R.id.container_fragm, Detailed)
                    .commit()
            }
        }
    }
}