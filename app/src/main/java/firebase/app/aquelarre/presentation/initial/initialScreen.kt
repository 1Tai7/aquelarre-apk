package firebase.app.aquelarre.presentation.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import firebase.app.aquelarre.R
import firebase.app.aquelarre.ui.theme.PrimaryPurple
import firebase.app.aquelarre.ui.theme.White


@Preview
@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit = {},
    navigateToForo: () -> Unit = {} ,
    navigateToHome: () -> Unit = {},
    navigateToPost: () -> Unit = {},
    navigateToPerfil: () -> Unit ={},
    auth: FirebaseAuth
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(White, PrimaryPurple),
                    startY = 0f,
                    endY = 800f
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(200.dp)

        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Espacio",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Seguro", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold
        )
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
            if(auth.currentUser != null){
                BottomMenuItem(icon = Icons.Outlined.Add, label = "post", onClick = navigateToPost)
                BottomMenuItem(icon = Icons.Outlined.Person, label = "perfil", onClick = navigateToPerfil)
            } else {
                BottomMenuItem(icon = Icons.Outlined.Person, label = "login", onClick = navigateToLogin)
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Composable
fun BottomMenuItem(icon: ImageVector, label: String, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White
        )
        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}