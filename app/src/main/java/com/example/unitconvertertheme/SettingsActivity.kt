package com.example.unitconvertertheme

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
        val currentTheme = sharedPreferences.getInt("theme", AppCompatDelegate.MODE_NIGHT_NO)

        val themeRadioGroup = findViewById<RadioGroup>(R.id.themeRadioGroup)
        val lightTheme = findViewById<RadioButton>(R.id.lightTheme)
        val darkTheme = findViewById<RadioButton>(R.id.darkTheme)

        when (currentTheme) {
            AppCompatDelegate.MODE_NIGHT_NO -> lightTheme.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> darkTheme.isChecked = true
        }

        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lightTheme -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    sharedPreferences.edit().putInt("theme", AppCompatDelegate.MODE_NIGHT_NO).apply()
                }
                R.id.darkTheme -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    sharedPreferences.edit().putInt("theme", AppCompatDelegate.MODE_NIGHT_YES).apply()
                }
            }
            recreate()
        }
    }
} 