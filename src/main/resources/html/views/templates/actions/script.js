/**
 * @id 15
 * @pasta actions
 * @nome script.js
 * @descricao Ações de Teste dos Botões
 */
class Actions {
    constructor(geoTab) {
        this.div = document.getElementById('actions');
        this.geoTab = geoTab;
        this.render();
        this.renderActions();
    }
    render() {

        let index = `
            <div style="position: fixed; right: 20px; top: 50%">
               <div style="display: flex; flex-direction: column;">
                    <label style="margin: 10px;">Botões de ação</label>
                        <button id="btn-update-geo-table" style="margin:5px;">update interferencia</button>
                        <button id="btn-update-limits-table" style="margin:5px;">update limites autorizados</button>
                        <button id="btn-update-all">Editar Tudo com o objeto Documento</button>
               </div>
            </div>
            `;
        this.div.innerHTML = index;
    }
    
    renderActions () {
    	
    	// Atualiza tabela com informações do ponto (latitude, longitude, bacia e subbacia
        document.getElementById('btn-update-geo-table').onclick = function () {

            let interferencia = new Interferencia().getSample()
       
            geographicTable.updateTableInfo(interferencia);
        };
        // Atualiza os limites outorgáveis
        document.getElementById('btn-update-limits-table').onclick = function () {

            let interferencia = new Interferencia().getSample()
       
            limitsTable.updateAuthorizedLimits(interferencia);
        };

        // Atualiza os limites outorgáveis
        document.getElementById('btn-update-all').onclick = function () {

            if (!documento){
            	documento = new Documento().getSample()
            }
       
            utils.updateHtmlDocument(documento)
        };

    }



}