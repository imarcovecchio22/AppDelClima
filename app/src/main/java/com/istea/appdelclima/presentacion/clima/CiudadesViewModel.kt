package com.istea.appdelclima.presentacion.clima

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.istea.appdelclima.repository.Repositorio
import com.istea.appdelclima.repository.RepositorioApi
import com.istea.appdelclima.repository.modelos.Ciudad
import kotlinx.coroutines.launch

class CiudadesViewModel(
    private val repositorio: Repositorio
) : ViewModel() {

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repositorio = RepositorioApi()
                CiudadesViewModel(repositorio)
            }
        }
    }

    var uiState by mutableStateOf<CiudadesEstado>(CiudadesEstado.Vacio)
    var selectedCiudad by mutableStateOf<Ciudad?>(null)

    fun ejecutar(intencion: CiudadesIntencion) {
        when (intencion) {
            is CiudadesIntencion.BuscarCiudad -> buscarCiudad(intencion.nombre)
            CiudadesIntencion.BuscarPorGeolocalizacion -> buscarPorGeolocalizacion()
            is CiudadesIntencion.SeleccionarCiudad -> seleccionarCiudad(intencion.ciudad)
        }
    }

    private fun buscarCiudad(nombre: String) {
        uiState = CiudadesEstado.Cargando
        viewModelScope.launch {
            try {
                val ciudades = repositorio.buscarCiudad(nombre)
                if (ciudades.isNotEmpty()) {
                    uiState = CiudadesEstado.Exitoso(ciudades)
                } else {
                    uiState = CiudadesEstado.Error("No se encontraron ciudades.")
                }
            } catch (e: Exception) {
                uiState = CiudadesEstado.Error("Error al buscar ciudades: ${e.message}")
            }
        }
    }

    private fun buscarPorGeolocalizacion() {
        uiState = CiudadesEstado.Cargando
        // Implementar lógica de búsqueda por geolocalización aquí
        // Por ahora, simulamos la búsqueda con una lista vacía
        viewModelScope.launch {
            try {
                // Aquí deberías agregar la lógica para obtener la ubicación actual
                // y luego buscar las ciudades cercanas usando el repositorio
                val ciudades = listOf<Ciudad>() // Lista simulada, reemplazar con la real
                uiState = CiudadesEstado.Exitoso(ciudades)
            } catch (exception: Exception) {
                uiState = CiudadesEstado.Error("Error al buscar por geolocalización")
            }
        }
    }

    private fun seleccionarCiudad(ciudad: Ciudad) {
        selectedCiudad = ciudad
        // Aquí deberías implementar la lógica para navegar a la vista de clima
        // Por ejemplo, podrías actualizar otro estado que gestione la navegación
    }
}