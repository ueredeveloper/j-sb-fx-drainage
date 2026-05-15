/**
 * @file user-view.js
 * @description Drawer de cadastro de usuários. Apenas o formulário.
 * A seção de pesquisa/lista é delegada ao componente UserList.
 *
 * Eventos disparados:
 *  - `user-view:select` → usuário selecionado via UserList { id, label }.
 *  - `user-view:saved`  → novo usuário salvo com sucesso.
 *
 * Escuta:
 *  - `user-list:select` → preenche formulário e notifica SelectUser.
 */
const UserView = (() => {
  let _mounted      = false
  let _container    = null
  let _selectedId   = null
  let _lastSelected = null

  /**
   * @description Renderiza o drawer e monta UserList abaixo do formulário.
   * @param {HTMLElement} container
   */
  function mount(container) {
    _container = container
    _container.className = 'side-drawer'

    _container.innerHTML = `
      <div class="av-header">
        <button type="button" class="av-back-btn" id="uvBack">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <polyline points="15 18 9 12 15 6"/>
          </svg>
          Voltar
        </button>
        <span class="av-title">Cadastro de Usuário</span>
        <button type="button" class="btn btn-secondary av-new-btn" id="uvNew">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="16"/><line x1="8" y1="12" x2="16" y2="12"/>
          </svg>
          Novo
        </button>
        <button type="button" class="btn btn-primary" id="uvSave">Salvar</button>
      </div>

      <div class="av-content">

        <form id="uvForm" autocomplete="off">
          <fieldset class="form-section">
            <legend class="section-title">
              <span class="section-icon">👤</span> Usuário
            </legend>

            <div class="form-row">
              <div class="form-group grow">
                <label for="uvNome">Nome <span class="required">*</span></label>
                <input type="text" id="uvNome" name="uvNome"
                  placeholder="Nome completo" required>
              </div>
            </div>

            <div class="form-row">
              <div class="form-group grow">
                <label for="uvCpfCnpj">CPF / CNPJ <span class="required">*</span></label>
                <input type="text" id="uvCpfCnpj" name="uvCpfCnpj"
                  placeholder="000.000.000-00 ou 00.000.000/0000-00"
                  maxlength="18" required>
              </div>
            </div>

          </fieldset>
        </form>

        <div id="uvListMount"></div>

        <div id="uvUserDocs" hidden>
          <div class="doc-list-header" style="margin-top:12px">
            <span class="doc-list-title">Documentos do Usuário</span>
          </div>
          <div class="doc-list-wrap">
            <table class="doc-list-table" style="table-layout:fixed" aria-label="Documentos do usuário">
              <thead>
                <tr>
                  <th style="width:110px">Número</th>
                  <th style="width:90px">SEI</th>
                  <th>Processo</th>
                  <th style="width:160px">Endereço</th>
                </tr>
              </thead>
              <tbody id="uvDocListBody"></tbody>
            </table>
            <p class="doc-list-empty" id="uvDocListEmpty" hidden>Nenhum documento encontrado.</p>
          </div>
        </div>

      </div>
    `

    _bindEvents()
    UserList.mount(_el('uvListMount'))
    _mounted = true
  }

  /**
   * @description Registra os eventos do formulário e escuta seleção da lista.
   */
  function _bindEvents() {
    _el('uvBack').addEventListener('click', close)
    _el('uvNew').addEventListener('click', _new)
    _el('uvSave').addEventListener('click', _save)

    _el('uvCpfCnpj').addEventListener('input', (e) => {
      e.target.value = _maskCpfCnpj(e.target.value)
    })

    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && _container?.classList.contains('open')) close()
    })

    document.addEventListener('user-list:select', (e) => {
      const { id, nome, cpfCnpj, label } = e.detail
      _selectedId = id
      _el('uvSave').textContent  = 'Editar'
      _el('uvNome').value        = nome    || ''
      _el('uvCpfCnpj').value     = _maskCpfCnpj(cpfCnpj || '')
      _lastSelected = { userId: id, userLabel: nome || '', cpfCnpj: cpfCnpj || '' }
      document.dispatchEvent(new CustomEvent('user-view:select', { detail: { id, label } }))
      _loadUserDocs(id)
    })
  }

  /**
   * @description Busca e renderiza os documentos relacionados ao usuário selecionado.
   * @param {string|number} userId
   */
  async function _loadUserDocs(userId) {
    const panel = _el('uvUserDocs')
    const tbody = _el('uvDocListBody')
    const empty = _el('uvDocListEmpty')
    if (!panel) return

    panel.removeAttribute('hidden')
    tbody.innerHTML = '<tr><td colspan="4" style="text-align:center;color:var(--text-light);padding:8px">Carregando...</td></tr>'
    empty.setAttribute('hidden', '')

    try {
      const docs = await window.documentService.fetchByUserId(userId)
      if (!docs.length) {
        tbody.innerHTML = ''
        empty.removeAttribute('hidden')
        return
      }
      tbody.innerHTML = docs.map(d => `
        <tr class="doc-list-row">
          <td title="${d.numero || ''}">${d.numero || '—'}</td>
          <td>${d.numeroSei || '—'}</td>
          <td title="${d.processoNumero || ''}">${d.processoNumero || '—'}</td>
          <td title="${d.logradouro || ''}">${d.logradouro || '—'}</td>
        </tr>`).join('')
    } catch (err) {
      console.error('UserView: erro ao buscar documentos do usuário', err)
      tbody.innerHTML = ''
      empty.textContent = 'Erro ao carregar documentos.'
      empty.removeAttribute('hidden')
    }
  }

  /**
   * @description Valida e salva um usuário.
   */
  async function _save() {
    const form = _el('uvForm')
    if (!form.checkValidity()) { form.reportValidity(); return }

    const data = {
      nome:    _el('uvNome').value.trim(),
      cpfCnpj: _el('uvCpfCnpj').value.trim()
    }
    const payload = _selectedId ? { id: _selectedId, ...data } : data

    try {
      const raw   = await window.userService.save(payload)
      const saved = raw?.object ?? raw
      const savedId = saved?.id ?? _selectedId
      _lastSelected = { userId: savedId, userLabel: saved?.nome ?? data.nome, cpfCnpj: saved?.cpfCnpj ?? data.cpfCnpj }
      document.dispatchEvent(new CustomEvent('user-view:saved', { detail: saved }))
      _selectedId = null
      _el('uvSave').textContent = 'Salvar'
      form.reset()
      UserList.prependRow({
        id:      saved?.id      ?? savedId,
        nome:    saved?.nome    ?? data.nome,
        cpfCnpj: saved?.cpfCnpj ?? data.cpfCnpj
      })
    } catch (err) {
      console.error('UserView: erro ao salvar usuário', err)
      window.showToast?.('Erro ao salvar usuário. Tente novamente.', 'error')
    }
  }

  /**
   * @description Limpa o formulário e reseta para modo de criação.
   */
  function _new() {
    _selectedId = null
    _el('uvSave').textContent = 'Salvar'
    _el('uvForm').reset()
  }

  /**
   * @description Abre o drawer e pré-preenche com o usuário selecionado em SelectUser.
   */
  async function open() {
    if (!_mounted) return
    _lastSelected = null
    const { userId } = SelectUser.getValue()
    if (userId) {
      let r = SelectUser.getData()
      if (!r) {
        try { r = await window.userService.fetchById(userId) } catch { r = null }
      }
      if (r) {
        _selectedId            = userId
        _el('uvNome').value    = r.nome    || ''
        _el('uvCpfCnpj').value = _maskCpfCnpj(r.cpfCnpj || '')
      }
    }
    _el('uvSave').textContent = _selectedId ? 'Editar' : 'Salvar'
    _container.classList.add('open')
    setTimeout(() => _el('ulvSearch')?.focus(), 320)
  }

  /**
   * @description Fecha o drawer.
   */
  function close() {
    if (_lastSelected && SelectUser.isMounted()) {
      SelectUser.setValue(_lastSelected)
    }
    _container?.classList.remove('open')
  }

  /**
   * @description Limpa o formulário e a lista.
   */
  function reset() {
    if (!_mounted) return
    _el('uvForm')?.reset()
    UserList.reset()
    close()
  }

  /**
   * @description Aplica máscara de CPF (000.000.000-00) ou CNPJ (00.000.000/0000-00).
   * @param {string} raw
   * @returns {string}
   */
  function _maskCpfCnpj(raw) {
    let v = raw.replace(/\D/g, '')
    if (v.length <= 11) {
      v = v.replace(/(\d{3})(\d)/, '$1.$2')
           .replace(/(\d{3})(\d)/, '$1.$2')
           .replace(/(\d{3})(\d{1,2})$/, '$1-$2')
    } else {
      v = v.replace(/(\d{2})(\d)/, '$1.$2')
           .replace(/(\d{3})(\d)/, '$1.$2')
           .replace(/(\d{3})(\d)/, '$1/$2')
           .replace(/(\d{4})(\d{1,2})$/, '$1-$2')
    }
    return v
  }

  function validate()  { return true }
  function getValue()  { return {} }
  function isMounted() { return _mounted }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, open, close, getValue, reset, validate, isMounted }
})()
