/* ══════════════════════════════════════════════════════════
   Italo Maps — Modo Acessível 60+
   Ativa o estilo acessível e controla o tamanho da letra.
   Preferência salva no navegador (localStorage).
   ══════════════════════════════════════════════════════════ */
(function () {
  'use strict';

  var STORAGE_KEY = 'italoFontSize';
  var sizes = ['normal', 'grande', 'maior'];
  var body = document.body;

  if (!body) return;

  body.classList.add('senior-ui');

  function apply(size) {
    body.classList.remove('fs-grande', 'fs-maior');
    if (size === 'grande') body.classList.add('fs-grande');
    if (size === 'maior') body.classList.add('fs-maior');

    try { localStorage.setItem(STORAGE_KEY, size); } catch (_) {}

    var buttons = document.querySelectorAll('[data-font]');
    for (var i = 0; i < buttons.length; i++) {
      var active = buttons[i].getAttribute('data-font') === size;
      buttons[i].classList.toggle('active', active);
      buttons[i].setAttribute('aria-pressed', active ? 'true' : 'false');
    }

    /* O mapa precisa recalcular o tamanho após a mudança de letra  Gabriel resolveu aqui */
    window.setTimeout(function () {
      window.dispatchEvent(new Event('resize'));
    }, 150);
  }

  function init() {
    var saved = 'normal';
    try { saved = localStorage.getItem(STORAGE_KEY) || 'normal'; } catch (_) {}
    if (sizes.indexOf(saved) === -1) saved = 'normal';
    apply(saved);

    var buttons = document.querySelectorAll('[data-font]');
    for (var i = 0; i < buttons.length; i++) {
      buttons[i].addEventListener('click', function () {
        var size = this.getAttribute('data-font');
        if (sizes.indexOf(size) === -1) size = 'normal';
        apply(size);
      });
    }
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    init();
  }
})();
