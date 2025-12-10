package backend.routes

import backend.database.DatabaseFactory.dbQuery
import backend.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.Instant
import java.util.*

fun Route.trackRoutes() {
    route("/tracks") {

        // CREATE
        post {
            val request = call.receive<TrackRequest>()

            val track = dbQuery {
                Tracks.insert {
                    it[title] = request.title
                    it[durationSeconds] = request.durationSeconds
                    it[albumId] = UUID.fromString(request.albumId)
                    it[createdAt] = Instant.now()
                    it[updatedAt] = Instant.now()
                }.resultedValues?.first()?.toTrack()
            }

            if (track == null)
                call.respond(HttpStatusCode.InternalServerError, "Error creando track")
            else
                call.respond(HttpStatusCode.Created, track)
        }

        // READ ALL
        get {
            val tracks = dbQuery {
                Tracks.selectAll().map { it.toTrack() }
            }
            call.respond(tracks)
        }

        // READ ONE
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )

            val track = dbQuery {
                Tracks.select { Tracks.id eq UUID.fromString(id) }
                    .map { it.toTrack() }
                    .singleOrNull()
            }

            if (track == null)
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Track no encontrado"))
            else
                call.respond(track)
        }

        // UPDATE
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )
            val request = call.receive<TrackRequest>()

            val updated = dbQuery {
                val count = Tracks.update({ Tracks.id eq UUID.fromString(id) }) {
                    it[title] = request.title
                    it[durationSeconds] = request.durationSeconds
                    it[albumId] = UUID.fromString(request.albumId)
                    it[updatedAt] = Instant.now()
                }

                if (count > 0) {
                    Tracks.select { Tracks.id eq UUID.fromString(id) }
                        .map { it.toTrack() }
                        .singleOrNull()
                } else null
            }

            if (updated == null)
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Track no encontrado"))
            else
                call.respond(updated)
        }

        // DELETE
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )

            val deleted = dbQuery {
                val track = Tracks.select { Tracks.id eq UUID.fromString(id) }
                    .map { it.toTrack() }
                    .singleOrNull()

                if (track != null) {
                    Tracks.deleteWhere { Tracks.id eq UUID.fromString(id) }
                    track
                } else null
            }

            if (deleted == null)
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Track no encontrado"))
            else
                call.respond(mapOf("mensaje" to "Track eliminado", "track" to deleted))
        }
    }
}
