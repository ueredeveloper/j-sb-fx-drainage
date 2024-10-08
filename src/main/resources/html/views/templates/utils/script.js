/**
 * @id 23
 * @nome script.js
 * @pasta utils
 * @descricao Funções compartilhadas
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
<<<<<<< HEAD
    	console.log(value, 'convert to string ')
    	return value?.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
=======
    	return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
>>>>>>> feat/web-view-and-html-editor
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
<<<<<<< HEAD
    		element.innerHTML = this.usuario.formatCpfCnpj(usuario.cpfCnpj);
=======
    		element.innerHTML = new Usuario().formatCpfCnpj(usuario.cpfCnpj);
>>>>>>> feat/web-view-and-html-editor
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
<<<<<<< HEAD

	updatePurpouseString (finalidades){

		let str = new Finalidade().createPurpouseString(finalidades); 

		console.log(str)

		let items = document.getElementsByClassName('inter-finalidades');

    	Array.from(items).forEach(element => {
    		element.innerHTML = str
    	});
	}
=======
    
    
    updatePurpouses (finalidades) {
    	let items = document.getElementsByClassName('inter-finalidades');

    	Array.from(items).forEach(element => {
    		let strFinalidades = new Finalidade().createPurpouseString(finalidades)
    		element.innerHTML = strFinalidades
    	});

    }

>>>>>>> feat/web-view-and-html-editor
    /**
     * Atualiza tudo utilizando um exemplo da tabela documento com endereço, interferências e usuário
     * @param {*} documento 
     */
    updateHtmlDocument(documento) {
    	
    	let usuario = documento.usuarios[0];
    	
<<<<<<< HEAD
    	this.updateUserData(usuario);

    	/*let endereco = documento.endereco;
    	this.udpateAddress(endereco);

    	let interferencia = documento.endereco.interferencias[0];

		this.updatePurpouseString(interferencia.finalidades);

    	geographicTable.updateTableInfo(interferencia);
    	limitsTable.updateAuthorizedLimits(interferencia);*/
=======
    	console.log(usuario.nome)
    	this.updateUserData(usuario);
    	
    	let endereco = documento.endereco;
    	this.udpateAddress(endereco);

    	let interferencia = documento.endereco.interferencias[0];
    	
    	let finalidades = interferencia.finalidades;
    	this.updatePurpouses (finalidades);
    	
    	
    	new GeographicTable().updateTableInfo(interferencia);
    	new LimitsTable().updateAuthorizedLimits(interferencia);
    	
    	
    	//this.createButtonForUpdate(documento)
>>>>>>> feat/web-view-and-html-editor
    }
    
    createButtonForUpdate(documento) {
        let button = document.createElement('button');
        button.innerText = 'Update Document';
        
        // Append the button to the body or any specific container
        document.body.appendChild(button);

        // Set up the click event
        button.onclick = () => {
        	console.log('button clicked!')
            this.updateHtmlDocument(documento);
        };

        // Automatically click the button (if needed)
        button.click();
    }
    
    extractTagsOnly() {
    	// Get the rendered HTML content
    	const renderedHtml = document.documentElement.outerHTML;

    	// Use DOMPurify to clean the HTML
    	const cleanHtmlContent = DOMPurify.sanitize(renderedHtml, {
    	    ALLOWED_ATTR: ['id', 'class', 'src', 'href', 'alt', 'style'],  // Only allow specific attributes
    	    ALLOWED_TAGS: DOMPurify.defaults.ALLOWED_TAGS // Keep the default allowed tags
    	});
    	
    	return cleanHtmlContent;
    }
    
   
}

