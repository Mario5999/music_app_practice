package backend.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object Artistas : UUIDTable("artistas") {
    val name = varchar("name", 100)
    val genre = varchar("genre", 50).nullable()
    val createdAt = timestamp("created_at").default(Instant.now())
    val updatedAt = timestamp("updated_at").default(Instant.now())
}

@Serializable
data class Artista(
    val id: String,
    val name: String,
    val genre: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class ArtistaRequest(
    val name: String,
    val genre: String? = null
)

fun ResultRow.toArtista() = Artista(
    id = this[Artistas.id].toString(),
    name = this[Artistas.name],
    genre = this[Artistas.genre],
    createdAt = this[Artistas.createdAt].toString(),
    updatedAt = this[Artistas.updatedAt].toString()
)