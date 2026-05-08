/**
 * @file document-view.js
 * @description Componente de cadastro de documento.
 * Exibe os campos: tipo de documento, número e número SEI em uma única linha.
 * Os tipos são carregados da tabela `documento_tipo`.
 */
const DocumentView = (() => {
  let _mounted = false

  /** @type {Array<{id: number, name: string}>} Espelho da tabela documento_tipo */
  const TYPES = [
    { id: 1, name: 'Parecer' },
    { id: 2, name: 'Despacho' },
    { id: 3, name: 'Requerimento' },
    { id: 4, name: 'Ofício' }
  ]

  /**
   * @description Renderiza o componente dentro do container e ativa os eventos.
   * @param {HTMLElement} container - Elemento que receberá o HTML.
   */
  function mount(container) {
    container.innerHTML = `
      <fieldset class="form-section">
        <legend class="section-title">
          <span class="section-icon">📄</span> Documento
        </legend>
        <div class="form-row">
          <div class="form-group type-select">
            <label for="docType">Tipo <span class="required">*</span></label>
            <select id="docType" name="docType" required>
              <option value="">Selecione...</option>
              ${TYPES.map(t => `<option value="${t.id}">${t.name}</option>`).join('\n              ')}
            </select>
          </div>
          <div class="form-group grow">
            <label for="docNumber">Número <span class="required">*</span></label>
            <input
              type="text"
              id="docNumber"
              name="docNumber"
              placeholder="Ex: 155378018"
              required
            >
          </div>
          <div class="form-group grow">
            <label for="docNumberSei">Número SEI</label>
            <input
              type="text"
              id="docNumberSei"
              name="docNumberSei"
              placeholder="Ex: 00391.000001/2026-01"
            >
          </div>
          <button type="button" class="btn btn-primary" id="btnSave">Salvar Cadastro</button>
        </div>
      </fieldset>
    `
    _mounted = true
  }

  /**
   * @description Retorna os valores atuais dos campos do documento.
   * @returns {{ typeId: string, number: string, numberSei: string }}
   */
  function getValue() {
    return {
      typeId:    _el('docType').value,
      number:    _el('docNumber').value.trim(),
      numberSei: _el('docNumberSei').value.trim()
    }
  }

  /**
   * @description Preenche os campos com os dados fornecidos.
   * @param {{ typeId?: string, number?: string, numberSei?: string }} data
   */
  function setValue(data = {}) {
    _el('docType').value      = data.typeId    ?? ''
    _el('docNumber').value    = data.number    ?? ''
    _el('docNumberSei').value = data.numberSei ?? ''
  }

  /** @description Limpa todos os campos do componente. */
  function reset() {
    if (_mounted) setValue({})
  }

  /**
   * @description Valida os campos obrigatórios.
   * @returns {boolean}
   */
  function validate() {
    const v = getValue()
    return v.typeId !== '' && v.number !== ''
  }

  /** @returns {boolean} */
  function isMounted() { return _mounted }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, getValue, setValue, reset, validate, isMounted }
})()
