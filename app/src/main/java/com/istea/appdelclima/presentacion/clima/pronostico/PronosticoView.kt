package com.istea.appdelclima.presentacion.clima.pronostico

import androidx.compose.foundation.background
import com.istea.appdelclima.repository.modelos.Clima
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.istea.appdelclima.presentacion.clima.clima.ClimaEstado
import com.istea.appdelclima.presentacion.clima.clima.ClimaIntencion
import com.istea.appdelclima.presentacion.clima.clima.ClimaView
import com.istea.appdelclima.repository.modelos.ListForecast
import com.istea.appdelclima.repository.modelos.MainForecast
import com.istea.appdelclima.ui.theme.AppDelClimaTheme


@Composable
fun PronosticoView(
    modifier: Modifier = Modifier,
    state : PronosticoEstado,
    onAction: (PronosticoIntencion)->Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(PronosticoIntencion.actualizarClima)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state){
            is PronosticoEstado.Error -> ErrorView(mensaje = state.mensaje)
            is PronosticoEstado.Exitoso -> PronosticoListView(climas =state.climas)
            PronosticoEstado.Vacio -> EmptyView()
            PronosticoEstado.Cargando -> LoadingView()
        }
        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = { onAction(PronosticoIntencion.volverAtras) }) {
            Text(text = "Volver atrás")
        }
    }
}

@Composable
fun EmptyView(){
    Text(text = "No hay nada que mostrar")
}

@Composable
fun LoadingView(){
    Text(text = "Cargando")
}

@Composable
fun ErrorView(mensaje: String){
    Text(text = mensaje)
}

@Composable
fun PronosticoView(climas: List<ListForecast>){
    LazyColumn {
        items(items = climas) { item ->
            Card(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Temp: ${item.main.temp}°C", fontSize = 20.sp)
                    Text(text = "Min: ${item.main.temp_min}°C, Max: ${item.main.temp_max}°C", fontSize = 16.sp)
                    Text(text = "Fecha: ${item.dt_txt}", fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PronosticoPreviewExitoso() {
    val exampleForecasts = listOf(
        ListForecast(
            dt = 1618317040,
            main = MainForecast(
                temp = 20.0,
                feels_like = 19.0,
                temp_min = 18.0,
                temp_max = 22.0,
                pressure = 1013,
                sea_level = 1013,
                grnd_level = 1000,
                humidity = 60,
                temp_kf = 1.5
            ),
            dt_txt = "2021-04-13 06:00:00"
        ),
        ListForecast(
            dt = 1618327040,
            main = MainForecast(
                temp = 22.0,
                feels_like = 21.0,
                temp_min = 19.0,
                temp_max = 24.0,
                pressure = 1014,
                sea_level = 1014,
                grnd_level = 1001,
                humidity = 55,
                temp_kf = 1.5
            ),
            dt_txt = "2021-04-13 12:00:00"
        )
    )

    AppDelClimaTheme {
        PronosticoView(state = PronosticoEstado.Exitoso(exampleForecasts), onAction = {})
    }
}

@Composable
fun PronosticoListView(climas: List<ListForecast>) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(items = climas) { item ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.dt_txt.split(" ")[0], // Only display the date part
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${item.main.temp}°C",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Min: ${item.main.temp_min}°C",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Max: ${item.main.temp_max}°C",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }

            }
        }
    }
}