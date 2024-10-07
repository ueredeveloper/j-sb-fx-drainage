/**
 * Funções compartilhadas
 * @id 22
 * @descricao Funções Compartilhadas
 * @pasta utils
 * @nome script.js
 */

class Utils {

    constructor() {
		this.usuario = new Usuario();
	}
    
 // Muda o ponto do valor double para vígula do float. Ex: 20.00 para 20,00
    maskDoubleToFloat(value) {
    	console.log('mascara, double para float: ', value)
    	return parseFloat(value).toFixed(2).toString().replace('.', ',');
    }

    // Função para formatar o número com ponto separador de milhar
    formatNumber(value) {
    	console.log(value, 'convert to string ')
    	return value?.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    }
    /**
     * Atualiza os dados do usuário
     * @param {*} usuario 
     */
    updateUserData(usuario) {
    	let names = document.getElementsByClassName('us-nome');

    	// Converte o resultado para array e atualiza
    	Array.from(names).forEach(element => {
    		element.innerHTML = this.usuario.getNome(usuario);
    	});

    	let cpfcnpjs = document.getElementsByClassName("us-cpf-cnpj");

    	// Converte o resultado para array e atualiza
    	Array.from(cpfcnpjs).forEach(element => {
    		element.innerHTML = this.usuario.formatCpfCnpj(usuario.cpfCnpj);
    	});

    }
    /**
     * Atualiza o endereço do usuário
     * @param {*} endereco 
     */
    udpateAddress(endereco) {
    	let items = document.getElementsByClassName('end-logradouro');

    	Array.from(items).forEach(element => {
    		let logradouro = new Endereco().getLogradouro(endereco)
    		element.innerHTML = logradouro
    	});

    }

	updatePurpouseString (finalidades){

		let str = new Finalidade().createPurpouseString(finalidades); 

		console.log(str)

		let items = document.getElementsByClassName('inter-finalidades');

    	Array.from(items).forEach(element => {
    		element.innerHTML = str
    	});
	}
    /**
     * Atualiza tudo utilizando um exemplo da tabela documento com endereço, interferências e usuário
     * @param {*} documento 
     */
    updateHtmlDocument(documento) {
    	
    	let usuario = documento.usuarios[0];
    	
    	this.updateUserData(usuario);

    	/*let endereco = documento.endereco;
    	this.udpateAddress(endereco);

    	let interferencia = documento.endereco.interferencias[0];

		this.updatePurpouseString(interferencia.finalidades);

    	geographicTable.updateTableInfo(interferencia);
    	limitsTable.updateAuthorizedLimits(interferencia);*/
    }
    
    print (){console.log("print utils")}
    
    
    
    
}

