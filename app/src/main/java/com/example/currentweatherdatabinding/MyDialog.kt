package com.example.alertdialogdemo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.currentweatherdatabinding.DialogListener
import com.example.currentweatherdatabinding.R
import java.util.*

class MyDialog(val ctx: Context): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var choice = 0
        return ctx.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Choose display type")
            builder.setSingleChoiceItems(ctx.resources.getStringArray(R.array.weather_types),
                0
            ) { _, which -> choice = which }
            builder.setPositiveButton("Ok", DialogListener())
            builder.create()
        }
    }
}