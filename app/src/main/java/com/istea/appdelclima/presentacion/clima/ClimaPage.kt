package com.istea.appdelclima.presentacion.clima

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.istea.appdelclima.presentacion.clima.clima.ClimaIntencion
import com.istea.appdelclima.presentacion.clima.clima.ClimaView
import com.istea.appdelclima.presentacion.clima.clima.ClimaViewModel
import com.istea.appdelclima.presentacion.clima.clima.ClimaViewModelFactory
import com.istea.appdelclima.presentacion.clima.pronostico.PronosticoView
import com.istea.appdelclima.presentacion.clima.pronostico.PronosticoViewModel
import com.istea.appdelclima.presentacion.clima.pronostico.PronosticoViewModelFactory
import com.istea.appdelclima.repository.RepositorioApi
import com.istea.appdelclima.router.Enrutador

@Composable
fun ClimaPage(
    navHostController: NavHostController,
    lat : Float,
    lon : Float,
    nombre: String
){
    val viewModel : ClimaViewModel = viewModel(
        factory = ClimaViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            lat = lat,
            lon = lon,
            nombre = nombre
        )
    )
    val pronosticoViewModel : PronosticoViewModel = viewModel(
        factory = PronosticoViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            nombre = nombre
        )
    )

    Column(modifier =Modifier
        .fillMaxSize()
        .background(Color(0xFFBBDEFB))
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ClimaView(
            state = viewModel.uiState,
            onAction = { intencion ->
                viewModel.ejecutar(intencion)
            }
        )
        PronosticoView(
            state = pronosticoViewModel.uiState,
            onAction = { intencion ->
                pronosticoViewModel.ejecutar(intencion)
            }
        )
    }

}
