package com.istea.appdelclima.presentacion.clima

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CiudadesPage(){
    val viewModel : CiudadesViewModel = viewModel(factory = CiudadesViewModel.factory)
    CiudadesView(
        state = viewModel.uiState,
        onAction = { intencion ->
            viewModel.ejecutar(intencion)
        }
    )
}