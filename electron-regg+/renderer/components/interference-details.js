/**
 * @file interference-details.js
 * @description Painel de detalhes para interferências do tipo Subterrânea.
 * Exibe campos de poço e duas abas (Requeridas / Autorizadas), cada uma com
 * tabela de finalidades editável com linhas dinâmicas (adicionar/remover)
 * e tabela transposta de demandas mensais com preenchimento automático.
 */
const InterferenceDetails = (() => {
  const _MESES = ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez']
  const _DIAS  = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
  const _CHUVA = new Set([1, 2, 3, 11, 12])  // jan fev mar nov dez

  let _mounted = false

  /**
   * @description Renderiza o painel e registra os eventos de aba.
   * @param {HTMLElement} container
   */
  function mount(container) {
    container.innerHTML = `
      <div id="idSection" class="id-section" hidden>

        <div class="id-section-header">
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="2.5"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <path d="M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20z"/>
            <path d="M12 6v6l4 2"/>
          </svg>
          Detalhes — Poço Subterrâneo
        </div>

        <div class="id-fields">
          <div class="form-row">
            <div class="form-group">
              <label for="idTipoPoco">Tipo de Poço</label>
              <select id="idTipoPoco"><option value="">Selecione...</option></select>
            </div>
            <div class="form-group">
              <label for="idCaebs">Caesb</label>
              <select id="idCaebs">
                <option value="">Selecione...</option>
                <option value="true">Sim</option>
                <option value="false">Não</option>
              </select>
            </div>
            <div class="form-group grow">
              <label for="idSubsistema">Subsistema</label>
              <select id="idSubsistema"><option value="">Selecione...</option></select>
            </div>
            <div class="form-group">
              <label for="idCodSubsistema">Código do Subsistema</label>
              <input type="text" id="idCodSubsistema">
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label for="idVazaoSistema">Vazão do Subsistema</label>
              <input type="number" id="idVazaoSistema" step="any">
            </div>
            <div class="form-group">
              <label for="idVazaoOutorgavel">Vazão Outorgável</label>
              <input type="number" id="idVazaoOutorgavel" step="any">
            </div>
            <div class="form-group">
              <label for="idVazaoTeste">Vazão de Teste</label>
              <input type="number" id="idVazaoTeste" step="any">
            </div>
            <div class="form-group">
              <label for="idNivelEstatico">Nível Estático</label>
              <input type="number" id="idNivelEstatico" step="any">
            </div>
            <div class="form-group">
              <label for="idNivelDinamico">Nível Dinâmico</label>
              <input type="number" id="idNivelDinamico" step="any">
            </div>
            <div class="form-group">
              <label for="idProfundidade">Profundidade do Poço</label>
              <input type="number" id="idProfundidade" step="any">
            </div>
          </div>
        </div>

        <div class="id-tabs">
          <button type="button" class="id-tab id-tab-active" id="idTabReqBtn">Requeridas</button>
          <button type="button" class="id-tab"               id="idTabAuthBtn">Autorizadas</button>
        </div>

        <div id="idPanelReq"  class="id-tab-panel">${_panelHTML('Req')}</div>
        <div id="idPanelAuth" class="id-tab-panel" hidden>${_panelHTML('Auth')}</div>

      </div>
    `

    _el('idTabReqBtn').addEventListener('click',  () => _switchTab('requeridas'))
    _el('idTabAuthBtn').addEventListener('click', () => _switchTab('autorizadas'))
    _mounted = true
  }

  function _panelHTML(tab) {
    return `
      <p class="id-group-title">Finalidades</p>
      <div class="doc-list-wrap id-fin-wrap">
        <table class="doc-list-table id-fin-table">
          <thead>
            <tr>
              <th>Finalidade</th>
              <th>Subfinalidade</th>
              <th class="id-col-n">Qtd</th>
              <th class="id-col-n">Total</th>
              <th class="id-col-n">Consumo</th>
              <th class="id-col-acts"></th>
            </tr>
          </thead>
          <tbody id="idFin${tab}Body"></tbody>
          <tfoot>
            <tr class="id-total-row">
              <td colspan="4" class="id-total-label">Total consumo</td>
              <td class="id-total-value" id="idFin${tab}Sum">—</td>
              <td></td>
            </tr>
          </tfoot>
        </table>
      </div>

      <p class="id-group-title">Demandas Mensais</p>
      <div class="iv-demandas-scroll">
        <table class="iv-demandas-table id-dem-table" id="idDem${tab}Table"></table>
      </div>
    `
  }

  function _switchTab(tab) {
    const isReq = tab === 'requeridas'
    _el('idTabReqBtn').classList.toggle('id-tab-active',  isReq)
    _el('idTabAuthBtn').classList.toggle('id-tab-active', !isReq)
    _el('idPanelReq').hidden  = !isReq
    _el('idPanelAuth').hidden = isReq
  }

  /**
   * @description Preenche os selects de domínio após carregamento dos domínios.
   * @param {Object} d - Objeto retornado por domainService.fetchAll().
   */
  function fillDomains(d) {
    _fillSelect('idTipoPoco',   Object.values(d.tipoPoco   ?? {}))
    _fillSelect('idSubsistema', Object.values(d.subsistema ?? {}))
  }

  /**
   * @description Preenche todos os campos e reconstrói as tabelas editáveis.
   * Se finalidades/demandas forem vazias, inicializa com uma linha em branco.
   * @param {Object} r - Interferência normalizada por interference-service._normalize().
   */
  function fill(r) {
    if (!_mounted) return

    _setSelectValue('idTipoPoco',    r.tipoPoco)
    _el('idCaebs').value           = r.caesb != null ? String(r.caesb) : ''
    _el('idCodSubsistema').value   = r.codigoSubsistema ?? ''
    _el('idVazaoSistema').value    = r.vazaoSistema     ?? ''
    _el('idVazaoOutorgavel').value = r.vazaoOutorgavel  ?? ''
    _el('idVazaoTeste').value      = r.vazaoTeste       ?? ''
    _el('idNivelEstatico').value   = r.nivelEstatico    ?? ''
    _el('idNivelDinamico').value   = r.nivelDinamico    ?? ''
    _el('idProfundidade').value    = r.profundidade     ?? ''

    const fin = r.finalidades || []
    const dem = r.demandas    || []

    _buildFinTable('Req',  fin.filter(f => f.tipoFinalidade?.id === 1))
    _buildFinTable('Auth', fin.filter(f => f.tipoFinalidade?.id === 2))
    _buildDemTable('Req',  dem.filter(d => d.tipoFinalidade?.id === 1))
    _buildDemTable('Auth', dem.filter(d => d.tipoFinalidade?.id === 2))

    _switchTab('requeridas')
  }

  /**
   * @description Inicializa o painel com campos vazios e uma linha por aba de finalidades.
   * Chamado ao selecionar "Subterrânea" no cadastro de nova interferência.
   */
  function fillEmpty() {
    fill({
      tipoPoco: null, caesb: null, codigoSubsistema: null,
      vazaoSistema: null, vazaoOutorgavel: null, vazaoTeste: null,
      nivelEstatico: null, nivelDinamico: null, profundidade: null,
      finalidades: [], demandas: []
    })
  }

  /** @description Exibe o painel. */
  function show() { if (_mounted) _el('idSection').removeAttribute('hidden') }

  /** @description Oculta o painel. */
  function hide() { if (_mounted) _el('idSection').setAttribute('hidden', '') }

  // ── Finalidades ──────────────────────────────────────────────────────────

  /**
   * @description Constrói a tabela de finalidades com linhas dinâmicas.
   * Se não houver dados, inicia com uma linha vazia.
   * @param {'Req'|'Auth'} tab
   * @param {Array} rows - Finalidades filtradas por tipoFinalidade.
   */
  function _buildFinTable(tab, rows) {
    const tbody = _el(`idFin${tab}Body`)
    tbody.innerHTML = ''

    const initial = rows.length ? rows : [{}]
    initial.forEach(f => _addFinRow(tab, f))
    _updateFinTotal(tab)
  }

  /**
   * @description Cria e insere uma linha de finalidade editável.
   * Cada linha tem: calc (consumo = qtd × total), adicionar linha abaixo, remover linha.
   * @param {'Req'|'Auth'} tab
   * @param {Object} f - Dados da finalidade (pode ser vazio {}).
   * @param {HTMLTableRowElement|null} afterRow - Inserir após esta linha; null = append.
   */
  function _addFinRow(tab, f = {}, afterRow = null) {
    const tbody = _el(`idFin${tab}Body`)
    const tr    = document.createElement('tr')

    tr.innerHTML = `
      <td><input type="text"   class="id-fin-input"             value="${_esc(f.finalidade    ?? '')}"></td>
      <td><input type="text"   class="id-fin-input"             value="${_esc(f.subfinalidade ?? '')}"></td>
      <td><input type="number" class="id-fin-input id-fin-qtd"  value="${f.quantidade ?? ''}"></td>
      <td><input type="number" class="id-fin-input id-fin-vtot" value="${f.total      ?? ''}"></td>
      <td><input type="number" class="id-fin-input id-fin-cons" value="${f.consumo    ?? ''}"></td>
      <td class="id-fin-actions">
        <button type="button" class="id-calc-btn" title="Consumo = Qtd × Total">
          <svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="3"
               stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/>
          </svg>
        </button>
        <button type="button" class="id-row-btn id-add-btn" title="Adicionar linha abaixo">+</button>
        <button type="button" class="id-row-btn id-del-btn" title="Remover esta linha">−</button>
      </td>
    `

    tr.querySelector('.id-calc-btn').addEventListener('click', () => {
      const qtd  = parseFloat(tr.querySelector('.id-fin-qtd').value)  || 0
      const vtot = parseFloat(tr.querySelector('.id-fin-vtot').value) || 0
      tr.querySelector('.id-fin-cons').value = qtd * vtot
      _updateFinTotal(tab)
    })

    tr.querySelector('.id-add-btn').addEventListener('click', () => _addFinRow(tab, {}, tr))

    tr.querySelector('.id-del-btn').addEventListener('click', () => {
      tr.remove()
      _updateFinTotal(tab)
    })

    tr.querySelector('.id-fin-cons').addEventListener('input', () => _updateFinTotal(tab))

    if (afterRow) afterRow.insertAdjacentElement('afterend', tr)
    else          tbody.appendChild(tr)

    return tr
  }

  function _updateFinTotal(tab) {
    const sum = _getFinTotal(tab)
    _el(`idFin${tab}Sum`).textContent = sum ? sum.toLocaleString('pt-BR') : '—'
  }

  function _getFinTotal(tab) {
    return Array.from(_el(`idFin${tab}Body`).querySelectorAll('.id-fin-cons'))
      .reduce((acc, inp) => acc + (parseFloat(inp.value) || 0), 0)
  }

  // ── Demandas ─────────────────────────────────────────────────────────────

  /**
   * @description Constrói a tabela transposta de demandas mensais.
   * Colunas = meses (Jan–Dez). Linhas = Vazão | Tempo | Período.
   * Cada célula é um input editável com botões de preenchimento automático.
   * @param {'Req'|'Auth'} tab
   * @param {Array} demandas - Demandas filtradas por tipoFinalidade.
   */
  function _buildDemTable(tab, demandas) {
    const table = _el(`idDem${tab}Table`)
    const byMes = {}
    demandas.forEach(d => { byMes[d.mes] = d })

    const thMeses = _MESES.map(m => `<th class="id-dem-th">${m}</th>`).join('')
    const mkCells = (field) =>
      _MESES.map((_, i) =>
        `<td><input type="number" class="id-dem-cell" data-tab="${tab}" data-mes="${i + 1}" data-field="${field}" value="${byMes[i + 1]?.[field] ?? ''}"></td>`
      ).join('')

    table.innerHTML = `
      <thead>
        <tr><th class="id-metric-label"></th>${thMeses}</tr>
      </thead>
      <tbody>
        <tr>
          <td class="id-metric-label">
            <span class="id-metric-name">Vazão (l/dia)</span>
            <div class="id-fill-row">
              <button type="button" class="id-fill-btn" data-action="vazao-all"
                title="Preencher todos os meses com a finalidade total">↓ Todos</button>
              <button type="button" class="id-fill-btn id-fill-dry" data-action="vazao-dry"
                title="Meses secos — zera jan fev mar nov dez">↓ Secos</button>
            </div>
          </td>
          ${mkCells('vazao')}
        </tr>
        <tr>
          <td class="id-metric-label">
            <span class="id-metric-name">Tempo (h/dia)</span>
            <div class="id-fill-row">
              <select class="id-tempo-sel" id="idTempoSel${tab}">
                ${Array.from({length: 25}, (_, i) => `<option value="${i}">${i}h</option>`).join('')}
              </select>
              <button type="button" class="id-fill-btn" data-action="tempo-all"
                title="Preencher todos os meses com o valor selecionado">↓ Todos</button>
              <button type="button" class="id-fill-btn id-fill-dry" data-action="tempo-dry"
                title="Meses secos — zera jan fev mar nov dez">↓ Secos</button>
            </div>
          </td>
          ${mkCells('tempo')}
        </tr>
        <tr>
          <td class="id-metric-label">
            <span class="id-metric-name">Período (dia/mês)</span>
            <div class="id-fill-row">
              <button type="button" class="id-fill-btn" data-action="per-all"
                title="Preencher com os dias de cada mês (31,28,31,30,...)">↓ Todos</button>
              <button type="button" class="id-fill-btn id-fill-dry" data-action="per-dry"
                title="Meses secos — zera jan fev mar nov dez">↓ Secos</button>
            </div>
          </td>
          ${mkCells('periodo')}
        </tr>
      </tbody>
    `

    table.querySelectorAll('.id-fill-btn').forEach(btn =>
      btn.addEventListener('click', () => _handleFill(btn, table, tab))
    )
  }

  /**
   * @description Executa a ação de preenchimento automático conforme o botão clicado.
   * @param {HTMLButtonElement} btn
   * @param {HTMLTableElement}  table
   * @param {'Req'|'Auth'}      tab
   */
  function _handleFill(btn, table, tab) {
    const action = btn.dataset.action

    if (action === 'vazao-all') {
      const total = _getFinTotal(tab)
      table.querySelectorAll('[data-field="vazao"]').forEach(inp => { inp.value = total })

    } else if (action === 'vazao-dry') {
      const total = _getFinTotal(tab)
      table.querySelectorAll('[data-field="vazao"]').forEach(inp => {
        inp.value = _CHUVA.has(+inp.dataset.mes) ? 0 : total
      })

    } else if (action === 'tempo-all') {
      const val = _el(`idTempoSel${tab}`).value
      table.querySelectorAll('[data-field="tempo"]').forEach(inp => { inp.value = val })

    } else if (action === 'tempo-dry') {
      const val = _el(`idTempoSel${tab}`).value
      table.querySelectorAll('[data-field="tempo"]').forEach(inp => {
        inp.value = _CHUVA.has(+inp.dataset.mes) ? 0 : val
      })

    } else if (action === 'per-all') {
      table.querySelectorAll('[data-field="periodo"]').forEach(inp => {
        inp.value = _DIAS[+inp.dataset.mes - 1]
      })

    } else if (action === 'per-dry') {
      table.querySelectorAll('[data-field="periodo"]').forEach(inp => {
        const mes = +inp.dataset.mes
        inp.value = _CHUVA.has(mes) ? 0 : _DIAS[mes - 1]
      })
    }
  }

  // ── Utilitários ──────────────────────────────────────────────────────────

  function _fillSelect(id, items) {
    const sel = _el(id)
    if (!sel || !Array.isArray(items)) return
    const opts = items.map(i => `<option value="${i.id}">${i.descricao}</option>`).join('')
    sel.innerHTML = `<option value="">Selecione...</option>${opts}`
  }

  function _setSelectValue(id, value) {
    const sel = _el(id)
    if (sel && value != null) sel.value = String(value)
  }

  function _esc(s) {
    return String(s ?? '')
      .replace(/&/g, '&amp;').replace(/"/g, '&quot;')
      .replace(/</g, '&lt;').replace(/>/g, '&gt;')
  }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, fillDomains, fill, fillEmpty, show, hide }
})()
