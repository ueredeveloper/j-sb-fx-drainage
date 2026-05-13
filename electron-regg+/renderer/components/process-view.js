/**
 * @file process-view.js
 * @description Drawer de cadastro de processos. Apenas o formulário.
 * A seção de pesquisa/lista é delegada ao componente ProcessList.
 *
 * Eventos disparados:
 *  - `process-view:select` → processo selecionado via ProcessList.
 *  - `process-view:saved`  → processo salvo com sucesso.
 *
 * Escuta:
 *  - `process-list:select` → preenche formulário e notifica SelectProcess.
 */
const ProcessView = (() => {
  let _mounted    = false
  let _container  = null
  let _selectedId = null

  /**
   * @description Renderiza o drawer e monta ProcessList abaixo do formulário.
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
        <button type="button" class="btn btn-secondary av-new-btn" id="pvNew">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="16"/><line x1="8" y1="12" x2="16" y2="12"/>
          </svg>
          Novo
        </button>
        <button type="button" class="btn btn-primary" id="pvSave">Salvar</button>
      </div>

      <div class="av-content">

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

        <div id="pvListMount"></div>

      </div>
    `

    _bindEvents()
    ProcessList.mount(_el('pvListMount'))
    _mounted = true
  }

  /**
   * @description Registra os eventos do formulário e escuta seleção da lista.
   */
  function _bindEvents() {
    _el('pvBack').addEventListener('click', close)
    _el('pvNew').addEventListener('click', _new)
    _el('pvSave').addEventListener('click', _save)

    _bindInlineSearch('pvAnexoSearch', 'pvAnexoDropdown', 'pvAnexoClear', _searchAnexo)
    _bindInlineSearch('pvUserSearch',  'pvUserDropdown',  'pvUserClear',  _searchUser)

    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && _container?.classList.contains('open')) close()
    })

    document.addEventListener('process-list:select', (e) => {
      const { id, label, numero, usuarioNome, anexoNumero } = e.detail
      _selectedId = id
      _el('pvSave').textContent      = 'Editar'
      _el('pvNum').value             = numero      || ''
      _el('pvUserSearch').value      = usuarioNome || ''
      _el('pvUserClear').hidden      = !usuarioNome
      _el('pvAnexoSearch').value     = anexoNumero || ''
      _el('pvAnexoClear').hidden     = !anexoNumero
      document.dispatchEvent(new CustomEvent('process-view:select', { detail: { id, label } }))
    })
  }

  /**
   * @description Configura um campo de busca inline com dropdown.
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
   */
  function _closeInlineDropdown(dropdownId, inputId) {
    _el(dropdownId)?.setAttribute('hidden', '')
    _el(inputId)?.setAttribute('aria-expanded', 'false')
  }

  /**
   * @description Busca anexos para o campo processo principal.
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
   * @description Busca usuários para o campo usuário.
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
   * @param {Array<{id, label}>} rows
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
   * @description Valida e salva um processo.
   */
  async function _save() {
    const form = _el('pvForm')
    if (!form.checkValidity()) { form.reportValidity(); return }

    const data = {
      numero:           _el('pvNum').value.trim(),
      processPrincipal: _el('pvAnexoSearch').value.trim(),
      usuario:          _el('pvUserSearch').value.trim()
    }
    const payload = _selectedId ? { id: _selectedId, ...data } : data

    try {
      const raw   = await window.processService.save(payload)
      const saved = raw?.object ?? raw
      document.dispatchEvent(new CustomEvent('process-view:saved', { detail: saved }))
      const savedId = _selectedId
      _selectedId = null
      _el('pvSave').textContent = 'Salvar'
      form.reset()
      _el('pvUserSearch').value  = ''
      _el('pvUserClear').hidden  = true
      _el('pvAnexoSearch').value = ''
      _el('pvAnexoClear').hidden = true
      ProcessList.prependRow({
        id:          saved?.id          ?? savedId,
        numero:      saved?.numero      ?? data.numero,
        usuarioNome: saved?.usuarioNome ?? data.usuario,
        anexoNumero: saved?.anexoNumero ?? data.processPrincipal
      })
    } catch (err) {
      console.error('ProcessView: erro ao salvar processo', err)
    }
  }

  /**
   * @description Limpa o formulário e reseta para modo de criação.
   */
  function _new() {
    _selectedId = null
    _el('pvSave').textContent = 'Salvar'
    _el('pvForm').reset()
    _el('pvUserSearch').value  = ''
    _el('pvUserClear').hidden  = true
    _el('pvAnexoSearch').value = ''
    _el('pvAnexoClear').hidden = true
  }

  /**
   * @description Abre o drawer e pré-preenche com o processo selecionado em SelectProcess.
   */
  async function open() {
    if (!_mounted) return
    const { processId } = SelectProcess.getValue()
    if (processId) {
      let r = SelectProcess.getData()
      if (!r) {
        try { r = await window.processService.fetchById(processId) } catch { r = null }
      }
      if (r) {
        _selectedId                = processId
        _el('pvNum').value         = r.numero       || ''
        _el('pvUserSearch').value  = r.usuarioNome  || ''
        _el('pvUserClear').hidden  = !r.usuarioNome
        _el('pvAnexoSearch').value = r.anexoNumero  || ''
        _el('pvAnexoClear').hidden = !r.anexoNumero
      }
    }
    _el('pvSave').textContent = _selectedId ? 'Editar' : 'Salvar'
    _container.classList.add('open')
    setTimeout(() => _el('plvSearch')?.focus(), 320)
  }

  /**
   * @description Fecha o drawer.
   */
  function close() {
    _container?.classList.remove('open')
  }

  /**
   * @description Limpa o formulário e a lista.
   */
  function reset() {
    if (!_mounted) return
    _el('pvForm')?.reset()
    ProcessList.reset()
    close()
  }

  function validate()  { return true }
  function getValue()  { return {} }
  function isMounted() { return _mounted }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, open, close, getValue, reset, validate, isMounted }
})()
