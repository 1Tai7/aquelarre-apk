import firebase.app.aquelarre.presentation.post.MissingAuthorException
import firebase.app.aquelarre.presentation.post.MissingTextException
import firebase.app.aquelarre.presentation.post.MissingTitleException
import firebase.app.aquelarre.presentation.post.MissingUserIdException
import firebase.app.aquelarre.presentation.post.createPosts
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PostTest {

    @Test
    fun `crear post con todos los datos`() {
        val post = createPosts(
            userId = "user123",
            title = "Mi primer post",
            text = "Este es el contenido de mi post.",
            autor = "miAlias",
            tags = listOf("#kotlin", "#programación")
        )

        assertEquals("user123", post.userId)
        assertEquals("Mi primer post", post.title)
        assertEquals("Este es el contenido de mi post.", post.text)
        assertEquals("miAlias", post.autor)
        assertEquals(listOf("#kotlin", "#programación"), post.tags)
    }

    @Test
    fun `crear post sin userId lanza MissingUserIdException`() {
        assertThrows(MissingUserIdException::class.java) {
            createPosts(
                userId = null,
                title = "Mi primer post",
                text = "Este es el contenido de mi post.",
                autor = "miAlias",
                tags = listOf("#kotlin", "#programación")
            )
        }
    }

    @Test
    fun `crear post sin titulo lanza MissingTitleException`() {
        assertThrows(MissingTitleException::class.java) {
            createPosts(
                userId = "user123",
                title = null,
                text = "Este es el contenido de mi post.",
                autor = "miAlias",
                tags = listOf("#kotlin", "#programación")
            )
        }
    }

    @Test
    fun `crear post sin texto lanza MissingTextException`() {
        assertThrows(MissingTextException::class.java) {
            createPosts(
                userId = "user123",
                title = "Mi primer post",
                text = null,
                autor = "miAlias",
                tags = listOf("#kotlin", "#programación")
            )
        }
    }

    @Test
    fun `crear post sin autor lanza MissingAuthorException`() {
        assertThrows(MissingAuthorException::class.java) {
            createPosts(
                userId = "user123",
                title = "Mi primer post",
                text = "Este es el contenido de mi post.",
                autor = null,
                tags = listOf("#kotlin", "#programación")
            )
        }
    }
}