/**
 * @nome Despacho de Outorga Prévia
 * @descricao Arquivo principal .js
 * @diretorio 1
 * @arquivo script.js
 * @id 4
 * 
 * 
 * 
 * 
 * 
 * 
 *
 */


function App() {

	const appDiv = document.getElementById("app");

	appDiv.innerHTML = `
		<div style="display:flex;flex-direction:column;">
			<div id="subject-view"></div>
			<div id="object-view"></div>

			<div align="justify" id="geographic-table-view"></div>

			<p>Art. 2º A reserva de disponibilidade hídrica para cada um dos poços tubulares mencionados no art. 1º é a seguinte:</p>

			<p>I – Tabela dos limites outorgados.</p>

			<p>&nbsp;</p>
			
			<div id="limits-table-view"></div>

			<p>&nbsp;</p>

			<div id="grant-requirements"></div>
			<div id="chief-signature-view"></div>
		</div>`;
}
var documento;
var utils;

document.addEventListener('DOMContentLoaded', function () {

	App();
	
	utils = new Utils();

	new SubjectView();
	new ObjectView();
	new LimitsTableView();
	new GrantRequirementsView();
	new ChiefSignatureView();
	new ActionsView();

	new ActionsView();
	
});
