package zamudio.ramon.thecheezery.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import zamudio.ramon.thecheezery.R
import zamudio.ramon.thecheezery.model.data.coldDrinks
import zamudio.ramon.thecheezery.model.data.database.AppDatabase
import zamudio.ramon.thecheezery.model.data.database.entity.ComboEntity
import zamudio.ramon.thecheezery.model.data.database.entity.ProductEntity
import zamudio.ramon.thecheezery.model.data.database.relation.ComboWithProducts
import zamudio.ramon.thecheezery.model.data.hotDrinks
import zamudio.ramon.thecheezery.model.data.repository.CheezeryRepository
import zamudio.ramon.thecheezery.model.data.salties
import zamudio.ramon.thecheezery.model.data.sweets
import zamudio.ramon.thecheezery.model.domain.Product

class CheezeryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CheezeryRepository =
        CheezeryRepository(AppDatabase.getInstance(application))

    private val estadoCarrito = MutableStateFlow<List<Product>>(emptyList())
    val carrito: StateFlow<List<Product>> = estadoCarrito.asStateFlow()

    private val _tituloCategoriaActual = MutableStateFlow("")
    val tituloCategoriaActual: StateFlow<String> = _tituloCategoriaActual.asStateFlow()

    private val _productosActuales = MutableStateFlow<List<Product>>(emptyList())
    val productosActuales: StateFlow<List<Product>> = _productosActuales.asStateFlow()

    val todosLosProductos: StateFlow<List<ProductEntity>> = repository.getAllProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val combos: StateFlow<List<ComboWithProducts>> = repository.getCombosWithProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var categoryJob: Job? = null

    init {
        seedInitialProductsIfNeeded()
    }

    private fun seedInitialProductsIfNeeded() {
        viewModelScope.launch {
            if (repository.getProductCount() == 0) {
                val initialEntities = buildList {
                    addAll(hotDrinks.toEntities("Hot Drinks"))
                    addAll(coldDrinks.toEntities("Cold Drinks"))
                    addAll(sweets.toEntities("Sweets"))
                    addAll(salties.toEntities("Salties"))
                }
                repository.insertProducts(initialEntities)
            }
        }
    }

    private fun List<Product>.toEntities(category: String): List<ProductEntity> {
        val resources = getApplication<Application>().resources
        return map { product ->
            val imageName = if (product.image != 0) {
                runCatching { resources.getResourceEntryName(product.image) }.getOrNull()
            } else {
                null
            }
            ProductEntity(
                name = product.name,
                price = product.price,
                image = imageName,
                description = product.description,
                category = category
            )
        }
    }

    private fun ProductEntity.toDomain(): Product {
        val app = getApplication<Application>()
        val resId = if (!image.isNullOrBlank()) {
            app.resources.getIdentifier(image, "drawable", app.packageName)
        } else {
            0
        }
        return Product(
            name = name,
            price = price,
            image = if (resId != 0) resId else R.drawable.the_cheezery,
            description = description
        )
    }

    fun establecerCategoria(title: String) {
        _tituloCategoriaActual.value = title
        categoryJob?.cancel()
        categoryJob = viewModelScope.launch {
            repository.getProductsByCategory(title).collect { entities ->
                _productosActuales.value = entities.map { it.toDomain() }
            }
        }
    }

    fun agregarProducto(
        name: String,
        price: Float,
        category: String,
        description: String?
    ) {
        viewModelScope.launch {
            repository.insertProduct(
                ProductEntity(
                    name = name.trim(),
                    price = price,
                    image = null,
                    description = description?.takeIf { it.isNotBlank() },
                    category = category
                )
            )
        }
    }

    fun crearCombo(name: String, price: Float, productIds: List<Int>) {
        viewModelScope.launch {
            repository.createComboWithProducts(
                name = name.trim(),
                price = price,
                productIds = productIds
            )
        }
    }

    fun eliminarCombo(combo: ComboEntity) {
        viewModelScope.launch {
            repository.deleteCombo(combo)
        }
    }

    fun agregarComboAlCarrito(comboWithProducts: ComboWithProducts) {
        val productSummary = comboWithProducts.products.joinToString(", ") { it.name }
        val comboAsProduct = Product(
            name = comboWithProducts.combo.name,
            price = comboWithProducts.combo.price,
            image = R.drawable.the_cheezery,
            description = productSummary
        )
        agregarAlCarrito(comboAsProduct)
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
