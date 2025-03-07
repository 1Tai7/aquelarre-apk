

package firebase.app.aquelarre

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import firebase.app.aquelarre.presentation.foro.ForoScreen
import firebase.app.aquelarre.presentation.home.HomeScreen
import firebase.app.aquelarre.presentation.initial.InitialScreen
import firebase.app.aquelarre.presentation.login.LoginScreen
import firebase.app.aquelarre.presentation.perfil.PerfilScreen
import firebase.app.aquelarre.presentation.post.CreateScreen
import firebase.app.aquelarre.presentation.post.PostScreen
import firebase.app.aquelarre.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    db: FirebaseFirestore
) {

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("login") },
                navigateToForo = { navHostController.navigate("foro") },
                navigateToHome = { navHostController.navigate("home") },
                navigateToPost = { navHostController.navigate("createpost") },
                navigateToPerfil = { navHostController.navigate("perfil") },
                auth
            )

        }
        composable("login") {
            LoginScreen(
                navigateToSignUp = { navHostController.navigate("signup") },
                navigateToForo = { navHostController.navigate("foro") },
                navigateToHome = { navHostController.navigate("home") },
                auth = auth
            )
        }
        composable("signup") {
            SignUpScreen(
                navigateToLogin = { navHostController.navigate("login") },
                navigateToForo = { navHostController.navigate("foro") },
                navigateToHome = { navHostController.navigate("home") },
                auth,
                db
            )
        }

        composable("home"){
            HomeScreen(
                navigateToLogin = { navHostController.navigate("login") },
                navigateToPost = { navHostController.navigate("createpost") },
                navigateToForo = { navHostController.navigate("foro") },
                navigateToHome = { navHostController.navigate("home") },
                navigateToPerfil = { navHostController.navigate("perfil") },
                auth = auth
            )
        }

        composable("perfil"){
            PerfilScreen(
                navigateToForo = { navHostController.navigate("foro") },
                navigateToHome = { navHostController.navigate("home") },
                navigateToPost = { navHostController.navigate("createpost") },
                auth, db)
        }
        composable("foro"){

            ForoScreen(db,  navigateToLogin = { navHostController.navigate("login") },
                navigateToPost = { navHostController.navigate("createpost") },
                navigateToForo = { navHostController.navigate("foro") },
                navigateToHome = { navHostController.navigate("home") },
                navigateToPerfil = { navHostController.navigate("perfil") },
                auth = auth)
        }

        composable("createpost") {
            CreateScreen(db, goBack = { navHostController.popBackStack()}, auth)
        }


    }
}