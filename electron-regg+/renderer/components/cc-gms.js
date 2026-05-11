/**
 * @file cc-gms.js
 * @description Painel GMS do conversor de coordenadas. Emite o evento `cc:converted`
 * com `{ lat, lon }` ao realizar a conversão.
 */
const CcGms = (() => {
  let _container = null

  /**
   * @description Monta o painel GMS no container informado.
   * @param {HTMLElement} container
   */
  function mount(container) {
    if (!container) return
    _container = container
    _render()
    _bindEvents()
  }

  const _EXAMPLES = [
    { label: '8°45\'43"S / 63°54\'14"O', ld: 8, lm: 45, ls: 43, ldir: 'S', od: 63, om: 54, os: 14, odir: 'O' }
  ]

  function _render() {
    _container.innerHTML = `
      <div class="cc-row">
        <div class="cc-field narrow"><label>Lat °</label><input type="number" id="ccGmsLatD" placeholder="8"></div>
        <div class="cc-field narrow"><label>′</label><input type="number" id="ccGmsLatM" placeholder="45"></div>
        <div class="cc-field sec"><label>″</label><input type="number" id="ccGmsLatS" placeholder="43.000" step="0.001"></div>
        <div class="cc-field narrow">
          <label>Dir.</label>
          <select id="ccGmsLatDir"><option value="S">S</option><option value="N">N</option></select>
        </div>
        <div class="cc-field narrow"><label>Lon °</label><input type="number" id="ccGmsLonD" placeholder="63"></div>
        <div class="cc-field narrow"><label>′</label><input type="number" id="ccGmsLonM" placeholder="54"></div>
        <div class="cc-field sec"><label>″</label><input type="number" id="ccGmsLonS" placeholder="14.000" step="0.001"></div>
        <div class="cc-field narrow">
          <label>Dir.</label>
          <select id="ccGmsLonDir"><option value="O">O</option><option value="W">W</option><option value="E">E</option></select>
        </div>
        <button class="btn btn-primary cc-convert-btn" id="ccGmsBtn">
          <svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <line x1="2" x2="5" y1="12" y2="12"/><line x1="19" x2="22" y1="12" y2="12"/>
            <line x1="12" x2="12" y1="2" y2="5"/><line x1="12" x2="12" y1="19" y2="22"/>
            <circle cx="12" cy="12" r="7"/>
          </svg>
          Converter
        </button>
      </div>
      <div class="cc-examples">
        <span class="cc-examples-label">Teste:</span>
        ${_EXAMPLES.map(ex =>
          `<button class="cc-example-btn"
            data-ld="${ex.ld}" data-lm="${ex.lm}" data-ls="${ex.ls}" data-ldir="${ex.ldir}"
            data-od="${ex.od}" data-om="${ex.om}" data-os="${ex.os}" data-odir="${ex.odir}"
          >${ex.label}</button>`
        ).join('')}
      </div>
    `
  }

  function _bindEvents() {
    _container.querySelectorAll('.cc-example-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        _container.querySelector('#ccGmsLatD').value   = btn.dataset.ld
        _container.querySelector('#ccGmsLatM').value   = btn.dataset.lm
        _container.querySelector('#ccGmsLatS').value   = btn.dataset.ls
        _container.querySelector('#ccGmsLatDir').value = btn.dataset.ldir
        _container.querySelector('#ccGmsLonD').value   = btn.dataset.od
        _container.querySelector('#ccGmsLonM').value   = btn.dataset.om
        _container.querySelector('#ccGmsLonS').value   = btn.dataset.os
        _container.querySelector('#ccGmsLonDir').value = btn.dataset.odir
      })
    })

    _container.querySelector('#ccGmsBtn').addEventListener('click', async () => {
      const ld   = _container.querySelector('#ccGmsLatD').value
      const lm   = _container.querySelector('#ccGmsLatM').value
      const ls   = _container.querySelector('#ccGmsLatS').value
      const ldir = _container.querySelector('#ccGmsLatDir').value
      const od   = _container.querySelector('#ccGmsLonD').value
      const om   = _container.querySelector('#ccGmsLonM').value
      const os   = _container.querySelector('#ccGmsLonS').value
      const odir = _container.querySelector('#ccGmsLonDir').value
      if (!ld || !od) {
        showToast('Preencha os graus de latitude e longitude.', 'error')
        return
      }
      try {
        const result = await window.coordService.dmsToDecimal(ld, lm, ls, ldir, od, om, os, odir)
        document.dispatchEvent(new CustomEvent('interference:goto', {
          detail: { lat: result.lat, lng: result.lon }
        }))
      } catch (err) {
        console.error('Erro na conversão GMS:', err)
        showToast('Erro na conversão GMS.', 'error')
      }
    })
  }

  return { mount }
})()
