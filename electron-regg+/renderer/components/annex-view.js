/**
 * @file annex-view.js
 * @description Painel lateral deslizante (drawer) para cadastro e seleção de anexos
 * (processos principais). Desliza da direita para a esquerda sobre o painel de formulário.
 *
 * Seções internas:
 *  1. Formulário de cadastro: processo principal (texto), processo e usuário.
 *  2. Pesquisa de anexos cadastrados com lista de resultados.
 *
 * Eventos disparados:
 *  - `annex-view:select` → usuário clica em um anexo da lista (fecha o drawer).
 *  - `annex-view:saved`  → novo anexo salvo com sucesso.
 *
 * Aberto via `AnnexView.open()` e fechado pelo botão Voltar ou tecla Escape.
 */
const AnnexView = (() => {
  let _mounted   = false
  let _container = null

  /**
   * @description Renderiza o drawer no container e registra os eventos.
   * O container deve ser o `#annex-drawer` no DOM.
   * @param {HTMLElement} container
   */
  function mount(container) {
    _container = container
    _container.className = 'side-drawer'

    _container.innerHTML = `
      <div class="av-header">
        <button type="button" class="av-back-btn" id="axBack">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <polyline points="15 18 9 12 15 6"/>
          </svg>
          Voltar
        </button>
        <span class="av-title">Cadastro de Processo Principal</span>
        <button type="button" class="btn btn-primary" id="axSave">Salvar Anexo</button>
      </div>

      <div class="av-content">

        <!-- Formulário de cadastro de novo anexo -->
        <form id="axForm" autocomplete="off">
          <fieldset class="form-section">
            <legend class="section-title">
              <span class="section-icon">🗂️</span> Processo Principal
            </legend>

            <div class="form-row">
              <div class="form-group grow">
                <label for="axProcPrincipal">Número <span class="required">*</span></label>
                <input type="text" id="axProcPrincipal" name="axProcPrincipal"
                  placeholder="Ex: 00197-00002342/2025-61" required>
              </div>
              <div class="form-group grow autocomplete-wrap" id="axProcWrap">
                <label for="axProcSearch">Processo</label>
                <div class="search-input-wrap">
                  <input type="text" id="axProcSearch" name="axProcSearch"
                    placeholder="Número do processo..."
                    autocomplete="off" aria-autocomplete="list"
                    aria-controls="axProcDropdown" aria-expanded="false">
                  <button type="button" id="axProcClear" class="search-clear"
                    title="Limpar" hidden>×</button>
                </div>
                <ul class="autocomplete-dropdown" id="axProcDropdown" role="listbox" hidden></ul>
              </div>
              <div class="form-group grow autocomplete-wrap" id="axUserWrap">
                <label for="axUserSearch">Usuário</label>
                <div class="search-input-wrap">
                  <input type="text" id="axUserSearch" name="axUserSearch"
                    placeholder="Nome ou CPF/CNPJ..."
                    autocomplete="off" aria-autocomplete="list"
                    aria-controls="axUserDropdown" aria-expanded="false">
                  <button type="button" id="axUserClear" class="search-clear"
                    title="Limpar" hidden>×</button>
                </div>
                <ul class="autocomplete-dropdown" id="axUserDropdown" role="listbox" hidden></ul>
              </div>
            </div>

          </fieldset>
        </form>

        <!-- Pesquisa e lista de anexos cadastrados -->
        <div class="form-section">
          <div class="doc-list-header">
            <span class="doc-list-title">Pesquisar Processos Principais</span>
          </div>
          <div class="doc-search-bar">
            <div class="form-group grow">
              <input type="text" id="axSearch"
                placeholder="Pesquisar por número do processo principal..."
                autocomplete="off">
            </div>
            <button type="button" id="axSearchBtn" class="btn btn-primary">Pesquisar</button>
          </div>
          <div class="doc-list-wrap">
            <table class="doc-list-table" aria-label="Lista de processos principais">
              <thead>
                <tr>
                  <th style="width:50%">Processo Principal</th>
                  <th style="width:30%">Processo</th>
                  <th style="width:20%">Usuário</th>
                </tr>
              </thead>
              <tbody id="axListBody"></tbody>
            </table>
            <p class="doc-list-empty" id="axListEmpty" hidden>Nenhum processo principal encontrado.</p>
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
    _el('axBack').addEventListener('click', close)
    _el('axSave').addEventListener('click', _save)

    _el('axSearchBtn').addEventListener('click', () => _searchList(_el('axSearch').value.trim()))
    _el('axSearch').addEventListener('keydown', (e) => {
      if (e.key === 'Enter') _searchList(_el('axSearch').value.trim())
    })

    _bindInlineSearch('axProcSearch', 'axProcDropdown', 'axProcClear', _searchProc)
    _bindInlineSearch('axUserSearch', 'axUserDropdown', 'axUserClear', _searchUser)

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
   * @description Busca processos para o campo processo via API.
   * @param {string} term
   */
  async function _searchProc(term) {
    try {
      const rows = await window.processService.fetchByKeyword(term)
      _renderInlineDropdown('axProcDropdown', 'axProcSearch', 'axProcClear',
        rows.map(r => ({ id: r.id, label: r.numero })))
    } catch (err) {
      console.error('AnnexView: erro ao buscar processos', err)
      _renderInlineDropdown('axProcDropdown', 'axProcSearch', 'axProcClear', [])
    }
  }

  /**
   * @description Busca usuários para o campo usuário via API.
   * @param {string} term
   */
  async function _searchUser(term) {
    try {
      const rows = await window.userService.fetchByKeyword(term)
      _renderInlineDropdown('axUserDropdown', 'axUserSearch', 'axUserClear',
        rows.map(r => ({ id: r.id, label: r.nome })))
    } catch (err) {
      console.error('AnnexView: erro ao buscar usuários', err)
      _renderInlineDropdown('axUserDropdown', 'axUserSearch', 'axUserClear', [])
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
   * @description Valida e salva um novo anexo.
   * TODO: conectar a window.documentService.saveAnnex(data).
   */
  function _save() {
    const form = _el('axForm')
    if (!form.checkValidity()) { form.reportValidity(); return }

    const data = {
      processPrincipal: _el('axProcPrincipal').value.trim(),
      processo:         _el('axProcSearch').value.trim(),
      usuario:          _el('axUserSearch').value.trim()
    }

    console.log('Salvar anexo:', data)
    document.dispatchEvent(new CustomEvent('annex-view:saved', { detail: data }))
    form.reset()
    _searchList('')
  }

  /**
   * @description Busca anexos (processos principais) cadastrados pelo número via API.
   * @param {string} term
   */
  async function _searchList(term) {
    try {
      const rows = await window.annexService.fetchByKeyword(term)
      _renderRows(rows)
    } catch (err) {
      console.error('AnnexView: erro ao buscar processos principais', err)
      _renderRows([])
    }
  }

  /**
   * @description Popula a tabela de processos principais com os resultados da busca.
   * A API retorna { id, numero, processos: [{id, numero, usuario}] }.
   * Cada processo do array gera uma linha separada na tabela.
   * @param {Array<Object>} rows
   */
  function _renderRows(rows) {
    const tbody = _el('axListBody')
    const empty = _el('axListEmpty')

    const flat = rows.flatMap(r => {
      const lista = Array.isArray(r.processos) && r.processos.length
        ? r.processos
        : [{ id: null, numero: '', usuario: {} }]
      return lista.map(p => ({
        annexId:    r.id,
        numero:     r.numero     || '',
        procNumero: p.numero     || '',
        userNome:   p.usuario?.nome || ''
      }))
    })

    if (!flat.length) {
      tbody.innerHTML = ''
      empty.removeAttribute('hidden')
      return
    }

    empty.setAttribute('hidden', '')
    tbody.innerHTML = flat.map(f => `
      <tr class="doc-list-row"
          data-id="${f.annexId}"
          data-label="${f.numero}"
          data-numero="${f.numero}"
          data-proc-numero="${f.procNumero}"
          data-usuario-nome="${f.userNome}">
        <td title="${f.numero}">${f.numero || '—'}</td>
        <td>${f.procNumero || '—'}</td>
        <td>${f.userNome   || '—'}</td>
      </tr>`).join('')

    tbody.querySelectorAll('.doc-list-row').forEach(tr =>
      tr.addEventListener('click', () => _pickAnnex(tr))
    )
  }

  /**
   * @description Seleciona um processo principal da lista, preenche o formulário e notifica o SelectAnnex.
   * O drawer permanece aberto para permitir nova seleção se necessário.
   * @param {HTMLTableRowElement} tr
   */
  function _pickAnnex(tr) {
    _el('axProcPrincipal').value = tr.dataset.numero    || ''

    _el('axProcSearch').value = tr.dataset.procNumero  || ''
    _el('axProcClear').hidden = !tr.dataset.procNumero

    _el('axUserSearch').value = tr.dataset.usuarioNome || ''
    _el('axUserClear').hidden = !tr.dataset.usuarioNome

    document.dispatchEvent(new CustomEvent('annex-view:select', {
      detail: { id: tr.dataset.id, label: tr.dataset.label }
    }))
  }

  /**
   * @description Abre o drawer com animação de deslize.
   */
  function open() {
    if (!_mounted) return
    _container.classList.add('open')
    setTimeout(() => _el('axSearch')?.focus(), 320)
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
    _el('axForm')?.reset()
    _el('axSearch').value = ''
    _el('axListBody').innerHTML = ''
    _el('axListEmpty').setAttribute('hidden', '')
    close()
  }

  function validate() { return true }
  function getValue()  { return {} }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, open, close, getValue, reset, validate, isMounted }
})()
