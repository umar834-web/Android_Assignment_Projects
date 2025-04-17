package com.example.unitconvertertheme

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
//import com.example.unitconvertertheme.R
import com.firstapp.unitconvertertheme.R

class MainActivity : AppCompatActivity() {
    private lateinit var inputValue: EditText
    private lateinit var resultText: TextView
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private var fromUnit: String = "Meters"
    private var toUnit: String = "Meters"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputValue = findViewById(R.id.inputValue)
        resultText = findViewById(R.id.resultText)
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)

        val units = arrayOf("Feet", "Inches", "Centimeters", "Meters", "Yards")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        fromUnitSpinner.adapter = adapter
        toUnitSpinner.adapter = adapter

        fromUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fromUnit = units[position]
                convert()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        toUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                toUnit = units[position]
                convert()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        findViewById<View>(R.id.convertButton).setOnClickListener {
            convert()
        }

        findViewById<View>(R.id.settingsButton).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun convert() {
        val input = inputValue.text.toString().toDoubleOrNull() ?: 0.0
        val result = when {
            fromUnit == toUnit -> input
            fromUnit == "Meters" -> when (toUnit) {
                "Feet" -> input * 3.28084
                "Inches" -> input * 39.3701
                "Centimeters" -> input * 100
                "Yards" -> input * 1.09361
                else -> input
            }
            fromUnit == "Feet" -> when (toUnit) {
                "Meters" -> input * 0.3048
                "Inches" -> input * 12
                "Centimeters" -> input * 30.48
                "Yards" -> input * 0.333333
                else -> input
            }
            fromUnit == "Inches" -> when (toUnit) {
                "Meters" -> input * 0.0254
                "Feet" -> input * 0.0833333
                "Centimeters" -> input * 2.54
                "Yards" -> input * 0.0277778
                else -> input
            }
            fromUnit == "Centimeters" -> when (toUnit) {
                "Meters" -> input * 0.01
                "Feet" -> input * 0.0328084
                "Inches" -> input * 0.393701
                "Yards" -> input * 0.0109361
                else -> input
            }
            fromUnit == "Yards" -> when (toUnit) {
                "Meters" -> input * 0.9144
                "Feet" -> input * 3
                "Inches" -> input * 36
                "Centimeters" -> input * 91.44
                else -> input
            }
            else -> input
        }
        resultText.text = String.format("%.2f", result)
    }
} 