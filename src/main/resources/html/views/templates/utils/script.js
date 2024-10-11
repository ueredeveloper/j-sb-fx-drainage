/**
 * @arquivo script.js
 * @id 
 * @diretorio utils
 * @descricao Funções compartilhadas
 */

class Utils {

    constructor() {}
    
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
    updateUserData(usuario) {
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
    
    
    updatePurpouses (finalidades) {
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
 
    	let usuario = documento.usuarios[0];
    	
    	this.updateUserData(usuario);
    	
    	let endereco = documento.endereco;
    	
    	this.udpateAddress(endereco);

    	let interferencia = documento.endereco.interferencias[0];
    	
    	let finalidades = interferencia.finalidades;
    	this.updatePurpouses(finalidades);
    	
    	// Atualiza despacho de outorga prévia
    	new GeographicTableView().updateTableInfo(interferencia);
    	new LimitsTableView().updateAuthorizedLimits(interferencia);
    	
    	// Classes do parecer de outorga prévia
    	if (typeof ObjectiveView !== 'undefined') {
    		
    		// Objeto do parecer
            new ObjectiveView().updateInfo(usuario, endereco, finalidades);
            // Análise do parecer
            new AnalysisView().updateInfo(interferencia);
            // Tipo de poço no caso de outorga subterrânea
            new WellInfoView().updateInfo(interferencia);

            // Finalidades requeridas
            new PurpouseTableView(documento, 1, 'tbl-finalidades-requeridas');
            // Finalidades autorizadas
            new PurpouseTableView(documento, 2, 'tbl-finalidades-autorizadas');

            new ExploitableReserveView().updateInfo(documento);
    	   
    	} else {
    	    console.error("classes do parecer de outorga prévia não definidos.");
    	}
    	

    }
    
    
    
    extractBody () {
    	// Get the rendered HTML content
    	const renderedHtml = window.document.body.innerHTML;

    	return renderedHtml;
    }
    
   
}

