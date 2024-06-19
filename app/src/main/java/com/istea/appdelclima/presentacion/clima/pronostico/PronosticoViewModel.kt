package com.istea.appdelclima.presentacion.clima.pronostico
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.appdelclima.repository.Repositorio
import com.istea.appdelclima.repository.modelos.ListForecast
import com.istea.appdelclima.repository.modelos.MainForecast
import com.istea.appdelclima.router.Router
import kotlinx.coroutines.launch

class PronosticoViewModel(
    val respositorio: Repositorio,
    val router: Router,
    val nombre: String
) : ViewModel() {

    var uiState by mutableStateOf<PronosticoEstado>(PronosticoEstado.Vacio)

    fun ejecutar(intencion: PronosticoIntencion){
        when(intencion){
            PronosticoIntencion.actualizarClima -> traerPronostico()
            PronosticoIntencion.volverAtras-> back()
        }
    }

    fun traerPronostico() {
        uiState = PronosticoEstado.Cargando
        viewModelScope.launch {
            try{
                val forecasts = respositorio.traerPronostico(nombre)
                    .filter { it.dt_txt.contains("12:00:00") } // Filtra por la hora deseada
                    .groupBy { it.dt_txt.split(" ")[0] } // Agrupa por fecha (ignorando la hora)

                // Ahora obtenemos solo un pronóstico por día (puedes elegir el primero, el último, etc.)
                val filteredForecasts = forecasts.mapValues { (_, forecastsForDay) ->
                    forecastsForDay.maxByOrNull { it.main.temp_max }!! // Pronóstico máximo del día
                }.values.toList()

                println("Pronóstico cargado exitosamente: $filteredForecasts")
                uiState = PronosticoEstado.Exitoso(filteredForecasts)
            } catch (exception: Exception){
                println("Error al cargar pronóstico: ${exception.localizedMessage}")
                uiState = PronosticoEstado.Error(exception.localizedMessage ?: "error desconocido")
            }
        }
    }
    fun back(){
        router.back()
    }

}

class PronosticoViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router,
    private val nombre: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PronosticoViewModel::class.java)) {
            return PronosticoViewModel(repositorio,router,nombre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}