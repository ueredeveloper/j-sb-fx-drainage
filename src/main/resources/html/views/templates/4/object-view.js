/**
 * Objeto do parecer
* @nome Parecer de Outorga De Direito de Uso
* @descricao Objeto do parecer
* @diretorio 4
* @arquivo object-view.js
* @id 19
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

class ObjectView {
	constructor() {
		this.div = document.getElementById('objective-view');
		this.render();
	}

	// classes: tipo de poço: type-well

	render() {
		let innerHTML = `
				<div>
					<p style="text-align: justify;">
					<strong>I. DO OBJETO</strong></p>
					<p></p>
					<p>
					1. Em <span class="highlight"></span>, foi protocolado requerimento de outorga de direito 
					de uso de água subterrânea, por meio de 01 (um) poço <span id="inter-tipo-poco"></span> em nome de 
					<span id="us-nome"></span>, 
					CPF/CNPJ: <span id="us-cpfcnpj"></span>, 
					no endereço: <span id="doc-endereco"></span> - Distrito Federal, 
					para fins de <span class="inter-purpouses"></span>.
					</p>
					<p>
					2. Trata o presente processo de outorga de direito de uso de água subterrânea por meio de 01 (um) poço <span class="highlight" class="type-well"></span>, para fins de irrigação paisagística - (0,2 ha - paisagismo) e demanda total de 4.449 L/dia. Foi apresentado: 
					perfilagem ótica - () onde indica características no domínio freático/poroso. Conforme Resolução nº 16, de 03 
					de fevereiro de 2023, a captação de água existente no domínio freático/poroso de um <span class="highlight" class="type-well"></span> é considerado 
					um <span class="highlight" class="type-well"></span>. Dessa forma, o pedido de outorga será objeto de análise do presente parecer.
					</p>
				</div>
		`
		this.div.innerHTML = innerHTML;

	}
	update (usuario, endereco, finalidades){

		let tipoPoco = endereco.interferencias[0].tipoPoco;

		document.getElementById('inter-tipo-poco').innerHTML = tipoPoco?.descricao?.toLowerCase() || 'XXX';
		let items = document.getElementsByClassName('inter-purpouses');

    	Array.from(items).forEach(element => {
    		let innerHTML = new FinalidadeModel().getPurpouseString(finalidades);
    		element.innerHTML = innerHTML
    	});

        document.getElementById('us-nome').innerHTML = usuario.nome;
		document.getElementById('us-cpfcnpj').innerHTML = new UsuarioModel().formatCpfCnpj(usuario.cpfCnpj);


	}
}