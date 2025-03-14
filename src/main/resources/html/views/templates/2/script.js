/**
 * 
 * @nome Parecer de Outorga Prévia
 * @descricao Arquivo principal .js
 * @diretorio 2
 * @arquivo script.js
 * @id 
 *
 *
 */


function App() {

	const appDiv = document.getElementById("app");

	appDiv.innerHTML = `
			<div style="display:flex; flex-direction:column">
				<div id="subject-view"></div>
				<div id="object-view"></div>
				<div id="legal-basis-view"></div>
				<div id="analyse-view"></div>
				<div id="conclusion-view"></div>
				<div id="signature-view"></div>
				
			</div>
			`;

	new SubjectView();
	new ObjectView();
	new LegalBasisView();
	new ActionsView();
	new AnalyseView();
	new ConclusionView();
	new SignatureView();


}

// Objetos do serviço
var documento;
// Classe de conexão com o java e ações de atualização de renderização
var utils;

document.addEventListener('DOMContentLoaded', function () {

	utils = new Utils();
	
	App();
});