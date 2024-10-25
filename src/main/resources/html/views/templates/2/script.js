/**
 * 
 * @nome Parecer de Outorga Prévia
 * @descricao Arquivo principal .js
 * @diretorio 2
 * @arquivo script.js
 * @id 11
 * 
 * 
 * 
 * 
 *
 */


function App() {

	const appDiv = document.getElementById("app");

	appDiv.innerHTML = `
			<div style="display:flex; flex-direction:column">
				<div id="subjective-view"></div>
				<div id="object-view"></div>
				<div id="legal-basis-view"></div>
				<div id="analyse-view"></div>
				<div id="signature-view"></div>
			</div>
			`;

			new SubjectView();
			new ObjectView();
			new LegalBasisView();
			new AnalyseView();
			new SignatureView();

			
			new ActionsView();

}
// Objetos do serviço
var documento;
var utils;

document.addEventListener('DOMContentLoaded', function () {

	App();

	utils = new Utils();
});