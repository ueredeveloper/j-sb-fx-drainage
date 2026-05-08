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
  let _mounted   = false
  let _container = null
  let _rows      = []

  /** Lista de estados brasileiros para reutilizar no select. */
  const _UF_OPTIONS = [
    'AC','AL','AP','AM','BA','CE','DF','ES','GO',
    'MA','MT','MS','MG','PA','PB','PR','PE','PI',
    'RJ','RN','RS','RO','RR','SC','SP','SE','TO'
  ].map(uf => `<option value="${uf}"${uf === 'DF' ? ' selected' : ''}>${uf}</option>`).join('')

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
        <button type="button" class="btn btn-primary" id="avSave">Salvar Endereço</button>
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
                  ${_UF_OPTIONS}
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
                  <th style="width:42%">Logradouro</th>
                  <th style="width:20%">Bairro</th>
                  <th style="width:25%">Cidade</th>
                  <th style="width:13%">UF</th>
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
  }

  /**
   * @description Registra todos os eventos do drawer.
   */
  function _bindEvents() {
    /* Botão Voltar */
    _el('avBack').addEventListener('click', close)

    /* Botão Salvar */
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
  function _save() {
    const form = _el('avForm')
    if (!form.checkValidity()) { form.reportValidity(); return }

    const data = {
      logradouro: _el('avLogradouro').value.trim(),
      bairro:     _el('avBairro').value.trim(),
      cidade:     _el('avCidade').value.trim(),
      cep:        _el('avCep').value.trim(),
      estado:     _el('avEstado').value
    }

    /* TODO: const id = window.db.insertAddress(data) */
    console.log('Salvar endereço:', data)
    document.dispatchEvent(new CustomEvent('address-view:saved', { detail: data }))

    form.reset()
    _searchList('')
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
    _rows = rows

    if (!rows.length) {
      tbody.innerHTML = ''
      empty.removeAttribute('hidden')
      return
    }

    empty.setAttribute('hidden', '')
    tbody.innerHTML = rows.map((r, idx) => `
      <tr class="doc-list-row" data-idx="${idx}">
        <td title="${r.logradouro}">${r.logradouro}</td>
        <td>${r.bairro || '—'}</td>
        <td>${r.cidade}</td>
        <td>${r.estado}</td>
      </tr>
    `).join('')

    tbody.querySelectorAll('.doc-list-row').forEach(tr =>
      tr.addEventListener('click', () => _pickAddress(tr))
    )
  }

  /**
   * @description Seleciona um endereço da lista, preenche o formulário e notifica o SelectAddress.
   * O drawer permanece aberto. O evento `address-view:select` é capturado pelo SelectAddress.
   * @param {HTMLTableRowElement} tr
   */
  function _pickAddress(tr) {
    const r = _rows[parseInt(tr.dataset.idx, 10)]
    if (!r) return

    _el('avLogradouro').value = r.logradouro || ''
    _el('avCep').value        = r.cep        || ''
    _el('avBairro').value     = r.bairro     || ''
    _el('avCidade').value     = r.cidade     || ''
    _el('avEstado').value     = r.estado     || ''

    document.dispatchEvent(new CustomEvent('address-view:select', {
      detail: { id: r.id, label: `${r.logradouro} — ${r.cidade}/${r.estado}` }
    }))
  }

  /**
   * @description Abre o drawer com animação de deslize.
   * Foca automaticamente no campo de pesquisa da lista.
   */
  function open() {
    if (!_mounted) return
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
