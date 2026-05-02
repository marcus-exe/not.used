package android.com.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.com.lemonade.ui.theme.LemonadeTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import java.time.Year

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeApp()
                }

            }
        }
    }
}
@Preview
@Composable
fun LemonadeApp() {
    AppBar()
    ImageButtonAndText(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun ImageButtonAndText(modifier: Modifier = Modifier){

    var tapCount by remember { mutableStateOf(0) }

    var randomTapNumber by remember { mutableStateOf((3..6).random())}

    var stage by remember { mutableStateOf(1)}

    val imageResource = when (stage) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    var textResource = when (stage) {
        1 -> R.string.select_lemon_command
        2 -> R.string.squeeze_lemon_command
        3 -> R.string.drink_lemonade_command
        else -> R.string.empty_glass_command
    }
    var contentDescription = when (stage) {
        1 -> R.string.lemon_tree_content_description
        2 -> R.string.lemon_content_description
        3 -> R.string.squeeze_lemon_command
        else -> R.string.empty_glass_content_description
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {


        Button(
            onClick = {
                if (stage == 2) {
                    if (tapCount >= randomTapNumber){
                        stage++
                        tapCount = -1
                    }
                    tapCount ++

                }
                else if(stage in 1..3){
                    stage++
                }
                else {
                    stage = 1
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xC3,0xEC,0xD2))
        ) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = stringResource(contentDescription)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(textResource),
            color = Color.Black,
            fontSize = 18.sp
        )
    }
    
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(){
    TopAppBar(
        title = {
            Text(text = "Lemonade")
        },
        Modifier.background(Color.Yellow)
    )
}
