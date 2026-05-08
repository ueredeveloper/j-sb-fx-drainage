/**
 * @file address-service.js
 * @description Serviço HTTP para a entidade Endereço.
 * Espelha EnderecoService.java: fetchAddressByKeyword chama
 *   GET /addresses/search-address-by-param?param=<keyword>
 * A resposta aninhada é normalizada para um objeto plano antes de retornar.
 */

const path          = require('path')
const { writeJson } = require('../utils/write-json')

const BASE_URL   = 'https://app-sis-out-srh-backend-01-h3hkbcf5f8dubbdy.brazilsouth-01.azurewebsites.net'
const SAMPLE_OUT = path.join(__dirname, 'json', 'address-fetch-by-keyword.json')

class AddressService {
  /**
   * @description Busca endereços por logradouro, cidade ou bairro.
   * Equivale a: GET /addresses/search-address-by-param?param=<keyword>
   * @param {string} keyword - Termo de busca.
   * @returns {Promise<Object[]>} Lista de endereços normalizados.
   */
  async fetchByKeyword(keyword) {
    const url = `${BASE_URL}/addresses/search-address-by-param?param=${encodeURIComponent(keyword)}`
    const res = await fetch(url)
    if (!res.ok) throw new Error(`fetchByKeyword: HTTP ${res.status} ${res.statusText}`)
    const data = await res.json()
    if (Array.isArray(data) && data.length > 0) writeJson(SAMPLE_OUT, data[0])
    return Array.isArray(data) ? data.map(a => this._normalize(a)) : []
  }

  /**
   * @description Persiste ou atualiza um endereço.
   * @param {Object} address
   * @returns {Promise<Object>}
   */
  async save(address) {
    const res = await fetch(`${BASE_URL}/addresses/upsert-address`, {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify(address)
    })
    if (!res.ok) throw new Error(`save: HTTP ${res.status} ${res.statusText}`)
    return res.json()
  }

  /**
   * @description Remove um endereço pelo ID.
   * @param {number} id
   * @returns {Promise<void>}
   */
  async deleteById(id) {
    const res = await fetch(`${BASE_URL}/addresses/delete-address?id=${id}`, { method: 'DELETE' })
    if (!res.ok) throw new Error(`deleteById: HTTP ${res.status} ${res.statusText}`)
  }

  /**
   * @description Normaliza o objeto de endereço da API para campos planos.
   * Estrutura da API: { id, logradouro, bairro, cidade, cep, estado: { id, sigla } }
   * @param {Object} addr
   * @returns {Object}
   */
  _normalize(addr) {
    const estado = addr.estado || {}
    return {
      id:         addr.id,
      logradouro: addr.logradouro ?? null,
      bairro:     addr.bairro     ?? null,
      cidade:     addr.cidade     ?? null,
      cep:        addr.cep        ?? null,
      estado:     estado.descricao ?? estado.sigla ?? null
    }
  }
}

module.exports = AddressService
