package com.istea.appdelclima.presentacion.clima

import com.istea.appdelclima.repository.modelos.Ciudad

sealed class CiudadesEstado {
    data object Vacio : CiudadesEstado()
    data object Cargando : CiudadesEstado()
    data class Exitoso(val ciudades: List<Ciudad>) : CiudadesEstado()
    data class Error(val mensaje: String) : CiudadesEstado()
}