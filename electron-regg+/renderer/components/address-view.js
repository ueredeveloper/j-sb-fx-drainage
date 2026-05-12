/**
 * @file address-view.js
 * @description Painel lateral deslizante (drawer) para cadastro e seleção de endereços.
 * Desliza da direita para a esquerda sobre o painel de formulário.
 *
 * Seções internas:
 *  1. Formulário de cadastro: logradouro, bairro, cidade, CEP, estado.
 *  2. Pesquisa de endereços existentes com lista de resultados.
 *
 * Eventos disparados:
 *  - `address-view:select`  → usuário clica em um endereço da lista (fecha o drawer).
 *  - `address-view:saved`   → usuário salva um novo endereço.
 *
 * Aberto via `AddressView.open()` e fechado pelo botão Voltar ou tecla Escape.
 */
const AddressView = (() => {
  let _mounted     = false
  let _container   = null
  let _selectedId  = null
  let _estadoList  = []

  /**
   * @description Renderiza o drawer no container e registra os eventos.
   * O container deve ser o `#address-drawer` no DOM.
   * @param {HTMLElement} container
   */
  function mount(container) {
    _container = container
    _container.className = 'side-drawer'

    _container.innerHTML = `
      <div class="av-header">
        <button type="button" class="av-back-btn" id="avBack">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <polyline points="15 18 9 12 15 6"/>
          </svg>
          Voltar
        </button>
        <span class="av-title">Cadastro de Endereço</span>
        <button type="button" class="btn btn-secondary av-new-btn" id="avNew">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="16"/><line x1="8" y1="12" x2="16" y2="12"/>
          </svg>
          Novo
        </button>
        <button type="button" class="btn btn-primary" id="avSave">Salvar</button>
      </div>

      <div class="av-content">

        <!-- Formulário de cadastro de novo endereço -->
        <form id="avForm" autocomplete="off">
          <fieldset class="form-section">
            <legend class="section-title">
              <span class="section-icon">🏠</span> Endereço
            </legend>
            <div class="form-row">
              <div class="form-group grow">
                <label for="avLogradouro">Logradouro <span class="required">*</span></label>
                <input type="text" id="avLogradouro" name="avLogradouro"
                  placeholder="Ex: Rodeador, Gleba 01, Lotes 076/077" required>
              </div>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label for="avCep">CEP</label>
                <input type="text" id="avCep" name="avCep" placeholder="00000-000" maxlength="9">
              </div>
              <div class="form-group grow">
                <label for="avBairro">Bairro</label>
                <input type="text" id="avBairro" name="avBairro" placeholder="Bairro">
              </div>
              <div class="form-group grow">
                <label for="avCidade">Cidade <span class="required">*</span></label>
                <input type="text" id="avCidade" name="avCidade" placeholder="Cidade" required>
              </div>
              <div class="form-group narrow">
                <label for="avEstado">UF</label>
                <select id="avEstado" name="avEstado">
                  <option value="">UF</option>
                </select>
              </div>
            </div>
          </fieldset>
        </form>

        <!-- Pesquisa e lista de endereços cadastrados -->
        <div class="form-section">
          <div class="doc-list-header">
            <span class="doc-list-title">Pesquisar Endereços</span>
          </div>
          <div class="doc-search-bar">
            <div class="form-group grow">
              <input type="text" id="avSearch"
                placeholder="Pesquisar por logradouro ou cidade..."
                autocomplete="off">
            </div>
            <button type="button" id="avSearchBtn" class="btn btn-primary">Pesquisar</button>
          </div>
          <div class="doc-list-wrap">
            <table class="doc-list-table" style="table-layout:fixed" aria-label="Lista de endereços">
              <thead>
                <tr>
                  <th style="width:38%">Logradouro</th>
                  <th style="width:19%">Bairro</th>
                  <th style="width:22%">Cidade</th>
                  <th style="width:12%">UF</th>
                  <th style="width:44px"></th>
                </tr>
              </thead>
              <tbody id="avListBody"></tbody>
            </table>
            <p class="doc-list-empty" id="avListEmpty" hidden>Nenhum endereço encontrado.</p>
          </div>
        </div>

      </div>
    `

    _bindEvents()
    _mounted = true
    _loadEstados()
  }

  /**
   * @description Carrega a lista de estados da API e popula o select de UF.
   */
  async function _loadEstados() {
    try {
      _estadoList = await window.estadoService.fetchAll()
    } catch (err) {
      console.error('AddressView: erro ao carregar estados', err)
      _estadoList = []
    }
    const sel = _el('avEstado')
    if (!sel || !_estadoList.length) return
    sel.innerHTML = '<option value="">UF</option>' +
      _estadoList.map(e => `<option value="${e.id}">${e.descricao}</option>`).join('')
  }

  /**
   * @description Registra todos os eventos do drawer.
   */
  function _bindEvents() {
    /* Botão Voltar */
    _el('avBack').addEventListener('click', close)
    _el('avNew').addEventListener('click', _new)
    _el('avSave').addEventListener('click', _save)

    /* Pesquisa na lista */
    _el('avSearchBtn').addEventListener('click', () => _searchList(_el('avSearch').value.trim()))
    _el('avSearch').addEventListener('keydown', (e) => {
      if (e.key === 'Enter') _searchList(_el('avSearch').value.trim())
    })

    /* Máscara de CEP */
    _el('avCep').addEventListener('input', (e) => {
      let v = e.target.value.replace(/\D/g, '')
      v = v.replace(/(\d{5})(\d)/, '$1-$2')
      e.target.value = v
    })

    /* Escape fecha o drawer */
    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && _container?.classList.contains('open')) close()
    })
  }

  /**
   * @description Valida e salva um novo endereço.
   * Dispara `address-view:saved` com os dados para que o banco possa persistir.
   * TODO: conectar a window.db.insertAddress(data).
   */
  async function _save() {
    const form = _el('avForm')
    if (!form.checkValidity()) { form.reportValidity(); return }

    const sel      = _el('avEstado')
    const estadoId = sel.value ? Number(sel.value) : null
    const estado   = sel.options[sel.selectedIndex]?.text ?? ''

    const formData = {
      logradouro: _el('avLogradouro').value.trim(),
      bairro:     _el('avBairro').value.trim(),
      cidade:     _el('avCidade').value.trim(),
      cep:        _el('avCep').value.trim(),
      estado,
      estadoId
    }
    const payload = _selectedId ? { id: _selectedId, ...formData } : formData

    try {
      const raw  = await window.addressService.save(payload)
      const saved = raw?.object ?? raw
      document.dispatchEvent(new CustomEvent('address-view:saved', { detail: saved }))
      _selectedId = null
      _el('avSave').textContent = 'Salvar'
      form.reset()
      _prependRow({
        id:         saved?.id         ?? payload.id,
        logradouro: saved?.logradouro ?? formData.logradouro,
        bairro:     saved?.bairro     ?? formData.bairro,
        cidade:     saved?.cidade     ?? formData.cidade,
        cep:        saved?.cep        ?? formData.cep,
        estado:     saved?.estado     ?? formData.estado,
        estadoId:   saved?.estadoId   ?? formData.estadoId
      })
    } catch (err) {
      console.error('AddressView: erro ao salvar endereço', err)
    }
  }

  /**
   * @description Insere ou move um endereço para o topo da tabela e destaca com animação.
   * @param {Object} r - Endereço normalizado.
   */
  function _prependRow(r) {
    if (!r?.id) return
    const tbody = _el('avListBody')
    const empty = _el('avListEmpty')

    tbody.querySelector(`tr[data-id="${r.id}"]`)?.remove()

    const tr = document.createElement('tr')
    tr.className          = 'doc-list-row'
    tr.dataset.id         = String(r.id)
    tr.dataset.logradouro = r.logradouro || ''
    tr.dataset.bairro     = r.bairro     || ''
    tr.dataset.cidade     = r.cidade     || ''
    tr.dataset.cep        = r.cep        || ''
    tr.dataset.estado     = r.estado     || ''
    tr.dataset.estadoId   = String(r.estadoId || '')

    tr.innerHTML = `
      <td title="${r.logradouro}">${r.logradouro || '—'}</td>
      <td>${r.bairro || '—'}</td>
      <td>${r.cidade || '—'}</td>
      <td>${r.estado || '—'}</td>
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
    `

    tbody.prepend(tr)
    empty.setAttribute('hidden', '')

    tr.addEventListener('click', () => _pickAddress(tr))
    tr.querySelector('.doc-list-delete-btn').addEventListener('click', (e) => {
      e.stopPropagation(); _deleteRow(tr)
    })

    void tr.offsetWidth
    tr.classList.add('doc-list-row--flash')
  }

  /**
   * @description Pesquisa endereços pelo logradouro ou cidade via API.
   * @param {string} term
   */
  async function _searchList(term) {
    try {
      const rows = await window.addressService.fetchByKeyword(term)
      _renderRows(rows)
    } catch (err) {
      console.error('AddressView: erro ao buscar endereços', err)
      _renderRows([])
    }
  }

  /**
   * @description Popula a tabela de endereços com os resultados da busca.
   * Armazena os dados completos em `_rows` para preencher o formulário ao selecionar.
   * @param {Array<{id: number, logradouro: string, bairro: string, cidade: string, cep: string, estado: string}>} rows
   */
  function _renderRows(rows) {
    const tbody = _el('avListBody')
    const empty = _el('avListEmpty')

    if (!rows.length) {
      tbody.innerHTML = ''
      empty.removeAttribute('hidden')
      return
    }

    empty.setAttribute('hidden', '')
    tbody.innerHTML = rows.map(r => `
      <tr class="doc-list-row"
          data-id="${r.id}"
          data-logradouro="${r.logradouro || ''}"
          data-bairro="${r.bairro || ''}"
          data-cidade="${r.cidade || ''}"
          data-cep="${r.cep || ''}"
          data-estado="${r.estado || ''}"
          data-estado-id="${r.estadoId || ''}">
        <td title="${r.logradouro}">${r.logradouro || '—'}</td>
        <td>${r.bairro || '—'}</td>
        <td>${r.cidade || '—'}</td>
        <td>${r.estado || '—'}</td>
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
      tr.addEventListener('click', () => _pickAddress(tr))
    )
    tbody.querySelectorAll('.doc-list-delete-btn').forEach(btn =>
      btn.addEventListener('click', (e) => { e.stopPropagation(); _deleteRow(btn.closest('tr')) })
    )
  }

  /**
   * @description Remove um endereço pelo id.
   * @param {HTMLTableRowElement} tr
   */
  async function _deleteRow(tr) {
    const id = tr.dataset.id
    if (!confirm('Confirmar exclusão do endereço?')) return
    try {
      await window.addressService.deleteById(id)
      document.dispatchEvent(new CustomEvent('address-view:deleted', { detail: { id } }))
      tr.remove()
    } catch (err) {
      console.error('AddressView: erro ao excluir endereço', err)
    }
  }

  /**
   * @description Seleciona um endereço da lista, preenche o formulário e notifica o SelectAddress.
   * O drawer permanece aberto. O evento `address-view:select` é capturado pelo SelectAddress.
   * @param {HTMLTableRowElement} tr
   */
  function _pickAddress(tr) {
    _selectedId               = tr.dataset.id
    _el('avSave').textContent = 'Editar'
    _el('avLogradouro').value = tr.dataset.logradouro || ''
    _el('avCep').value        = tr.dataset.cep        || ''
    _el('avBairro').value     = tr.dataset.bairro     || ''
    _el('avCidade').value     = tr.dataset.cidade     || ''
    _el('avEstado').value     = tr.dataset.estadoId   || ''

    document.dispatchEvent(new CustomEvent('address-view:select', {
      detail: {
        id:    tr.dataset.id,
        label: `${tr.dataset.logradouro} — ${tr.dataset.cidade}/${tr.dataset.estado}`
      }
    }))
  }

  /**
   * @description Abre o drawer com animação de deslize e pré-preenche o formulário
   * com os dados do endereço atualmente selecionado em SelectAddress, se houver.
   */
  /**
   * @description Limpa o formulário e reseta para modo de criação.
   */
  function _new() {
    _selectedId = null
    _el('avSave').textContent = 'Salvar'
    _el('avForm').reset()
  }

  async function open() {
    if (!_mounted) return
    const { addressId } = SelectAddress.getValue()
    if (addressId) {
      let r = SelectAddress.getData()
      if (!r) {
        try { r = await window.addressService.fetchById(addressId) } catch { r = null }
      }
      if (r) {
        _selectedId               = addressId
        _el('avLogradouro').value = r.logradouro          || ''
        _el('avCep').value        = r.cep                 || ''
        _el('avBairro').value     = r.bairro              || ''
        _el('avCidade').value     = r.cidade              || ''
        _el('avEstado').value     = String(r.estadoId     || '')
      }
    }
    _el('avSave').textContent = _selectedId ? 'Editar' : 'Salvar'
    _container.classList.add('open')
    setTimeout(() => _el('avSearch')?.focus(), 320)
  }

  /**
   * @description Fecha o drawer com animação de deslize.
   */
  function close() {
    _container?.classList.remove('open')
  }

  /** @returns {boolean} */
  function isMounted() { return _mounted }

  /** @description Limpa o formulário e a lista ao fechar/resetar. */
  function reset() {
    if (!_mounted) return
    _el('avForm')?.reset()
    _el('avSearch').value = ''
    _el('avListBody').innerHTML = ''
    _el('avListEmpty').setAttribute('hidden', '')
    close()
  }

  function validate() { return true }
  function getValue()  { return {} }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, open, close, getValue, reset, validate, isMounted }
})()
