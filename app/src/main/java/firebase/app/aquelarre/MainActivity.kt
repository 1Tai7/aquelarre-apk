package firebase.app.aquelarre

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import firebase.app.aquelarre.ui.theme.AquelarreTheme

class MainActivity : ComponentActivity() {
    private lateinit var navHostController : NavHostController
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = Firebase.firestore
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()

            AquelarreTheme {
               Surface(modifier = Modifier.fillMaxSize(),
                 color = MaterialTheme.colorScheme.background
                   ) {
                   NavigationWrapper(navHostController, auth, db)
               }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
Log.i("GGG", "LOGUEADOO")

            // auth.signOut()
        }
    }
}

