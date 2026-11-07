/* ============================================================
   CONFIGURAÇÃO CENTRAL DO APP
   Edite este arquivo para trocar o endereço do backend e
   colocar a chave da sua API de mapa.
   ============================================================ */
window.APP_CONFIG = {
  // Endereço do backend Spring Boot
  backendUrl: 'http://localhost:8081',

  // API de mapa (opcional).
  // Cole a chave e a URL de tiles do provedor que você escolher.
  // Exemplos:
  //   Mapbox:
  //     tileUrl: 'https://api.mapbox.com/styles/v1/mapbox/dark-v11/tiles/{z}/{x}/{y}?access_token=SUA_CHAVE'
  //   TomTom:
  //     tileUrl: 'https://api.tomtom.com/map/1/tile/basic/main/{z}/{x}/{y}.png?key=SUA_CHAVE'
  //   Google (via provedor compatível com XYZ):
  //     tileUrl: 'https://mt1.google.com/vt/lyrs=m&x={x}&y={y}&z={z}'
  map: {
    apiKey: '',
    tileUrl: '', // se preencher, este provedor é usado antes dos gratuitos
    maxZoom: 19,
    attribution: ''
  }
};
