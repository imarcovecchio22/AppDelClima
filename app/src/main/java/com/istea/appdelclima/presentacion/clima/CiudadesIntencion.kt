package com.istea.appdelclima.presentacion.clima

import com.istea.appdelclima.repository.modelos.Ciudad

sealed class CiudadesIntencion {
    data class BuscarCiudad(val nombre: String) : CiudadesIntencion()
    object BuscarPorGeolocalizacion : CiudadesIntencion()

    data class SeleccionarCiudad(val ciudad: Ciudad) : CiudadesIntencion()
}