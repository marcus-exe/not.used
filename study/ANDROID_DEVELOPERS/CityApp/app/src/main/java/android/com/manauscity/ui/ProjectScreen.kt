package android.com.manauscity.ui

import android.app.Activity
import android.com.manauscity.R
import android.com.manauscity.data.CityDataProvider
import android.com.manauscity.model.Restaurant
import android.com.manauscity.ui.theme.ManausCityTheme
import android.com.manauscity.utils.ListContentType
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManausCityApp(
    windowSize: WindowWidthSizeClass
) {
    val viewModel: ProjectViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact -> ListContentType.ListOnly
        WindowWidthSizeClass.Medium -> ListContentType.ListOnly
        WindowWidthSizeClass.Expanded -> ListContentType.ListAndDetail
        else -> ListContentType.ListOnly
    }

    Scaffold(
        topBar = {
            ManausCityAppBar(
                onBackButtonClick = { viewModel.navigateToListPage() },
                isShowingListPage = uiState.isShowingListPage,
                windowSize = windowSize,
            )
        }


    ) { innerPadding ->
        if (contentType == ListContentType.ListAndDetail) {
            RestaurantListAndDetail(
                restaurants = uiState.restaurantList,
                onClick = { viewModel.updateCurrentRestaurant(it) },
                selectedRestaurant = uiState.currestRestaurant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding((innerPadding))
            )
        } else {
            if (uiState.isShowingListPage) {
                RestaurantListItemCard(
                    restaurants = uiState.restaurantList,
                    onClick = {
                        viewModel.updateCurrentRestaurant(it)
                        viewModel.navigateToDetailPage()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding((innerPadding))
                )
            } else {
                RestaurantDetail(
                    selectedRestaurant = uiState.currestRestaurant,
                    onBackPressed = { viewModel.navigateToListPage() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding((innerPadding))
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManausCityAppBar(
    onBackButtonClick: () -> Unit,
    isShowingListPage: Boolean,
    windowSize: WindowWidthSizeClass
) {

    val isShowingDetailPage = windowSize != WindowWidthSizeClass.Expanded && !isShowingListPage
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
        navigationIcon = if (isShowingDetailPage) {
            {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        } else {
            { Box() {} }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )


    )
}

@Composable
fun ItemCardImage(restaurant: Restaurant, modifier: Modifier) {
    Box(
        modifier = Modifier.padding(12.dp)
    ) {
        Image(
            painter = painterResource(restaurant.logoImageRes),
            contentDescription = null,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ItemCardText(restaurant: Restaurant, modifier: Modifier) {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        Text(
            text = stringResource(restaurant.name),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(restaurant.adress),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    restaurant: Restaurant,
    modifier: Modifier,
    onItemClick: (Restaurant) -> Unit

) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(),
        onClick = { onItemClick(restaurant) }
    ) {
        Row() {
            ItemCardImage(restaurant = restaurant, modifier = Modifier.weight(2f))
            ItemCardText(restaurant = restaurant, modifier = Modifier.weight(3f))
        }
    }
}


@Composable
fun RestaurantListItemCard(
    restaurants: List<Restaurant>,
    onClick: (Restaurant) -> Unit,
    modifier: Modifier
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        contentPadding = PaddingValues(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        state = scrollState


    ) {
        items(restaurants, key = { restaurant -> restaurant.id }) { restaurant ->
            ItemCard(
                restaurant = restaurant,
                modifier = Modifier.padding(12.dp),
                onItemClick = onClick
            )
        }
    }
}

@Composable
fun RestaurantDetail(
    selectedRestaurant: Restaurant,
    onBackPressed: () -> Unit,
    modifier: Modifier
) {
    BackHandler {
        onBackPressed()
    }


    Box() {
        Column() {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box {
                    Box(
                        modifier = Modifier.height(150.dp),

                        ) {
                        Image(
                            painterResource(id = selectedRestaurant.foodImageRes),
                            contentDescription = null,
                            alignment = Alignment.TopCenter,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth(),

                            )
                    }


                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(start = 12.dp)
                    ) {
                        Text(
                            text = stringResource(id = selectedRestaurant.name),
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.White
                        )
                    }

                }
            }
            Text(
                text = stringResource(id = selectedRestaurant.description),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(12.dp)
            )
            Button(
                onClick = { TODO() },
                modifier = Modifier.padding(12.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.content_copy),
                    contentDescription = "Copy Content",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(id = selectedRestaurant.adress),
                    modifier = Modifier.weight(4f)
                )
            }
        }
    }

}





@Composable
fun RestaurantListAndDetail(
    restaurants: List<Restaurant>,
    onClick: (Restaurant) -> Unit,
    selectedRestaurant: Restaurant,
    modifier: Modifier
) {

    Row {
        Column(
            modifier = Modifier.weight(2f)
        ) {
            RestaurantListItemCard(
                restaurants = restaurants,
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier.weight(3f)
        ) {
            val activity = (LocalContext.current as Activity)
            RestaurantDetail(
                selectedRestaurant = selectedRestaurant,
                onBackPressed = { activity.finish() },
                modifier = Modifier.fillMaxWidth(),
            )
        }

    }
}


@Preview
@Composable
fun ItemCardPreview() {
    ManausCityTheme {
        // A surface container using the 'background' color from the theme
        Surface() {
            ItemCard(
                restaurant = CityDataProvider.defaultRestaurant,
                modifier = Modifier.padding(12.dp),
                onItemClick = {}
            )
        }
    }
}

@Preview
@Composable
fun RestaurantListItemCardPreview() {
    ManausCityTheme {
        // A surface container using the 'background' color from the theme
        Surface() {
            RestaurantListItemCard(
                restaurants = CityDataProvider.getRestaurantData(),
                onClick = {},
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}


@Preview
@Composable
fun RestaurantDetailPreview() {
    ManausCityTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            RestaurantDetail(
                selectedRestaurant = CityDataProvider.defaultRestaurant,
                onBackPressed = {},
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ReplyAppExpandedPreview() {
    ManausCityTheme {
        Surface {
            RestaurantListAndDetail(
                restaurants = CityDataProvider.getRestaurantData(),
                selectedRestaurant = CityDataProvider.defaultRestaurant,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
}


