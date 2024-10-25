/**
 * 
 * @nome Utilizades
 * @descricao Funções compartilhadas
 * @diretorio utils
 * @arquivo script.js
 * @id 38
 * 
 * 
 * 
 */

 class Utils {

	constructor() {}

	/**
	 * Atualiza tudo utilizando um exemplo da tabela documento com endereço,
	 * interferências e usuário
	 * 
	 * @param {*}
	 *            documento
	 */
	updateHtmlDocument(documento) {

		// Verifica se a classe ObjectView está disponível
		if (typeof ObjectView !== 'undefined') {
			// Atualiza ObjectView
			new ObjectView().update(documento);
		} else {
			console.error("SubjectView não definida.");
		}

		// Verifica e atualiza SubjectView se estiver disponível
		if (typeof SubjectView !== 'undefined') {
			new SubjectView().update(documento);
		} else {
			console.error("SubjectView não definida.");
		}


		// Verifica e atualiza AnalyseView se estiver disponível
		if (typeof AnalyseView !== 'undefined') {
			new AnalyseView().update(documento);
		} else {
			console.error("AnalyseView não definida.");
		}

		// Verifica e atualiza WellInfoView se estiver disponível
		if (typeof WellInfoView !== 'undefined') {
			let interferencia = documento.endereco.interferencias[0];
			new WellInfoView().update(interferencia);
		} else {
			console.error("WellInfoView não definida.");
		}

		// Verifica e inicializa PurpouseTableView
		if (typeof PurpouseTableView !== 'undefined') {
			new PurpouseTableView(documento, 1, 'tbl-request-purpouse-view');
			new PurpouseTableView(documento, 2, 'tbl-authorized-purpouse-view');
		} else {
			console.error("PurpouseTableView não definida.");
		}

		// Verifica e atualiza ExploitableReserveView
		if (typeof ExploitableReserveView !== 'undefined') {
			new ExploitableReserveView().update(documento);
		} else {
			console.error("ExploitableReserveView não definida.");
		}

		// Atualiza despachos com as tabelas geográficas
		if (typeof GeographicTableView !== 'undefined') {
			new GeographicTableView().update(documento);
		} else {
			console.error("GeographicTableView não definida.");
		}

		// Atualiza limites autorizados
		if (typeof LimitsTableView !== 'undefined') {
			new LimitsTableView().update(documento);
		} else {
			console.error("LimitsTableView não definida.");
		}


	}


}

