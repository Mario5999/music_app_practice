// Ejemplo de cómo actualizar app.component.ts para agregar navegación al CRUD

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterOutlet],
  template: `
    <div class="app-container">
      <nav class="navbar">
        <div class="nav-content">
          <div class="logo">🎵 App Reproductor</div>
          <ul class="nav-links">
            <li><a routerLink="/Playlist" routerLinkActive="active">📋 Mi Playlist</a></li>
            <li><a routerLink="/search" routerLinkActive="active">🔍 Buscar</a></li>
            <li><a routerLink="/crud" routerLinkActive="active">⚙️ CRUD</a></li>
          </ul>
        </div>
      </nav>

      <main class="main-content">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    .app-container {
      min-height: 100vh;
      background: #f5f5f5;
    }

    .navbar {
      background: linear-gradient(135deg, #1db954 0%, #1aa34a 100%);
      padding: 15px 0;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      sticky: top 0;
      z-index: 1000;
    }

    .nav-content {
      max-width: 1200px;
      margin: 0 auto;
      padding: 0 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .logo {
      color: white;
      font-size: 1.3rem;
      font-weight: 700;
    }

    .nav-links {
      list-style: none;
      display: flex;
      gap: 30px;
      margin: 0;
      padding: 0;
    }

    .nav-links a {
      color: white;
      text-decoration: none;
      font-weight: 600;
      transition: all 0.3s;
      padding: 8px 12px;
      border-radius: 4px;
    }

    .nav-links a:hover {
      background: rgba(255, 255, 255, 0.2);
      transform: scale(1.05);
    }

    .nav-links a.active {
      background: rgba(0, 0, 0, 0.2);
      border-bottom: 3px solid white;
    }

    .main-content {
      max-width: 1400px;
      margin: 0 auto;
      padding: 20px;
    }

    @media (max-width: 768px) {
      .nav-content {
        flex-direction: column;
        gap: 15px;
      }

      .nav-links {
        gap: 15px;
      }

      .logo {
        font-size: 1rem;
      }
    }
  `]
})
export class AppComponent {
  title = 'App Reproductor';
}

// ============================================
// ALTERNATIVA: Si usas un navbar component separado
// ============================================

// navbar.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <nav class="navbar">
      <div class="nav-container">
        <div class="logo">
          <span class="icon">🎵</span>
          <span class="title">App Reproductor</span>
        </div>

        <ul class="nav-menu">
          <li class="nav-item">
            <a routerLink="/Playlist" routerLinkActive="active" class="nav-link">
              <span class="icon">📋</span>
              <span class="label">Mi Playlist</span>
            </a>
          </li>
          <li class="nav-item">
            <a routerLink="/search" routerLinkActive="active" class="nav-link">
              <span class="icon">🔍</span>
              <span class="label">Buscar</span>
            </a>
          </li>
          <li class="nav-item">
            <a routerLink="/crud" routerLinkActive="active" class="nav-link">
              <span class="icon">⚙️</span>
              <span class="label">CRUD</span>
            </a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link">
              <span class="icon">📚</span>
              <span class="label">Gestión</span>
            </a>
            <ul class="submenu">
              <li><a routerLink="/crud/playlists">Playlists</a></li>
              <li><a routerLink="/crud/artists">Artistas</a></li>
              <li><a routerLink="/crud/albums">Álbumes</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>
  `,
  styles: [`
    .navbar {
      background: linear-gradient(135deg, #1db954 0%, #1aa34a 100%);
      padding: 15px 20px;
      box-shadow: 0 4px 12px rgba(29, 185, 84, 0.3);
      position: sticky;
      top: 0;
      z-index: 1000;
    }

    .nav-container {
      max-width: 1200px;
      margin: 0 auto;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .logo {
      color: white;
      font-size: 1.3rem;
      font-weight: 700;
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .nav-menu {
      list-style: none;
      display: flex;
      gap: 5px;
      margin: 0;
      padding: 0;
    }

    .nav-item {
      position: relative;
    }

    .nav-link {
      color: white;
      text-decoration: none;
      padding: 10px 15px;
      display: flex;
      align-items: center;
      gap: 8px;
      border-radius: 6px;
      transition: all 0.3s;
      font-weight: 600;
    }

    .nav-link:hover {
      background: rgba(255, 255, 255, 0.2);
      transform: translateY(-2px);
    }

    .nav-link.active {
      background: rgba(0, 0, 0, 0.3);
      box-shadow: inset 0 2px 6px rgba(0, 0, 0, 0.2);
    }

    .dropdown:hover .submenu {
      opacity: 1;
      visibility: visible;
      transform: translateY(0);
    }

    .submenu {
      position: absolute;
      top: 100%;
      left: 0;
      background: rgba(0, 0, 0, 0.9);
      list-style: none;
      padding: 0;
      margin: 8px 0 0 0;
      min-width: 200px;
      border-radius: 6px;
      opacity: 0;
      visibility: hidden;
      transform: translateY(-10px);
      transition: all 0.3s;
    }

    .submenu li {
      border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    }

    .submenu li:last-child {
      border-bottom: none;
    }

    .submenu a {
      display: block;
      padding: 12px 20px;
      color: white;
      text-decoration: none;
      transition: all 0.3s;
    }

    .submenu a:hover {
      background: rgba(29, 185, 84, 0.5);
      padding-left: 25px;
    }

    .icon {
      font-size: 1.1em;
    }

    @media (max-width: 768px) {
      .label {
        display: none;
      }

      .nav-menu {
        gap: 0;
      }

      .nav-link {
        padding: 8px 12px;
      }

      .submenu {
        left: -150px;
      }
    }
  `]
})
export class NavbarComponent {}

// ============================================
// CÓMO ACTUALIZAR app.component.ts
// ============================================

// Opción 1: Importar directamente
import { NavbarComponent } from './components/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NavbarComponent, RouterOutlet],
  template: `
    <app-navbar></app-navbar>
    <main>
      <router-outlet></router-outlet>
    </main>
  `
})
export class AppComponent {}

// Opción 2: Con lazy loading
@Component({
  selector: 'app-root',
  standalone: true,
  template: `
    <div class="app-wrapper">
      <app-navbar></app-navbar>
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent {}
