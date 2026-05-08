/**
 * @file coord-converter.js
 * @description Componente conversor de coordenadas geográficas (UTM ↔ decimal, GMS ↔ decimal).
 * Emite o evento customizado `interference:goto` ao usar o resultado no mapa.
 */
const CoordConverter = (() => {
  let _mounted = false
  let _container = null
  let _result = null

  /**
   * @description Monta o componente no container informado.
   * @param {HTMLElement} container
   */
  function mount(container) {
    if (!container) return
    _container = container
    _mounted = true
    _render()
    _bindEvents()
  }

  /** @returns {boolean} */
  function isMounted() { return _mounted }

  function _render() {
    _container.innerHTML = `
      <div class="cc-wrapper">
        <div class="cc-tabbar">
          <span class="cc-title">Conversor de Coordenadas</span>
          <div class="cc-tabs">
            <button class="cc-tab active" data-tab="utm">UTM</button>
            <button class="cc-tab" data-tab="gms">GMS</button>
          </div>
        </div>

        <div class="cc-panel" id="ccUtmPanel">
          <div class="cc-row">
            <div class="cc-field">
              <label>Zona</label>
              <input type="number" id="ccUtmZone" min="1" max="60" placeholder="23">
            </div>
            <div class="cc-field narrow">
              <label>Hem.</label>
              <select id="ccUtmHem">
                <option value="S">S</option>
                <option value="N">N</option>
              </select>
            </div>
            <div class="cc-field grow">
              <label>Leste — E (m)</label>
              <input type="number" id="ccUtmE" placeholder="188000">
            </div>
            <div class="cc-field grow">
              <label>Norte — N (m)</label>
              <input type="number" id="ccUtmN" placeholder="8261000">
            </div>
            <button class="btn btn-primary cc-convert-btn" id="ccUtmBtn">
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
        </div>

        <div class="cc-panel" id="ccGmsPanel" hidden>
          <div class="cc-row">
            <div class="cc-field narrow"><label>Lat °</label><input type="number" id="ccGmsLatD" placeholder="15"></div>
            <div class="cc-field narrow"><label>′</label><input type="number" id="ccGmsLatM" placeholder="46"></div>
            <div class="cc-field sec"><label>″</label><input type="number" id="ccGmsLatS" placeholder="55.000" step="0.001"></div>
            <div class="cc-field narrow">
              <label>Dir.</label>
              <select id="ccGmsLatDir"><option value="S">S</option><option value="N">N</option></select>
            </div>
            <div class="cc-field narrow"><label>Lon °</label><input type="number" id="ccGmsLonD" placeholder="47"></div>
            <div class="cc-field narrow"><label>′</label><input type="number" id="ccGmsLonM" placeholder="55"></div>
            <div class="cc-field sec"><label>″</label><input type="number" id="ccGmsLonS" placeholder="33.000" step="0.001"></div>
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
        </div>

        <div class="cc-result" id="ccResult" hidden>
          <span id="ccResultText"></span>
          <button class="btn btn-map cc-use-btn" id="ccUseBtn" title="Usar no mapa" aria-label="Usar no mapa">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24"
                 fill="none" stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
              <path d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0Z"/>
              <circle cx="12" cy="10" r="3"/>
            </svg>
          </button>
        </div>
      </div>
    `
  }

  function _bindEvents() {
    _container.querySelectorAll('.cc-tab').forEach(btn => {
      btn.addEventListener('click', () => {
        _container.querySelectorAll('.cc-tab').forEach(b => b.classList.remove('active'))
        btn.classList.add('active')
        const tab = btn.dataset.tab
        document.getElementById('ccUtmPanel').hidden = (tab !== 'utm')
        document.getElementById('ccGmsPanel').hidden = (tab !== 'gms')
        document.getElementById('ccResult').setAttribute('hidden', '')
        _result = null
      })
    })

    document.getElementById('ccUtmBtn').addEventListener('click', async () => {
      const e = parseFloat(document.getElementById('ccUtmE').value)
      const n = parseFloat(document.getElementById('ccUtmN').value)
      const z = parseInt(document.getElementById('ccUtmZone').value)
      const h = document.getElementById('ccUtmHem').value
      if (isNaN(e) || isNaN(n) || isNaN(z)) {
        showToast('Preencha todos os campos UTM.', 'error')
        return
      }
      try {
        _result = await window.coordService.utmToDecimal(e, n, z, h)
        document.getElementById('ccResultText').textContent =
          `Lat: ${_result.lat.toFixed(6)}   Lon: ${_result.lon.toFixed(6)}`
        document.getElementById('ccResult').removeAttribute('hidden')
      } catch (err) {
        console.error('Erro na conversão UTM:', err)
        showToast('Erro na conversão UTM.', 'error')
      }
    })

    document.getElementById('ccGmsBtn').addEventListener('click', async () => {
      const ld   = document.getElementById('ccGmsLatD').value
      const lm   = document.getElementById('ccGmsLatM').value
      const ls   = document.getElementById('ccGmsLatS').value
      const ldir = document.getElementById('ccGmsLatDir').value
      const od   = document.getElementById('ccGmsLonD').value
      const om   = document.getElementById('ccGmsLonM').value
      const os   = document.getElementById('ccGmsLonS').value
      const odir = document.getElementById('ccGmsLonDir').value
      if (!ld || !od) {
        showToast('Preencha os graus de latitude e longitude.', 'error')
        return
      }
      try {
        _result = await window.coordService.dmsToDecimal(ld, lm, ls, ldir, od, om, os, odir)
        document.getElementById('ccResultText').textContent =
          `Lat: ${_result.lat.toFixed(6)}   Lon: ${_result.lon.toFixed(6)}`
        document.getElementById('ccResult').removeAttribute('hidden')
      } catch (err) {
        console.error('Erro na conversão GMS:', err)
        showToast('Erro na conversão GMS.', 'error')
      }
    })

    document.getElementById('ccUseBtn').addEventListener('click', () => {
      if (!_result) return
      document.dispatchEvent(new CustomEvent('interference:goto', {
        detail: { lat: _result.lat, lng: _result.lon }
      }))
    })
  }

  return { mount, isMounted }
})()
