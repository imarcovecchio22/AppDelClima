package com.istea.appdelclima.presentacion.clima

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.istea.appdelclima.repository.RepositorioApi
import com.istea.appdelclima.router.Enrutador

@Composable
fun ClimaPage(
    navHostController: NavHostController,
    lat : Float,
    lon : Float
){
    val viewModel : ClimaViewModel = viewModel(
        factory = ClimaViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            lat = lat,
            lon = lon
        )
    )
    ClimaView(
        state = viewModel.uiState,
        onAction = { intencion ->
            viewModel.ejecutar(intencion)
        }
    )
}
