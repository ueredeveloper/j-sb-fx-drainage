const { contextBridge, ipcRenderer } = require('electron')

contextBridge.exposeInMainWorld('electronAPI', {
  onMessage: (callback) => ipcRenderer.on('message', (_event, value) => callback(value))
})

/** @description Serviço de documentos. */
contextBridge.exposeInMainWorld('documentService', {
  fetchByParam: (keyword) => ipcRenderer.invoke('document:fetchByParam', keyword),
  save:         (doc)     => ipcRenderer.invoke('document:save', doc),
  update:       (doc)     => ipcRenderer.invoke('document:update', doc),
  deleteById:   (id)      => ipcRenderer.invoke('document:deleteById', id)
})

/** @description Serviço de tabelas de domínio (selects de interferência). */
contextBridge.exposeInMainWorld('domainService', {
  fetchAll:     () => ipcRenderer.invoke('domain:fetchAll'),
  listBacias:   () => ipcRenderer.invoke('domain:listBacias'),
  listUnidades: () => ipcRenderer.invoke('domain:listUnidades')
})

/** @description Serviço de endereços. */
contextBridge.exposeInMainWorld('addressService', {
  fetchByKeyword: (keyword) => ipcRenderer.invoke('address:fetchByKeyword', keyword),
  save:           (addr)    => ipcRenderer.invoke('address:save', addr),
  deleteById:     (id)      => ipcRenderer.invoke('address:deleteById', id)
})

/** @description Serviço de usuários. */
contextBridge.exposeInMainWorld('userService', {
  fetchByKeyword: (keyword) => ipcRenderer.invoke('user:fetchByKeyword', keyword),
  save:           (user)    => ipcRenderer.invoke('user:save', user),
  deleteById:     (id)      => ipcRenderer.invoke('user:deleteById', id)
})

/** @description Serviço de processos. */
contextBridge.exposeInMainWorld('processService', {
  fetchByKeyword: (keyword) => ipcRenderer.invoke('process:fetchByKeyword', keyword),
  save:           (proc)    => ipcRenderer.invoke('process:save', proc),
  deleteById:     (id)      => ipcRenderer.invoke('process:deleteById', id)
})

/** @description Serviço de anexos (processos principais). */
contextBridge.exposeInMainWorld('annexService', {
  fetchByKeyword: (keyword) => ipcRenderer.invoke('annex:fetchByKeyword', keyword),
  save:           (annex)   => ipcRenderer.invoke('annex:save', annex),
  deleteById:     (id)      => ipcRenderer.invoke('annex:deleteById', id)
})

/** @description Serviço de interferências hídricas. */
contextBridge.exposeInMainWorld('interferenceService', {
  fetchByKeyword: (keyword) => ipcRenderer.invoke('interference:fetchByKeyword', keyword),
  save:           (obj)     => ipcRenderer.invoke('interference:save', obj),
  deleteById:     (id)      => ipcRenderer.invoke('interference:deleteById', id)
})

/** @description Serviço de bacias hidrográficas. */
contextBridge.exposeInMainWorld('baciaService', {
  listAll:     ()         => ipcRenderer.invoke('bacia:listAll'),
  findByPoint: (lat, lng) => ipcRenderer.invoke('bacia:findByPoint', lat, lng)
})

/** @description Serviço de unidades hidrográficas. */
contextBridge.exposeInMainWorld('unidadeService', {
  listAll:     ()         => ipcRenderer.invoke('unidade:listAll'),
  findByPoint: (lat, lng) => ipcRenderer.invoke('unidade:findByPoint', lat, lng)
})

/** @description Utilitário de conversão de coordenadas geográficas. */
contextBridge.exposeInMainWorld('coordService', {
  utmToDecimal: (e, n, z, h)                          => ipcRenderer.invoke('coords:utmToDecimal', e, n, z, h),
  dmsToDecimal: (ld, lm, ls, ldir, od, om, os, odir)  => ipcRenderer.invoke('coords:dmsToDecimal', ld, lm, ls, ldir, od, om, os, odir)
})
