/**
 * @nome Ações de Botões de Teste
 * @descricao Ações de verificação dos métodos
 * @diretorio actions
 * @arquivo script.js
 * @id 24
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *
 */
 

class ActionsView {
	
	constructor (){
        this.div = document.getElementById('actions');
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
                    
                    <label style="margin: 10px; background-color: #555; color: white; padding: 5px 10px; border-radius: 5px; font-weight: bold;">Template 4 - Parecer de Out. De Dir.</label>
                    <button id="btn-update-4" style="margin: 5px; padding: 10px 15px; background-color: #FFC107; color: white; border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s ease;">Editar Parecer Definitivo</button>
                 
                    </div>
            </div>

            `;
        this.div.innerHTML = index;
    }

    renderActions() {

        // Atualiza tabela com informações do ponto (latitude, longitude, bacia e subbacia
        document.getElementById('btn-update-geo-table').onclick = function () {

            let interferencia = new DocumentoModel().getSample().endereco.interferencias[0];

            new GeographicTableView().updateTableInfo(interferencia);
        };
        // Atualiza os limites outorgáveis
        document.getElementById('btn-update-limits-table').onclick = function () {

            let interferencia = new DocumentoModel().getSample().endereco.interferencias[0];

            new LimitsTableView().updateAuthorizedLimits(interferencia);
        };

        // Atualiza os limites outorgáveis
        document.getElementById('btn-update-all').onclick = function () {

            if (!documento) {
                documento = new DocumentoModel().getSample()
            }

            utils.updateHtmlDocument(documento)
        };



        // Atualiza os objetivo do parecer, adicionando nome, endereço e finalidades
        document.getElementById('btn-update-objective-view').onclick = function () {

            if (!documento) {
                documento = new DocumentoModel().getSample();
            }

            let usuario = documento.usuarios[0];
            let endereco = documento.endereco;
            let interferencia = documento.endereco.interferencias[0];
            let finalidades = interferencia.finalidades;

            new ObjectView().update(usuario, endereco, finalidades);
            new AnalyseView().update(interferencia);
            new WellInfoView().update(interferencia);

            // Finalidades requeridas
            new PurpouseTableView(documento, 1, 'tbl-finalidades-requeridas');
            // Finalidades autorizadas
            new PurpouseTableView(documento, 2, 'tbl-finalidades-autorizadas');

            new ExploitableReserveView().update(documento);

            new GeographicTableView().updateTableInfo(interferencia);
            new LimitsTableView().updateAuthorizedLimits(interferencia);

        };


        
        // Atualiza os objetivo do parecer, adicionando nome, endereço e finalidades
        document.getElementById('btn-update-4').onclick = function () {

            if (!documento) {
                documento = new DocumentoModel().getSample();
            }   

            new SubjetiveView().update(documento);

            let usuario = documento.usuarios[0];
            let endereco = documento.endereco;
            let interferencia = documento.endereco.interferencias[0];
            let finalidades = interferencia.finalidades;

            new ObjectView().update(usuario, endereco, finalidades);
            new WellInfoView().update(interferencia);
            // Finalidades requeridas
            new PurpouseTableView(documento, 1, 'tbl-request-purpouse-view');

             // Finalidades autorizadas
             new PurpouseTableView(documento, 2, 'tbl-authorized-purpouse-view');

             new ExploitableReserveView().update(documento);
             new LimitsTableView().updateAuthorizedLimits(interferencia);

        };

    }



}