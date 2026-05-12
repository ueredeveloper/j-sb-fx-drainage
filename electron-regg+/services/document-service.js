/**
 * @file document-service.js
 * @description Serviço HTTP para a entidade Documento.
 * Espelha DocumentoService.java: fetchByParam chama
 *   GET /documents/search-documents-by-param?param=<keyword>
 * A resposta aninhada da API é normalizada para um objeto plano antes de
 * ser retornada ao renderer, mantendo a interface estável independente de
 * mudanças no backend.
 */

const path      = require('path')
const { appendJson } = require('../utils/write-json')

const BASE_URL    = 'https://app-sis-out-srh-backend-01-h3hkbcf5f8dubbdy.brazilsouth-01.azurewebsites.net'
const OUT_FETCH   = path.join(__dirname, 'json', 'document-fetch-by-param.json')
const OUT_SAVE    = path.join(__dirname, 'json', 'document-save-response.json')
const OUT_UPDATE  = path.join(__dirname, 'json', 'document-update-response.json')
const OUT_DELETE  = path.join(__dirname, 'json', 'document-delete-response.json')

class DocumentService {
  /**
   * @description Busca documentos cujo número, SEI, usuário ou CPF/CNPJ
   * correspondam ao termo informado.
   * @param {string} keyword - Termo de busca.
   * @returns {Promise<Object[]>} Lista de documentos normalizados.
   */
  async fetchByParam(keyword) {
    const url = `${BASE_URL}/documents/search-documents-by-param?param=${encodeURIComponent(keyword)}`
    const res = await fetch(url)
    if (!res.ok) throw new Error(`fetchByParam: HTTP ${res.status} ${res.statusText}`)
    const data = await res.json()

    if (Array.isArray(data) && data.length > 0) appendJson(OUT_FETCH, data[0])

    return Array.isArray(data) ? data.map(d => this._normalize(d)) : []
  }

  /**
   * @description Persiste um novo documento.
   * @param {Object} doc
   * @returns {Promise<Object>} Documento normalizado retornado pela API.
   */
  async save(doc) {
    const payload = this._toPayload(doc)
   
    const res = await fetch(`${BASE_URL}/documents/upsert-document`, {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify(payload)
    })
    if (!res.ok) throw new Error(`save: HTTP ${res.status} ${res.statusText}`)
    const raw = await res.json()
    appendJson(OUT_SAVE, raw)
    return this._normalize(raw.object ?? raw)
  }

  /**
   * @description Atualiza os dados de um documento existente.
   * Usa o mesmo endpoint de upsert — o backend distingue create/update pelo campo `id`.
   * @param {Object} doc - Deve conter `id`.
   * @returns {Promise<Object>} Documento normalizado retornado pela API.
   */
  async update(doc) {
    const payload = this._toPayload(doc)
   
    const res = await fetch(`${BASE_URL}/documents/upsert-document`, {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify(payload)
    })
    if (!res.ok) throw new Error(`update: HTTP ${res.status} ${res.statusText}`)
    const raw = await res.json()
    appendJson(OUT_UPDATE, raw)
    return this._normalize(raw.object ?? raw)
  }

  /**
   * @description Remove um documento pelo seu ID.
   * @param {number} id
   * @returns {Promise<void>}
   */
  async deleteById(id) {
    const res = await fetch(`${BASE_URL}/documents/delete-document?id=${id}`, { method: 'DELETE' })
    if (!res.ok) throw new Error(`deleteById: HTTP ${res.status} ${res.statusText}`)
    const text = await res.text()
    appendJson(OUT_DELETE, text ? JSON.parse(text) : { deleted: true, id: Number(id) })
  }

  /**
   * @description Converte o objeto aninhado da API para um objeto plano
   * compatível com os componentes do renderer.
   * Estrutura da API:
   *   { id, numero, numeroSei,
   *     tipoDocumento: { id, descricao },
   *     endereco: { id, logradouro, interferencias: [{ latitude, longitude }] },
   *     processo: { id, numero, anexo: { id, numero } },
   *     usuarios: [{ id, nome, cpfCnpj }] }
   * @param {Object} doc - Documento bruto da API.
   * @returns {Object} Documento com campos planos.
   */
  _normalize(doc) {
    const tipo = doc.tipoDocumento        || {}
    const end  = doc.endereco             || {}
    const proc = doc.processo             || {}
    const anx  = proc.anexo               || {}
    const inf  = (end.interferencias || [])[0] || {}
    const user = (doc.usuarios       || [])[0] || {}

    return {
      id:                doc.id,
      numero:            doc.numero,
      numeroSei:         doc.numeroSei,
      tipoDocumentoId:   tipo.id          ?? null,
      tipoDocumentoNome: tipo.descricao   ?? null,
      enderecoId:        end.id           ?? null,
      logradouro:        end.logradouro   ?? null,
      bairro:            end.bairro       ?? null,
      cidade:            end.cidade       ?? null,
      cep:               end.cep          ?? null,
      enderecoEstado:    (end.estado?.descricao ?? end.estado?.sigla) ?? null,
      enderecoEstadoId:  end.estado?.id ?? null,
      latitude:          inf.latitude     ?? null,
      longitude:         inf.longitude    ?? null,
      processoId:        proc.id          ?? null,
      processoNumero:    proc.numero      ?? null,
      anexoId:           anx.id           ?? null,
      anexoNumero:       anx.numero       ?? null,
      usuarioId:         user.id          ?? null,
      usuarioNome:       user.nome        ?? null,
      cpfCnpj:           user.cpfCnpj     ?? null
    }
  }

  /**
   * @description Converte o objeto plano do formulário para o formato aninhado
   * esperado pelo backend no upsert.
   *
   * Campos de entrada flat (getValue):
   *   id?, documentId?, number, numberSei, typeId, typeDescricao,
   *   addressId, processId, annexId, userId
   * Campos de entrada rich (getData):
   *   addressData:  { id, logradouro, bairro, cidade, cep, estado }
   *   processData:  { id, numero, usuarioId, usuarioNome, anexoId, anexoNumero }
   *   annexData:    { id, numero, ... }
   *   userData:     { id, nome, cpfCnpj }
   *
   * @param {Object} data
   * @returns {Object} Payload aninhado para a API.
   */
  _toPayload(data) {
    const payload = {}

    const id = data.id ?? data.documentId
    if (id) payload.id = Number(id)

    if (data.number)    payload.numero    = data.number
    if (data.numberSei) payload.numeroSei = data.numberSei

    // tipoDocumento
    if (data.typeId) {
      payload.tipoDocumento = { id: Number(data.typeId) }
      if (data.typeDescricao) payload.tipoDocumento.descricao = data.typeDescricao
    }

    // endereco
    const addr = data.addressData || {}
    if (data.addressId) {
      payload.endereco = { id: Number(data.addressId) }
      if (addr.logradouro) payload.endereco.logradouro = addr.logradouro
      if (addr.cidade)     payload.endereco.cidade     = addr.cidade
      if (addr.bairro)     payload.endereco.bairro     = addr.bairro
      if (addr.cep)        payload.endereco.cep        = addr.cep
      if (addr.estado)     payload.endereco.estado     = { descricao: addr.estado }
    }

    // processo + anexo
    const proc = data.processData || {}
    const anx  = data.annexData   || {}

    if (data.processId || data.annexId) {
      payload.processo = {}

      if (data.processId) payload.processo.id     = Number(data.processId)
      if (proc.numero)    payload.processo.numero  = proc.numero

      // processo.usuario
      if (proc.usuarioId || proc.usuarioNome) {
        payload.processo.usuario = {}
        if (proc.usuarioId)   payload.processo.usuario.id   = Number(proc.usuarioId)
        if (proc.usuarioNome) payload.processo.usuario.nome = proc.usuarioNome
      }

      // processo.anexo
      if (data.annexId) {
        payload.processo.anexo = { id: Number(data.annexId) }
        if (anx.numero) payload.processo.anexo.numero = anx.numero

        // processo.anexo.processos (referência circular ao próprio processo)
        if (data.processId) {
          const procRef = { id: Number(data.processId) }
          if (proc.numero) procRef.numero = proc.numero
          if (proc.usuarioId || proc.usuarioNome) {
            procRef.usuario = {}
            if (proc.usuarioId)   procRef.usuario.id   = Number(proc.usuarioId)
            if (proc.usuarioNome) procRef.usuario.nome = proc.usuarioNome
          }
          payload.processo.anexo.processos = [procRef]
        }
      }
    }

    // usuarios (requerente)
    const user = data.userData || {}
    if (data.userId) {
      const u = { id: Number(data.userId) }
      if (user.nome)    u.nome    = user.nome
      if (user.cpfCnpj) u.cpfCnpj = user.cpfCnpj
      payload.usuarios = [u]
    }

    return payload
  }
}

module.exports = DocumentService
