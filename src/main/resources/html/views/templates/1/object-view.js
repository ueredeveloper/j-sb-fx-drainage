class ObjectView {
	constructor() {
		this.div = document.getElementById('object-view');
		this.render();
	}
	render() {

		//

		let innerHTML = `
        <div align="justify">
			<p>O SUPERINTENDENTE DE RECURSOS HÍDRICOS DA AGÊNCIA REGULADORA DE ÁGUAS, ENERGIA E SANEAMENTO BÁSICO DO DISTRITO FEDERAL – 
			ADASA, no uso de suas atribuições regimentais e com base na competência que lhe foi delegada pela Diretoria Colegiada, 
			nos termos da Resolução 
			nº 02, de 25 de janeiro de 2019, c/c Portaria nº 49, de 02 de maio de 2019 e com base no art. 12 da Lei nº 2.725, de 13 de 
			junho de 2001, 
			e inciso VII do art. 23 da Lei nº 4.285, de 26 de dezembro de 2008, tendo em vista o que consta do Processo SEI N.º <b>
			<span class="proc-anexo"></span></b>, resolve:</p>

			<p>Art. 1º Emitir outorga prévia para reservar o direito de uso de água subterrânea a <b><span class="us-nome"></span>
			</b>, 
			CPF/CNPJ n.º <b><span class="us-cpf-cnpj"></span></b>, mediante a perfuração de 01 (um) poço 
			<span class="int-tipo-poco"></span>, 
			para fins de <span class="int-finalidades"></span>, localizado no endereço: <span class="end-logradouro"></span> - 
			Distrito Federal, 
			tendo a seguinte característica:</p>
			</div>
        `;

		this.div.innerHTML = innerHTML;


	}
	update(documento) {

		let interferencia = documento.endereco.interferencias[0];
		let finalidades = interferencia.finalidades;
		let usuario = documento.usuarios[0];
		let endereco = documento.endereco;

		let _items = document.getElementsByClassName('proc-anexo');

		Array.from(_items).forEach(element => {
			let innerHTML = documento?.processo?.anexo?.numero || 'XXX';
			element.innerHTML = innerHTML;
		});

		let __items = document.getElementsByClassName('int-tipo-poco');

		Array.from(__items).forEach(element => {
			let innerHTML = documento.endereco.interferencias[0]?.tipoPoco?.descricao || 'XXX';
			element.innerHTML = innerHTML;
		});

		let ___items = document.getElementsByClassName('end-logradouro');
		// Converte o resultado para array e atualiza
		Array.from(___items).forEach(element => {
			element.innerHTML = new EnderecoModel().getLogradouro(endereco);
		});

		let ____items = document.getElementsByClassName('inter-finalidades');
		// Converte o resultado para array e atualiza
		Array.from(____items).forEach(element => {
			element.innerHTML = new FinalidadeModel().getPurpouseString(finalidades);
		});

		let _____items = document.getElementsByClassName('us-nome');
		// Converte o resultado para array e atualiza
		Array.from(_____items).forEach(element => {
			console.log('usuario', usuario)
			element.innerHTML = new UsuarioModel().getNome(usuario);
		});
		let ______items = document.getElementsByClassName('us-cpf-cnpj');
		// Converte o resultado para array e atualiza
		Array.from(______items).forEach(element => {
			element.innerHTML = new UsuarioModel().formatCpfCnpj(usuario.cpfCnpj);
		});

	}
}