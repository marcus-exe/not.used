package android.com.amphiniansapp.ui.screens

import android.com.amphiniansapp.R
import android.com.amphiniansapp.model.AmphibianInfo
import android.com.amphiniansapp.ui.theme.AmphiniansAppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (amphibiansUiState) {
        is AmphibiansUiState.Loading -> LoadingScreen(modifier)
        is AmphibiansUiState.Success -> AmphibianListCard(amphibianListInfo = amphibiansUiState.info)
        is AmphibiansUiState.Error -> ErrorScreen(retryAction, modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(dimensionResource(id = R.dimen.image_size)),
        painter = painterResource(id = R.drawable.loading),
        contentDescription = stringResource(id = R.string.loading_description)
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
            painter = painterResource(id = R.drawable.warning),
            contentDescription = stringResource(id = R.string.error_description)
        )
        Button(onClick = retryAction) {
            Text(text = stringResource(id = R.string.retry_button))
        }
    }
}

@Composable
fun AmphibianCard(amphibian: AmphibianInfo, modifier: Modifier = Modifier) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation)),
        modifier = modifier,
    ) {

        Text(
            text = "${amphibian.name} (${amphibian.type})",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )


        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(amphibian.imgSrc)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.amphibian_image),
            error = painterResource(id = R.drawable.warning),
            placeholder = painterResource(id = R.drawable.loading),
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxWidth(),

            )
        Text(
            text = amphibian.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}

@Composable
fun AmphibianListCard(
    amphibianListInfo: List<AmphibianInfo>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.content_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spaced_by)),
        state = scrollState
    ) {
        items(
            amphibianListInfo
        ) {
            AmphibianCard(amphibian = it)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AmphiniansAppTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AmphiniansAppTheme {
        ErrorScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibianCardPreview() {
    val mockdata = AmphibianInfo("Sapo-Boi", "Sapão", "É um sapão", ".")
    AmphiniansAppTheme(darkTheme = true) {
        AmphibianCard(mockdata)
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibianListCardPreview() {
    val mockdata = List(10) { AmphibianInfo("Sapo-Boi", "Sapão", "É um sapão", ".") }
    AmphiniansAppTheme {
        AmphibianListCard(mockdata)
    }
}
