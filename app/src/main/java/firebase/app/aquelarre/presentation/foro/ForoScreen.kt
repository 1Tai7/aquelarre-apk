package firebase.app.aquelarre.presentation.foro

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.firestore.FirebaseFirestore
import firebase.app.aquelarre.entities.Post
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import firebase.app.aquelarre.presentation.initial.BottomMenuItem
import firebase.app.aquelarre.ui.theme.PrimaryPurple

@Composable
fun ForoScreen(
    db: FirebaseFirestore,
    navigateToLogin: () -> Unit = {},
    navigateToForo: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    navigateToPost: () -> Unit = {},
    navigateToPerfil: () -> Unit = {},
    auth: FirebaseAuth
) {
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedPost by remember { mutableStateOf<Post?>(null) }

    LaunchedEffect(Unit) {
        db.collection("posts").get()
            .addOnSuccessListener { querySnapshot ->
                posts = querySnapshot.documents.map { document ->
                    Post(
                        userId = document.getString("userId") ?: "",
                        title = document.getString("title") ?: "",
                        text = document.getString("text") ?: "",
                        autor = document.getString("autor") ?: "",
                        tags = document.get("tags") as? List<String> ?: emptyList()
                    )
                }
            }.addOnFailureListener { exception ->
                Log.e("ForoScreen", "Error al obtener posts: ${exception.message}", exception)
            }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(PrimaryPurple),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomMenuItem(icon = Icons.Filled.Home, label = "inicio", onClick = navigateToHome)
                BottomMenuItem(icon = Icons.Rounded.Search, label = "foro", onClick = navigateToForo)
                if (auth.currentUser != null) {
                    BottomMenuItem(icon = Icons.Outlined.Add, label = "post", onClick = navigateToPost)
                    BottomMenuItem(icon = Icons.Outlined.Person, label = "perfil", onClick = navigateToPerfil)
                } else {
                    BottomMenuItem(icon = Icons.Outlined.Person, label = "login", onClick = navigateToLogin)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            posts.forEach { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { /* Navegar a la pantalla de detalle del post */ },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = post.title, style = MaterialTheme.typography.titleLarge)
                        Text(text = "@${post.autor}", style = MaterialTheme.typography.labelMedium)
                        Text(text = post.text, style = MaterialTheme.typography.labelSmall)

                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            post.tags.forEach { tag ->
                                AssistChip(
                                    onClick = { /* Acción al hacer clic en la tag */ },
                                    label = { Text(tag) }
                                )
                            }
                        }
                        Button(
                            onClick = {  selectedPost = post // guarda el post seleccionado
                                showDialog = true },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Ver Post")
                        }
                    }
                }
            }
        }
    }

    if (showDialog && selectedPost != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(selectedPost!!.title) },
            text = {
                Column {
                    Text(text = "Autor: @${selectedPost!!.autor}")
                    Text(text = selectedPost!!.text)
                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

}

fun getPosts(): List<Post> {


    val post1 = Post(
        userId = "user123",
        title = "Mi primer post",
        text = "Este es el contenido de mi primer post.",
        autor = "miAlias",
        tags = listOf("kotlin", "programación")
    )

    val post2 = Post(
        userId = "user456",
        title = "Otro post interesante",
        text = "Contenido de un post diferente.",
        autor = "otroAlias",
        tags = listOf("java", "desarrollo")
    )

    val post3 = Post(
        userId = "user123",
        title = "Un post del mismo usuario",
        text = "Contenido extra de miAlias",
        autor = "miAlias",
        tags = listOf("kotlin", "testing")
    )

    return listOf(post1, post2, post3)
}