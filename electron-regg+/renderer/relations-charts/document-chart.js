/**
 * @file document-chart.js
 * @description Inicializa o ECharts e expõe updateChart(doc, users, interferences).
 * Exibe: Documento (número) → Usuários[], Endereço (→ Interferências[]), Processo (→ Proc. Principal).
 * Cada nó com ID suporta exclusão via clique, confirmação e chamada a window._services.
 */

var myChart = echarts.init(document.getElementById('chart-container'), null, {
  renderer: 'canvas',
  useDirtyRect: false
})

var _pendingDelete = null

/**
 * @description Constrói e renderiza o gráfico de árvore com os dados do documento.
 * @param {Object} doc             - Documento normalizado (flat).
 * @param {Array}  [users]         - Usuários retornados por userService.fetchByDocumentId.
 * @param {Array}  [interferences] - Interferências retornadas por interferenceService.fetchByKeyword.
 */
function updateChart(doc, users, interferences) {
  users         = Array.isArray(users)         ? users         : []
  interferences = Array.isArray(interferences) ? interferences : []

  var COLOR = {
    root:          '#3b82f6',
    usuario:       '#10b981',
    endereco:      '#f59e0b',
    interferencia: '#ef4444',
    processo:      '#8b5cf6',
    leaf:          '#475569'
  }

  /**
   * @description Cria um nó de árvore para o ECharts.
   * @param {string}      name
   * @param {*}           value
   * @param {string}      color
   * @param {Array|null}  [children]
   * @param {Object|null} [del]     - { fn, id, label } para ação de exclusão no clique.
   * @returns {Object}
   */
  function node(name, value, color, children, del) {
    var n = { name: name, itemStyle: { color: color, borderColor: color } }
    var val = (value != null && value !== '') ? String(value) : null
    if (val) n.value = val
    if (children) {
      var filtered = children.filter(function(c) { return c != null })
      if (filtered.length) n.children = filtered
    }
    if (del && del.id != null) n._delete = del
    return n
  }

  var v = function(x) { return (x != null && x !== '') ? String(x) : '—' }

  /* ── Usuários ─────────────────────────────────────────────────────────── */
  var userNodes = users.length
    ? users.map(function(u) {
        return node(u.nome || ('ID ' + u.id), u.cpfCnpj || null, COLOR.usuario, null,
          { fn: 'deleteUser', id: u.id, label: u.nome || ('ID ' + u.id) })
      })
    : [ node(v(doc.usuarioNome), doc.cpfCnpj || null, COLOR.usuario, null,
        doc.usuarioId
          ? { fn: 'deleteUser', id: doc.usuarioId, label: doc.usuarioNome || ('ID ' + doc.usuarioId) }
          : null) ]

  /* ── Interferências do endereço ───────────────────────────────────────── */
  var interfNodes = interferences.length
    ? interferences.map(function(i) {
        return node(
          '#' + i.id,
          (i.latitude != null && i.longitude != null)
            ? v(i.latitude) + ', ' + v(i.longitude)
            : null,
          COLOR.interferencia,
          null,
          { fn: 'deleteInterference', id: i.id, label: 'Interferência #' + i.id }
        )
      })
    : (doc.latitude != null
        ? [ node('Interferência', v(doc.latitude) + ', ' + v(doc.longitude), COLOR.interferencia) ]
        : [])

  /* ── Endereço ─────────────────────────────────────────────────────────── */
  var enderecoNode = node(
    'Endereço', doc.logradouro, COLOR.endereco,
    interfNodes.length ? interfNodes : null,
    doc.enderecoId
      ? { fn: 'deleteAddress', id: doc.enderecoId, label: doc.logradouro || ('Endereço ID ' + doc.enderecoId) }
      : null
  )

  /* ── Processo ─────────────────────────────────────────────────────────── */
  var processoChildren = []
  if (doc.anexoId) {
    processoChildren.push(node(
      'Proc. Principal', doc.anexoNumero, COLOR.leaf, null,
      { fn: 'deleteAnnex', id: doc.anexoId, label: doc.anexoNumero || ('Anexo ID ' + doc.anexoId) }
    ))
  }

  var processoNode = doc.processoNumero
    ? node('Processo', doc.processoNumero, COLOR.processo,
        processoChildren.length ? processoChildren : null,
        doc.processoId
          ? { fn: 'deleteProcess', id: doc.processoId, label: doc.processoNumero || ('Processo ID ' + doc.processoId) }
          : null)
    : null

  /* ── Raiz ─────────────────────────────────────────────────────────────── */
  var treeData = node('Documento', doc.numero, COLOR.root, [
    node('Usuários (' + userNodes.length + ')', null, COLOR.usuario, userNodes),
    enderecoNode,
    processoNode
  ])

  myChart.setOption({
    backgroundColor: '#0f172a',
    tooltip: {
      trigger: 'item',
      triggerOn: 'mousemove',
      formatter: function(params) {
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
        color: '#94a3b8',
        formatter: function(params) {
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
          formatter: function(params) {
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
      lineStyle: {
        color: '#334155',
        width: 1.5,
        curveness: 0.5
      },
      emphasis: { focus: 'descendant' },
      expandAndCollapse: false,
      animationDuration: 550,
      animationDurationUpdate: 750
    }]
  })
}

/* ── Clique em nó: abre confirmação de exclusão ───────────────────────────── */
myChart.on('click', function(params) {
  var d = params.data
  if (d && d._delete) _showConfirm(d._delete)
})

/**
 * @description Exibe o dialog de confirmação de exclusão.
 * @param {{ fn: string, id: number, label: string }} del
 */
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

document.getElementById('dc-confirm-ok').addEventListener('click', async function() {
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

    _showToast('Excluído com sucesso.', false)
  } catch (err) {
    console.error('document-chart: erro ao excluir', err)
    _showToast('Erro ao excluir. Tente novamente.', true)
  }
})

/**
 * @description Exibe um toast temporário no rodapé do chart.
 * @param {string}  msg
 * @param {boolean} isError
 */
function _showToast(msg, isError) {
  var t = document.getElementById('dc-toast')
  t.textContent = msg
  t.className = 'dc-toast ' + (isError ? 'dc-toast-error' : 'dc-toast-ok')
  t.removeAttribute('hidden')
  setTimeout(function() { t.setAttribute('hidden', '') }, 3000)
}

window.addEventListener('resize', function() { myChart.resize() })
