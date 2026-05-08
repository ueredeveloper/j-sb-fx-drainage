/**
 * @file document-list.js
 * @description Componente de pesquisa e listagem de documentos cadastrados.
 * Exibe uma barra de busca e uma tabela com os resultados.
 * Ao clicar em uma linha, dispara o evento `document-list:select` com os
 * dados do documento para que o renderer.js carregue o formulário em modo edição.
 *
 * Colunas exibidas: Tipo de Documento, Número, Número SEI, Processo, Endereço.
 *
 */
const DocumentList = (() => {
  let _mounted    = false
  let _selectedId = null
  let _rows       = []

  /**
   * @description Renderiza o componente e registra os eventos.
   * @param {HTMLElement} container
   */
  function mount(container) {
    container.innerHTML = `
      <div class="doc-list-container">

        <div class="doc-list-header">
          <span class="doc-list-title">Buscar Documentos</span>
        </div>

        <div class="doc-search-bar">
          <div class="form-group grow">
            <input
              type="text"
              id="docListSearch"
              placeholder="Pesquisar por número, número SEI, processo e endereço..."
              autocomplete="off"
            >
          </div>
          <button type="button" id="docListBtn" class="btn btn-primary">
            Pesquisar
          </button>
        </div>

        <div class="doc-list-wrap">
          <table class="doc-list-table" aria-label="Lista de documentos">
            <thead>
              <tr>
                <th>Tipo de Documento</th>
                <th>Número</th>
                <th>Número SEI</th>
                <th>Processo</th>
                <th class="col-address">Endereço</th>
              </tr>
            </thead>
            <tbody id="docListBody"></tbody>
          </table>
          <p class="doc-list-empty" id="docListEmpty" hidden>
            Nenhum documento encontrado.
          </p>
        </div>

      </div>
    `
    _bindEvents()
    _mounted = true
  }

  /**
   * @description Registra eventos do botão de pesquisa e da tecla Enter.
   */
  function _bindEvents() {
    _el('docListBtn').addEventListener('click', () =>
      _search(_el('docListSearch').value.trim())
    )
    _el('docListSearch').addEventListener('keydown', (e) => {
      if (e.key === 'Enter') _search(_el('docListSearch').value.trim())
    })
  }

  /**
   * @description Executa a busca de documentos pelo termo informado.
   * Filtra por número, SEI, nome ou CPF/CNPJ do usuário vinculado.
   * @param {string} term - Texto digitado pelo usuário.
   */
  async function _search(term) {
    _el('docListBtn').disabled = true
    _el('docListBtn').textContent = 'Buscando…'
    try {
      const rows = await window.documentService.fetchByParam(term)
      _renderRows(rows)
    } catch (err) {
      console.error('[DocumentList] Erro na busca:', err)
      _renderRows([])
    } finally {
      _el('docListBtn').disabled = false
      _el('docListBtn').textContent = 'Pesquisar'
    }
  }

  /**
   * @description Popula a tabela com os documentos retornados pela busca.
   * Armazena os dados completos em `_rows` para uso no evento de seleção.
   * @param {Object[]} rows - Linhas retornadas por documentService.fetchByParam.
   */
  function _renderRows(rows) {
    const tbody = _el('docListBody')
    const empty = _el('docListEmpty')
    _selectedId = null
    _rows = rows

    if (!rows.length) {
      tbody.innerHTML = ''
      empty.removeAttribute('hidden')
      return
    }

    empty.setAttribute('hidden', '')
    tbody.innerHTML = rows.map((r, idx) => `
      <tr class="doc-list-row" data-idx="${idx}">
        <td>${r.tipoDocumentoNome || '—'}</td>
        <td>${r.numero           || '—'}</td>
        <td>${r.numeroSei        || '—'}</td>
        <td>${r.processoNumero   || '—'}</td>
        <td class="col-address" title="${r.logradouro || ''}">${r.logradouro || '—'}</td>
      </tr>
    `).join('')

    tbody.querySelectorAll('.doc-list-row').forEach(tr =>
      tr.addEventListener('click', () => _selectRow(tr))
    )
  }

  /**
   * @description Destaca a linha selecionada e dispara o evento de seleção
   * com todos os dados do documento para que o renderer.js preencha o formulário.
   * @param {HTMLTableRowElement} tr
   */
  function _selectRow(tr) {
    _el('docListBody').querySelectorAll('.doc-list-row')
      .forEach(r => r.classList.remove('selected'))

    tr.classList.add('selected')
    const row = _rows[parseInt(tr.dataset.idx, 10)]
    _selectedId = String(row.id)

    document.dispatchEvent(
      new CustomEvent('document-list:select', { detail: row })
    )
  }

  /**
   * @description Remove o destaque da linha selecionada.
   */
  function clearSelection() {
    _selectedId = null
    _el('docListBody')?.querySelectorAll('.doc-list-row')
      .forEach(r => r.classList.remove('selected'))
  }

  /** @returns {{ selectedDocumentId: string|null }} */
  function getValue() { return { selectedDocumentId: _selectedId } }

  /**
   * @description Limpa o campo de busca e a tabela de resultados.
   */
  function reset() {
    if (!_mounted) return
    _selectedId = null
    _rows = []
    _el('docListSearch').value = ''
    _el('docListBody').innerHTML = ''
    _el('docListEmpty').setAttribute('hidden', '')
  }

  /** @returns {boolean} */
  function validate() { return true }

  /** @returns {boolean} */
  function isMounted() { return _mounted }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, getValue, clearSelection, reset, validate, isMounted }
})()
