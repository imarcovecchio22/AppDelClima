package com.istea.appdelclima.presentacion.clima.pronostico

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
            dt_txt = "2021-04-14 12:00:00"
        )
    )

    AppDelClimaTheme {
        PronosticoView(state = PronosticoEstado.Exitoso(exampleForecasts), onAction = {})
    }
}

@Composable
fun PronosticoListView(climas: List<ListForecast>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items = climas) { item ->
            PronosticoCard(item = item)
            Divider()
        }
    }
}

@Composable
fun PronosticoCard(item: ListForecast) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.dt_txt.split(" ")[0],
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${item.main.temp}°C",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Temp",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${item.main.temp_max}°C",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    Text(
                        text = "Max",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${item.main.temp_min}°C",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                    Text(
                        text = "Min",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


