package android.com.artspaceapp

import android.com.artspaceapp.ui.theme.ArtSpaceAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(

                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    FinalApp()
                }
            }
        }
    }
}

@Composable
fun FinalApp(){
    var number by remember { mutableStateOf(1) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        ArtDisplay(number)
        Spacer(modifier = Modifier.padding(20.dp))
        InfoDisplay(number)
        Spacer(modifier = Modifier.padding(10.dp))


        //Don't know how to use number outside this function
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(
                modifier = Modifier.padding(30.dp)
            )
            Button(
                onClick = { number = logicButtonPrevious(number) },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Previous",
                    textAlign = TextAlign.Center
                )
            }
            Spacer(
                modifier = Modifier.padding(10.dp)
            )

            Button(
                onClick = { number = logicButtonNext(number) },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Next",
                    textAlign = TextAlign.Center
                )
            }
            Spacer(
                modifier = Modifier.padding(30.dp)
            )
        }

    }
}

@Composable
fun ArtDisplay(
    number: Int
){
    val painterResource = when(number){
        1 -> R.drawable.bianco_garotos_brincando
        2 -> R.drawable.bianco_nu_1
        3 -> R.drawable.bianco_nu_2
        4 -> R.drawable.bianco_nu_3
        5 -> R.drawable.orlando_teruz_cavalo_branco
        else -> R.drawable.ralph_genre_montanha
    }
    Card(
        modifier = Modifier
            .padding(16.dp)
        ,
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Image(
            painter = painterResource(id = painterResource),
            contentDescription = null,
            alignment = Alignment.Center,
            modifier = Modifier.padding(
                start = 35.dp,
                end = 35.dp,
                top = 15.dp,
                bottom = 15.dp)

        )
    }
}

@Composable
fun InfoDisplay(number: Int){

    val artWorkTitle = when(number) {
        1 -> stringResource(R.string.titulo_garotos_brincando)
        2 -> stringResource(R.string.titulo_nu_1)
        3 -> stringResource(R.string.titulo_nu_2)
        4 -> stringResource(R.string.titulo_nus)
        5 -> stringResource(R.string.titulo_cavalo_branco)
        else -> stringResource(R.string.titulo_fonte_e_montanha)
    }
    val artWorkArtist = when(number) {
        1 -> stringResource(R.string.artista_enrico_bianco)
        2 -> stringResource(R.string.artista_enrico_bianco)
        3 -> stringResource(R.string.artista_enrico_bianco)
        4 -> stringResource(R.string.artista_enrico_bianco)
        5 -> stringResource(R.string.artista_orlando_teruz)
        else -> stringResource(R.string.artista_ralph_genre)
    }

    val yearArtWorkArtist = when(number) {
        1 -> stringResource(R.string.year_2005)
        2 -> stringResource(R.string.year_1987)
        3 -> stringResource(R.string.year_1990)
        4 -> stringResource(R.string.year_1975)
        5 -> stringResource(R.string.year_1983)
        else -> stringResource(R.string.year_2022)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .background(Color.LightGray)
            .padding(16.dp)


    ){
        Text(text = artWorkTitle)
        Row{
            Text(text = artWorkArtist)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = yearArtWorkArtist)
        }
    }
}

@Composable
fun ButtonMoves(number: Int){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(
            modifier = Modifier.padding(30.dp)
        )
        Button(
            onClick = { logicButtonNext(number) },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Previous",
                textAlign = TextAlign.Center
            )
        }
        Spacer(
            modifier = Modifier.padding(10.dp)
        )

        Button(
            onClick = { logicButtonPrevious(number) },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Next",
                textAlign = TextAlign.Center
            )
        }
        Spacer(
            modifier = Modifier.padding(30.dp)
        )
    }
}


fun logicButtonNext(number: Int) : Int{
    var new_number : Int
    if (number > 5) {
        new_number = 1
        return new_number
    } else {
        new_number = number + 1
        return new_number
    }

}

fun logicButtonPrevious(number: Int) : Int{
    var new_number : Int
    if (number <= 1) {
        new_number = 6
        return new_number
    } else {
        new_number = number - 1
        return new_number
    }
}

@Preview (showBackground = true)
@Composable
fun DefaultPreview(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FinalApp()
    }
}