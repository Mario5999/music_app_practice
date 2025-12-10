package backend.routes

import backend.database.DatabaseFactory.dbQuery
import backend.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq  // ⬅️ AGREGAR ESTA LÍNEA
import java.time.Instant
import java.util.*
fun Route.albumRoutes() {
    route("/albumes") {

        // CREATE
        post {
            val request = call.receive<AlbumRequest>()

            val album = dbQuery {
                Albumes.insert {
                    it[title] = request.title
                    it[releaseYear] = request.releaseYear
                    it[artistId] = UUID.fromString(request.artistId)
                    it[createdAt] = Instant.now()
                    it[updatedAt] = Instant.now()
                }.resultedValues?.first()?.toAlbum()
            }

            call.respond(HttpStatusCode.Created, album!!)
        }

        // READ ALL
        get {
            val albumes = dbQuery {
                Albumes.selectAll().map { it.toAlbum() }
            }
            call.respond(albumes)
        }

        // READ ONE
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )

            val album = dbQuery {
                Albumes.select { Albumes.id eq UUID.fromString(id) }
                    .mapNotNull { it.toAlbum() }
                    .singleOrNull()
            }

            if (album == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Álbum no encontrado"))
            } else {
                call.respond(album)
            }
        }

        // UPDATE
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )
            val request = call.receive<AlbumRequest>()

            val updated = dbQuery {
                val count = Albumes.update({ Albumes.id eq UUID.fromString(id) }) {
                    it[title] = request.title
                    it[releaseYear] = request.releaseYear
                    it[artistId] = UUID.fromString(request.artistId)
                    it[updatedAt] = Instant.now()
                }

                if (count > 0) {
                    Albumes.select { Albumes.id eq UUID.fromString(id) }
                        .map { it.toAlbum() }
                        .singleOrNull()
                } else null
            }

            if (updated == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Álbum no encontrado"))
            } else {
                call.respond(updated)
            }
        }

        // DELETE
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )

            val deleted = dbQuery {
                val album = Albumes.select { Albumes.id eq UUID.fromString(id) }
                    .map { it.toAlbum() }
                    .singleOrNull()

                if (album != null) {
                    Albumes.deleteWhere { Albumes.id eq UUID.fromString(id) }
                    album
                } else null
            }

            if (deleted == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Álbum no encontrado"))
            } else {
                call.respond(mapOf("mensaje" to "Álbum eliminado", "album" to deleted))
            }
        }
    }
}