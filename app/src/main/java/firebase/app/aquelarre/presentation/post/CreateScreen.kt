package firebase.app.aquelarre.presentation.post

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import firebase.app.aquelarre.entities.Post
import firebase.app.aquelarre.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(db: FirebaseFirestore, goBack: () -> Unit = {}, auth: FirebaseAuth) {
    val title = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }
    val tags = remember { mutableStateOf("") }
    val context = LocalContext.current
    val currentUser = auth.currentUser
    val alias = remember { mutableStateOf("") } // Estado para almacenar el alias

    // Obtener el alias del usuario
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            db.collection("user")
                .whereEqualTo("email", user.email)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val document = documents.documents[0]
                        alias.value = document.getString("alias") ?: ""
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("CreateScreen", "Error getting user alias: ${exception.message}", exception)
                }
        }
    }

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear Post",
                    fontSize = 22.sp,
                    color = Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    label = { Text("Texto") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = tags.value,
                    onValueChange = { tags.value = it },
                    label = { Text("Tags (separadas por comas)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {
                    val tagList = tags.value.split(",").map { it.trim() }
                    val userId = currentUser?.uid
                    if (userId != null) {
                        createPost(db, userId, title.value, text.value, tagList, alias.value) { // Usar el alias
                            goBack()
                            Toast.makeText(context, "Post Creado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text(text = "¡Crear!")
                }

                Button(onClick = { goBack() }) {
                    Text(text = "Cerrar")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

fun createPost(
    db: FirebaseFirestore,
    userId: String,
    title: String,
    text: String,
    tags: List<String>,
    autor: String, // Ahora el autor es el alias
    onSuccess: () -> Unit
) {
    val post = Post(
        userId,
        title,
        text,
        autor,
        tags = tags
    )
    db.collection("posts").add(post)
        .addOnSuccessListener {
            Log.i("CreatePost", "Post creado exitosamente")
            onSuccess()
        }
        .addOnFailureListener { exception ->
            Log.e("CreatePost", "Error al crear el post: ${exception.message}", exception)
        }
}