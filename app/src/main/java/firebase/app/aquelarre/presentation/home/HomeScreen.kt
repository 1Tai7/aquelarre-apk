package firebase.app.aquelarre.presentation.home



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import firebase.app.aquelarre.presentation.initial.BottomMenuItem
import firebase.app.aquelarre.ui.theme.PrimaryPurple
import firebase.app.aquelarre.ui.theme.White


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.google.firebase.auth.FirebaseAuth
import firebase.app.aquelarre.R

@Composable
fun HomeScreen(
    navigateToLogin: () -> Unit = {},
    navigateToForo: () -> Unit = {} ,
    navigateToHome: () -> Unit = {},
    navigateToPost: () -> Unit = {},
    navigateToPerfil: () -> Unit = {},
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
                BottomMenuItem(icon = Icons.Outlined.Add, label = "post", onClick =  navigateToPost)
                BottomMenuItem(icon = Icons.Outlined.Person, label = "perfil", onClick = navigateToPerfil)
            } else {
                BottomMenuItem(icon = Icons.Outlined.Person, label = "login", onClick = navigateToLogin)
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

    }
}

