/**
 * @file user-view.js
 * @description Painel lateral deslizante (drawer) para cadastro e seleção de usuários/requerentes.
 * Desliza da direita para a esquerda sobre o painel de formulário.
 *
 * Seções internas:
 *  1. Formulário de cadastro: nome e CPF/CNPJ.
 *  2. Pesquisa de usuários cadastrados com lista de resultados.
 *
 * Eventos disparados:
 *  - `user-view:select` → usuário clica em um registro da lista (fecha o drawer).
 *  - `user-view:saved`  → novo usuário salvo com sucesso.
 *
 * Aberto via `UserView.open()` e fechado pelo botão Voltar ou tecla Escape.
 */
const UserView = (() => {
  let _mounted   = false
  let _container = null

  /**
   * @description Renderiza o drawer no container e registra os eventos.
   * O container deve ser o `#user-drawer` no DOM.
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
        <button type="button" class="btn btn-primary" id="uvSave">Salvar Usuário</button>
      </div>

      <div class="av-content">

        <!-- Formulário de cadastro de novo usuário -->
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

        <!-- Pesquisa e lista de usuários cadastrados -->
        <div class="form-section">
          <div class="doc-list-header">
            <span class="doc-list-title">Pesquisar Usuários</span>
          </div>
          <div class="doc-search-bar">
            <div class="form-group grow">
              <input type="text" id="uvSearch"
                placeholder="Pesquisar por nome e CPF/CNPJ..."
                autocomplete="off">
            </div>
            <button type="button" id="uvSearchBtn" class="btn btn-primary">Pesquisar</button>
          </div>
          <div class="doc-list-wrap">
            <table class="doc-list-table" aria-label="Lista de usuários">
              <thead>
                <tr>
                  <th style="width:55%">Nome</th>
                  <th style="width:45%">CPF / CNPJ</th>
                </tr>
              </thead>
              <tbody id="uvListBody"></tbody>
            </table>
            <p class="doc-list-empty" id="uvListEmpty" hidden>Nenhum usuário encontrado.</p>
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
    _el('uvBack').addEventListener('click', close)
    _el('uvSave').addEventListener('click', _save)

    _el('uvSearchBtn').addEventListener('click', () => _searchList(_el('uvSearch').value.trim()))
    _el('uvSearch').addEventListener('keydown', (e) => {
      if (e.key === 'Enter') _searchList(_el('uvSearch').value.trim())
    })

    /* Máscara de CPF/CNPJ */
    _el('uvCpfCnpj').addEventListener('input', (e) => {
      e.target.value = _maskCpfCnpj(e.target.value)
    })

    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && _container?.classList.contains('open')) close()
    })
  }

  /**
   * @description Valida e salva um novo usuário.
   * TODO: conectar a window.documentService.saveUser(data).
   */
  function _save() {
    const form = _el('uvForm')
    if (!form.checkValidity()) { form.reportValidity(); return }

    const data = {
      nome:     _el('uvNome').value.trim(),
      cpfCnpj:  _el('uvCpfCnpj').value.trim()
    }

    console.log('Salvar usuário:', data)
    document.dispatchEvent(new CustomEvent('user-view:saved', { detail: data }))
    form.reset()
    _searchList('')
  }

  /**
   * @description Busca usuários cadastrados pelo nome ou CPF/CNPJ via API.
   * @param {string} term
   */
  async function _searchList(term) {
    try {
      const rows = await window.userService.fetchByKeyword(term)
      _renderRows(rows)
    } catch (err) {
      console.error('UserView: erro ao buscar usuários', err)
      _renderRows([])
    }
  }

  /**
   * @description Popula a tabela de usuários com os resultados da busca.
   * @param {Array<{id: number, nome: string, cpfCnpj: string}>} rows
   */
  function _renderRows(rows) {
    const tbody = _el('uvListBody')
    const empty = _el('uvListEmpty')

    if (!rows.length) {
      tbody.innerHTML = ''
      empty.removeAttribute('hidden')
      return
    }

    empty.setAttribute('hidden', '')
    tbody.innerHTML = rows.map(r => {
      const cpf = _maskCpfCnpj(r.cpfCnpj || '')
      return `
        <tr class="doc-list-row"
            data-id="${r.id}"
            data-nome="${r.nome || ''}"
            data-cpfcnpj="${r.cpfCnpj || ''}"
            data-label="${r.nome}">
          <td title="${r.nome}">${r.nome}</td>
          <td>${cpf || '—'}</td>
        </tr>`
    }).join('')

    tbody.querySelectorAll('.doc-list-row').forEach(tr =>
      tr.addEventListener('click', () => _pickUser(tr))
    )
  }

  /**
   * @description Seleciona um usuário da lista, preenche o formulário e notifica o SelectUser.
   * O drawer permanece aberto para permitir nova seleção se necessário.
   * @param {HTMLTableRowElement} tr
   */
  function _pickUser(tr) {
    _el('uvNome').value    = tr.dataset.nome                       || ''
    _el('uvCpfCnpj').value = _maskCpfCnpj(tr.dataset.cpfcnpj || '')

    document.dispatchEvent(new CustomEvent('user-view:select', {
      detail: { id: tr.dataset.id, label: tr.dataset.label }
    }))
  }

  /**
   * @description Abre o drawer com animação de deslize.
   */
  function open() {
    if (!_mounted) return
    _container.classList.add('open')
    setTimeout(() => _el('uvSearch')?.focus(), 320)
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
    _el('uvForm')?.reset()
    _el('uvSearch').value = ''
    _el('uvListBody').innerHTML = ''
    _el('uvListEmpty').setAttribute('hidden', '')
    close()
  }

  /**
   * @description Aplica máscara de CPF (000.000.000-00) ou CNPJ (00.000.000/0000-00).
   * @param {string} raw - Valor com ou sem máscara.
   * @returns {string} Valor formatado.
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

  function validate() { return true }
  function getValue()  { return {} }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, open, close, getValue, reset, validate, isMounted }
})()
