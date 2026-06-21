package com.example.weatherapp2.ui

import android.app.Activity
import android.content.res.Configuration
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
import com.example.weatherapp2.ui.theme.CoolWeatherApp2Theme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp2.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(/*weatherViewModel: WeatherViewModel*/) {
    /*
    val weatherUIState by weatherViewModel.uiState.collectAsState()
    val latitude = weatherUIState.latitude
    val longitude = weatherUIState.longitude
    */

    val varOrientation = LocalConfiguration.current.orientation

    if(varOrientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeWeatherScreen()
    }
    else {
        PortraitWeatherScreen()
    }
}

@Composable
fun PortraitWeatherScreen() {

}

@Composable
fun LandscapeWeatherScreen() {

}

@Preview(showBackground = true)
@Composable
fun PortraitWeatherScreenPreview() {
    CoolWeatherApp2Theme {
        PortraitWeatherScreen()
    }
}


@Preview(showBackground = true)
@Composable
fun LandscapeWeatherScreenPreview() {
    CoolWeatherApp2Theme {
        LandscapeWeatherScreen()
    }
}
