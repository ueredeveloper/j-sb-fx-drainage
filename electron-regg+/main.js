const { app, BrowserWindow, ipcMain } = require('electron')
const path = require('path')
const fs   = require('fs')

const DocumentService      = require('./services/document-service')
const DomainService        = require('./services/domain-service')
const AddressService       = require('./services/address-service')
const UserService          = require('./services/user-service')
const ProcessService       = require('./services/process-service')
const AnnexService         = require('./services/annex-service')
const InterferenceService  = require('./services/interference-service')
const BaciaService         = require('./services/bacia-service')
const UnidadeService       = require('./services/unidade-service')
const CoordConverter       = require('./utils/coord-converter')

const _docSvc     = new DocumentService()
const _domainSvc  = new DomainService()
const _addrSvc    = new AddressService()
const _userSvc    = new UserService()
const _procSvc    = new ProcessService()
const _annexSvc   = new AnnexService()
const _ifSvc      = new InterferenceService()
const _baciaSvc   = new BaciaService()
const _unidadeSvc = new UnidadeService()

/* Caminho real do electron.exe lido do path.txt do pacote npm.
   Necessário porque dentro do processo Electron require('electron')
   retorna as APIs, não o executável. */
const _electronExec = path.join(
  __dirname, 'node_modules', 'electron', 'dist',
  fs.readFileSync(
    path.join(__dirname, 'node_modules', 'electron', 'path.txt'), 'utf-8'
  ).trim()
)

/* Hot reload ativo apenas fora do build empacotado (modo desenvolvimento).
   - Mudanças em renderer/ → recarrega só a janela (rápido).
   - Mudanças em main.js ou preload.js → reinicia o processo inteiro. */
if (!app.isPackaged) {
  const reload = require('electron-reload')

  /* Renderer: recarrega a janela sem reiniciar o processo */
  reload(path.join(__dirname, 'renderer'), {
    electron: _electronExec
  })

  /* Processo principal: reinicia o app ao alterar main ou preload */
  reload([
    path.join(__dirname, 'main.js'),
    path.join(__dirname, 'preload.js')
  ], {
    electron:        _electronExec,
    forceHardReset:  true,
    hardResetMethod: 'exit'
  })
}

function createWindow() {
  const win = new BrowserWindow({
    width: 1400,
    height: 900,
    minWidth: 900,
    minHeight: 600,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: false,
      contextIsolation: true
    },
    title: 'REEG+ - Registro de Dados e Criação de Atos',
    show: false
  })

  win.loadFile(path.join(__dirname, 'renderer', 'index.html'))

  win.once('ready-to-show', () => {
    win.show()
  })
}

/* ── IPC: DocumentService ──────────────────────────────────────────────────── */

ipcMain.handle('document:fetchByParam', (_event, keyword) => _docSvc.fetchByParam(keyword))
ipcMain.handle('document:save',         (_event, doc)     => _docSvc.save(doc))
ipcMain.handle('document:update',       (_event, doc)     => _docSvc.update(doc))
ipcMain.handle('document:deleteById',   (_event, id)      => _docSvc.deleteById(id))

/* ── IPC: DomainService ────────────────────────────────────────────────────── */

ipcMain.handle('domain:fetchAll',    () => _domainSvc.fetchAll())
ipcMain.handle('domain:listBacias',  () => _domainSvc.listBacias())
ipcMain.handle('domain:listUnidades',() => _domainSvc.listUnidades())

/* ── IPC: AddressService ───────────────────────────────────────────────────── */

ipcMain.handle('address:fetchByKeyword', (_event, keyword) => _addrSvc.fetchByKeyword(keyword))
ipcMain.handle('address:save',           (_event, addr)    => _addrSvc.save(addr))
ipcMain.handle('address:deleteById',     (_event, id)      => _addrSvc.deleteById(id))

/* ── IPC: UserService ──────────────────────────────────────────────────────── */

ipcMain.handle('user:fetchByKeyword', (_event, keyword) => _userSvc.fetchByKeyword(keyword))
ipcMain.handle('user:save',           (_event, user)    => _userSvc.save(user))
ipcMain.handle('user:deleteById',     (_event, id)      => _userSvc.deleteById(id))

/* ── IPC: ProcessService ───────────────────────────────────────────────────── */

ipcMain.handle('process:fetchByKeyword', (_event, keyword) => _procSvc.fetchByKeyword(keyword))
ipcMain.handle('process:save',           (_event, proc)    => _procSvc.save(proc))
ipcMain.handle('process:deleteById',     (_event, id)      => _procSvc.deleteById(id))

/* ── IPC: AnnexService ─────────────────────────────────────────────────────── */

ipcMain.handle('annex:fetchByKeyword', (_event, keyword) => _annexSvc.fetchByKeyword(keyword))
ipcMain.handle('annex:save',           (_event, annex)   => _annexSvc.save(annex))
ipcMain.handle('annex:deleteById',     (_event, id)      => _annexSvc.deleteById(id))

/* ── IPC: InterferenceService ──────────────────────────────────────────────── */

ipcMain.handle('interference:fetchByKeyword', (_event, keyword) => _ifSvc.fetchByKeyword(keyword))
ipcMain.handle('interference:save',           (_event, obj)     => _ifSvc.save(obj))
ipcMain.handle('interference:deleteById',     (_event, id)      => _ifSvc.deleteById(id))

/* ── IPC: BaciaService ─────────────────────────────────────────────────────── */

ipcMain.handle('bacia:listAll',       ()                   => _baciaSvc.listAll())
ipcMain.handle('bacia:findByPoint',   (_event, lat, lng)   => _baciaSvc.findByPoint(lat, lng))

/* ── IPC: UnidadeService ───────────────────────────────────────────────────── */

ipcMain.handle('unidade:listAll',     ()                   => _unidadeSvc.listAll())
ipcMain.handle('unidade:findByPoint', (_event, lat, lng)   => _unidadeSvc.findByPoint(lat, lng))

/* ── IPC: CoordConverter ───────────────────────────────────────────────────── */

ipcMain.handle('coords:utmToDecimal', (_event, e, n, z, h)              => CoordConverter.utmToDecimal(e, n, z, h))
ipcMain.handle('coords:dmsToDecimal', (_event, ld, lm, ls, ldir, od, om, os, odir) => CoordConverter.dmsToDecimal(ld, lm, ls, ldir, od, om, os, odir))

/* ── App lifecycle ─────────────────────────────────────────────────────────── */

app.whenReady().then(() => {
  createWindow()

  app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) createWindow()
  })
})

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') app.quit()
})
