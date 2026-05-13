/**
 * @file hidrogeo-poroso-service.js
 * @description Serviço HTTP para a entidade Hidrogeo Poroso.
 * Espelha HidrogeoPorosoService.java:
 *   GET /hydrogeo-poroso/list-all
 *   GET /hydrogeo-poroso/find-by-point?latitude=&longitude=
 *   GET /poroso/list-by-cod-plan?codPlan=
 */

const BASE_URL = 'https://app-sis-out-srh-backend-01-h3hkbcf5f8dubbdy.brazilsouth-01.azurewebsites.net'

class HidrogeoPorosoService {
  _map(p) {
    return {
      id:       p.objectid,
      codPlan:  p.codPlan  ?? null,
      descricao: p.sistema ?? p.codPlan ?? '',
      qMedia:   p.qMedia   ?? null
    }
  }

  /**
   * @description Lista todos os sistemas porosos cadastrados.
   * @returns {Promise<Array<{id, codPlan, descricao, qMedia}>>}
   */
  async listAll() {
    const res = await fetch(`${BASE_URL}/hydrogeo-poroso/list-all`)
    if (!res.ok) throw new Error(`poroso/listAll: HTTP ${res.status} ${res.statusText}`)
    const data  = await res.json()
    const items = Array.isArray(data) ? data : Object.values(data)
    items.sort((a, b) => (a.sistema ?? '').localeCompare(b.sistema ?? '', 'pt'))
    return items.map(p => this._map(p))
  }

  /**
   * @description Busca o sistema poroso que contém o ponto geográfico informado.
   * @param {number|string} lat
   * @param {number|string} lng
   * @returns {Promise<Array<{id, codPlan, descricao, qMedia}>>}
   */
  async findByPoint(lat, lng) {
    const res = await fetch(`${BASE_URL}/hydrogeo-poroso/find-by-point?latitude=${lat}&longitude=${lng}`)
    if (!res.ok) throw new Error(`poroso/findByPoint: HTTP ${res.status} ${res.statusText}`)
    const data  = await res.json()
    const items = Array.isArray(data) ? data : Object.values(data)
    return items.map(p => this._map(p))
  }

  /**
   * @description Busca sistemas porosos pelo código de planejamento.
   * @param {string} codPlan
   * @returns {Promise<Array<{id, codPlan, descricao, qMedia}>>}
   */
  async findByCodPlan(codPlan) {
    const res = await fetch(`${BASE_URL}/poroso/list-by-cod-plan?codPlan=${encodeURIComponent(codPlan)}`)
    if (!res.ok) throw new Error(`poroso/findByCodPlan: HTTP ${res.status} ${res.statusText}`)
    const data  = await res.json()
    const items = Array.isArray(data) ? data : Object.values(data)
    return items.map(p => this._map(p))
  }
}

module.exports = HidrogeoPorosoService
