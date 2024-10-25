/**
* Assunto do parecer.
* @nome Despacho de Outorga Prévia
* @descricao Assunto do Despacho
* @diretorio 1
* @arquivo subject-view.js
* @id 5
 * 
 * 
 * 
* 
* 
*/

class SubjectView {
	constructor() {
		this.div = document.getElementById('subject-view');
		this.render();
	}

	// classes: int-finalidades int-tipo-poco

	render() {
		let innerHTML = `
			<div style="float:right;width:40rem">
				<p>Emite outorga prévia para reservar o direito de uso de água subterrânea a <b><span class="us-name"></span>
				</b>, para fins de <span class="int-finalidades"></span>.
			</div>
		`;
		this.div.innerHTML = innerHTML;

	}
	update(documento){

		let finalidades = documento.endereco.interferencias[0].finalidades;
		let usuario = documento.usuarios[0];

		let _items = document.getElementsByClassName('us-name');

    	Array.from(_items).forEach(element => {
    		let innerHTML = usuario?.nome || 'XXX';
    		element.innerHTML = innerHTML
    	});
		
		let __items = document.getElementsByClassName('int-finalidades');

    	Array.from(__items).forEach(element => {
    		let innerHTML = new FinalidadeModel().getPurpouseString(finalidades);
    		element.innerHTML = innerHTML
    	});

	}
}