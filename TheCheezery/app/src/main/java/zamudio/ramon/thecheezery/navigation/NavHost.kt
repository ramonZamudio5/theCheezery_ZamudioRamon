package zamudio.ramon.thecheezery.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import zamudio.ramon.thecheezery.view.screens.AddComboScreen
import zamudio.ramon.thecheezery.view.screens.CarritoScreen
import zamudio.ramon.thecheezery.view.screens.LoginScreen
import zamudio.ramon.thecheezery.view.screens.MenuScreen
import zamudio.ramon.thecheezery.view.screens.PrincipalScreen
import zamudio.ramon.thecheezery.view.screens.ProductsScreen
import zamudio.ramon.thecheezery.viewModel.CheezeryViewModel

@Composable
fun Myapp(innerPadding: PaddingValues, viewModel: CheezeryViewModel = viewModel()) {
    val navController = rememberNavController()
    val navigateToLogin: () -> Unit = {
        navController.navigate(Login)
    }
    val navigateToMenu: () -> Unit = {
        navController.navigate(Menu)
    }
    val navigateToCarrito: () -> Unit = {
        navController.navigate(Carrito)
    }
    val navigateToAddCombo: () -> Unit = {
        navController.navigate(AddCombo)
    }

    val cartItems by viewModel.carrito.collectAsState()
    val currentTitle by viewModel.tituloCategoriaActual.collectAsState()
    val currentProducts by viewModel.productosActuales.collectAsState()
    val allProducts by viewModel.todosLosProductos.collectAsState()

    NavHost(navController, startDestination = Principal) {
        composable<Principal> {
            PrincipalScreen(navigateToLogin)
        }
        composable<Menu> {
            MenuScreen(
                navigateToProducts = { titulo ->
                    viewModel.establecerCategoria(titulo)
                    navController.navigate(Producto)
                },
                navigateToCart = navigateToCarrito,
                navigateToAddCombo = navigateToAddCombo
            )
        }
        composable<Login> {
            LoginScreen(navigateToMenu)
        }
        composable<Producto> {
            ProductsScreen(
                title = currentTitle,
                products = currentProducts,
                onAgregarAlCarrito = { product -> viewModel.agregarAlCarrito(product) }
            )
        }
        composable<Carrito> {
            CarritoScreen(
                cartItems = cartItems,
                onRemoveItem = { product -> viewModel.eliminarDelCarrito(product) }
            )
        }
        composable<AddCombo> {
            AddComboScreen(
                products = allProducts,
                onSaveCombo = { name, price, productIds ->
                    viewModel.crearCombo(name, price, productIds)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
