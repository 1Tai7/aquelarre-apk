package firebase.app.aquelarre.presentation.foro

import org.junit.Assert.*
import org.junit.Test


class ForoTest {

    @Test
    fun `getPosts devuelve una lista de posts`() {
        val posts = getPosts()

        assertEquals(3, posts.size) // Verifica que la lista tenga 3 posts

        // Verifica el contenido del primer post
        assertEquals("user123", posts[0].userId)
        assertEquals("Mi primer post", posts[0].title)
        assertEquals("Este es el contenido de mi primer post.", posts[0].text)
        assertEquals("miAlias", posts[0].autor)
        assertEquals(listOf("kotlin", "programación"), posts[0].tags)

        // Verifica el contenido del segundo post
        assertEquals("user456", posts[1].userId)
        assertEquals("Otro post interesante", posts[1].title)
        assertEquals("Contenido de un post diferente.", posts[1].text)
        assertEquals("otroAlias", posts[1].autor)
        assertEquals(listOf("java", "desarrollo"), posts[1].tags)

        // Verifica el contenido del tercer post
        assertEquals("user123", posts[2].userId)
        assertEquals("Un post del mismo usuario", posts[2].title)
        assertEquals("Contenido extra de miAlias", posts[2].text)
        assertEquals("miAlias", posts[2].autor)
        assertEquals(listOf("kotlin", "testing"), posts[2].tags)
    }

    @Test
    fun `getPosts devuelve una lista no vacia`(){
        val posts = getPosts()
        assertEquals(true, posts.isNotEmpty())
    }
}