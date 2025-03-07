package firebase.app.aquelarre.presentation.perfil

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.window.Popup
import androidx.emoji2.emojipicker.EmojiPickerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import firebase.app.aquelarre.R
import firebase.app.aquelarre.presentation.initial.BottomMenuItem
import firebase.app.aquelarre.ui.theme.PrimaryPurple
import firebase.app.aquelarre.ui.theme.White
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navigateToForo: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToPost: () -> Unit,
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
    val currentUser = auth.currentUser
    var passwordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            email = user.email ?: ""
            // Obtener la información completa del usuario desde Firestore
            db.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val document = documents.documents[0]
                        name = document.getString("name") ?: ""
                        alias = document.getString("alias") ?: ""
                        imageUrl = document.getString("imageUrl") ?: "😸"
                        password = document.getString("password") ?: ""
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("PerfilScreen", "Error getting user data: ${exception.message}", exception)
                }
        }
    }



    emojiPickerView.setOnEmojiPickedListener { emoji ->
        imageUrl = emoji.emoji
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
            emojis = listOf("😸", "😋", "👩", "🙆‍♀️", "💃", "👧" , "👩", "🙆‍♀️", "💃", "👧")
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre", color = Color.White) },
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
            onValueChange = { alias = it },
            label = { Text("Alias", color = Color.White) },
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
            onValueChange = { email = it },
            label = { Text("Email", color = Color.White) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.White
            ), readOnly = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
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
            updateUser(db, name, alias, email, password, imageUrl, navigateToHome)
            val user = auth.currentUser
            user?.updatePassword(password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("UpdatePassword", "Contraseña actualizada con éxito")
                        Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("UpdatePassword", "Error al actualizar la contraseña", task.exception)
                        Toast.makeText(context, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show()
                    }
                }

        }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text(text = "Actualizar")
        }

        TextButton(
            onClick = { auth.signOut(); navigateToHome() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
        ) {
            Text(text = "Cerrar sesion")
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
            BottomMenuItem(icon = Icons.Outlined.Add, label = "post", onClick = navigateToPost)

            BottomMenuItem(icon = Icons.Outlined.ArrowBack, label = "volver", onClick = navigateToHome)
        }
    }
}

fun updateUser(
    db: FirebaseFirestore,
    name: String,
    alias: String,
    email: String,
    password: String,
    imageUrl: String,
    navigateToHome: () -> Unit
) {
    db.collection("user")
        .whereEqualTo("email", email)
        .get()
        .addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                val document = documents.documents[0] // Obtiene el primer documento encontrado
                val documentId = document.id // Obtiene el ID del documento

                // Actualiza los campos del documento
                db.collection("user").document(documentId)
                    .update(
                        "name", name,
                        "alias", alias,
                        "imageUrl", imageUrl,
                        "password",password
                    )
                    .addOnSuccessListener {
                        Log.i("GGG", "ACTUALIZADO EXITOSO")
                        navigateToHome()
                    }
                    .addOnFailureListener { exception ->
                        Log.e("GGG", "ACTUALIZACIÓN FALLÓ: ${exception.message}", exception)
                    }
                    .addOnCompleteListener {
                        Log.i("GGG", "COMPLETADO")
                    }
            } else {
                Log.w("GGG", "Usuario con email $email no encontrado.")
            }
        }
        .addOnFailureListener { exception ->
            Log.e("GGG", "Búsqueda de usuario falló: ${exception.message}", exception)
        }
}

@Composable
fun EmojiTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,

    emojis: List<String>
) {
    var showEmojiPicker by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = value,
            onValueChange = {onValueChange(value)}, // No permitir la escritura directa
            label = { Text(label,  color = Color.White) },
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


