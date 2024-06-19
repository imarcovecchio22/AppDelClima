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
                val forecast2 = respositorio.traerPronostico(nombre).filter {
                    //TODO agregar logica de filtrado
                    it.dt_txt.contains("06:00:00") || it.dt_txt.contains("12:00:00") || it.dt_txt.contains("18:00:00")
                    true
                }
                val forecast = listOf(
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
                println("Pronóstico cargado exitosamente: $forecast2")
                uiState = PronosticoEstado.Exitoso(forecast2)
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