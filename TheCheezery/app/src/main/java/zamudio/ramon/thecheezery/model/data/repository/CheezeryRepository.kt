package zamudio.ramon.thecheezery.model.data.repository

import kotlinx.coroutines.flow.Flow
import zamudio.ramon.thecheezery.model.data.database.AppDatabase
import zamudio.ramon.thecheezery.model.data.database.entity.ComboEntity
import zamudio.ramon.thecheezery.model.data.database.entity.ProductComboEntity
import zamudio.ramon.thecheezery.model.data.database.entity.ProductEntity
import zamudio.ramon.thecheezery.model.data.database.relation.ComboWithProducts

class CheezeryRepository(private val database: AppDatabase) {

    private val productDao = database.productDao()
    private val comboDao = database.comboDao()
    private val productComboDao = database.productComboDao()

    // Products
    suspend fun insertProduct(product: ProductEntity): Long {
        return productDao.insertProduct(product)
    }

    suspend fun insertProducts(products: List<ProductEntity>) {
        productDao.insertProducts(products)
    }

    suspend fun updateProduct(product: ProductEntity) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun getProductsByCategory(category: String): Flow<List<ProductEntity>> {
        return productDao.getProductsByCategory(category)
    }

    suspend fun getProductById(id: Int): ProductEntity? {
        return productDao.getProductById(productId = id)
    }

    fun searchProducts(name: String): Flow<List<ProductEntity>> {
        return productDao.searchProducts(name)
    }

    suspend fun getProductCount(): Int {
        return productDao.getProductCount()
    }

    // Combos
    suspend fun insertCombo(combo: ComboEntity): Long {
        return comboDao.insertCombo(combo)
    }

    suspend fun updateCombo(combo: ComboEntity) {
        comboDao.updateCombo(combo)
    }

    suspend fun deleteCombo(combo: ComboEntity) {
        comboDao.deleteCombo(combo)
    }

    fun getAllCombos(): Flow<List<ComboEntity>> {
        return comboDao.getAllCombos()
    }

    fun getCombosWithProducts(): Flow<List<ComboWithProducts>> {
        return comboDao.getCombosWithProducts()
    }

    suspend fun getComboWithProductsById(comboId: Int): ComboWithProducts? {
        return comboDao.getComboWithProductsById(comboId)
    }

    // Product-Combo relation
    suspend fun insertProductCombo(productCombo: ProductComboEntity): Long {
        return productComboDao.insertProductCombo(productCombo)
    }

    suspend fun insertProductCombos(productCombos: List<ProductComboEntity>) {
        productComboDao.insertProductCombos(productCombos)
    }

    suspend fun deleteProductCombo(productCombo: ProductComboEntity) {
        productComboDao.deleteProductCombo(productCombo)
    }

    suspend fun createComboWithProducts(
        name: String,
        price: Float,
        productIds: List<Int>
    ): Long {
        val comboId = comboDao.insertCombo(ComboEntity(name = name, price = price)).toInt()
        val relations = productIds.distinct().map { productId ->
            ProductComboEntity(idProduct = productId, idCombo = comboId)
        }
        if (relations.isNotEmpty()) {
            productComboDao.insertProductCombos(relations)
        }
        return comboId.toLong()
    }
}
