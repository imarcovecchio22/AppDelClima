package com.istea.appdelclima.presentacion.clima

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.istea.appdelclima.repository.modelos.Ciudad

@Composable
fun CiudadesView(
    modifier: Modifier = Modifier,
    state : CiudadesEstado,
    onAction: (CiudadesIntencion)->Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier.weight(1f).padding(end = 8.dp).background(Color.White)
            )

            Button(onClick = { onAction(CiudadesIntencion.BuscarCiudad(textState.value.text)) }) {
                Text(text = "Buscar ciudad")
            }

            Button(onClick = { onAction(CiudadesIntencion.BuscarPorGeolocalizacion) }) {
                Text(text = "Geolocalización")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (state) {
            is CiudadesEstado.Error -> ErrorViewC(mensaje = state.mensaje)
            is CiudadesEstado.Exitoso -> CiudadesList(ciudades = state.ciudades)
            CiudadesEstado.Vacio -> EmptyViewC()
            CiudadesEstado.Cargando -> LoadingView()
        }
    }
}

@Composable
fun EmptyViewC() {
    Text(text = "No hay nada que mostrar")
}

@Composable
fun ErrorViewC(mensaje: String) {
    Text(text = mensaje)
}

@Composable
fun LoadingView() {
    Text(text = "Cargando...")
}

@Composable
fun CiudadesList(ciudades: List<Ciudad>) {
    Column {
        ciudades.forEach { ciudad ->
            Text(text = ciudad.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}