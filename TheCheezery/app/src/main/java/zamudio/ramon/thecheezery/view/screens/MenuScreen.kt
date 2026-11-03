package zamudio.ramon.thecheezery.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // Importación necesaria para los clics
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zamudio.ramon.thecheezery.R
import zamudio.ramon.thecheezery.model.domain.Product
import zamudio.ramon.thecheezery.model.data.hotDrinks
import zamudio.ramon.thecheezery.model.data.coldDrinks
import zamudio.ramon.thecheezery.model.data.salties
import zamudio.ramon.thecheezery.model.data.sweets
import zamudio.ramon.thecheezery.ui.theme.Brighter_Pink
import zamudio.ramon.thecheezery.ui.theme.Pinky

@Composable
fun MenuScreen(
    navigateToProducts: (String, List<Product>) -> Unit = { _, _ -> },
    navigateToCart: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.End) {
            Image(
                painter = painterResource(R.drawable.cart),
                contentDescription = "Cart",
                modifier = Modifier
                    .size(50.dp)
                    .background(Pinky, CircleShape)
                    .clickable { navigateToCart() }
                    .padding(12.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp), horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(R.drawable.grupo2),
                contentDescription = "The Cheezery Logo",
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(260.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Brighter_Pink, Pinky)
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Hot drinks",
                    color = Color.White,
                    modifier = Modifier.clickable { navigateToProducts("Hot Drinks", hotDrinks) }.padding(8.dp)
                )
                Text(
                    text = "Salties",
                    color = Color.White,
                    modifier = Modifier.clickable { navigateToProducts("Salties", salties) }.padding(8.dp)
                )
                Text(
                    text = "Combos",
                    color = Color.White,
                    modifier = Modifier.clickable {  }.padding(8.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(260.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Brighter_Pink, Pinky)
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Cold drinks",
                    color = Color.White,
                    modifier = Modifier.clickable { navigateToProducts("Cold Drinks", coldDrinks) }.padding(8.dp)
                )
                Text(
                    text = "Sweets",
                    color = Color.White,
                    modifier = Modifier.clickable { navigateToProducts("Sweets", sweets) }.padding(8.dp)
                )
                Text(
                    text = "Add new product",
                    color = Color.White,
                    modifier = Modifier.clickable { }.padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun previewMenuScreen() {
    MenuScreen()
}