/**
 * @id
 * @arquivo objective-view.js
 * @diretorio 2
 * @descricao Parecer de Outorga Prévia
 */
class ObjectiveView {
	
    constructor() {
    	this.render();
    }

    render() {
    	let html = `
    	<h2>I. DO OBJETO</h2>
    	<p>1. Em <span class="doc-data-recebimento highlight"></span>, foi protocolado requerimento relacionado a 
    	<span class="inter-tipo-outorga" class="highlight"></span> para reservar o direito de uso de água subterrânea, 
    	por meio de 01 (um) poço <span class="inter-tipo-poco" class="highlight"></span> em nome de 
    	<span class="us-nome" class="highlight"></span>, 
    	CPF/CNPJ: <span class="us-cpf-cnpj" class="highlight"></span>, 
    	no endereço: <span class="end-logradouro" class="highlight"></span>, <span class="end-ra" class="highlight"></span>
    	 - Distrito Federal, para fins de <span class="inter-finalidades"></span>.</p>
    `;
    	
    	document.getElementById("objetivo").innerHTML = html;
    }
    
    updateInfo (usuario, endereco, finalidades){
    	
    	let names = document.getElementsByClassName('us-nome');
    	// Converte o resultado para array e atualiza
    	Array.from(names).forEach(element => {
    		element.innerHTML = userModel.getNome(usuario);
    	});
    	let cpfcnpjs = document.getElementsByClassName('us-cpf-cnpj');
    	// Converte o resultado para array e atualiza
    	Array.from(cpfcnpjs).forEach(element => {
    		element.innerHTML = userModel.formatCpfCnpj(usuario.cpfCnpj);
    	});
    	
    	let addresses = document.getElementsByClassName('end-logradouro');
    	// Converte o resultado para array e atualiza
    	Array.from(addresses).forEach(element => {
    		element.innerHTML = addressModel.getLogradouro(endereco);
    	});
    	
    	let purpouses = document.getElementsByClassName('inter-finalidades');
    	// Converte o resultado para array e atualiza
    	Array.from(purpouses).forEach(element => {
    		element.innerHTML = purpouseModel.getPurpouseString(finalidades);
    	});
    	
    	
    
    }
}

