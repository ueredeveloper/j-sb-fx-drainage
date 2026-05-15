/**
 * @file write-json.js
 * @description Utilitários para gravar objetos JavaScript como arquivo JSON.
 * Usado principalmente para inspecionar respostas brutas da API em desenvolvimento.
 */

const fs   = require('fs')
const path = require('path')

/**
 * @description Serializa `data` e grava em `filePath`, sobrescrevendo o conteúdo anterior.
 * @param {string} filePath - Caminho absoluto do arquivo de destino.
 * @param {*} data - Valor serializável para JSON.
 */
function writeJson(filePath, data) {
  fs.mkdirSync(path.dirname(filePath), { recursive: true })
  fs.writeFileSync(filePath, JSON.stringify(data, null, 2), 'utf-8')
}

/**
 * @description Adiciona `data` a um array no arquivo `filePath`.
 * Se o arquivo não existir, cria com `[data]`.
 * Se existir e contiver um array, faz push. Caso contrário, envolve o conteúdo anterior em array.
 * @param {string} filePath - Caminho absoluto do arquivo de destino.
 * @param {*} data - Valor serializável para JSON.
 */
function appendJson(filePath, data) {
  fs.mkdirSync(path.dirname(filePath), { recursive: true })
  let arr = []
  if (fs.existsSync(filePath)) {
    try {
      const parsed = JSON.parse(fs.readFileSync(filePath, 'utf-8'))
      arr = Array.isArray(parsed) ? parsed : [parsed]
    } catch { arr = [] }
  }
  arr.push(data)
  fs.writeFileSync(filePath, JSON.stringify(arr, null, 2), 'utf-8')
}

module.exports = { writeJson, appendJson }
