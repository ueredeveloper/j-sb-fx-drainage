/**
 * @file user-service.js
 * @description Serviço HTTP para a entidade Usuário.
 * Espelha UsuarioService.java: listByName chama
 *   GET /users/search-users-by-param?param=<keyword>
 */

const path          = require('path')
const { appendJson } = require('../utils/write-json')

const BASE_URL   = 'https://app-sis-out-srh-backend-01-h3hkbcf5f8dubbdy.brazilsouth-01.azurewebsites.net'
const OUT_FETCH  = path.join(__dirname, 'json', 'user-fetch-by-keyword.json')
const OUT_SAVE   = path.join(__dirname, 'json', 'user-save-response.json')
const OUT_DELETE = path.join(__dirname, 'json', 'user-delete-response.json')

class UserService {
  /**
   * @description Busca usuários por nome ou CPF/CNPJ.
   * Equivale a: GET /users/search-users-by-param?param=<keyword>
   * @param {string} keyword - Termo de busca.
   * @returns {Promise<Object[]>} Lista de usuários.
   */
  async fetchByKeyword(keyword) {
    const url = `${BASE_URL}/users/search-users-by-param?param=${encodeURIComponent(keyword)}`
    const res = await fetch(url)
    if (!res.ok) throw new Error(`fetchByKeyword: HTTP ${res.status} ${res.statusText}`)
    const data = await res.json()
    if (Array.isArray(data) && data.length > 0) appendJson(OUT_FETCH, data[0])
    return Array.isArray(data) ? data : []
  }

  /**
   * @description Persiste ou atualiza um usuário.
   * @param {Object} user
   * @returns {Promise<Object>}
   */
  async save(user) {
    const res = await fetch(`${BASE_URL}/users/upsert-user`, {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify(user)
    })
    if (!res.ok) throw new Error(`save: HTTP ${res.status} ${res.statusText}`)
    const raw = await res.json()
    appendJson(OUT_SAVE, raw)
    return raw
  }

  /**
   * @description Remove um usuário pelo ID.
   * @param {number} id
   * @returns {Promise<void>}
   */
  async deleteById(id) {
    const res = await fetch(`${BASE_URL}/users/delete-user-by-id?id=${id}`, { method: 'DELETE' })
    if (!res.ok) throw new Error(`deleteById: HTTP ${res.status} ${res.statusText}`)
    const text = await res.text()
    appendJson(OUT_DELETE, text ? JSON.parse(text) : { deleted: true, id: Number(id) })
  }
}

module.exports = UserService
