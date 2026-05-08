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
const { writeJson } = require('../utils/write-json')

const BASE_URL   = 'https://app-sis-out-srh-backend-01-h3hkbcf5f8dubbdy.brazilsouth-01.azurewebsites.net'
const SAMPLE_OUT = path.join(__dirname, 'json', 'document-fetch-by-param.json')

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

    // Salva o primeiro registro da array em uma pasta para avaliação (...\services\json\document-fetch-by-param.json)
    if (Array.isArray(data) && data.length > 0) writeJson(SAMPLE_OUT, data[0])

    return Array.isArray(data) ? data.map(d => this._normalize(d)) : []
  }

  /**
   * @description Persiste um novo documento.
   * @param {Object} doc
   * @returns {Promise<Object>}
   */
  async save(doc) {
    const res = await fetch(`${BASE_URL}/documents`, {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify(doc)
    })
    if (!res.ok) throw new Error(`save: HTTP ${res.status} ${res.statusText}`)
    return res.json()
  }

  /**
   * @description Atualiza os dados de um documento existente.
   * @param {Object} doc - Deve conter `id`.
   * @returns {Promise<Object>}
   */
  async update(doc) {
    const res = await fetch(`${BASE_URL}/documents/${doc.id}`, {
      method:  'PUT',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify(doc)
    })
    if (!res.ok) throw new Error(`update: HTTP ${res.status} ${res.statusText}`)
    return res.json()
  }

  /**
   * @description Remove um documento pelo seu ID.
   * @param {number} id
   * @returns {Promise<void>}
   */
  async deleteById(id) {
    const res = await fetch(`${BASE_URL}/documents/${id}`, { method: 'DELETE' })
    if (!res.ok) throw new Error(`deleteById: HTTP ${res.status} ${res.statusText}`)
  }

  /**
   * @description Converte o objeto aninhado da API para um objeto plano
   * compatível com os componentes do renderer.
   * Estrutura da API:
   *   { tipoDocumento: { id, descricao },
   *     endereco: { id, logradouro, ..., interferencias: [{ latitude, longitude }] },
   *     processo: { id, numero, usuario: { id, nome, cpfCnpj }, anexo: { id, numero } } }
   * @param {Object} doc - Documento bruto da API.
   * @returns {Object} Documento com campos planos.
   */
  _normalize(doc) {
    const tipo = doc.tipoDocumento || {}
    const end  = doc.endereco      || {}
    const proc = doc.processo      || {}
    const user = proc.usuario      || {}
    const anx  = proc.anexo        || {}
    const inf  = (end.interferencias || [])[0] || {}

    return {
      id:                doc.id,
      numero:            doc.numero,
      numeroSei:         doc.numeroSei,
      tipoDocumentoId:   tipo.id   ?? null,
      tipoDocumentoNome: tipo.descricao ?? null,
      enderecoId:        end.id    ?? null,
      logradouro:        end.logradouro ?? null,
      bairro:            end.bairro    ?? null,
      cidade:            end.cidade    ?? null,
      cep:               end.cep       ?? null,
      latitude:          inf.latitude  ?? null,
      longitude:         inf.longitude ?? null,
      processoId:        proc.id     ?? null,
      processoNumero:    proc.numero  ?? null,
      anexoId:           anx.id      ?? null,
      anexoNumero:       anx.numero   ?? null,
      usuarioId:         user.id      ?? null,
      usuarioNome:       user.nome    ?? null,
      cpfCnpj:           user.cpfCnpj ?? null
    }
  }
}

module.exports = DocumentService
