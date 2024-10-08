/**
 * @id 6
 * @nome script.js
 * @pasta 1
 * @descricao Despacho de Outorga Prévia
 */

function App() {

	const appDiv = document.getElementById("app");

	appDiv.innerHTML = `
		<div id="container">
			<div style="text-align: center;">&nbsp;</div>

			<p style="margin-left: 400px;">Emite outorga prévia para reservar o direito de uso de água subterrânea a <b><span class="us-nome"></span></b>, para fins de <span class="inter-finalidades"></span>.

			</p><p>&nbsp;</p>
		

			<div align="justify">
			<p>O SUPERINTENDENTE DE RECURSOS HÍDRICOS DA AGÊNCIA REGULADORA DE ÁGUAS, ENERGIA E SANEAMENTO BÁSICO DO DISTRITO FEDERAL – ADASA, no uso de suas atribuições regimentais e com base na competência que lhe foi delegada pela Diretoria Colegiada, nos termos da Resolução nº 02, de 25 de janeiro de 2019, c/c Portaria nº 49, de 02 de maio de 2019 e com base no art. 12 da Lei nº 2.725, de 13 de junho de 2001, e inciso VII do art. 23 da Lei nº 4.285, de 26 de dezembro de 2008, tendo em vista o que consta do Processo SEI N.º <b><span id="proc-anexo"></span></b>, resolve:</p>

			<p>Art. 1º Emitir outorga prévia para reservar o direito de uso de água subterrânea a <b><span class="us-nome"></span></b>, CPF/CNPJ n.º <b><span class="us-cpf-cnpj"></span></b>, mediante a perfuração de 01 (um) poço <span id="inter-tipo-poço"></span>, para fins de <span class="inter-finalidades"></span>, localizado no endereço: <span class="end-logradouro"></span> - Distrito Federal, tendo a seguinte característica:</p>
			</div>

			<p>&nbsp;</p>

			<div align="justify" id="geographic-table"></div>

			<p style="text-align: center;">&nbsp;</p>

			<p>Art. 2º A reserva de disponibilidade hídrica para cada um dos poços tubulares mencionados no art. 1º é a seguinte:</p>

			<p>&nbsp;</p>

			<p>I – Tabela dos limites outorgados.</p>

			<p>&nbsp;</p>
			
			<div id="authorized-limits-table"></div>

			<p>&nbsp;</p>

			<div id="grant-requirements"></div>
			<span class="us-nome"></span></b>
			<div id="chief-signature"></div>
		</div>`;
}
var documento;
var utils, geographicTable, limitsTable, user, demand;

document.addEventListener('DOMContentLoaded', function () {

	App();
	
	utils = new Utils();
	user = new Usuario();
	geographicTable = new GeographicTable();
	limitsTable = new LimitsTable();
	demand = new Demanda();
	new GrantRequirements();
	new ChiefSignature();
	new Actions();
	
});
