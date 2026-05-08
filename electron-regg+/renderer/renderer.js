/**
 * @file renderer.js
 * @description Orquestrador principal: inicializa o mapa Leaflet e monta
 * os componentes ativos no formulário. Para ativar um componente futuro,
 * descomente o div correspondente em index.html e a linha .mount() abaixo.
 */

// ── COMPONENTES ATIVOS ──────────────────────────────────────────────────────

DocumentList.mount(document.getElementById('section-document-list'))
DocumentView.mount(document.getElementById('section-document'))
AddressView.mount(document.getElementById('address-drawer'))
InterferenceView.mount(document.getElementById('interference-drawer'))
UserView.mount(document.getElementById('user-drawer'))
ProcessView.mount(document.getElementById('process-drawer'))
AnnexView.mount(document.getElementById('annex-drawer'))
SelectAddress.mount(document.getElementById('section-address'))
SelectInterference.mount(document.getElementById('section-interference'))
SelectUser.mount(document.getElementById('section-user'))
SelectProcess.mount(document.getElementById('section-process'))
SelectAnnex.mount(document.getElementById('section-attachment'))
CoordConverter.mount(document.getElementById('coord-converter'))

// ── MAPA ─────────────────────────────────────────────────────────────────────

const map = L.map('map', {
  center: [-15.7801, -47.9292],
  zoom: 7,
  zoomControl: true
})

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
  maxZoom: 19
}).addTo(map)

let marker = null

const markerIcon = L.divIcon({
  className: '',
  html: `<svg xmlns="http://www.w3.org/2000/svg" width="28" height="40" viewBox="0 0 28 40">
    <path d="M14 0C6.27 0 0 6.27 0 14c0 10.5 14 26 14 26S28 24.5 28 14C28 6.27 21.73 0 14 0z"
      fill="#1a5276" stroke="#fff" stroke-width="1.5"/>
    <circle cx="14" cy="14" r="5" fill="#fff"/>
  </svg>`,
  iconSize: [28, 40],
  iconAnchor: [14, 40],
  popupAnchor: [0, -40]
})

/**
 * @description Posiciona ou reposiciona o marcador no mapa.
 * Atualiza o rodapé do mapa e notifica o InterferenceView se estiver montado.
 * @param {number} lat
 * @param {number} lng
 */
function placeMarker(lat, lng) {
  if (marker) map.removeLayer(marker)

  marker = L.marker([lat, lng], { icon: markerIcon, draggable: true })
    .addTo(map)
    .bindPopup(`<b>Interferência</b><br>Lat: ${lat.toFixed(6)}<br>Lon: ${lng.toFixed(6)}`)

  marker.on('dragend', (e) => {
    const pos = e.target.getLatLng()
    _updateMapFooter(pos.lat, pos.lng)
    if (InterferenceView.isMounted()) InterferenceView.setCoords(pos.lat, pos.lng)
  })

  _updateMapFooter(lat, lng)
  if (SelectInterference.isMounted()) SelectInterference.setCoords(lat, lng)
  if (InterferenceView.isMounted())   InterferenceView.setCoords(lat, lng)
}

/**
 * @description Atualiza o rodapé do mapa com as coordenadas atuais.
 * @param {number} lat
 * @param {number} lng
 */
function _updateMapFooter(lat, lng) {
  document.getElementById('mapCoords').style.display = 'flex'
  document.getElementById('coordDisplay').textContent =
    `Lat: ${lat.toFixed(6)}  Lon: ${lng.toFixed(6)}`
  document.getElementById('mapHint').textContent = 'Marcador pode ser arrastado'
}

/* Clique no mapa posiciona o marcador */
map.on('click', (e) => placeMarker(e.latlng.lat, e.latlng.lng))

/* Botão de remover marcador */
document.getElementById('btnRemoveMarker').addEventListener('click', () => {
  if (marker) { map.removeLayer(marker); marker = null }
  document.getElementById('mapCoords').style.display = 'none'
  document.getElementById('mapHint').textContent = 'Clique no mapa para marcar a interferência'
  if (SelectInterference.isMounted()) SelectInterference.reset()
  if (InterferenceView.isMounted())   InterferenceView.reset()
})

/* InterferenceView emite este evento ao clicar em "Ir ao mapa" */
document.addEventListener('interference:goto', (e) => {
  const { lat, lng } = e.detail
  if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
    showToast('Coordenadas fora do intervalo válido.', 'error')
    return
  }
  map.setView([lat, lng], 14)
  placeMarker(lat, lng)
})

// ── SELEÇÃO NA LISTA DE DOCUMENTOS ───────────────────────────────────────────

/**
 * @description Preenche todos os campos do formulário com os dados do
 * documento selecionado na lista. O evento `document-list:select` carrega
 * o registro completo diretamente (sem segunda query).
 */
document.addEventListener('document-list:select', (e) => {
  const doc = e.detail

  if (DocumentView.isMounted()) {
    DocumentView.setValue({
      typeId:    doc.tipoDocumentoId,
      number:    doc.numero    || '',
      numberSei: doc.numeroSei || ''
    })
  }

  if (SelectAddress.isMounted()) {
    SelectAddress.setValue({
      addressId:    doc.enderecoId,
      addressLabel: doc.logradouro || ''
    })
  }

  if (SelectInterference.isMounted()) {
    SelectInterference.setValue({
      latitude:  doc.latitude  != null ? String(doc.latitude)  : '',
      longitude: doc.longitude != null ? String(doc.longitude) : ''
    })
  }

  if (SelectUser.isMounted()) {
    SelectUser.setValue({
      userId:    doc.usuarioId,
      userLabel: doc.usuarioNome || ''
    })
  }

  if (SelectProcess.isMounted()) {
    SelectProcess.setValue({
      processId:    doc.processoId,
      processLabel: doc.processoNumero || ''
    })
  }

  if (SelectAnnex.isMounted()) {
    SelectAnnex.setValue({
      annexId:    doc.anexoId,
      annexLabel: doc.anexoNumero || ''
    })
  }

  /* Posiciona o marcador no mapa se as coordenadas estiverem disponíveis */
  if (doc.latitude != null && doc.longitude != null) {
    placeMarker(doc.latitude, doc.longitude)
    map.setView([doc.latitude, doc.longitude], 14)
  }
})

// ── AÇÕES DO FORMULÁRIO ───────────────────────────────────────────────────────

document.getElementById('btnSave').addEventListener('click', () => {
  const form = document.getElementById('documentForm')
  if (!form.checkValidity()) { form.reportValidity(); return }

  const data = _collectData()
  console.log('Dados do cadastro:', data)
  showToast('Cadastro salvo com sucesso!', 'success')
})

document.getElementById('btnToggleMap').addEventListener('click', () => {
  document.querySelector('.workspace').classList.toggle('map-expanded')
  setTimeout(() => map.invalidateSize({ animate: false }), 370)
})

/**
 * @description Coleta os dados de todos os componentes montados.
 * @returns {Object}
 */
function _collectData() {
  const components = [
    DocumentView, SelectAddress, SelectInterference,
    SelectUser, SelectProcess, SelectAnnex,
    InterferenceView, UserView, ProcessView, AnnexView
  ]
  return components
    .filter(c => c.isMounted())
    .reduce((acc, c) => ({ ...acc, ...c.getValue() }), {})
}

// ── TOAST ─────────────────────────────────────────────────────────────────────

let _toastTimer = null

/**
 * @description Exibe uma notificação temporária na tela.
 * @param {string} msg - Mensagem a exibir.
 * @param {'success'|'error'|''} type - Tipo visual da notificação.
 */
function showToast(msg, type) {
  const toast = document.getElementById('toast')
  toast.textContent = msg
  toast.className = 'toast show' + (type ? ` ${type}` : '')
  clearTimeout(_toastTimer)
  _toastTimer = setTimeout(() => toast.classList.remove('show'), 3000)
}
