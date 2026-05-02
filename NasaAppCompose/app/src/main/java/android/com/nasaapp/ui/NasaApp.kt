package android.com.nasaapp.ui

import android.com.nasaapp.ui.screens.HomeScreen
import android.com.nasaapp.ui.screens.NasaViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NasaApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = { topAppBar(scrollBehavior = scrollBehavior) },
        modifier = Modifier.padding(4.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val nasaViewModel: NasaViewModel = viewModel(factory = NasaViewModel.Factory)
            HomeScreen(
                nasaUiState = nasaViewModel.nasaUiState,
                retryAction = nasaViewModel::getNasaInfo
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Nasa App"
            )
        },
        modifier = modifier
    )
}