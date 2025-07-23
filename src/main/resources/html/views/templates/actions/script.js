/**
 * @nome Ações de Botões de Teste
 * @descricao Ações de verificação dos métodos
 * @diretorio actions
 * @arquivo script.js
 * @id 
 *
 *
 */

class ActionsView {

	constructor() {
		this.div = document.getElementById('actions');
		this.render();
		this.renderActions();
	}

	render() {
		let index = `
            <div style="position: fixed; right: 20px; top: 50%; transform: translateY(-50%); z-index:100;">
                <div style="display: flex; flex-direction: column; align-items: flex-start; min-width:170px;">
                    <label style="margin: 10px 0 5px 0; background-color: #555; color: white; padding: 4px 8px; border-radius: 5px; font-weight: bold;">Template 1</label>
                    <button id="btn-update-all" class="btn-acao">Editar Tudo</button>
                </div>
            </div>
       `;
		this.div.innerHTML = index;
	}

	renderActions() {
		// Atualiza tudo
		document.getElementById('btn-update-all').onclick = function() {
			if (!documento) {
				documento = new DocumentoModel().getSample();
			}
			let interferencia = documento.endereco.interferencias[0];
			utils.updateHtmlDocument(documento, interferencia);
		};
	}
}

