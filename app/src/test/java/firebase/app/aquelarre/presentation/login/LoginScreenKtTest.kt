import firebase.app.aquelarre.presentation.login.Auth
import firebase.app.aquelarre.presentation.login.LoginResult
import firebase.app.aquelarre.presentation.login.login
import org.junit.Assert
import org.junit.Test

class LoginTest {

    @Test
    fun `login con credenciales correctas devuelve Success`() {
        val resultado = login("testing@testing.com", "123456789")
        val esperado = LoginResult.Success(Auth("testing", "testing", "testing@testing.com"))
        Assert.assertEquals(esperado, resultado)
    }

    @Test
    fun `login con credenciales incorrectas devuelve Failure`() {
        val resultado = login("usuario@incorrecto.com", "contrasenaInvalida")
        Assert.assertEquals(LoginResult.Failure, resultado)
    }

    @Test
    fun `login con email correcto y contraseña incorrecta devuelve Failure`() {
        val resultado = login("testing@testing.com", "contrasenaInvalida")
        Assert.assertEquals(LoginResult.Failure, resultado)
    }

    @Test
    fun `login con email incorrecto y contraseña correcta devuelve Failure`() {
        val resultado = login("usuario@incorrecto.com", "123456789")
        Assert.assertEquals(LoginResult.Failure, resultado)
    }
}