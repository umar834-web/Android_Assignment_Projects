package com.example.unitconverter


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var fromUnitSpinner: AutoCompleteTextView
    private lateinit var toUnitSpinner: AutoCompleteTextView
    private lateinit var valueInput: TextInputEditText
    private lateinit var resultText: TextView

    private val units = arrayOf("Feet", "Inches", "Centimeters", "Meters", "Yards")
    private val df = DecimalFormat("#.####")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)
        valueInput = findViewById(R.id.valueInput)
        resultText = findViewById(R.id.resultText)

        // Set up spinners
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, units)
        fromUnitSpinner.setAdapter(adapter)
        toUnitSpinner.setAdapter(adapter)

        // Set default values
        fromUnitSpinner.setText(units[0], false)
        toUnitSpinner.setText(units[1], false)

        // Add listeners
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                convert()
            }
        }

        valueInput.addTextChangedListener(textWatcher)
        fromUnitSpinner.setOnItemClickListener { _, _, _, _ -> convert() }
        toUnitSpinner.setOnItemClickListener { _, _, _, _ -> convert() }
    }

    private fun convert() {
        val value = valueInput.text.toString().toDoubleOrNull() ?: return
        val fromUnit = fromUnitSpinner.text.toString()
        val toUnit = toUnitSpinner.text.toString()

        val result = when {
            // Convert to meters first (as base unit), then to target unit
            fromUnit == toUnit -> value
            else -> {
                val meters = toMeters(value, fromUnit)
                fromMeters(meters, toUnit)
            }
        }

        resultText.text = "${df.format(result)} $toUnit"
    }

    private fun toMeters(value: Double, unit: String): Double = when (unit) {
        "Meters" -> value
        "Centimeters" -> value / 100
        "Inches" -> value * 0.0254
        "Feet" -> value * 0.3048
        "Yards" -> value * 0.9144
        else -> value
    }

    private fun fromMeters(meters: Double, unit: String): Double = when (unit) {
        "Meters" -> meters
        "Centimeters" -> meters * 100
        "Inches" -> meters / 0.0254
        "Feet" -> meters / 0.3048
        "Yards" -> meters / 0.9144
        else -> meters
    }
}