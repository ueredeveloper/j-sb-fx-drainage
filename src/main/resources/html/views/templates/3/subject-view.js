/**
* @nome Despacho de Outorga De Direito de Uso
* @descricao Assunto do Despacho
* @diretorio 3
* @arquivo subject-view.js
* @id 5
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
				<p class="outorga-uso">
				Emite outorga de direito de uso de água subterrânea a <span class="us-nome"></span>, 
				para fins de <span class="int-finalidades"></span>.
				</p>
			</div>
		`;
		this.div.innerHTML = innerHTML;

	}
	update(documento, interferencia){

		let finalidades = interferencia.finalidades;
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