/**
* Assunto do parecer.
* @nome Parecer de Outorga De Direito de Uso
* @descricao Assunto do parecer
* @diretorio 4
* @arquivo subjective-view.js
* @id 21
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

class SubjetiveView {
	constructor() {
		this.div = document.getElementById('subjective-view');
		this.render();
	}

	render() {

		let innerHTML = `
				<p style="float:right;width:40rem">Assunto: análise de requerimento de outorga de direito de uso de recursos hídricos subterrâneo, 
				por meio de 01 (um) poço <span id="inter-tipo-poco"></span> para fins de 
				<span class="inter-purpouses"></span>.
				</p>
		`;

		this.div.innerHTML = innerHTML;

	}
	update(documento) {

		let tipoPoco = documento.endereco.interferencias[0].tipoPoco;
		let finalidades = documento.endereco.interferencias[0].finalidades;

		document.getElementById('inter-tipo-poco').innerHTML = tipoPoco?.descricao?.toLowerCase() || 'XXX';

		let items = document.getElementsByClassName('inter-purpouses');

		Array.from(items).forEach(element => {
			let innerHTML = new FinalidadeModel().getPurpouseString(finalidades);
			element.innerHTML = innerHTML
		});

	}
}