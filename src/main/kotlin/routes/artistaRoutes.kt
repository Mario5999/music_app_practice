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

fun Route.artistaRoutes() {
    route("/artistas") {

        // CREATE
        post {
            val request = call.receive<ArtistaRequest>()

            val artista = dbQuery {
                Artistas.insert {
                    it[name] = request.name
                    it[genre] = request.genre
                    it[createdAt] = Instant.now()
                    it[updatedAt] = Instant.now()
                }.resultedValues?.first()?.toArtista()
            }

            call.respond(HttpStatusCode.Created, artista!!)
        }

        // READ ALL
        get {
            val artistas = dbQuery {
                Artistas.selectAll().map { it.toArtista() }
            }
            call.respond(artistas)
        }

        // READ ONE
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )

            val artista = dbQuery {
                Artistas.select { Artistas.id eq UUID.fromString(id) }
                    .mapNotNull { it.toArtista() }
                    .singleOrNull()
            }

            if (artista == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Artista no encontrado"))
            } else {
                call.respond(artista)
            }
        }

        // UPDATE
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "ID inválido")
            )
            val request = call.receive<ArtistaRequest>()

            val updated = dbQuery {
                val count = Artistas.update({ Artistas.id eq UUID.fromString(id) }) {
                    it[name] = request.name
                    it[genre] = request.genre
                    it[updatedAt] = Instant.now()
                }

                if (count > 0) {
                    Artistas.select { Artistas.id eq UUID.fromString(id) }
                        .map { it.toArtista() }
                        .singleOrNull()
                } else null
            }

            if (updated == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Artista no encontrado"))
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
                val artista = Artistas.select { Artistas.id eq UUID.fromString(id) }
                    .map { it.toArtista() }
                    .singleOrNull()

                if (artista != null) {
                    Artistas.deleteWhere { Artistas.id eq UUID.fromString(id) }
                    artista
                } else null
            }

            if (deleted == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Artista no encontrado"))
            } else {
                call.respond(mapOf("mensaje" to "Artista eliminado", "artista" to deleted))
            }
        }
    }
}