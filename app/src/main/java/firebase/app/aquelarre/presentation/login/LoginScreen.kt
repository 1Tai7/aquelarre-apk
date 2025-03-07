package firebase.app.aquelarre.presentation.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import firebase.app.aquelarre.presentation.initial.BottomMenuItem
import firebase.app.aquelarre.ui.theme.PrimaryPurple
import firebase.app.aquelarre.ui.theme.White


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import firebase.app.aquelarre.R

@Composable
fun LoginScreen(
    navigateToSignUp: () -> Unit = {},
    navigateToForo: () -> Unit = {} ,
    navigateToHome: () -> Unit = {},
    auth: FirebaseAuth
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(White, PrimaryPurple), startY = 0f, endY = 800f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("aris", "LOGIN OK")
                        Toast.makeText(context, "Inicio exitoso", Toast.LENGTH_SHORT).show();
                        navigateToHome()
                    } else {
                        Log.i("aris", "LOGIN NOO")
                        Toast.makeText(context, "Inicio fallido: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text(text = "Iniciar Sesion")
        }


        TextButton(
            onClick = { navigateToSignUp() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
        ) {
            Text(text = "Aun no tengo una cuenta")
        }


        Spacer(modifier = Modifier.weight(3f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(PrimaryPurple),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomMenuItem(icon = Icons.Filled.Home, label = "inicio", onClick = navigateToHome)
            BottomMenuItem(icon = Icons.Rounded.Search, label = "foro", onClick = navigateToForo)
            BottomMenuItem(icon = Icons.Outlined.Person, label = "Registrate", onClick = navigateToSignUp)

        }

        Spacer(modifier = Modifier.height(24.dp))

    }
}

sealed class LoginResult {
    data class Success(val auth: Auth) : LoginResult()
    object Failure : LoginResult()
}

data class Auth(val name: String, val alias: String, val email: String)

fun login(email: String, password: String): LoginResult {
    if (email == "testing@testing.com" && password == "123456789") {
        val auth = Auth("testing", "testing", "testing@testing.com")
        return LoginResult.Success(auth)
    } else {
        return LoginResult.Failure
    }
}

