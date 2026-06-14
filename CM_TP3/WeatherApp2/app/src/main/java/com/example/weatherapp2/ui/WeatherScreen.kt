package com.example.weatherapp2.ui

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp2.R
import com.example.weatherapp2.ui.theme.WeatherTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp2.data.WMO_WeatherCode
import com.example.weatherapp2.data.getWeatherCodeMap
import androidx.compose.runtime.*
import com.example.weatherapp2.viewmodel.WeatherViewModel

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun WeatherUI (weatherViewModel : WeatherViewModel = viewModel () ) {
    val weatherUIState by weatherViewModel.uiState.collectAsState()
    val latitude = weatherUIState . latitude
    val longitude = weatherUIState . longitude
    val temperature = weatherUIState . temperature
    val windSpeed = weatherUIState . windspeed
    val windDirection = weatherUIState . winddirection
    val weathercode = weatherUIState . weathercode
    val seaLevelPressure = weatherUIState . seaLevelPressure
    val time = weatherUIState . time
    val configuration = LocalConfiguration . current
    val day = true // Must change this in the future
    val mapt = getWeatherCodeMap () ;
    val wCode = mapt . get ( weathercode )
    val wImage = when ( wCode ) {
        WMO_WeatherCode. CLEAR_SKY ,
        WMO_WeatherCode . MAINLY_CLEAR ,
        WMO_WeatherCode . PARTLY_CLOUDY -> if ( day ) wCode ?. image + " day "
        else wCode ?. image + " night "
        else -> wCode ?. image
    }
    val context = LocalContext . current
    val wIcon = context . resources . getIdentifier ( wImage , " drawable " ,
        context . packageName )
    if ( configuration . orientation == Configuration. ORIENTATION_LANDSCAPE ) {
        LandscapeWeatherUI (
            wIcon ,
            latitude ,
            longitude ,
            temperature ,
            windSpeed ,
            windDirection ,
            weathercode ,
            seaLevelPressure ,
            time ,
            onLatitudeChange = {
                    newValue -> newValue . toFloatOrNull () ?. let {
                weatherViewModel . updateLatitude ( it ) }
            },
            onLongitudeChange = {
                newValue -> newValue . toFloatOrNull () ?. let {
                weatherViewModel . updateLongitude ( it ) }
            },
            onUpdateButtonClick = {
                weatherViewModel . fetchWeather ()
            }
        )
    } else {
        PortraitWeatherUI (
            wIcon ,
            latitude ,
            longitude ,
            temperature ,
            windSpeed ,
            windDirection ,
            weathercode ,
            seaLevelPressure ,
            time ,
            onLatitudeChange = {
                    newValue ->
                newValue . toFloatOrNull () ?. let {
                    weatherViewModel . updateLatitude ( it ) }
            },
            onLongitudeChange = {
                    newValue ->
                newValue . toFloatOrNull () ?. let {
                    weatherViewModel . updateLongitude ( it ) }
            },
            onUpdateButtonClick = {
                weatherViewModel . fetchWeather ()
            }
        )
    }
}
@Composable
fun PortraitWeatherUI (
    wIcon : Int ,
    latitude : Float ,
    longitude : Float ,
    temperature : Float ,
    windSpeed : Float ,
    windDirection : Int ,
    weathercode : Int ,
    seaLevelPressure : Float ,
    time : String ,
    onLatitudeChange : ( String ) -> Unit ,
    onLongitudeChange : ( String ) -> Unit ,
    onUpdateButtonClick : () -> Unit ,
) {
// ToDo
}
@Composable
fun LandscapeWeatherUI (
    wIcon : Int ,
    latitude : Float ,
    longitude : Float ,
    temperature : Float ,
    windSpeed : Float ,

    windDirection : Int ,
    weathercode : Int ,
    seaLevelPressure : Float ,
    time : String ,
    onLatitudeChange : ( String ) -> Unit ,
    onLongitudeChange : ( String ) -> Unit ,
    onUpdateButtonClick : () -> Unit ,
) {
// ToDo
}