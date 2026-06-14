package dam_A13507.coolweatherapp

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    var day:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val submeter: Button = findViewById(R.id.botaoSubmeter)
        val switch: SwitchCompat = findViewById(R.id.switch_nightmode)

        setTheme(R.style.WeatherThemeDay)

        // switch
        switch.setOnCheckedChangeListener { button, state ->
            if(state) {
                day = false
                setTheme(R.style.WeatherThemeNight)
            }
            else {
                day = true
                setTheme(R.style.WeatherThemeDay)
            }
        }

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                if(day) {
                    setTheme(R.style.WeatherThemeDay)
                }
                else {
                    setTheme(R.style.WeatherThemeNight)
                }
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                if(day) {
                    setTheme(R.style.WeatherThemeDayLand)
                }
                else {
                    setTheme(R.style.WeatherThemeNightLand)
                }
            }

        }

        submeter.setOnClickListener {
            val editLatitude: EditText = findViewById(R.id.editLatitude)
            val editLongitude: EditText = findViewById(R.id.editLongitude)

            val lat = editLatitude.text.toString().toFloat()
            val long = editLongitude.text.toString().toFloat()

            fetchWeatherData(lat, long).start()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun WeatherAPI_Call (lat:Float, long:Float) : WeatherData {
        val reqString = buildString {
            append ("https://api.open-meteo.com/v1/forecast?")
            append ("latitude=${lat}&longitude=${long}&")
            append ("current_weather=true&")
            append ("hourly=temperature_2m,weathercode,pressure_msl,windspeed_10m")
        }
        val str = reqString.toString()
        val url = URL(reqString.toString());
        url.openStream().use {
            val request = Gson().fromJson(InputStreamReader(it, "UTF-8"), WeatherData::class.java)
            return request
        }
    }

    private fun fetchWeatherData (lat:Float, long:Float) : Thread {
        return Thread {
            val weather = WeatherAPI_Call(lat, long)
            updateUI (weather)
        }
    }
    private fun updateUI(request:WeatherData) {
        runOnUiThread {
            val weatherImage:ImageView = findViewById(R.id.weatherImage)
            val pressure:TextView = findViewById(R.id.resSeaLevel)
            val wind_dir:TextView = findViewById(R.id.resWindDir)
            val wind_speed:TextView = findViewById(R.id.resWindSpeed)
            val temp:TextView = findViewById(R.id.resTemperature)
            val time_weather:TextView = findViewById(R.id.resTime)

            pressure.text = request.hourly.pressure_msl[0].toString() + "hPa"
            wind_dir.text = request.current_weather.winddirection.toString() + "º"
            wind_speed.text = request.current_weather.windspeed.toString() + "km/h"
            temp.text = request.current_weather.temperature.toString() + " °C"
            time_weather.text = request.current_weather.time



            val wCode = request.current_weather.weathercode
            when(wCode) {
                0 -> if(day) weatherImage.setImageResource(R.drawable.clear_day) else weatherImage.setImageResource(R.drawable.clear_night)
                1 -> if(day) weatherImage.setImageResource(R.drawable.mostly_clear_day) else weatherImage.setImageResource(R.drawable.mostly_clear_night)
                2 -> if(day) weatherImage.setImageResource(R.drawable.partly_cloudy_day)
                3 -> weatherImage.setImageResource(R.drawable.cloudy)
                45 -> weatherImage.setImageResource(R.drawable.fog)
                48 -> weatherImage.setImageResource(R.drawable.fog)
                51 -> weatherImage.setImageResource(R.drawable.drizzle)
                53 -> weatherImage.setImageResource(R.drawable.drizzle)
                55 -> weatherImage.setImageResource(R.drawable.drizzle)
                56 -> weatherImage.setImageResource(R.drawable.freezing_drizzle)
                57 -> weatherImage.setImageResource(R.drawable.freezing_drizzle)
                61 -> weatherImage.setImageResource(R.drawable.rain_light)
                63 -> weatherImage.setImageResource(R.drawable.rain)
                65 -> weatherImage.setImageResource(R.drawable.rain_heavy)
                66 -> weatherImage.setImageResource(R.drawable.freezing_rain_light)
                67 -> weatherImage.setImageResource(R.drawable.freezing_rain_heavy)
                71 -> weatherImage.setImageResource(R.drawable.snow_light)
                73 -> weatherImage.setImageResource(R.drawable.snow)
                75 -> weatherImage.setImageResource(R.drawable.snow_heavy)
                77 -> weatherImage.setImageResource(R.drawable.snow)
                80 -> weatherImage.setImageResource(R.drawable.rain_light)
                81 -> weatherImage.setImageResource(R.drawable.rain)
                82 -> weatherImage.setImageResource(R.drawable.rain_heavy)
                85 -> weatherImage.setImageResource(R.drawable.snow_light)
                86 -> weatherImage.setImageResource(R.drawable.snow_heavy)
                95 -> weatherImage.setImageResource(R.drawable.tstorm)
                96 -> weatherImage.setImageResource(R.drawable.tstorm)
                99 -> weatherImage.setImageResource(R.drawable.tstorm)
                else -> weatherImage.setImageResource(R.drawable.cloudy)
            }
        }
    }
}