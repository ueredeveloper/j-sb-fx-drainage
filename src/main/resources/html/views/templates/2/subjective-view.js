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

	render() {
		let innerHTML = `
				<p style="float:right;width:40rem">Assunto: análise de requerimento de outorga prévia para 
                reservar o direito de uso de água subterrânea por meio de 01 (um) poço 
                <span class="int-type-of-well"></span> para fins de 
				<span class="purpouses"></span>.
				</p>
		`
		this.div.innerHTML = innerHTML;

	}
	update(documento){

		let tipoPoco = documento.endereco.interferencias[0].tipoPoco;
		let finalidades = documento.endereco.interferencias[0].finalidades;

		document.getElementById('int-type-of-well').innerHTML = tipoPoco?.descricao?.toLowerCase() || 'XXX';

		let items = document.getElementsByClassName('purpouses');

    	Array.from(items).forEach(element => {
    		let innerHTML = new FinalidadeModel().getPurpouseString(finalidades);
    		element.innerHTML = innerHTML
    	});

	}
}