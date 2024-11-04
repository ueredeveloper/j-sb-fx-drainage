/**
* @nome Parecer de Outorga De Direito de Uso
* @descricao Objeto do parecer
* @diretorio 4
* @arquivo object-view.js
* @id 21
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
		this.div = document.getElementById('object-view');
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
					<span class="us-nome"></span>, 
					CPF/CNPJ: <span class="us-cpf-cnpj"></span>, 
					no endereço: <span id="doc-endereco"></span> - Distrito Federal, 
					para fins de <span class="inter-finalidades"></span>.
					</p>
					<p>
					2. Trata o presente processo de outorga de direito de uso de água subterrânea por meio de 01 (um) poço <span class="highlight" class="type-well"></span>, para fins de irrigação paisagística - (0,2 ha - paisagismo) e demanda total de 4.449 L/dia. Foi apresentado: 
					perfilagem ótica - () onde indica características no domínio freático/poroso. Conforme Resolução nº 16, de 03 
					de fevereiro de 2023, a captação de água existente no domínio freático/poroso de um <span class="highlight" class="type-well"></span> é considerado 
					um <span class="highlight" class="type-well"></span>. Dessa forma, o pedido de outorga será objeto de análise do presente parecer.
					</p>
				</div>
		`;
		if (this.div !== null) this.div.innerHTML = innerHTML;

	}
	update(documento) {

		let _items = document.getElementsByClassName('inter-finalidades');

		let finalidades = documento.endereco.interferencias[0].finalidades;
		let usuario = documento.usuarios[0];
		let endereco = documento.endereco;

		Array.from(_items).forEach(element => {
			let innerHTML = new FinalidadeModel().getPurpouseString(finalidades);
			element.innerHTML = innerHTML
		});

		let __items = document.getElementsByClassName('us-nome');

		Array.from(__items).forEach(element => {
			let innerHTML = usuario.nome;
			element.innerHTML = innerHTML;
		});

		let ___items = document.getElementsByClassName('us-cpf-cnpj')

		Array.from(___items).forEach(element => {
			let innerHTML = new UsuarioModel().formatCpfCnpj(usuario.cpfCnpj)
			element.innerHTML = innerHTML;
		});

		let tipoPoco = endereco.interferencias[0].tipoPoco;

		let ____items = document.getElementsByClassName('inter-tipo-poco');

		Array.from(____items).forEach(element => {
			let innerHTML = tipoPoco?.descricao?.toLowerCase() || 'XXX';
			element.innerHTML = innerHTML;
		});

	}
}