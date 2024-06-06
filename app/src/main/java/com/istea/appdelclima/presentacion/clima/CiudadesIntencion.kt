package com.istea.appdelclima.presentacion.clima

sealed class CiudadesIntencion {
    data class BuscarCiudad(val nombre: String) : CiudadesIntencion()
    object BuscarPorGeolocalizacion : CiudadesIntencion()
}