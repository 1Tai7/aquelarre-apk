package firebase.app.aquelarre.entities

data class Post(
    val userId: String,
    val title: String,
    val text: String,
    val autor: String,
    val tags: List<String>
)