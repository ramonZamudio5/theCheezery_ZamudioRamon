package zamudio.ramon.thecheezery.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import zamudio.ramon.thecheezery.model.domain.Product

class CheezeryViewModel : ViewModel() {

    private val estadoCarrito = MutableStateFlow<List<Product>>(emptyList())
    val carrito: StateFlow<List<Product>> = estadoCarrito.asStateFlow()

    private val _tituloCategoriaActual = MutableStateFlow("")
    val tituloCategoriaActual: StateFlow<String> = _tituloCategoriaActual.asStateFlow()

    private val _productosActuales = MutableStateFlow<List<Product>>(emptyList())
    val productosActuales: StateFlow<List<Product>> = _productosActuales.asStateFlow()

    fun establecerCategoria(title: String, products: List<Product>) {
        _tituloCategoriaActual.value = title
        _productosActuales.value = products
    }

    fun agregarAlCarrito(product: Product) {
        estadoCarrito.value = estadoCarrito.value + product
    }

    fun eliminarDelCarrito(product: Product) {
        val currentList = estadoCarrito.value.toMutableList()
        currentList.remove(product)
        estadoCarrito.value = currentList
    }
}