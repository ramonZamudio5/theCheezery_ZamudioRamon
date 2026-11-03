package zamudio.ramon.thecheezery.view.screens
import zamudio.ramon.thecheezery.ui.theme.Very_purple
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zamudio.ramon.thecheezery.R
import zamudio.ramon.thecheezery.model.domain.Product
import zamudio.ramon.thecheezery.ui.theme.HelloBunnyFont
import zamudio.ramon.thecheezery.ui.theme.Pinky
import zamudio.ramon.thecheezery.ui.theme.Less_Purple

@Composable
fun CarritoScreen(
    cartItems: List<Product>,
    onRemoveItem: (Product) -> Unit
) {
    val totalAmount = cartItems.sumOf { it.price.toDouble() }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Order",
            color = Pinky,
            fontSize = 72.sp,
            fontFamily = HelloBunnyFont,
            modifier = Modifier.padding(top = 40.dp, bottom = 24.dp)
        )

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "Add some products to your order!",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 32.dp)
            ) {
                items(cartItems) { product ->
                    CartItemRow(product = product, onRemove = { onRemoveItem(product) })
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp, top = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = "Total  ", fontSize = 40.sp, color = Color.DarkGray)
            Text(text = "$${totalAmount}", fontSize = 50.sp, color = Less_Purple, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CartItemRow(product: Product, onRemove: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = product.name, fontSize = 20.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(text = "$${product.price}", fontSize = 20.sp, color = Very_purple, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = onRemove, modifier = Modifier.size(28.dp)) {
            Icon(painterResource(R.drawable.delete), contentDescription = "Remove item", tint = Very_purple)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCartScreen() {
    val dummyCart = listOf(
        Product(name = "Panini", price = 4.0F, image = 0),
        Product(name = "Cold Brew", price = 3.0F, image = 0),
        Product(name = "Matcha Latte", price = 4.0F, image = 0),
        Product(name = "Nachos", price = 7.0F, image = 0),
        Product(name = "Tiramisu", price = 6.0F, image = 0)
    )

    CarritoScreen(cartItems = dummyCart,{})
}