/**
 * @id
 * @arquivo script.js
 * @diretorio actions
 * @descricao Ações de Teste dos Botões
 */
class ActionsView {
    constructor(geoTab) {
        this.div = document.getElementById('actions');
        this.geoTab = geoTab;
        this.render();
        this.renderActions();
    }
    render() {

        let index = `
            <div style="position: fixed; right: 20px; top: 50%; transform: translateY(-50%);">
                <div style="display: flex; flex-direction: column; align-items: flex-start;">
                    <label style="margin: 10px; background-color: #555; color: white; padding: 5px 10px; border-radius: 5px; font-weight: bold;">Template 1</label>
                    <button id="btn-update-geo-table" style="margin: 5px; padding: 10px 15px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s ease;">Update Interferencia</button>
                    <button id="btn-update-limits-table" style="margin: 5px; padding: 10px 15px; background-color: #2196F3; color: white; border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s ease;">Update Limites Autorizados</button>
                    <button id="btn-update-all" style="margin: 5px; padding: 10px 15px; background-color: #FF5722; color: white; border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s ease;">Editar Tudo com o Objeto Documento</button>

                    <label style="margin: 10px; background-color: #555; color: white; padding: 5px 10px; border-radius: 5px; font-weight: bold;">Template 2</label>
                    <button id="btn-update-objective-view" style="margin: 5px; padding: 10px 15px; background-color: #FFC107; color: white; border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s ease;">Editar Objetivo Parecer</button>
                </div>
            </div>

            `;
        this.div.innerHTML = index;
    }
    
    renderActions () {
    	
    	// Atualiza tabela com informações do ponto (latitude, longitude, bacia e subbacia
        document.getElementById('btn-update-geo-table').onclick = function () {

            let interferencia = new InterferenciaModel().getSample()
       
            geographicTable.updateTableInfo(interferencia);
        };
        // Atualiza os limites outorgáveis
        document.getElementById('btn-update-limits-table').onclick = function () {

            let interferencia = new InterferenciaModel().getSample()
       
            limitsTable.updateAuthorizedLimits(interferencia);
        };

        // Atualiza os limites outorgáveis
        document.getElementById('btn-update-all').onclick = function () {

            if (!documento){
            	documento = new DocumentoModel().getSample()
            }
       
            utils.updateHtmlDocument(documento)
        };
        
      
        
        // Atualiza os objetivo do parecer, adicionando nome, endereço e finalidades
        document.getElementById('btn-update-objective-view').onclick = function () {

            if (!documento){
            	documento = new DocumentoModel().getSample();
            	
            	
            }
       
        	let usuario = documento.usuarios[0];
        	let endereco = documento.endereco;
        	let interferencia = documento.endereco.interferencias[0];
        	let finalidades = interferencia.finalidades;
        	
        	objectiveView.updateInfo(usuario, endereco, finalidades);
        	analysisView.updateInfo(interferencia);
        	wellInfoView.updateInfo(interferencia);
        };

    }



}