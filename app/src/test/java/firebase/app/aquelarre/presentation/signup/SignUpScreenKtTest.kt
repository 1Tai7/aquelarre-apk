package firebase.app.aquelarre.presentation.signup

import org.junit.Assert.*
import org.junit.Test


class CreateUserTest {

    @Test
    fun `crear usuario con todos los datos`() {
        val user = createUsers(
            "John Doe",
            "johndoe",
            "john.doe@example.com",
            "password123",
            "😸"
        )
        assertEquals("John Doe", user.name)
        assertEquals("johndoe", user.alias)
        assertEquals("john.doe@example.com", user.email)
        assertNotNull(user.password) // Verifica que el hash no sea nulo
        assertEquals("😸", user.imageUrl)
    }

    @Test
    fun `crear usuario sin nombre lanza MissingNameException`() {
        assertThrows(MissingNameException::class.java) {
            createUsers(
                name = null,
                alias = "johndoe",
                email = "john.doe@example.com",
                password = "password123",
                imageUrl = "https://example.com/image.jpg"
            )
        }
    }

    @Test
    fun `crear usuario sin alias lanza MissingAliasException`() {
        assertThrows(MissingAliasException::class.java) {
            createUsers(
                name = "John Doe",
                alias = null,
                email = "john.doe@example.com",
                password = "password123",
                imageUrl = "https://example.com/image.jpg"
            )
        }
    }

    @Test
    fun `crear usuario sin email lanza MissingEmailException`() {
        assertThrows(MissingEmailException::class.java) {
            createUsers(
                name = "John Doe",
                alias = "johndoe",
                email = null,
                password = "password123",
                imageUrl = "https://example.com/image.jpg"
            )
        }
    }

    @Test
    fun `crear usuario sin contraseña lanza MissingPasswordException`() {
        assertThrows(MissingPasswordException::class.java) {
            createUsers(
                name = "John Doe",
                alias = "johndoe",
                email = "john.doe@example.com",
                password = null,
                imageUrl = "https://example.com/image.jpg"
            )
        }
    }




}