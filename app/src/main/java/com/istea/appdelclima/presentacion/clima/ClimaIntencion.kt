package com.istea.appdelclima.presentacion.clima

sealed class ClimaIntencion {
    object BorrarTodo: ClimaIntencion()
    object MostrarDetalleClima: ClimaIntencion()
    object MostrarGrafico: ClimaIntencion()
    object MostrarError: ClimaIntencion()
}
