package zamudio.ramon.thecheezery.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zamudio.ramon.thecheezery.R
import zamudio.ramon.thecheezery.model.domain.Product
import zamudio.ramon.thecheezery.ui.theme.Pinky
import zamudio.ramon.thecheezery.model.data.salties
import zamudio.ramon.thecheezery.ui.theme.DisabledGrey
import zamudio.ramon.thecheezery.ui.theme.HelloBunnyFont
import zamudio.ramon.thecheezery.ui.theme.Less_Purple
import zamudio.ramon.thecheezery.ui.theme.PurpleGrey40
import zamudio.ramon.thecheezery.ui.theme.Very_purple

@Composable
fun ProductsScreen(title: String,
                   products: List<Product>,
                   onAgregarAlCarrito: (Product) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = Pinky, fontSize = 88.sp, fontFamily = HelloBunnyFont,
            modifier = Modifier.padding(vertical = 24.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductItem(product, { onAgregarAlCarrito(product) })
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onAgregarAlCarrito: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = product.image), contentDescription = product.name,
            contentScale = ContentScale.Crop, modifier = Modifier.size(70.dp).clip(CircleShape))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(product.name, color = Less_Purple, fontSize = 20.sp,
                fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif)
            Text(product.description ?: "", color = Color.Gray, fontSize = 12.sp, lineHeight = 16.sp,
                modifier = Modifier.padding(top = 4.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("$${product.price}", color = Pinky, fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            FilledIconButton(
                onClick =  onAgregarAlCarrito,
                modifier = Modifier.size(36.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Very_purple,
                    contentColor = Color.White,
                    disabledContainerColor = DisabledGrey,
                    disabledContentColor = PurpleGrey40
                ),
                enabled = true
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "Add to cart",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductsScreen() {
    ProductsScreen( "Salties", salties,{})
}