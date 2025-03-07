package firebase.app.aquelarre.presentation.signup

import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.emoji2.emojipicker.EmojiPickerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import firebase.app.aquelarre.R
import firebase.app.aquelarre.entities.User
import firebase.app.aquelarre.presentation.initial.BottomMenuItem
import firebase.app.aquelarre.ui.theme.PrimaryPurple
import firebase.app.aquelarre.ui.theme.White
import java.security.MessageDigest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigateToLogin: () -> Unit,
    navigateToForo: () -> Unit,
    navigateToHome: () -> Unit,
    auth: FirebaseAuth,
    db: FirebaseFirestore
) {
    var showDialog by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var alias by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    val context = LocalContext.current
    val emojiPickerView = remember { EmojiPickerView(context) }
    var passwordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    emojiPickerView.setOnEmojiPickedListener { emoji ->
        imageUrl = emoji.emoji
    }

    var isButtonEnabled by remember { mutableStateOf(false) } // Estado para habilitar/deshabilitar el botón

    fun checkFields() {
        isButtonEnabled = email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && alias.isNotEmpty() && imageUrl.isNotEmpty()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Usuario Creado") },
            text = { Text("El usuario ha sido creado exitosamente.") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(White, PrimaryPurple), startY = 0f, endY = 800f))
            .padding(16.dp).verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(150.dp)
        )
        Text(
            "Iniciar sesión",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        EmojiTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = "Emoji",
            emojis = listOf("😸", "😋", "👩", "🙆‍♀️", "💃", "👧" , "👩", "🙆‍♀️", "💃", "👧"),
            onValueChanged = { checkFields() }
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it; checkFields() },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.White
            )

        )

        OutlinedTextField(
            value = alias,
            onValueChange = { alias = it; checkFields() },
            label = { Text("Alias") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.White
            )
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; checkFields() },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.White
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; checkFields() },
            label = { Text("Password", color = Color.White) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.White
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña", tint = Color.Black)
                }
            }
        )

        Button(onClick = {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("aris", "REGISTER OK")
                        Toast.makeText(context, "Inicio exitoso", Toast.LENGTH_SHORT).show();

                        createUser(db, name, alias, email, password, imageUrl, navigateToHome)

                    } else {
                        Log.i("aris", "Resgistro NOO")
                        Toast.makeText(context, "Registro fallido: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), enabled = isButtonEnabled) {
            Text(text = "Registrate")
        }

        TextButton(
            onClick = { navigateToLogin() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
        ) {
            Text(text = "Ya tengo una cuenta")
        }

        Spacer(modifier = Modifier.weight(1f))

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
            BottomMenuItem(icon = Icons.Outlined.Person, label = "login", onClick = navigateToLogin)
        }
    }
}

fun createUser(db: FirebaseFirestore, name: String, alias: String, email: String, password:String, imageUrl: String, navigateToHome: () -> Unit){
    val user = User(name, alias, email, password, imageUrl)
    db.collection("user").add(user)
        .addOnSuccessListener {
            navigateToHome()
            Log.i("GGG", "CREADO EXITOSO")

        }
        .addOnFailureListener { Log.i("GGG", "CREADO FALLO") }
        .addOnCompleteListener{ Log.i("GGG", "COMPLETADO") }
}

@Composable
fun EmojiTextField(
    value: String,
    onValueChange: (String) -> Unit = {},
    label: String,
    emojis: List<String>,
    onValueChanged: () -> Unit = {}
) {
    var showEmojiPicker by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(value);  onValueChanged() },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable{ showEmojiPicker = !showEmojiPicker },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.White
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Emojis",
                    modifier = Modifier.clickable { showEmojiPicker = !showEmojiPicker }
                )
            },
            readOnly = true // Desactivar la edición directa
        )

        if (showEmojiPicker) {
            Popup(
                onDismissRequest = { showEmojiPicker = false },
                alignment = Alignment.TopEnd
            ) {
                Surface(color = Color.White) { // Agregar fondo blanco
                    CustomEmojiPicker(
                        emojis = emojis,
                        onEmojiClick = { emoji ->
                            onValueChange(emoji) // Agregar solo emojis
                            onValueChanged()
                            showEmojiPicker = false
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun CustomEmojiPicker(
    emojis: List<String>,
    onEmojiClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 40.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(emojis.size) { index ->
            Text(
                text = emojis[index],
                fontSize = 24.sp,
                modifier = Modifier
                    .clickable { onEmojiClick(emojis[index]) }
                    .padding(8.dp)
            )
        }
    }
}

class MissingNameException : IllegalArgumentException("El nombre es obligatorio.")
class MissingAliasException : IllegalArgumentException("El alias es obligatorio.")
class MissingEmailException : IllegalArgumentException("El email es obligatorio.")
class MissingPasswordException : IllegalArgumentException("La contraseña es obligatoria.")

fun createUsers(
    name: String?,
    alias: String?,
    email: String?,
    password: String?,
    imageUrl: String? = null
): User {
    if (name.isNullOrBlank()) {
        throw MissingNameException()
    }
    if (alias.isNullOrBlank()) {
        throw MissingAliasException()
    }
    if (email.isNullOrBlank()) {
        throw MissingEmailException()
    }
    if (password.isNullOrBlank()) {
        throw MissingPasswordException()
    }

    return User(name, alias, email, password, imageUrl.toString())
}


