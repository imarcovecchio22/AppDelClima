package com.istea.appdelclima.presentacion.ciudades

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.istea.appdelclima.repository.modelos.Ciudad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadesView(
    modifier: Modifier = Modifier,
    state: CiudadesEstado,
    onAction: (CiudadesIntencion) -> Unit
) {
    var value by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212)) // Fondo oscuro
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = value,
            label = { Text(text = "Buscar por nombre", color = Color.White) },
            onValueChange = {
                value = it
                onAction(CiudadesIntencion.Buscar(value))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        when (state) {
            CiudadesEstado.Cargando -> Text(text = "Cargando...", color = Color.White)
            is CiudadesEstado.Error -> Text(text = state.mensaje, color = Color.Red)
            is CiudadesEstado.Resultado -> ListaDeCiudades(state.ciudades, { onAction(CiudadesIntencion.Seleccionar(it)) })
            CiudadesEstado.Vacio -> Text(text = "No hay resultados", color = Color.White)
        }
        Button(
            onClick = { onAction(CiudadesIntencion.Seleccionar(0)) },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text(text = "Siguiente")
        }
    }
}

@Composable
fun ListaDeCiudades(ciudades: Array<Ciudad>, onSelect: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)), // Fondo oscuro para la lista
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items = ciudades) { index, ciudad ->
            CiudadItem(ciudad = ciudad, onSelect = { onSelect(index) })
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun CiudadItem(ciudad: Ciudad, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .padding(8.dp)
            .background(Color(0xFF1E1E1E)) // Fondo oscuro para cada item
            .padding(16.dp)
    ) {
        Column {
            Text(text = ciudad.name, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            Text(text = ciudad.country, style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
        }
    }
}
