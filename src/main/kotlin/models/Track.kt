package backend.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object Tracks : UUIDTable("tracks") {
    val title = varchar("title", 150)
    val durationSeconds = integer("duration_seconds")
    val albumId = uuid("album_id").references(Albumes.id, onDelete = ReferenceOption.CASCADE)
    val createdAt = timestamp("created_at").default(Instant.now())
    val updatedAt = timestamp("updated_at").default(Instant.now())
}

@Serializable
data class Track(
    val id: String,
    val title: String,
    val durationSeconds: Int,
    val albumId: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class TrackRequest(
    val title: String,
    val durationSeconds: Int,
    val albumId: String
)

fun ResultRow.toTrack() = Track(
    id = this[Tracks.id].toString(),
    title = this[Tracks.title],
    durationSeconds = this[Tracks.durationSeconds],
    albumId = this[Tracks.albumId].toString(),
    createdAt = this[Tracks.createdAt].toString(),
    updatedAt = this[Tracks.updatedAt].toString()
)