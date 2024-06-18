package com.istea.appdelclima.presentacion.clima

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.istea.appdelclima.R
import com.istea.appdelclima.ui.theme.AppDelClimaTheme

@Composable
fun ClimaView(
    modifier: Modifier = Modifier,
    state: ClimaEstado,
    onAction: (ClimaIntencion) -> Unit,
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(ClimaIntencion.actualizarClima)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFBBDEFB))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            is ClimaEstado.Error -> ErrorView(mensaje = state.mensaje)
            is ClimaEstado.Exitoso -> ClimaContentView(
                ciudad = state.ciudad,
                temperatura = state.temperatura,
                descripcion = state.descripcion,
                st = state.st
            )
            ClimaEstado.Vacio -> EmptyView()
            ClimaEstado.Cargando -> LoadingView()
        }
        Spacer(modifier = Modifier.height(20.dp))


        Button(onClick = { onAction(ClimaIntencion.volverAtras) }) {
            Text(text = "Volver atrás")
        }


        Spacer(modifier = Modifier.height(100.dp))

    }
}

@Composable
fun EmptyView() {
    Text(
        text = "No hay nada que mostrar",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LoadingView() {
    Text(
        text = "Cargando",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ErrorView(mensaje: String) {
    Text(
        text = mensaje,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.error
    )
}

@Composable
fun ClimaContentView(ciudad: String, temperatura: Double, descripcion: String, st: Double) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = ciudad,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${temperatura}°",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = descripcion,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sensación térmica: ${st}°",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_weather_icon),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(64.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClimaPreviewVacio() {
    AppDelClimaTheme {
        ClimaView(state = ClimaEstado.Vacio, onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ClimaPreviewError() {
    AppDelClimaTheme {
        ClimaView(state = ClimaEstado.Error("Se rompió todo"), onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ClimaPreviewExitoso() {
    AppDelClimaTheme {
        ClimaView(state = ClimaEstado.Exitoso(ciudad = "Mendoza", temperatura = 20.0, descripcion = "Soleado", st = 22.0), onAction = {})
    }
}
