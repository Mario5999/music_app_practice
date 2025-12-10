export const environment = {
  production: true,
  spotify: {
    clientId: '4d0289c7a015430192a75c06703da263',
    clientSecret: '6269f584ffb84f269c4d8741023e0f6d',
    redirectUri: 'http://127.0.0.1:4200/callback',
    scopes: [
      'user-read-private',
      'user-read-email',
      'playlist-read-private',
      'user-library-read'
    ]
  }
};
