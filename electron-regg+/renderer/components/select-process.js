/**
 * @file select-process.js
 * @description Componente de seleção pesquisável de processo.
 * A busca é feita pelo número na tabela `processo`.
 * Mínimo de 2 caracteres para ativar a pesquisa.
 * TODO: conectar ao banco via `window.db.searchProcess(term)`.
 */
const SelectProcess = (() => {
  let _mounted      = false
  let _selectedId   = null
  let _selectedData = null
  let _activeIdx    = -1
  let _lastRows     = []

  /**
   * @description Renderiza o componente e registra os eventos.
   * @param {HTMLElement} container
   */
  function mount(container) {
    container.innerHTML = `
      <fieldset class="form-section form-section--stretch">
        <legend class="section-title">
          <span class="section-icon">📁</span> Processo
        </legend>
        <div class="form-row">
          <button
            type="button"
            id="sProcOpenDrawer"
            class="btn-drawer"
            title="Cadastrar novo processo"
            aria-label="Cadastrar novo processo"
          >
            <!-- Lucide: file-plus-2 -->
            <svg xmlns="http://www.w3.org/2000/svg" width="17" height="17" viewBox="0 0 24 24"
                 fill="none" stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
              <path d="M4 22h14a2 2 0 0 0 2-2V7l-5-5H6a2 2 0 0 0-2 2v4"/>
              <path d="M14 2v4a2 2 0 0 0 2 2h4"/>
              <path d="M3 15h6"/>
              <path d="M6 12v6"/>
            </svg>
          </button>
          <div class="form-group grow autocomplete-wrap" id="sProcWrap">
            <label for="sProcSearch">Número <span class="required">*</span></label>
            <div class="search-input-wrap">
              <input
                type="text"
                id="sProcSearch"
                name="sProcSearch"
                placeholder="Ex: 0197-000477/2015"
                autocomplete="off"
                aria-autocomplete="list"
                aria-controls="sProcDropdown"
                aria-expanded="false"
              >
              <button type="button" id="sProcClear" class="search-clear" title="Limpar seleção" hidden>×</button>
            </div>
            <ul class="autocomplete-dropdown" id="sProcDropdown" role="listbox" hidden></ul>
          </div>
        </div>
      </fieldset>
    `
    _bindEvents()
    _mounted = true
  }

  /**
   * @description Registra eventos de input, teclado e clique fora do componente.
   */
  function _bindEvents() {
    _el('sProcSearch').addEventListener('input',   _onInput)
    _el('sProcSearch').addEventListener('keydown', _onKeydown)
    _el('sProcSearch').addEventListener('focus',   () => {
      if (_el('sProcSearch').value.trim().length >= 2) _search(_el('sProcSearch').value.trim())
    })
    _el('sProcClear').addEventListener('click', reset)
    _el('sProcOpenDrawer').addEventListener('click', () => ProcessView.open())

    /* Quando o usuário seleciona um processo dentro do ProcessView */
    document.addEventListener('process-view:select', (e) => {
      if (!_mounted) return
      _select({ id: e.detail.id, label: e.detail.label })
    })

    document.addEventListener('click', (e) => {
      if (!e.target.closest('#sProcWrap')) _closeDropdown()
    })
  }

  /**
   * @description Aciona a busca após mínimo de 2 caracteres.
   * @param {Event} e
   */
  function _onInput(e) {
    const term = e.target.value.trim()
    _selectedId = null
    _el('sProcClear').hidden = term.length === 0
    if (term.length < 2) { _closeDropdown(); return }
    _search(term)
  }

  /**
   * @description Navegação por teclado: setas, Enter e Escape.
   * @param {KeyboardEvent} e
   */
  function _onKeydown(e) {
    const items = _el('sProcDropdown').querySelectorAll('.autocomplete-item')
    if (e.key === 'ArrowDown')  { e.preventDefault(); _activeIdx = Math.min(_activeIdx + 1, items.length - 1); _highlight(items) }
    else if (e.key === 'ArrowUp')    { e.preventDefault(); _activeIdx = Math.max(_activeIdx - 1, 0);               _highlight(items) }
    else if (e.key === 'Enter' && _activeIdx >= 0) { e.preventDefault(); items[_activeIdx]?.click() }
    else if (e.key === 'Escape') _closeDropdown()
  }

  /**
   * @description Marca visualmente o item ativo.
   * @param {NodeList} items
   */
  function _highlight(items) {
    items.forEach((li, i) => li.classList.toggle('autocomplete-item--active', i === _activeIdx))
    items[_activeIdx]?.scrollIntoView({ block: 'nearest' })
  }

  /**
   * @description Busca processos pelo número via API.
   * @param {string} term
   */
  async function _search(term) {
    try {
      const rows = await window.processService.fetchByKeyword(term)
      _renderDropdown(rows)
    } catch (err) {
      console.error('SelectProcess: erro ao buscar processos', err)
      _renderDropdown([])
    }
  }

  /**
   * @description Popula o dropdown com os resultados da busca.
   * @param {Array<{id: number, numero: string}>} rows
   */
  function _renderDropdown(rows) {
    const list = _el('sProcDropdown')
    _activeIdx = -1
    _lastRows  = rows
    list.innerHTML = rows.length
      ? rows.map(r => `
          <li class="autocomplete-item" role="option"
              data-id="${r.id}"
              data-label="${r.numero}">
            <span class="autocomplete-item__main">${r.numero}</span>
          </li>`).join('')
      : '<li class="autocomplete-empty">Nenhum resultado</li>'

    list.removeAttribute('hidden')
    _el('sProcSearch').setAttribute('aria-expanded', 'true')

    list.querySelectorAll('.autocomplete-item').forEach(li => {
      li.addEventListener('click', () => {
        const data = _lastRows.find(r => String(r.id) === li.dataset.id) ?? null
        _select({ id: li.dataset.id, label: li.dataset.label }, data)
      })
      li.addEventListener('mouseenter', () => {
        _activeIdx = [...list.children].indexOf(li)
        _highlight(list.querySelectorAll('.autocomplete-item'))
      })
    })
  }

  /**
   * @description Confirma a seleção e fecha o dropdown.
   * @param {{ id: string, label: string }} item
   */
  function _select(item, data = null) {
    _selectedId   = item.id
    _selectedData = data
    _el('sProcSearch').value = item.label
    _el('sProcClear').hidden = false
    _closeDropdown()
  }

  /** @description Fecha e limpa o dropdown. */
  function _closeDropdown() {
    const list = _el('sProcDropdown')
    if (list) { list.setAttribute('hidden', ''); list.innerHTML = '' }
    _el('sProcSearch')?.setAttribute('aria-expanded', 'false')
    _activeIdx = -1
  }

  /** @returns {{ processId: string|null }} */
  function getValue() { return { processId: _selectedId } }

  /**
   * @param {{ processId?: string, processLabel?: string }} data
   */
  function setValue(data = {}) {
    _selectedId   = data.processId ?? null
    _selectedData = data.processId ? {
      id:          data.processId,
      numero:      data.processLabel ?? '',
      usuarioNome: data.usuarioNome  ?? '',
      anexoNumero: data.anexoNumero  ?? ''
    } : null
    const input = _el('sProcSearch')
    if (input) { input.value = data.processLabel ?? ''; _el('sProcClear').hidden = !data.processLabel }
  }

  /** @description Limpa a seleção e o campo de busca. */
  function reset() {
    if (!_mounted) return
    _selectedId   = null
    _selectedData = null
    const input = _el('sProcSearch')
    if (input) input.value = ''
    _el('sProcClear').hidden = true
    _closeDropdown()
  }

  /** @returns {boolean} */
  function validate() { return _selectedId !== null }

  /** @returns {boolean} */
  function isMounted() { return _mounted }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  /** @returns {string} Label do processo atualmente selecionado. */
  function getLabel() { return _el('sProcSearch')?.value ?? '' }

  /** @returns {Object|null} Objeto completo do processo selecionado. */
  function getData() { return _selectedData }

  return { mount, getValue, getLabel, getData, setValue, reset, validate, isMounted }
})()
