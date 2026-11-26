package zamudio.ramon.thecheezery.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zamudio.ramon.thecheezery.model.data.database.entity.ProductEntity
import zamudio.ramon.thecheezery.ui.theme.HelloBunnyFont
import zamudio.ramon.thecheezery.ui.theme.Less_Purple
import zamudio.ramon.thecheezery.ui.theme.Pinky
import zamudio.ramon.thecheezery.ui.theme.Very_purple

@Composable
fun AddComboScreen(
    products: List<ProductEntity>,
    onSaveCombo: (name: String, price: Float, productIds: List<Int>) -> Unit,
    onCancel: () -> Unit = {}
) {
    var comboName by remember { mutableStateOf("") }
    var comboPriceText by remember { mutableStateOf("") }
    val selectedProductIds = remember { mutableStateListOf<Int>() }

    val parsedPrice = comboPriceText.toFloatOrNull()
    val isFormValid = comboName.isNotBlank() &&
        parsedPrice != null &&
        parsedPrice > 0f &&
        selectedProductIds.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "New Combo",
            color = Pinky,
            fontSize = 64.sp,
            fontFamily = HelloBunnyFont,
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        )

        OutlinedTextField(
            value = comboName,
            onValueChange = { comboName = it },
            label = { Text("Combo name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = comboPriceText,
            onValueChange = { comboPriceText = it },
            label = { Text("Combo price ($)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Select products (${selectedProductIds.size} selected)",
            color = Less_Purple,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products, key = { it.id }) { product ->
                val isSelected = product.id in selectedProductIds
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isSelected) {
                                selectedProductIds.remove(product.id)
                            } else {
                                selectedProductIds.add(product.id)
                            }
                        }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { checked ->
                            if (checked) {
                                if (!isSelected) selectedProductIds.add(product.id)
                            } else {
                                selectedProductIds.remove(product.id)
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = Very_purple)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                        if (product.category.isNotBlank()) {
                            Text(
                                text = product.category,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    Text(
                        text = "$${product.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Pinky
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel", color = Color.DarkGray)
            }
            Button(
                onClick = {
                    if (parsedPrice != null && isFormValid) {
                        onSaveCombo(comboName.trim(), parsedPrice, selectedProductIds.toList())
                    }
                },
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(containerColor = Very_purple),
                modifier = Modifier.weight(1f)
            ) {
                Text("Save Combo", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddComboScreen() {
    AddComboScreen(
        products = listOf(
            ProductEntity(id = 1, name = "Latte", price = 6f, category = "Hot Drinks"),
            ProductEntity(id = 2, name = "Tiramisu", price = 6f, category = "Sweets")
        ),
        onSaveCombo = { _, _, _ -> }
    )
}
