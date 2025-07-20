package dev.andrescoder.weatherdsiplayapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


class MainActivity : AppCompatActivity() {
    private val weatherMap: Map<String, WeatherData> = mapOf(
        "New York" to WeatherData(
            "New York",
            "22°C",
            "Partly Cloudy",
            "65%",
            "10 km/h",
            "1012 hPa"
        ),
        "London" to WeatherData(
            "London",
            "18°C",
            "Light Rain",
            "75%",
            "12 km/h",
            "1015 hPa"
        ),
        "Tokyo" to WeatherData(
            "Tokyo",
            "27°C",
            "Sunny",
            "60%",
            "8 km/h",
            "1010 hPa"
        )
    )

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
        val btnChange = findViewById<Button>(R.id.btnChangeCity)

        fun updateUI(weather: WeatherData) {
            cityName.text = weather.city
            temperature.text = weather.temperature
            condition.text = weather.condition
            details.text =
                "Humidity: ${weather.humidity}\nWind: ${weather.wind}\nPressure: ${weather.pressure}"

            when (weather.condition) {
                "Sunny" -> weatherIcon.setImageResource(R.drawable.sunny)
                "Partly Cloudy" -> weatherIcon.setImageResource(R.drawable.partly_cloudy)
                "Light Rain" -> weatherIcon.setImageResource(R.drawable.light_rain)
                else -> weatherIcon.setImageResource(R.drawable.sunny)
            }
        }

        updateUI(weatherMap["New York"]!!)

        btnChange.setOnClickListener {
            val cities = weatherMap.keys.toTypedArray()
            AlertDialog.Builder(this)
                .setTitle("Select city")
                .setItems(cities) { _, which ->
                    val chosen = cities[which]
                    val data = weatherMap[chosen]!!
                    updateUI(data)
                }
                .show()
        }
    }
}