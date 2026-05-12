/**
 * @file interference-view.js
 * @description Painel lateral deslizante (drawer) para cadastro e seleção de interferências hídricas.
 * Desliza da direita para a esquerda sobre o painel de formulário.
 *
 * Seções internas:
 *  1. Formulário de cadastro: logradouro, latitude, longitude e selects de domínio.
 *  2. Pesquisa de interferências cadastradas com lista de resultados.
 *
 * Eventos disparados:
 *  - `interference-view:select` → usuário clica em uma interferência da lista (fecha o drawer).
 *  - `interference-view:saved`  → usuário salva uma nova interferência.
 *
 * Aberto via `InterferenceView.open()` e fechado pelo botão Voltar ou tecla Escape.
 */
const InterferenceView = (() => {
  let _mounted        = false
  let _container      = null
  let _domainsLoaded  = false
  let _addrSelectedId = null
  let _ifRows         = []
  let _selectedId     = null

  /**
   * @description Renderiza o drawer no container e registra os eventos.
   * O container deve ser o `#interference-drawer` no DOM.
   * @param {HTMLElement} container
   */
  function mount(container) {
    _container = container
    _container.className = 'side-drawer'

    _container.innerHTML = `
      <div class="av-header">
        <button type="button" class="av-back-btn" id="ivBack">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <polyline points="15 18 9 12 15 6"/>
          </svg>
          Voltar
        </button>
        <span class="av-title">Cadastro de Interferência</span>
        <button type="button" class="btn btn-secondary av-new-btn" id="ivNew">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="16"/><line x1="8" y1="12" x2="16" y2="12"/>
          </svg>
          Novo
        </button>
        <button type="button" class="btn btn-primary" id="ivSave">Salvar</button>
      </div>

      <div class="av-content">

        <!-- Formulário de cadastro de nova interferência -->
        <form id="ivForm" autocomplete="off">
          <fieldset class="form-section">
            <legend class="section-title">
              <span class="section-icon">📍</span> Interferência
            </legend>

            <div class="form-row">
              <div class="form-group grow autocomplete-wrap" id="ivAddrWrap">
                <label for="ivAddrSearch">Endereço <span class="required">*</span></label>
                <div class="search-input-wrap">
                  <input type="text" id="ivAddrSearch" name="ivAddrSearch"
                    placeholder="Digite o logradouro para pesquisar..."
                    autocomplete="off" aria-autocomplete="list"
                    aria-controls="ivAddrDropdown" aria-expanded="false">
                  <button type="button" id="ivAddrClear" class="search-clear" title="Limpar" hidden>×</button>
                </div>
                <ul class="autocomplete-dropdown" id="ivAddrDropdown" role="listbox" hidden></ul>
                <input type="hidden" id="ivAddrId">
              </div>
            </div>

            <div class="form-row iv-coords-row">
              <div class="form-group">
                <label for="ivLat">Latitude <span class="required">*</span></label>
                <input type="number" id="ivLat" name="ivLat"
                  placeholder="-15.780000" step="any" required>
              </div>
              <div class="form-group">
                <label for="ivLon">Longitude <span class="required">*</span></label>
                <input type="number" id="ivLon" name="ivLon"
                  placeholder="-47.930000" step="any" required>
              </div>
              <button type="button" id="ivGotoMap" class="btn-drawer iv-goto-map-btn" title="Ver no mapa">
                <svg xmlns="http://www.w3.org/2000/svg" width="17" height="17" viewBox="0 0 24 24"
                     fill="none" stroke="currentColor" stroke-width="2"
                     stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                  <path d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0Z"/>
                  <circle cx="12" cy="10" r="3"/>
                </svg>
              </button>
            </div>

            <div class="form-row">
              <div class="form-group grow">
                <label for="ivTipoInterf">Tipo de Interferência <span class="required">*</span></label>
                <select id="ivTipoInterf" name="ivTipoInterf" required>
                  <option value="">Selecione...</option>
                </select>
              </div>
              <div class="form-group grow">
                <label for="ivTipoOut">Tipo de Outorga <span class="required">*</span></label>
                <select id="ivTipoOut" name="ivTipoOut" required>
                  <option value="">Selecione...</option>
                </select>
              </div>
              <div class="form-group grow">
                <label for="ivSubtipoOut">Subtipo de Outorga</label>
                <select id="ivSubtipoOut" name="ivSubtipoOut">
                  <option value="">Selecione...</option>
                </select>
              </div>
              <div class="form-group grow">
                <label for="ivSituacao">Situação <span class="required">*</span></label>
                <select id="ivSituacao" name="ivSituacao" required>
                  <option value="">Selecione...</option>
                </select>
              </div>
            </div>

            <div class="form-row">
              <div class="form-group grow">
                <label for="ivTipoAto">Tipo de Ato</label>
                <select id="ivTipoAto" name="ivTipoAto">
                  <option value="">Selecione...</option>
                </select>
              </div>
              <div class="form-group grow">
                <label for="ivBacia">Bacia Hidrográfica</label>
                <select id="ivBacia" name="ivBacia">
                  <option value="">Selecione...</option>
                </select>
              </div>
              <div class="form-group grow">
                <label for="ivUnidade">Unidade Hidrográfica</label>
                <select id="ivUnidade" name="ivUnidade">
                  <option value="">Selecione...</option>
                </select>
              </div>
            </div>

          </fieldset>
        </form>

        <!-- Pesquisa e lista de interferências cadastradas -->
        <div class="form-section">
          <div class="doc-list-header">
            <span class="doc-list-title">Pesquisar Interferências</span>
          </div>
          <div class="doc-search-bar">
            <div class="form-group grow">
              <input type="text" id="ivSearch"
                placeholder="Pesquisar por endereço..."
                autocomplete="off">
            </div>
            <button type="button" id="ivSearchBtn" class="btn btn-primary">Pesquisar</button>
          </div>
          <div class="doc-list-wrap iv-if-list">
            <table class="doc-list-table" aria-label="Lista de interferências">
              <thead>
                <tr>
                  <th style="width:32%">Logradouro</th>
                  <th style="width:20%">Latitude</th>
                  <th style="width:20%">Longitude</th>
                  <th style="width:18%">Tipo</th>
                  <th style="width:44px"></th>
                </tr>
              </thead>
              <tbody id="ivListBody"></tbody>
            </table>
            <p class="doc-list-empty" id="ivListEmpty" hidden>Nenhuma interferência encontrada.</p>
          </div>
        </div>

        <!-- Montado por InterferenceDetails.mount() -->
        <div id="ivDetailsContainer"></div>

      </div>
    `

    InterferenceDetails.mount(_el('ivDetailsContainer'))
    _bindEvents()
    _mounted = true
  }

  /**
   * @description Registra todos os eventos do drawer.
   */
  function _bindEvents() {
    _el('ivBack').addEventListener('click', close)
    _el('ivNew').addEventListener('click', _new)
    _el('ivSave').addEventListener('click', _save)

    _el('ivSearchBtn').addEventListener('click', () => _searchList(_el('ivSearch').value.trim()))
    _el('ivSearch').addEventListener('keydown', (e) => {
      if (e.key === 'Enter') _searchList(_el('ivSearch').value.trim())
    })

    _el('ivTipoInterf').addEventListener('change', () => {
      if (_selectedId) return
      const sel  = _el('ivTipoInterf')
      const text = sel.options[sel.selectedIndex]?.text ?? ''
      if (text === 'Subterrânea') {
        InterferenceDetails.fillEmpty()
        InterferenceDetails.show()
      } else {
        InterferenceDetails.hide()
      }
    })

    _bindAddressSearch()

    _el('ivGotoMap').addEventListener('click', _gotoMap)

    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && _container?.classList.contains('open')) close()
    })
  }

  /**
   * @description Despacha o evento `interference:goto` com as coordenadas atuais dos inputs.
   * O renderer.js centraliza o mapa e posiciona o marcador ao receber este evento.
   */
  function _gotoMap() {
    const lat = parseFloat(_el('ivLat').value)
    const lng = parseFloat(_el('ivLon').value)
    if (isNaN(lat) || isNaN(lng)) return
    document.dispatchEvent(new CustomEvent('interference:goto', { detail: { lat, lng } }))
  }

  /**
   * @description Configura o autocomplete de endereço no formulário de interferência.
   * Chama window.addressService para buscar endereços conforme o usuário digita.
   */
  function _bindAddressSearch() {
    const input = _el('ivAddrSearch')
    const clear = _el('ivAddrClear')

    input.addEventListener('input', (e) => {
      const term = e.target.value.trim()
      _addrSelectedId = null
      _el('ivAddrId').value = ''
      clear.hidden = term.length === 0
      if (term.length < 2) { _closeAddrDropdown(); return }
      _searchAddr(term)
    })

    clear.addEventListener('click', () => {
      input.value = ''
      _el('ivAddrId').value = ''
      _addrSelectedId = null
      clear.hidden = true
      _closeAddrDropdown()
    })

    document.addEventListener('click', (e) => {
      if (!e.target.closest('#ivAddrWrap')) _closeAddrDropdown()
    })
  }

  /**
   * @description Busca endereços via API pelo termo digitado.
   * @param {string} term - Texto do logradouro.
   */
  async function _searchAddr(term) {
    try {
      const rows = await window.addressService.fetchByKeyword(term)
      _renderAddrDropdown(rows)
    } catch (err) {
      console.error('InterferenceView: erro ao buscar endereços', err)
      _renderAddrDropdown([])
    }
  }

  /**
   * @description Renderiza o dropdown de sugestões de endereço.
   * @param {Array<{id: number, logradouro: string, cidade: string, estado: string}>} rows
   */
  function _renderAddrDropdown(rows) {
    const list = _el('ivAddrDropdown')
    list.innerHTML = rows.length
      ? rows.map(r => `
          <li class="autocomplete-item" role="option"
              data-id="${r.id}"
              data-label="${r.logradouro}">
            <span class="autocomplete-item__main">${r.logradouro}</span>
          </li>`).join('')
      : '<li class="autocomplete-empty">Nenhum endereço encontrado</li>'

    list.removeAttribute('hidden')
    _el('ivAddrSearch').setAttribute('aria-expanded', 'true')

    list.querySelectorAll('.autocomplete-item').forEach(li => {
      li.addEventListener('click', () => {
        _addrSelectedId = li.dataset.id
        _el('ivAddrSearch').value = li.dataset.label
        _el('ivAddrId').value     = li.dataset.id
        _el('ivAddrClear').hidden = false
        _closeAddrDropdown()
      })
    })
  }

  /**
   * @description Fecha o dropdown de sugestões de endereço.
   */
  function _closeAddrDropdown() {
    _el('ivAddrDropdown')?.setAttribute('hidden', '')
    _el('ivAddrSearch')?.setAttribute('aria-expanded', 'false')
  }

  /**
   * @description Valida e salva uma nova interferência.
   * TODO: conectar a window.documentService.saveInterference(data).
   */
  function _save() {
    const form = _el('ivForm')
    if (!form.checkValidity()) { form.reportValidity(); return }

    const data = {
      enderecoId:        _addrSelectedId || null,
      logradouro:        _el('ivAddrSearch').value.trim(),
      latitude:          parseFloat(_el('ivLat').value),
      longitude:         parseFloat(_el('ivLon').value),
      tipoInterferencia: _el('ivTipoInterf').value,
      tipoOutorga:       _el('ivTipoOut').value,
      subtipoOutorga:    _el('ivSubtipoOut').value,
      situacao:          _el('ivSituacao').value,
      tipoAto:           _el('ivTipoAto').value,
      bacia:             _el('ivBacia').value,
      unidade:           _el('ivUnidade').value
    }

    if (_selectedId) {
      console.log('Editar interferência:', _selectedId, data)
      document.dispatchEvent(new CustomEvent('interference-view:saved', { detail: { id: _selectedId, ...data } }))
    } else {
      console.log('Criar interferência:', data)
      document.dispatchEvent(new CustomEvent('interference-view:saved', { detail: data }))
    }
    _selectedId = null
    form.reset()
    InterferenceDetails.hide()
    _el('ivSave').textContent = 'Salvar'
    _searchList('')
  }

  /**
   * @description Busca interferências cadastradas pelo logradouro via API.
   * @param {string} term
   */
  async function _searchList(term) {
    try {
      const rows = await window.interferenceService.fetchByKeyword(term)
      _renderRows(rows)
    } catch (err) {
      console.error('InterferenceView: erro ao buscar interferências', err)
      _renderRows([])
    }
  }

  /**
   * @description Popula um select com itens do domínio { id, descricao }.
   * @param {string} selectId
   * @param {Array<{id: number, descricao: string}>} items
   */
  function _fillSelect(selectId, items) {
    const sel = _el(selectId)
    if (!sel || !Array.isArray(items)) return
    const opts = items.map(i => `<option value="${i.id}">${i.descricao}</option>`).join('')
    sel.innerHTML = `<option value="">Selecione...</option>${opts}`
  }

  /**
   * @description Carrega todas as tabelas de domínio via window.domainService na primeira abertura.
   * A API retorna objetos indexados por ID string (ex: {"1":{id,descricao},...}), por isso Object.values().
   * Bacias e Unidades vêm de endpoints dedicados via listBacias()/listUnidades().
   */
  async function _loadDomains() {
    if (_domainsLoaded) return
    try {
      const d = await window.domainService.fetchAll()
      _fillSelect('ivTipoInterf', Object.values(d.tipoInterferencia ?? {}))
      _fillSelect('ivTipoOut',    Object.values(d.tipoOutorga       ?? {}))
      _fillSelect('ivSubtipoOut', Object.values(d.subtipoOutorga    ?? {}))
      _fillSelect('ivSituacao',   Object.values(d.situacaoProcesso  ?? {}))
      _fillSelect('ivTipoAto',    Object.values(d.tipoAto           ?? {}))
      InterferenceDetails.fillDomains(d)
      const bacias   = await window.baciaService.listAll()
      const unidades = await window.unidadeService.listAll()
      _fillSelect('ivBacia',   bacias)
      _fillSelect('ivUnidade', unidades)
      _domainsLoaded = true
    } catch (err) {
      console.error('InterferenceView: erro ao carregar domínios', err)
    }
  }

  /**
   * @description Popula a tabela de interferências com os resultados da busca.
   * Armazena os dados completos em `_ifRows` para preencher o formulário ao selecionar.
   * @param {Array<Object>} rows - Interferências normalizadas pelo serviço.
   */
  function _renderRows(rows) {
    const tbody = _el('ivListBody')
    const empty = _el('ivListEmpty')
    _ifRows = rows

    if (!rows.length) {
      tbody.innerHTML = ''
      empty.removeAttribute('hidden')
      return
    }

    empty.setAttribute('hidden', '')
    tbody.innerHTML = rows.map((r, idx) => `
      <tr class="doc-list-row" data-idx="${idx}" data-id="${r.id}">
        <td title="${r.logradouro || ''}">${r.logradouro || '—'}</td>
        <td>${r.latitude  ?? '—'}</td>
        <td>${r.longitude ?? '—'}</td>
        <td>${r.tipoInterferencia || '—'}</td>
        <td class="doc-list-action-cell">
          <button type="button" class="doc-list-delete-btn" title="Excluir">
            <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24"
                 fill="none" stroke="currentColor" stroke-width="2.5"
                 stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
              <polyline points="3 6 5 6 21 6"/>
              <path d="M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6"/>
              <path d="M10 11v6M14 11v6M9 6V4h6v2"/>
            </svg>
          </button>
        </td>
      </tr>
    `).join('')

    tbody.querySelectorAll('.doc-list-row').forEach(tr =>
      tr.addEventListener('click', () => _pickInterference(tr))
    )
    tbody.querySelectorAll('.doc-list-delete-btn').forEach(btn =>
      btn.addEventListener('click', (e) => { e.stopPropagation(); _deleteRow(btn.closest('tr')) })
    )
  }

  /**
   * @description Remove uma interferência pelo id.
   * @param {HTMLTableRowElement} tr
   */
  function _deleteRow(tr) {
    const id = tr.dataset.id
    console.log('Excluir interferência:', id)
    document.dispatchEvent(new CustomEvent('interference-view:deleted', { detail: { id } }))
    tr.remove()
  }

  /**
   * @description Seleciona uma interferência da lista, preenche o formulário e notifica o SelectInterference.
   * O drawer permanece aberto. O evento `interference-view:select` é capturado pelo SelectInterference.
   * @param {HTMLTableRowElement} tr
   */
  function _pickInterference(tr) {
    const r = _ifRows[parseInt(tr.dataset.idx, 10)]
    if (!r) return

    _selectedId     = r.id
    _addrSelectedId = r.enderecoId || null
    _el('ivAddrId').value     = r.enderecoId    || ''
    _el('ivAddrSearch').value = r.enderecoLabel || r.logradouro || ''
    _el('ivAddrClear').hidden = !r.logradouro

    _el('ivLat').value = r.latitude  ?? ''
    _el('ivLon').value = r.longitude ?? ''

    _setSelectValue('ivTipoInterf', r.tipoInterferenciaId)
    _setSelectValue('ivTipoOut',    r.tipoOutorgaId)
    _setSelectValue('ivSubtipoOut', r.subtipoOutorgaId)
    _setSelectValue('ivSituacao',   r.situacaoProcessoId)
    _setSelectValue('ivTipoAto',    r.tipoAtoId)
    _setSelectValue('ivBacia',      r.baciaHidrograficaId)
    _setSelectValue('ivUnidade',    r.unidadeHidrograficaId)

    _el('ivSave').textContent = 'Editar'

    if (r.tipoInterferencia === 'Subterrânea') {
      InterferenceDetails.show()
      InterferenceDetails.fill(r)
    } else {
      InterferenceDetails.hide()
    }

    document.dispatchEvent(new CustomEvent('interference-view:select', {
      detail: {
        id:        r.id,
        latitude:  r.latitude,
        longitude: r.longitude,
        label:     r.enderecoLabel || r.logradouro || ''
      }
    }))
  }

  /**
   * @description Define o valor de um select pelo ID da opção.
   * @param {string} selectId
   * @param {number|null} value
   */
  function _setSelectValue(selectId, value) {
    const sel = _el(selectId)
    if (sel && value != null) sel.value = String(value)
  }

  /**
   * @description Preenche os campos de latitude e longitude com as coordenadas do mapa.
   * Chamado pelo renderer.js quando o marcador é posicionado.
   * @param {number} lat
   * @param {number} lng
   */
  function setCoords(lat, lng) {
    if (!_mounted) return
    const latEl = _el('ivLat')
    const lonEl = _el('ivLon')
    if (latEl) { latEl.value = lat.toFixed(6); latEl.classList.add('from-map') }
    if (lonEl) { lonEl.value = lng.toFixed(6); lonEl.classList.add('from-map') }
  }

  /**
   * @description Abre o drawer com animação de deslize e pré-preenche os campos de
   * coordenadas com os valores atualmente selecionados em SelectInterference, se houver.
   */
  /**
   * @description Limpa o formulário e reseta para modo de criação.
   */
  function _new() {
    _selectedId     = null
    _addrSelectedId = null
    _el('ivForm').reset()
    _el('ivAddrClear').hidden = true
    _closeAddrDropdown()
    InterferenceDetails.hide()
    _el('ivSave').textContent = 'Salvar'
  }

  function open() {
    if (!_mounted) return
    const { latitude, longitude } = SelectInterference.getValue()
    if (latitude && longitude) {
      _el('ivLat').value = latitude
      _el('ivLon').value = longitude
    }
    _loadDomains()
    _el('ivSave').textContent = _selectedId ? 'Editar' : 'Salvar'
    _container.classList.add('open')
    setTimeout(() => _el('ivSearch')?.focus(), 320)
  }

  /**
   * @description Fecha o drawer com animação de deslize.
   */
  function close() {
    _container?.classList.remove('open')
  }

  /** @returns {boolean} */
  function isMounted() { return _mounted }

  /** @description Limpa o formulário, o autocomplete de endereço e a lista. */
  function reset() {
    if (!_mounted) return
    _el('ivForm')?.reset()
    _addrSelectedId = null
    _el('ivAddrClear').hidden = true
    _closeAddrDropdown()
    _el('ivSearch').value = ''
    _el('ivListBody').innerHTML = ''
    _el('ivListEmpty').setAttribute('hidden', '')
    close()
  }

  function validate() { return true }
  function getValue()  { return {} }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, open, close, getValue, reset, validate, isMounted, setCoords }
})()
