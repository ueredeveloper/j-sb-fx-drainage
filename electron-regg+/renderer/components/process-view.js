/**
 * @file process-view.js
 * @description Painel lateral deslizante (drawer) para cadastro e seleção de processos.
 * Desliza da direita para a esquerda sobre o painel de formulário.
 *
 * Seções internas:
 *  1. Formulário de cadastro: número do processo, processo principal e usuário.
 *  2. Pesquisa de processos cadastrados com lista de resultados.
 *
 * Eventos disparados:
 *  - `process-view:select` → usuário clica em um processo da lista (fecha o drawer).
 *  - `process-view:saved`  → novo processo salvo com sucesso.
 *
 * Aberto via `ProcessView.open()` e fechado pelo botão Voltar ou tecla Escape.
 */
const ProcessView = (() => {
  let _mounted   = false
  let _container = null

  /**
   * @description Renderiza o drawer no container e registra os eventos.
   * O container deve ser o `#process-drawer` no DOM.
   * @param {HTMLElement} container
   */
  function mount(container) {
    _container = container
    _container.className = 'side-drawer'

    _container.innerHTML = `
      <div class="av-header">
        <button type="button" class="av-back-btn" id="pvBack">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <polyline points="15 18 9 12 15 6"/>
          </svg>
          Voltar
        </button>
        <span class="av-title">Cadastro de Processo</span>
        <button type="button" class="btn btn-primary" id="pvSave">Salvar Processo</button>
      </div>

      <div class="av-content">

        <!-- Formulário de cadastro de novo processo -->
        <form id="pvForm" autocomplete="off">
          <fieldset class="form-section">
            <legend class="section-title">
              <span class="section-icon">📁</span> Processo
            </legend>

            <div class="form-row">
              <div class="form-group grow">
                <label for="pvNum">Número <span class="required">*</span></label>
                <input type="text" id="pvNum" name="pvNum"
                  placeholder="Ex: 0197-000477/2015" required>
              </div>
              <div class="form-group grow autocomplete-wrap" id="pvAnexoWrap">
                <label for="pvAnexoSearch">Processo Principal</label>
                <div class="search-input-wrap">
                  <input type="text" id="pvAnexoSearch" name="pvAnexoSearch"
                    placeholder="Pesquisar anexo..."
                    autocomplete="off" aria-autocomplete="list"
                    aria-controls="pvAnexoDropdown" aria-expanded="false">
                  <button type="button" id="pvAnexoClear" class="search-clear"
                    title="Limpar" hidden>×</button>
                </div>
                <ul class="autocomplete-dropdown" id="pvAnexoDropdown" role="listbox" hidden></ul>
              </div>
              <div class="form-group grow autocomplete-wrap" id="pvUserWrap">
                <label for="pvUserSearch">Usuário <span class="required">*</span></label>
                <div class="search-input-wrap">
                  <input type="text" id="pvUserSearch" name="pvUserSearch"
                    placeholder="Nome ou CPF/CNPJ..."
                    autocomplete="off" aria-autocomplete="list"
                    aria-controls="pvUserDropdown" aria-expanded="false">
                  <button type="button" id="pvUserClear" class="search-clear"
                    title="Limpar" hidden>×</button>
                </div>
                <ul class="autocomplete-dropdown" id="pvUserDropdown" role="listbox" hidden></ul>
              </div>
            </div>

          </fieldset>
        </form>

        <!-- Pesquisa e lista de processos cadastrados -->
        <div class="form-section">
          <div class="doc-list-header">
            <span class="doc-list-title">Pesquisar Processos</span>
          </div>
          <div class="doc-search-bar">
            <div class="form-group grow">
              <input type="text" id="pvSearch"
                placeholder="Pesquisar por número ou nome de usuário..."
                autocomplete="off">
            </div>
            <button type="button" id="pvSearchBtn" class="btn btn-primary">Pesquisar</button>
          </div>
          <div class="doc-list-wrap">
            <table class="doc-list-table" aria-label="Lista de processos">
              <thead>
                <tr>
                  <th style="width:45%">Número</th>
                  <th style="width:30%">Usuário</th>
                  <th style="width:25%">Processo Principal</th>
                </tr>
              </thead>
              <tbody id="pvListBody"></tbody>
            </table>
            <p class="doc-list-empty" id="pvListEmpty" hidden>Nenhum processo encontrado.</p>
          </div>
        </div>

      </div>
    `

    _bindEvents()
    _mounted = true
  }

  /**
   * @description Registra todos os eventos do drawer.
   */
  function _bindEvents() {
    _el('pvBack').addEventListener('click', close)
    _el('pvSave').addEventListener('click', _save)

    _el('pvSearchBtn').addEventListener('click', () => _searchList(_el('pvSearch').value.trim()))
    _el('pvSearch').addEventListener('keydown', (e) => {
      if (e.key === 'Enter') _searchList(_el('pvSearch').value.trim())
    })

    _bindInlineSearch('pvAnexoSearch', 'pvAnexoDropdown', 'pvAnexoClear', _searchAnexo)
    _bindInlineSearch('pvUserSearch',  'pvUserDropdown',  'pvUserClear',  _searchUser)

    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && _container?.classList.contains('open')) close()
    })
  }

  /**
   * @description Configura um campo de busca inline com dropdown simples.
   * @param {string} inputId
   * @param {string} dropdownId
   * @param {string} clearId
   * @param {Function} searchFn
   */
  function _bindInlineSearch(inputId, dropdownId, clearId, searchFn) {
    const input = _el(inputId)
    const clear = _el(clearId)

    input.addEventListener('input', (e) => {
      const term = e.target.value.trim()
      clear.hidden = term.length === 0
      if (term.length < 2) { _closeInlineDropdown(dropdownId, inputId); return }
      searchFn(term)
    })
    clear.addEventListener('click', () => {
      input.value = ''
      clear.hidden = true
      _closeInlineDropdown(dropdownId, inputId)
    })
    document.addEventListener('click', (e) => {
      if (!e.target.closest(`#${inputId}`) && !e.target.closest(`#${dropdownId}`))
        _closeInlineDropdown(dropdownId, inputId)
    })
  }

  /**
   * @description Fecha um dropdown inline.
   * @param {string} dropdownId
   * @param {string} inputId
   */
  function _closeInlineDropdown(dropdownId, inputId) {
    _el(dropdownId)?.setAttribute('hidden', '')
    _el(inputId)?.setAttribute('aria-expanded', 'false')
  }

  /**
   * @description Busca anexos (processos principais) para o campo processo principal via API.
   * @param {string} term
   */
  async function _searchAnexo(term) {
    try {
      const rows = await window.annexService.fetchByKeyword(term)
      _renderInlineDropdown('pvAnexoDropdown', 'pvAnexoSearch', 'pvAnexoClear',
        rows.map(r => ({ id: r.id, label: r.numero })))
    } catch (err) {
      console.error('ProcessView: erro ao buscar anexos', err)
      _renderInlineDropdown('pvAnexoDropdown', 'pvAnexoSearch', 'pvAnexoClear', [])
    }
  }

  /**
   * @description Busca usuários para o campo usuário via API.
   * @param {string} term
   */
  async function _searchUser(term) {
    try {
      const rows = await window.userService.fetchByKeyword(term)
      _renderInlineDropdown('pvUserDropdown', 'pvUserSearch', 'pvUserClear',
        rows.map(r => ({ id: r.id, label: r.nome })))
    } catch (err) {
      console.error('ProcessView: erro ao buscar usuários', err)
      _renderInlineDropdown('pvUserDropdown', 'pvUserSearch', 'pvUserClear', [])
    }
  }

  /**
   * @description Renderiza itens em um dropdown inline.
   * @param {string} dropdownId
   * @param {string} inputId
   * @param {string} clearId
   * @param {Array<{id: number, label: string}>} rows
   */
  function _renderInlineDropdown(dropdownId, inputId, clearId, rows) {
    const list = _el(dropdownId)
    list.innerHTML = rows.length
      ? rows.map(r => `<li class="autocomplete-item" role="option"
            data-id="${r.id}" data-label="${r.label}">
          <span class="autocomplete-item__main">${r.label}</span>
        </li>`).join('')
      : '<li class="autocomplete-empty">Nenhum resultado</li>'

    list.removeAttribute('hidden')
    _el(inputId).setAttribute('aria-expanded', 'true')

    list.querySelectorAll('.autocomplete-item').forEach(li => {
      li.addEventListener('click', () => {
        _el(inputId).value = li.dataset.label
        _el(clearId).hidden = false
        list.setAttribute('hidden', '')
      })
    })
  }

  /**
   * @description Valida e salva um novo processo.
   * TODO: conectar a window.documentService.saveProcess(data).
   */
  function _save() {
    const form = _el('pvForm')
    if (!form.checkValidity()) { form.reportValidity(); return }

    const data = {
      numero:          _el('pvNum').value.trim(),
      processPrincipal: _el('pvAnexoSearch').value.trim(),
      usuario:         _el('pvUserSearch').value.trim()
    }

    console.log('Salvar processo:', data)
    document.dispatchEvent(new CustomEvent('process-view:saved', { detail: data }))
    form.reset()
    _searchList('')
  }

  /**
   * @description Busca processos cadastrados pelo número via API.
   * @param {string} term
   */
  async function _searchList(term) {
    try {
      const rows = await window.processService.fetchByKeyword(term)
      _renderRows(rows)
    } catch (err) {
      console.error('ProcessView: erro ao buscar processos', err)
      _renderRows([])
    }
  }

  /**
   * @description Popula a tabela de processos com os resultados da busca.
   * Campos esperados: { id, numero, usuarioId, usuarioNome, anexoId, anexoNumero } (normalizados pelo serviço).
   * @param {Array<Object>} rows
   */
  function _renderRows(rows) {
    const tbody = _el('pvListBody')
    const empty = _el('pvListEmpty')

    if (!rows.length) {
      tbody.innerHTML = ''
      empty.removeAttribute('hidden')
      return
    }

    empty.setAttribute('hidden', '')
    tbody.innerHTML = rows.map(r => `
      <tr class="doc-list-row"
          data-id="${r.id}"
          data-label="${r.numero}"
          data-numero="${r.numero || ''}"
          data-usuario-nome="${r.usuarioNome || ''}"
          data-anexo-numero="${r.anexoNumero || ''}">
        <td title="${r.numero}">${r.numero}</td>
        <td>${r.usuarioNome || '—'}</td>
        <td>${r.anexoNumero || '—'}</td>
      </tr>
    `).join('')

    tbody.querySelectorAll('.doc-list-row').forEach(tr =>
      tr.addEventListener('click', () => _pickProcess(tr))
    )
  }

  /**
   * @description Seleciona um processo da lista, preenche o formulário e notifica o SelectProcess.
   * O drawer permanece aberto para permitir nova seleção se necessário.
   * @param {HTMLTableRowElement} tr
   */
  function _pickProcess(tr) {
    _el('pvNum').value = tr.dataset.numero || ''

    _el('pvUserSearch').value = tr.dataset.usuarioNome || ''
    _el('pvUserClear').hidden = !tr.dataset.usuarioNome

    _el('pvAnexoSearch').value = tr.dataset.anexoNumero || ''
    _el('pvAnexoClear').hidden = !tr.dataset.anexoNumero

    document.dispatchEvent(new CustomEvent('process-view:select', {
      detail: { id: tr.dataset.id, label: tr.dataset.label }
    }))
  }

  /**
   * @description Abre o drawer com animação de deslize.
   */
  function open() {
    if (!_mounted) return
    _container.classList.add('open')
    setTimeout(() => _el('pvSearch')?.focus(), 320)
  }

  /**
   * @description Fecha o drawer com animação de deslize.
   */
  function close() {
    _container?.classList.remove('open')
  }

  /** @returns {boolean} */
  function isMounted() { return _mounted }

  /** @description Limpa o formulário e a lista. */
  function reset() {
    if (!_mounted) return
    _el('pvForm')?.reset()
    _el('pvSearch').value = ''
    _el('pvListBody').innerHTML = ''
    _el('pvListEmpty').setAttribute('hidden', '')
    close()
  }

  function validate() { return true }
  function getValue()  { return {} }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, open, close, getValue, reset, validate, isMounted }
})()
