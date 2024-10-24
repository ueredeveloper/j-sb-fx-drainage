/**
* Assunto do parecer.
 * @nome Parecer de Outorga Prévia
* @descricao Assunto do Parecer
* @diretorio 2
* @arquivo subjective-view.js
* @id 10
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

	// classes: int-purpouses type-of-well 

	render() {
		let innerHTML = `
			<div style="float:right;width:40rem">
				<p>Emite outorga prévia para reservar o direito de uso de água subterrânea a <b><span	class="us-name"></span>
				</b>, para fins de <span class="int-purpouses"></span>.
			</div>
		`;
		this.div.innerHTML = innerHTML;

	}
	update(documento){

		let tipoPoco = documento.endereco.interferencias[0].tipoPoco;
		let finalidades = documento.endereco.interferencias[0].finalidades;

		document.getElementById('type-of-well').innerHTML = tipoPoco?.descricao?.toLowerCase() || 'XXX';

		let items = document.getElementsByClassName('int-purpouses');

    	Array.from(items).forEach(element => {
    		let innerHTML = new FinalidadeModel().getPurpouseString(finalidades);
    		element.innerHTML = innerHTML
    	});

	}
}