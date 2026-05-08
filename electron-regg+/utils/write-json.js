/**
 * @file write-json.js
 * @description Utilitário para gravar objetos JavaScript como arquivo JSON.
 * Usado principalmente para inspecionar respostas brutas da API em desenvolvimento.
 */

const fs   = require('fs')
const path = require('path')

/**
 * @description Serializa `data` e grava em `filePath`, criando diretórios
 * intermediários se necessário.
 * @param {string} filePath - Caminho absoluto do arquivo de destino.
 * @param {*} data - Valor serializável para JSON.
 */
function writeJson(filePath, data) {
  fs.mkdirSync(path.dirname(filePath), { recursive: true })
  fs.writeFileSync(filePath, JSON.stringify(data, null, 2), 'utf-8')
}

module.exports = { writeJson }
