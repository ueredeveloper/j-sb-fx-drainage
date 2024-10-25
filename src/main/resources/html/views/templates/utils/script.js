/**
 * @nome Utilizades
 * @descricao Funções compartilhadas
 * @diretorio utils
 * @arquivo script.js
 * @id 33
 * 
 * 
 * 
 *
 */

    
 class Utils {

	constructor() { }

	// Muda o ponto do valor double para vígula do float. Ex: 20.00 para 20,00
	maskDoubleToFloat(value) {
		return parseFloat(value).toFixed(2).toString().replace('.', ',');
	}

	// Função para formatar o número com ponto separador de milhar
	formatNumber(value) {
		return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
	}
	/**
	 * Atualiza os dados do usuário
	 * 
	 * @param {*}
	 *            usuario
	 */
	update(usuario) {

		let names = document.getElementsByClassName('us-nome');

		// Converte o resultado para array e atualiza
		Array.from(names).forEach(element => {
			element.innerHTML = new UsuarioModel().getNome(usuario);
		});

		let cpfcnpjs = document.getElementsByClassName("us-cpf-cnpj");

		// Converte o resultado para array e atualiza
		Array.from(cpfcnpjs).forEach(element => {
			element.innerHTML = new UsuarioModel().formatCpfCnpj(usuario.cpfCnpj);
		});

	}
	/**
	 * Atualiza o endereço do usuário
	 * 
	 * @param {*}
	 *            endereco
	 */
	udpateAddress(endereco) {
		let items = document.getElementsByClassName('end-logradouro');

		Array.from(items).forEach(element => {
			let logradouro = new EnderecoModel().getLogradouro(endereco)
			element.innerHTML = logradouro
		});

	}


	updatePurpouses(finalidades) {
		let items = document.getElementsByClassName('inter-finalidades');

		Array.from(items).forEach(element => {
			let strFinalidades = new FinalidadeModel().getPurpouseString(finalidades)
			element.innerHTML = strFinalidades
		});

	}

	/**
	 * Atualiza tudo utilizando um exemplo da tabela documento com endereço,
	 * interferências e usuário
	 * 
	 * @param {*}
	 *            documento
	 */
	updateHtmlDocument(documento) {

		/*let usuario = documento.usuarios[0];
		this.update(usuario);

		let endereco = documento.endereco;
		this.udpateAddress(endereco);

		let interferencia = documento.endereco.interferencias[0];
		let finalidades = interferencia.finalidades;
		this.updatePurpouses(finalidades);*/

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

