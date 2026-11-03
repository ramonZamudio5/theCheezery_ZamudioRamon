package zamudio.ramon.thecheezery.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import zamudio.ramon.thecheezery.view.screens.CarritoScreen
import zamudio.ramon.thecheezery.view.screens.LoginScreen
import zamudio.ramon.thecheezery.view.screens.MenuScreen
import zamudio.ramon.thecheezery.view.screens.PrincipalScreen
import zamudio.ramon.thecheezery.view.screens.ProductsScreen
import zamudio.ramon.thecheezery.viewModel.CheezeryViewModel

@Composable
fun Myapp(innerPadding : PaddingValues,viewModel: CheezeryViewModel = viewModel()){
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
    val viewModel: CheezeryViewModel = viewModel()
    val cartItems by viewModel.carrito.collectAsState()
    val currentTitle by viewModel.tituloCategoriaActual.collectAsState()
    val currentProducts by viewModel.productosActuales.collectAsState()

    NavHost(navController, startDestination = Principal) {
        composable<Principal> {
            PrincipalScreen(navigateToLogin)
        }
        composable<Menu> {
            MenuScreen({ titulo, listaProductos ->
                viewModel.establecerCategoria(titulo, listaProductos)
                navController.navigate(Producto)
            }, navigateToCarrito )
        }
        composable<Login> {
            LoginScreen(navigateToMenu)
        }
        composable<Producto> {
            ProductsScreen(
                currentTitle, currentProducts, { product -> viewModel.agregarAlCarrito(product) }
            )
        }
        composable<Carrito> {
            CarritoScreen(cartItems, { product -> viewModel.eliminarDelCarrito(product) })
        }
    }
}
