/**
 * 
* Assunto do parecer.
* @nome Parecer de Outorga Prévia
* @descricao Assunto do Parecer
* @diretorio 2
* @arquivo subject-view.js
* @id 12
 * 
 * 
 * 
 * 
 * 
* 
* 
*/

class SubjectView {
	constructor() {
		this.div = document.getElementById('subjective-view');
		this.render();
	}

	render() {
		let innerHTML = `
				<p style="float:right;width:40rem">Assunto: análise de requerimento de outorga prévia para 
                reservar o direito de uso de água subterrânea por meio de 01 (um) poço 
                <span class="int-tipo-poco"></span> para fins de 
				<span class="int-finalidades"></span>.
				</p>
		`;
		if (this.div !== null) this.div.innerHTML = innerHTML;

	}
	update(documento) {

		let tipoPoco = documento.endereco.interferencias[0].tipoPoco;
		let finalidades = documento.endereco.interferencias[0].finalidades;

		let _items = document.getElementsByClassName('int-tipo-poco');

		Array.from(_items).forEach(element => {
			let innerHTML = tipoPoco?.descricao?.toLowerCase() || 'XXX';
			element.innerHTML = innerHTML;
		});


		let __items = document.getElementsByClassName('int-finalidades');

		Array.from(__items).forEach(element => {
			let innerHTML = new FinalidadeModel().getPurpouseString(finalidades);
			element.innerHTML = innerHTML
		});

	}
}