/**
 * @file address-view.js
 * @description Drawer de cadastro de endereços. Apenas o formulário.
 * A seção de pesquisa/lista é delegada ao componente AddressList.
 *
 * Eventos disparados:
 *  - `address-view:select` → endereço selecionado via AddressList.
 *  - `address-view:saved`  → endereço salvo com sucesso.
 *
 * Escuta:
 *  - `address-list:select` → preenche formulário e notifica SelectAddress.
 */
const AddressView = (() => {
  let _mounted      = false
  let _container    = null
  let _selectedId   = null
  let _estadoList   = []
  let _lastSelected = null

  /**
   * @description Renderiza o drawer e monta AddressList abaixo do formulário.
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
                <input type="text" id="avCidade" name="avCidade" placeholder="Cidade" value="Brasília" required>
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

        <div id="avListMount"></div>

      </div>
    `

    _bindEvents()
    AddressList.mount(_el('avListMount'))
    _mounted = true
    _loadEstados()
  }

  /**
   * @description Carrega a lista de estados e popula o select de UF.
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
    _applyDefaults()
  }

  /** @description Aplica valores padrão de Brasília/DF quando nenhum endereço está selecionado. */
  function _applyDefaults() {
    if (_selectedId) return
    if (!_el('avCidade').value) _el('avCidade').value = 'Brasília'
    const dfOpt = Array.from(_el('avEstado').options).find(o => o.text === 'DF')
    if (dfOpt && !_el('avEstado').value) _el('avEstado').value = dfOpt.value
  }

  /**
   * @description Registra os eventos do formulário e escuta seleção da lista.
   */
  function _bindEvents() {
    _el('avBack').addEventListener('click', close)
    _el('avNew').addEventListener('click', _new)
    _el('avSave').addEventListener('click', _save)

    _el('avCep').addEventListener('input', (e) => {
      let v = e.target.value.replace(/\D/g, '')
      v = v.replace(/(\d{5})(\d)/, '$1-$2')
      e.target.value = v
    })

    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && _container?.classList.contains('open')) close()
    })

    document.addEventListener('address-list:select', (e) => {
      const d = e.detail
      _selectedId               = d.id
      _el('avSave').textContent = 'Editar'
      _el('avLogradouro').value = d.logradouro || ''
      _el('avCep').value        = d.cep        || ''
      _el('avBairro').value     = d.bairro     || ''
      _el('avCidade').value     = d.cidade     || ''
      _el('avEstado').value     = d.estadoId   || ''
      _lastSelected = {
        addressId:    d.id,
        addressLabel: d.logradouro || '',
        bairro:       d.bairro     || '',
        cidade:       d.cidade     || '',
        cep:          d.cep        || '',
        estado:       d.estado     || '',
        estadoId:     d.estadoId   || null
      }
      document.dispatchEvent(new CustomEvent('address-view:select', {
        detail: { id: d.id, label: d.label }
      }))
    })
  }

  /**
   * @description Valida e salva um endereço.
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
      const raw   = await window.addressService.save(payload)
      const saved = raw?.object ?? raw
      const savedId = saved?.id ?? payload.id
      _lastSelected = {
        addressId:    savedId,
        addressLabel: saved?.logradouro ?? formData.logradouro,
        bairro:       saved?.bairro     ?? formData.bairro,
        cidade:       saved?.cidade     ?? formData.cidade,
        cep:          saved?.cep        ?? formData.cep,
        estado:       saved?.estado     ?? formData.estado,
        estadoId:     saved?.estadoId   ?? formData.estadoId
      }
      document.dispatchEvent(new CustomEvent('address-view:saved', { detail: saved }))
      _selectedId = null
      _el('avSave').textContent = 'Salvar'
      form.reset()
      _applyDefaults()
      AddressList.prependRow({
        id:         savedId,
        logradouro: saved?.logradouro ?? formData.logradouro,
        bairro:     saved?.bairro     ?? formData.bairro,
        cidade:     saved?.cidade     ?? formData.cidade,
        cep:        saved?.cep        ?? formData.cep,
        estado:     saved?.estado     ?? formData.estado,
        estadoId:   saved?.estadoId   ?? formData.estadoId
      })
    } catch (err) {
      console.error('AddressView: erro ao salvar endereço', err)
      window.showToast?.('Erro ao salvar endereço. Tente novamente.', 'error')
    }
  }

  /**
   * @description Limpa o formulário e reseta para modo de criação.
   */
  function _new() {
    _selectedId = null
    _el('avSave').textContent = 'Salvar'
    _el('avForm').reset()
    _applyDefaults()
  }

  /**
   * @description Abre o drawer e pré-preenche com o endereço selecionado em SelectAddress.
   */
  async function open() {
    if (!_mounted) return
    _lastSelected = null
    const { addressId } = SelectAddress.getValue()
    if (addressId) {
      let r = SelectAddress.getData()
      if (!r) {
        try { r = await window.addressService.fetchById(addressId) } catch { r = null }
      }
      if (r) {
        _selectedId               = addressId
        _el('avLogradouro').value = r.logradouro      || ''
        _el('avCep').value        = r.cep             || ''
        _el('avBairro').value     = r.bairro          || ''
        _el('avCidade').value     = r.cidade          || ''
        _el('avEstado').value     = String(r.estadoId || '')
      }
    }
    _el('avSave').textContent = _selectedId ? 'Editar' : 'Salvar'
    _container.classList.add('open')
    setTimeout(() => _el('alvSearch')?.focus(), 320)
  }

  /**
   * @description Fecha o drawer.
   */
  function close() {
    if (_lastSelected && SelectAddress.isMounted()) {
      SelectAddress.setValue(_lastSelected)
    }
    _container?.classList.remove('open')
  }

  /**
   * @description Limpa o formulário e a lista.
   */
  function reset() {
    if (!_mounted) return
    _el('avForm')?.reset()
    AddressList.reset()
    close()
  }

  function validate()  { return true }
  function getValue()  { return {} }
  function isMounted() { return _mounted }

  /** @param {string} id @returns {HTMLElement} */
  function _el(id) { return document.getElementById(id) }

  return { mount, open, close, getValue, reset, validate, isMounted }
})()
