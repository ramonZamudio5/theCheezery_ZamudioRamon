package zamudio.ramon.thecheezery.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Button
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zamudio.ramon.thecheezery.R

@Composable
fun LoginScreen(NavigateToMenu : () -> Unit){
    var correo by remember{ mutableStateOf("")}
    var contrasenia = remember{ TextFieldState()}
    Column(Modifier.fillMaxSize().background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painterResource(R.drawable.grupo1),contentDescription = "The Cheezery Logo")
        TextField(
            value = correo,
            onValueChange = { it -> correo = it },
            label = {
                Text("Correo")
            }
        )
        SecureTextField(state = contrasenia,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = {
                Text("Contraseña")
            },
            textObfuscationMode = TextObfuscationMode.RevealLastTyped
        )
        Spacer(Modifier.size(20.dp))
        Button(onClick = {NavigateToMenu()}) {
            Text("Entrar")
        }
    }
}


@Preview
@Composable
fun previewLoginScreen(){
    LoginScreen({})
}