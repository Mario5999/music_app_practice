import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-main-screen',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './main-screen.component.html',
  styleUrls: ['./main-screen.component.css']
})
export class MainScreenComponent {
  // Datos de ejemplo para mostrar tarjetas (simulan playlists/álbumes)
  cards = Array.from({ length: 12 }).map((_, i) => ({
    id: i + 1,
    title: `Playlist ${i + 1}`,
    subtitle: `By Artist ${i + 1}`,
    image: 'https://via.placeholder.com/300?text=Cover+' + (i + 1)
  }));
}
