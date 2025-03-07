package firebase.app.aquelarre.presentation.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import firebase.app.aquelarre.entities.Post
import firebase.app.aquelarre.ui.theme.Black

@Composable
fun PostScreen(db: FirebaseFirestore){


        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .height(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ACTUALIZA",
                        fontSize = 22.sp,
                        color = Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Para poder disfrutar de todo nuestro contenido actualice la app",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {}) {
                        Text(text = "¡Actualizar!")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }

}

class MissingUserIdException : IllegalArgumentException("El userId es obligatorio.")
class MissingTitleException : IllegalArgumentException("El título es obligatorio.")
class MissingTextException : IllegalArgumentException("El texto es obligatorio.")
class MissingAuthorException : IllegalArgumentException("El autor es obligatorio.")

fun createPosts(
    userId: String?,
    title: String?,
    text: String?,
    autor: String?,
    tags: List<String> = emptyList()
): Post {
    if (userId.isNullOrBlank()) {
        throw MissingUserIdException()
    }
    if (title.isNullOrBlank()) {
        throw MissingTitleException()
    }
    if (text.isNullOrBlank()) {
        throw MissingTextException()
    }
    if (autor.isNullOrBlank()) {
        throw MissingAuthorException()
    }

    return Post(userId, title, text, autor, tags)
}