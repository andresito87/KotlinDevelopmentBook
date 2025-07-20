package dev.andrescoder.weatherdsiplayapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        // Cambia el color de fondo
        window.statusBarColor = ContextCompat.getColor(this, R.color.background_light)

        // Pide iconos oscuros
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        // Listener para Insets (ajusta paddings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom)
            insets
        }

        val cityName = findViewById<TextView>(R.id.cityName)
        val temperature = findViewById<TextView>(R.id.temperature)
        val condition = findViewById<TextView>(R.id.condition)
        val details = findViewById<TextView>(R.id.details)
        val weatherIcon = findViewById<ImageView>(R.id.weatherIcon)
        val weather = WeatherData(
            city = "New York",
            temperature = "22°C",
            condition = "Partly Cloudy",
            humidity = "65%",
            wind = "10 km/h",
            pressure = "1012 hPa"
        )
        cityName.text = weather.city
        temperature.text = weather.temperature
        condition.text = weather.condition
        details.text =
            "Humidity: ${weather.humidity}\nWind: ${weather.wind}\nPressure: ${weather.pressure}"
        // Placeholder icon; replace with a weather-specific drawable
        weatherIcon.setImageResource(R.drawable.sunny)
    }
}