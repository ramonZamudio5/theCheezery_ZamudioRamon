package zamudio.ramon.thecheezery.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zamudio.ramon.thecheezery.R
import zamudio.ramon.thecheezery.ui.theme.Pinky
import zamudio.ramon.thecheezery.ui.theme.Purple_grey

@Composable
fun PrincipalScreen(navigateToLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(R.drawable.the_cheezery), contentDescription = "The Cheezery Logo",
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentScale = ContentScale.Crop)
        Column(
            modifier = Modifier.fillMaxWidth().background(Purple_grey).padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("Welcome to The Cheezery", color = Color.White, fontSize = 24.sp,
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Home of the most wonderful desserts ever seen (and tasted) by the human being.", color = Color.White,
                fontSize = 14.sp, fontStyle = FontStyle.Italic, textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navigateToLogin() }, colors = ButtonDefaults.buttonColors(containerColor = Pinky)) {
                Text("Get started!", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPrincipalScreen() {
    PrincipalScreen(navigateToLogin = {})
}