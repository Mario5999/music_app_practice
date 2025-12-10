package backend.plugins

import backend.database.DatabaseFactory
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseFactory.init()
}