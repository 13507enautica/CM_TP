package com.example.weatherapp2.ui

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp2.R
import com.example.weatherapp2.ui.theme.CoolWeatherApp2Theme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp2.viewmodel.WeatherUiState
import com.example.weatherapp2.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel) {
    val weatherUIState by weatherViewModel.uiState.collectAsState()

    val varOrientation = LocalConfiguration.current.orientation

    if(varOrientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeWeatherScreen(
            weatherUIState,
            onFetchWeather = weatherViewModel::fetchWeather
        )
    }
    else {
        PortraitWeatherScreen(
            weatherUIState,
            onFetchWeather = weatherViewModel::fetchWeather
        )
    }
}

@Composable
fun PortraitWeatherScreen(weatherUIState: WeatherUiState, onFetchWeather: (Float,Float) -> Unit) {
    var latGuess by remember(weatherUIState.latitude) { mutableStateOf(if (weatherUIState.latitude == 0f) "" else weatherUIState.latitude.toString()) }
    var longGuess by remember(weatherUIState.longitude) { mutableStateOf(if (weatherUIState.longitude == 0f) "" else weatherUIState.longitude.toString()) }

    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        CoordinatesCard(
            latGuess = latGuess,
            longGuess = longGuess,
            onLatitudeChange = { latGuess = it },
            onLongitudeChange = { longGuess = it },
            onFetchWeather = {
                val latitude = latGuess.toFloatOrNull()
                val longitude = longGuess.toFloatOrNull()
                if (latitude != null && longitude != null) {
                    onFetchWeather(latitude, longitude)
                }
            }
        )
        WeatherCard(
            weatherUIState
        )
    }

}

@Composable
fun LandscapeWeatherScreen(weatherUIState: WeatherUiState, onFetchWeather: (Float,Float) -> Unit) {
    var latGuess by remember(weatherUIState.latitude) { mutableStateOf(if (weatherUIState.latitude == 0f) "" else weatherUIState.latitude.toString()) }
    var longGuess by remember(weatherUIState.longitude) { mutableStateOf(if (weatherUIState.longitude == 0f) "" else weatherUIState.longitude.toString()) }

    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        CoordinatesCard(
            latGuess = latGuess,
            longGuess = longGuess,
            onLatitudeChange = { latGuess = it },
            onLongitudeChange = { longGuess = it },
            onFetchWeather = {
                val latitude = latGuess.toFloatOrNull()
                val longitude = longGuess.toFloatOrNull()
                if (latitude != null && longitude != null) {
                    onFetchWeather(latitude, longitude)
                }
            }
        )
        WeatherCard(
            weatherUIState
        )
    }
}

@Composable
fun CoordinatesCard(
    latGuess:String,
    longGuess:String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onFetchWeather: () -> Unit
    ) {
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = latGuess,
            singleLine = true,
            onValueChange = onLatitudeChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.latitude)) }
        )
        OutlinedTextField(
            value = longGuess,
            singleLine = true,
            onValueChange = onLongitudeChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.longitude)) }
        )
        Button(
            onClick = onFetchWeather
        ) {
            Text(stringResource(R.string.update))
        }
    }
}

@Composable
fun WeatherCard(
    weatherUiState: WeatherUiState
) {
    Card(
    ) {
        WeatherRow(stringResource(R.string.pressure),weatherUiState.pressure.toString()+" hPa")
        WeatherRow(stringResource(R.string.winddir),weatherUiState.windDirection.toString()+" º")
        WeatherRow(stringResource(R.string.windspeed),weatherUiState.windSpeed.toString()+" km/h")
        WeatherRow(stringResource(R.string.temperature),weatherUiState.temperature.toString()+" ºC")
        WeatherRow(stringResource(R.string.time),weatherUiState.time)
    }
}

@Composable
fun WeatherRow(
    key:String,
    text:String,
) {
    Row(
    ) {
        Text(text = key)
        Text(text = text, textAlign = TextAlign.End)
    }
}