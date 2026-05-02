package android.com.nasaapp.ui.screens

import android.com.nasaapp.R
import android.com.nasaapp.model.NasaInfo
import android.com.nasaapp.ui.theme.NasaAppTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun HomeScreen(
    nasaUiState: NasaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (nasaUiState) {
        is NasaUiState.Loading -> LoadingScreen(modifier)
        is NasaUiState.Success -> NasaGridCard(nasaListInfo = nasaUiState.info)
        is NasaUiState.Error -> ErrorScreen(retryAction, modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = null,
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.broken_img),
            contentDescription = null
        )
        Button(onClick = retryAction) {
            Text(text = "Retry Action")
        }
    }
}

@Composable
fun NasaCard(
    nasaInfo: NasaInfo,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(nasaInfo.url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            error = painterResource(id = R.drawable.broken_img),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxWidth(),
        )
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NasaGridCard(
    nasaListInfo: List<NasaInfo>,
    modifier: Modifier = Modifier
){
    val scrollState = rememberLazyListState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(190.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(nasaListInfo) {
                NasaCard(nasaInfo = it)
            }
        }
    )

}




@Preview
@Composable
fun NasaListPreview() {
    val mockdata = List(10) { NasaInfo(".", ".", ".", ".", ".", ".", ".", ".") }
    NasaAppTheme {
        NasaGridCard(nasaListInfo = mockdata, modifier = Modifier.fillMaxWidth())
    }
}








