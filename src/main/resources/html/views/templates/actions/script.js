/**
 * Ações de Teste
 * @id 15
 * @descricao Ações de Teste
 * @pasta actions
 * @nome script.js
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
               </div>
            </div>
            `;
        this.div.innerHTML = index;
    }
    
    renderActions () {
    	
    	// Atualiza tabela com informações do ponto (latitude, longitude, bacia e subbacia
        document.getElementById('btn-update-geo-table').onclick = function () {

            let interferencia = new Interferencia().getTableExample()
       
            geographicTable.updateTableInfo(interferencia);
        };
        // Atualiza os limites outorgáveis
        document.getElementById('btn-update-limits-table').onclick = function () {

            let interferencia = new Interferencia().getTableExample()
       
            limitsTable.updateAuthorizedLimits(interferencia);
        };

    }



}