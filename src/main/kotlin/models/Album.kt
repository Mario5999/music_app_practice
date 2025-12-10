package backend.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object Albumes : UUIDTable("albumes") {
    val title = varchar("title", 150)
    val releaseYear = integer("release_year")
    val artistId = uuid("artist_id").references(Artistas.id, onDelete = ReferenceOption.CASCADE)
    val createdAt = timestamp("created_at").default(Instant.now())
    val updatedAt = timestamp("updated_at").default(Instant.now())
}

@Serializable
data class Album(
    val id: String,
    val title: String,
    val releaseYear: Int,
    val artistId: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class AlbumRequest(
    val title: String,
    val releaseYear: Int,
    val artistId: String
)

fun ResultRow.toAlbum() = Album(
    id = this[Albumes.id].toString(),
    title = this[Albumes.title],
    releaseYear = this[Albumes.releaseYear],
    artistId = this[Albumes.artistId].toString(),
    createdAt = this[Albumes.createdAt].toString(),
    updatedAt = this[Albumes.updatedAt].toString()
)