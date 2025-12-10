// EJEMPLOS PRÁCTICOS DE USO DEL CRUD

// ============================================
// EJEMPLO 1: Usar el CrudService en un componente
// ============================================

import { Component, OnInit } from '@angular/core';
import { CrudService } from '../../services/crud.service';
import { LocalPlaylist, LocalArtist, LocalAlbum } from '../../models/track.model';

@Component({
  selector: 'app-example',
  template: `
    <div>
      <h1>Mi Ejemplo CRUD</h1>
      
      <!-- Mostrar playlists -->
      <div>
        <h2>Playlists ({{ myPlaylists.length }})</h2>
        <button (click)="crearPlaylist()">Crear Playlist</button>
        <ul>
          <li *ngFor="let p of myPlaylists">
            {{ p.name }} - {{ p.tracks.length }} canciones
            <button (click)="eliminarPlaylist(p.id)">Eliminar</button>
          </li>
        </ul>
      </div>
    </div>
  `
})
export class ExampleComponent implements OnInit {
  myPlaylists: LocalPlaylist[] = [];
  myArtists: LocalArtist[] = [];
  myAlbums: LocalAlbum[] = [];

  constructor(private crudService: CrudService) {}

  ngOnInit(): void {
    // Suscribirse a cambios en playlists
    this.crudService.playlists$.subscribe(playlists => {
      this.myPlaylists = playlists;
      console.log('Playlists actualizadas:', playlists);
    });

    // Suscribirse a cambios en artistas
    this.crudService.artists$.subscribe(artists => {
      this.myArtists = artists;
    });

    // Suscribirse a cambios en álbumes
    this.crudService.albums$.subscribe(albums => {
      this.myAlbums = albums;
    });
  }

  // =========== PLAYLISTS ===========

  crearPlaylist(): void {
    const nuevaPlaylist = this.crudService.createPlaylist(
      'Mi Nueva Playlist',
      'Esta es una descripción'
    );
    console.log('Playlist creada:', nuevaPlaylist);
  }

  obtenerPlaylist(id: string): void {
    const playlist = this.crudService.getPlaylistById(id);
    if (playlist) {
      console.log('Playlist encontrada:', playlist);
    }
  }

  actualizarPlaylist(id: string): void {
    const actualizado = this.crudService.updatePlaylist(
      id,
      'Nuevo Nombre',
      'Nueva descripción'
    );
    if (actualizado) {
      console.log('Playlist actualizada');
    }
  }

  eliminarPlaylist(id: string): void {
    const eliminado = this.crudService.deletePlaylist(id);
    if (eliminado) {
      console.log('Playlist eliminada');
    }
  }

  // =========== ARTISTAS ===========

  crearArtista(): void {
    const nuevoArtista = this.crudService.createArtist(
      'The Beatles',
      'Banda legendaria de rock',
      'https://example.com/beatles.jpg'
    );
    console.log('Artista creado:', nuevoArtista);
  }

  actualizarArtista(id: string): void {
    const actualizado = this.crudService.updateArtist(
      id,
      'Nuevo Nombre del Artista',
      'Nueva biografía',
      'https://example.com/new-image.jpg'
    );
    if (actualizado) {
      console.log('Artista actualizado');
    }
  }

  // =========== ÁLBUMES ===========

  crearAlbum(): void {
    // Primero obtén artistas
    const artistas = this.crudService.getArtists();
    
    if (artistas.length > 0) {
      const nuevoAlbum = this.crudService.createAlbum(
        'Abbey Road',
        [artistas[0]], // Asignar primer artista
        '1969-09-26',
        'https://example.com/abbey-road.jpg'
      );
      console.log('Álbum creado:', nuevoAlbum);
    }
  }

  // =========== LIMPIEZA ===========

  limpiarTodo(): void {
    if (confirm('¿Eliminar todos los datos?')) {
      this.crudService.clearAll();
      console.log('Todos los datos han sido eliminados');
    }
  }
}


// ============================================
// EJEMPLO 2: Casos de uso comunes
// ============================================

export class UsoCrudService {
  constructor(private crudService: CrudService) {}

  // ❌ INCORRECTO: Acceder directamente sin suscripción
  mostrarPlaylistsIncorrecto(): void {
    const playlists = this.crudService.getPlaylists();
    console.log(playlists); // Funciona, pero no se actualiza cuando cambia
  }

  // ✅ CORRECTO: Usar observables para reactividad
  mostrarPlaylistsCorrecto(): void {
    this.crudService.playlists$.subscribe(playlists => {
      console.log('Playlists actualizadas:', playlists);
      // Este código se ejecuta cada vez que cambian las playlists
    });
  }

  // Crear playlis con canciones
  crearPlaylistConCanciones(): void {
    const playlist = this.crudService.createPlaylist(
      'Mis Favoritas',
      'Mis canciones favoritas'
    );
    
    // Luego agregar canciones (si tienes el Track)
    // playlist.tracks.push(track1, track2, track3);
    
    console.log('Playlist con canciones creada:', playlist);
  }

  // Crear artista completo
  crearArtistaCompleto(): void {
    const artista = this.crudService.createArtist(
      'Queen',
      'Banda de rock británica formada en 1970',
      'https://example.com/queen.jpg'
    );
    
    console.log('Artista completo:', artista);
  }

  // Crear álbum con múltiples artistas
  crearAlbumMultiArtista(): void {
    // Crear artistas primero
    const artista1 = this.crudService.createArtist('Artist 1');
    const artista2 = this.crudService.createArtist('Artist 2');

    // Crear álbum con ambos
    const album = this.crudService.createAlbum(
      'Álbum Colaborativo',
      [artista1, artista2],
      '2024-01-01',
      'https://example.com/album.jpg'
    );

    console.log('Álbum multi-artista:', album);
  }

  // Actualizar y validar
  actualizarSiExiste(playlistId: string): boolean {
    // Verificar que existe
    const existe = this.crudService.getPlaylistById(playlistId);
    
    if (!existe) {
      console.error('Playlist no encontrada');
      return false;
    }

    // Actualizar
    const actualizado = this.crudService.updatePlaylist(
      playlistId,
      'Nuevo nombre'
    );

    return actualizado;
  }

  // Eliminación con confirmación
  eliminarConConfirmacion(playlistId: string): void {
    const playlist = this.crudService.getPlaylistById(playlistId);
    
    if (!playlist) {
      console.error('Playlist no encontrada');
      return;
    }

    const confirmar = confirm(
      `¿Eliminar "${playlist.name}" (${playlist.tracks.length} canciones)?`
    );

    if (confirmar) {
      this.crudService.deletePlaylist(playlistId);
      console.log('Playlist eliminada');
    }
  }

  // Buscar playlist
  buscarPlaylist(nombre: string): LocalPlaylist | undefined {
    const playlists = this.crudService.getPlaylists();
    return playlists.find(p => p.name.toLowerCase().includes(nombre.toLowerCase()));
  }

  // Listar todos con detalles
  mostrarTodoDetallado(): void {
    console.log('=== PLAYLISTS ===');
    this.crudService.getPlaylists().forEach(p => {
      console.log(`${p.name}: ${p.tracks.length} canciones`);
    });

    console.log('=== ARTISTAS ===');
    this.crudService.getArtists().forEach(a => {
      console.log(`${a.name}`);
    });

    console.log('=== ÁLBUMES ===');
    this.crudService.getAlbums().forEach(a => {
      console.log(`${a.name} - ${a.artists.map(ar => ar.name).join(', ')}`);
    });
  }

  // Contar entidades
  contarEntidades(): void {
    const stats = {
      playlists: this.crudService.getPlaylists().length,
      artistas: this.crudService.getArtists().length,
      albums: this.crudService.getAlbums().length,
      totalCanciones: this.crudService.getPlaylists()
        .reduce((sum, p) => sum + p.tracks.length, 0)
    };

    console.table(stats);
  }

  // Exportar datos a JSON
  exportarDatos(): string {
    const datos = {
      playlists: this.crudService.getPlaylists(),
      artistas: this.crudService.getArtists(),
      albums: this.crudService.getAlbums(),
      fechaExportacion: new Date().toISOString()
    };

    return JSON.stringify(datos, null, 2);
  }

  // Verificar integridad
  verificarIntegridad(): boolean {
    try {
      const playlists = this.crudService.getPlaylists();
      const artistas = this.crudService.getArtists();
      const albums = this.crudService.getAlbums();

      // Verificar que todos tengan IDs únicos
      const playlistIds = new Set(playlists.map(p => p.id));
      const artistaIds = new Set(artistas.map(a => a.id));
      const albumIds = new Set(albums.map(a => a.id));

      const idsDuplicados = 
        playlistIds.size !== playlists.length ||
        artistaIds.size !== artistas.length ||
        albumIds.size !== albums.length;

      if (idsDuplicados) {
        console.error('Se encontraron IDs duplicados');
        return false;
      }

      console.log('✓ Datos íntegros');
      return true;
    } catch (error) {
      console.error('Error en verificación:', error);
      return false;
    }
  }
}


// ============================================
// EJEMPLO 3: Patrón de RxJS avanzado
// ============================================

import { combineLatest, map } from 'rxjs/operators';

export class AdvancedCrudExample {
  constructor(private crudService: CrudService) {}

  // Combinar múltiples observables
  obtenerEstadisticas() {
    return combineLatest([
      this.crudService.playlists$,
      this.crudService.artists$,
      this.crudService.albums$
    ]).pipe(
      map(([playlists, artists, albums]) => ({
        totalPlaylists: playlists.length,
        totalArtistas: artists.length,
        totalAlbums: albums.length,
        totalCanciones: playlists.reduce((sum, p) => sum + p.tracks.length, 0),
        promedioCancionesPlaylist: 
          playlists.length > 0 
            ? Math.round(
                playlists.reduce((sum, p) => sum + p.tracks.length, 0) / 
                playlists.length
              )
            : 0
      }))
    );
  }

  // Filtrar playlists con muchas canciones
  playlistsConMuchasCanciones() {
    return this.crudService.playlists$.pipe(
      map(playlists => playlists.filter(p => p.tracks.length > 5))
    );
  }
}


// ============================================
// EJEMPLO 4: Mensajes de consola para debugging
// ============================================

export function ejemplosConsola(): void {
  console.log('%c=== CRUD EJEMPLOS ===', 'font-size: 16px; color: #1db954; font-weight: bold');
  
  console.log('1. Crear playlist: crudService.createPlaylist("nombre")');
  console.log('2. Ver playlists: crudService.getPlaylists()');
  console.log('3. Actualizar: crudService.updatePlaylist(id, "nuevo nombre")');
  console.log('4. Eliminar: crudService.deletePlaylist(id)');
  
  console.log('%c Documentación: CRUD_DOCUMENTATION.md', 'color: blue');
}
