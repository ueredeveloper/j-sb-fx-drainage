/**
 * Hierarquia: Proc. Principal → Processo → Usuário(s) → Documento → Endereço → Interferências
 */

var myChart = echarts.init(document.getElementById('chart-container'), null, {
  renderer: 'canvas',
  useDirtyRect: false
})

var _pendingDelete = null
var _state = { doc: null, users: [], interferences: [] }

myChart.on('click', function (params) {
  var d = params.data
  if (d && d._delete) _showConfirm(d._delete)
})

window.addEventListener('resize', function () { myChart.resize() })

var COLOR = {
  principal:     '#f59e0b',
  processo:      '#8b5cf6',
  usuario:       '#10b981',
  documento:     '#3b82f6',
  endereco:      '#06b6d4',
  interferencia: '#ef4444'
}

function _node(name, value, color, children, del) {
  var n = { name: name, itemStyle: { color: color, borderColor: color } }
  if (value != null && value !== '') n.value = String(value)
  if (children) {
    var f = children.filter(function (c) { return c != null })
    if (f.length) n.children = f
  }
  if (del && del.id != null) n._delete = del
  return n
}

function _v(x) { return (x != null && x !== '') ? String(x) : '—' }

/**
 * @param {Object} doc             - Documento normalizado (flat).
 * @param {Array}  [users]         - Usuários vinculados ao documento.
 * @param {Array}  [interferences] - Interferências vinculadas ao endereço.
 */
function updateChart(doc, users, interferences) {
  if (!myChart || !doc) return

  users         = Array.isArray(users)         ? users         : []
  interferences = Array.isArray(interferences) ? interferences : []

  _state.doc           = doc
  _state.users         = users.slice()
  _state.interferences = interferences.slice()

  /* ── Interferências (filhos do endereço) ─────────────────────────── */
  var interfNodes = interferences.length
    ? interferences.map(function (i) {
        return _node(
          '#' + i.id,
          (i.latitude != null && i.longitude != null)
            ? _v(i.latitude) + ', ' + _v(i.longitude)
            : null,
          COLOR.interferencia,
          null,
          { fn: 'deleteInterference', id: i.id, label: 'Interferência #' + i.id }
        )
      })
    : (doc.latitude != null
        ? [ _node('Interferência', _v(doc.latitude) + ', ' + _v(doc.longitude), COLOR.interferencia) ]
        : [])

  /* ── Endereço (com interferências como filhos) ───────────────────── */
  var enderecoNode = doc.logradouro
    ? _node('Endereço', _v(doc.logradouro), COLOR.endereco,
        interfNodes.length ? interfNodes : null,
        doc.enderecoId
          ? { fn: 'deleteAddress', id: doc.enderecoId, label: doc.logradouro }
          : null)
    : null

  /* ── Documento (com endereço como filho) ─────────────────────────── */
  var documentoNode = _node(
    'Documento', _v(doc.numero), COLOR.documento,
    enderecoNode ? [enderecoNode] : null,
    doc.id
      ? { fn: 'deleteDocument', id: doc.id, label: doc.numero || ('Doc #' + doc.id) }
      : null
  )

  /* ── Usuário(s) (com documento como filho) ───────────────────────── */
  var usuarioNode
  if (users.length > 1) {
    var userLeaves = users.map(function (u) {
      return _node(u.nome || ('ID ' + u.id), u.cpfCnpj || null, COLOR.usuario, null,
        { fn: 'deleteUser', id: u.id, label: u.nome || ('ID ' + u.id) })
    })
    usuarioNode = _node(
      'Usuários (' + users.length + ')', null, COLOR.usuario,
      userLeaves.concat([documentoNode])
    )
  } else if (users.length === 1) {
    var u = users[0]
    usuarioNode = _node(
      u.nome || ('ID ' + u.id), u.cpfCnpj || null, COLOR.usuario,
      [documentoNode],
      { fn: 'deleteUser', id: u.id, label: u.nome || ('ID ' + u.id) }
    )
  } else if (doc.usuarioNome) {
    usuarioNode = _node(
      _v(doc.usuarioNome), doc.cpfCnpj || null, COLOR.usuario,
      [documentoNode],
      doc.usuarioId
        ? { fn: 'deleteUser', id: doc.usuarioId, label: doc.usuarioNome }
        : null
    )
  } else {
    usuarioNode = documentoNode
  }

  /* ── Processo (com usuário como filho) ───────────────────────────── */
  var processoNode = doc.processoNumero
    ? _node('Processo', _v(doc.processoNumero), COLOR.processo,
        [usuarioNode],
        doc.processoId
          ? { fn: 'deleteProcess', id: doc.processoId, label: doc.processoNumero }
          : null)
    : usuarioNode

  /* ── Processo Principal / raiz ───────────────────────────────────── */
  var treeData = doc.anexoNumero
    ? _node('Proc. Principal', _v(doc.anexoNumero), COLOR.principal,
        [processoNode],
        doc.anexoId
          ? { fn: 'deleteAnnex', id: doc.anexoId, label: doc.anexoNumero }
          : null)
    : processoNode

  /* ── Render ──────────────────────────────────────────────────────── */
  myChart.setOption({
    backgroundColor: '#0f172a',
    tooltip: {
      trigger: 'item',
      triggerOn: 'mousemove',
      formatter: function (params) {
        var d = params.data
        var tip = d.value
          ? '<b>' + d.name + '</b>: ' + d.value
          : '<b>' + d.name + '</b>'
        if (d._delete) tip += '<br><span style="color:#94a3b8;font-size:11px">Clique para excluir</span>'
        return tip
      },
      backgroundColor: '#1e293b',
      borderColor: '#334155',
      textStyle: { color: '#e2e8f0', fontSize: 13 }
    },
    series: [{
      type: 'tree',
      data: [treeData],
      top: '5%',
      left: '14%',
      bottom: '5%',
      right: '22%',
      symbolSize: 9,
      orient: 'LR',
      roam: true,
      label: {
        position: 'left',
        verticalAlign: 'middle',
        align: 'right',
        fontSize: 12,
        formatter: function (params) {
          var d = params.data
          var del = d._delete ? ' {del|✕}' : ''
          return d.value
            ? '{n|' + d.name + '} {v|' + d.value + '}' + del
            : '{n|' + d.name + '}' + del
        },
        rich: {
          n:   { color: '#64748b', fontSize: 11 },
          v:   { color: '#e2e8f0', fontSize: 12, fontWeight: 'bold' },
          del: { color: '#ef4444', fontSize: 11 }
        }
      },
      leaves: {
        label: {
          position: 'right',
          verticalAlign: 'middle',
          align: 'left',
          formatter: function (params) {
            var d = params.data
            var del = d._delete ? ' {del|✕}' : ''
            return d.value
              ? '{n|' + d.name + '}: {v|' + d.value + '}' + del
              : '{n|' + d.name + '}' + del
          },
          rich: {
            n:   { color: '#64748b', fontSize: 11 },
            v:   { color: '#e2e8f0', fontSize: 12, fontWeight: 'bold' },
            del: { color: '#ef4444', fontSize: 11 }
          }
        }
      },
      lineStyle: { color: '#334155', width: 1.5, curveness: 0.5 },
      emphasis: { focus: 'descendant' },
      expandAndCollapse: true,
      initialTreeDepth: -1,
      animationDuration: 550,
      animationDurationUpdate: 750
    }]
  }, { notMerge: true })

  myChart.resize()
}

/* ── Tradução de erros do backend ────────────────────────────────────── */
var _TABLE_PT = {
  processo:      'processo',
  documento:     'documento',
  usuario:       'usuário',
  endereco:      'endereço',
  interferencia: 'interferência',
  anexo:         'processo principal'
}

var _ENTITY_PT = {
  deleteUser:         'usuário',
  deleteAddress:      'endereço',
  deleteInterference: 'interferência',
  deleteProcess:      'processo',
  deleteAnnex:        'processo principal',
  deleteDocument:     'documento'
}

function _friendlyError(err, del) {
  var msg = (err && err.message) ? err.message : String(err)

  var fkMatch = msg.match(/on table "?(\w+)"?/i)
  if (fkMatch || /foreign key/i.test(msg)) {
    var blockedBy  = fkMatch ? (_TABLE_PT[fkMatch[1].toLowerCase()] || fkMatch[1]) : 'outro registro'
    var entityPt   = (del && _ENTITY_PT[del.fn]) || 'registro'
    return 'Não é possível excluir este(a) ' + entityPt + ': há um(a) ' + blockedBy + ' vinculado(a) a ele(a).'
  }

  var httpMatch = msg.match(/HTTP (\d{3})/i)
  if (httpMatch) {
    var code = Number(httpMatch[1])
    if (code === 404) return 'Registro não encontrado no servidor.'
    if (code === 409) return 'Conflito: registro está em uso por outro dado.'
    if (code >= 500) return 'Erro interno no servidor. Tente novamente.'
  }

  return 'Erro ao excluir. Tente novamente.'
}

/* ── Confirmação de exclusão ──────────────────────────────────────────── */
function _showConfirm(del) {
  _pendingDelete = del
  document.getElementById('dc-confirm-msg').textContent =
    'Excluir "' + del.label + '"? Esta ação não pode ser desfeita.'
  document.getElementById('dc-confirm').removeAttribute('hidden')
}

function _hideConfirm() {
  _pendingDelete = null
  document.getElementById('dc-confirm').setAttribute('hidden', '')
}

document.getElementById('dc-confirm-cancel').addEventListener('click', _hideConfirm)

document.getElementById('dc-confirm-ok').addEventListener('click', async function () {
  if (!_pendingDelete) return
  var del = _pendingDelete
  _hideConfirm()

  try {
    var svc = window._services
    if (!svc) throw new Error('Serviços não disponíveis')

    if      (del.fn === 'deleteUser')          await svc.deleteUser(del.id)
    else if (del.fn === 'deleteAddress')       await svc.deleteAddress(del.id)
    else if (del.fn === 'deleteInterference')  await svc.deleteInterference(del.id)
    else if (del.fn === 'deleteProcess')       await svc.deleteProcess(del.id)
    else if (del.fn === 'deleteAnnex')         await svc.deleteAnnex(del.id)

    _removeFromState(del)
    _showToast('Excluído com sucesso.', false)
  } catch (err) {
    console.error('document-chart: erro ao excluir', err)
    _showToast(_friendlyError(err, del), true)
  }
})

function _removeFromState(del) {
  var d = _state.doc
  if (!d) return

  if (del.fn === 'deleteUser') {
    _state.users = _state.users.filter(function (u) { return u.id !== del.id })
    if (_state.users.length === 0 && d.usuarioId === del.id) {
      d.usuarioId = null; d.usuarioNome = null; d.cpfCnpj = null
    }
  } else if (del.fn === 'deleteAddress') {
    d.enderecoId = null; d.logradouro = null
    d.bairro = null; d.cidade = null; d.cep = null
    _state.interferences = []
  } else if (del.fn === 'deleteInterference') {
    _state.interferences = _state.interferences.filter(function (i) { return i.id !== del.id })
    if (_state.interferences.length === 0) { d.latitude = null; d.longitude = null }
  } else if (del.fn === 'deleteProcess') {
    d.processoId = null; d.processoNumero = null
    d.anexoId    = null; d.anexoNumero    = null
  } else if (del.fn === 'deleteAnnex') {
    d.anexoId = null; d.anexoNumero = null
  }

  updateChart(_state.doc, _state.users, _state.interferences)
}

function _showToast(msg, isError) {
  var t = document.getElementById('dc-toast')
  t.textContent = msg
  t.className = 'dc-toast ' + (isError ? 'dc-toast-error' : 'dc-toast-ok')
  t.removeAttribute('hidden')
  setTimeout(function () { t.setAttribute('hidden', '') }, isError ? 5000 : 3000)
}
