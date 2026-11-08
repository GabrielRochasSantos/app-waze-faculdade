/* ============================================================
   CONFIGURAÇÃO CENTRAL DO APP
   Edite este arquivo para trocar o endereço do backend e
   configurar a API de mapa.
   ============================================================ */
window.APP_CONFIG = {
  // Endereço do backend Spring Boot
  backendUrl: 'http://localhost:8081',

  map: {
    // ''         = usa os provedores gratuitos sem chave (Carto/OSM) — padrão atual
    // 'maptiler' = usa a chave abaixo (o MapTiler NÃO funciona sem chave)
    provider: 'maptiler',

    // Eu usei o Mapfiller para colocar api aqui.
  
    apiKey: 'vmKOuooR2nlx546PtcyE',

    // Estilo do MapTiler (ex.: streets-v2, streets-v2-dark, basic-v2-dark, dataviz-v2)
    style: 'streets-v2-dark',

    // URL manual (usada quando provider não é 'maptiler')
    tileUrl: '',
    maxZoom: 20,
    attribution: '© MapTiler © OpenStreetMap'
  }
};
