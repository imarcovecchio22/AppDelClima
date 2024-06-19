package com.istea.appdelclima.presentacion.clima.pronostico

import com.istea.appdelclima.presentacion.clima.clima.ClimaIntencion

sealed class PronosticoIntencion {
    object actualizarClima: PronosticoIntencion()
    object volverAtras: PronosticoIntencion()
}
