/**
 * @id
 * @arquivo script.js
 * @diretorio 2
 * @descricao Parecer de Outorga Prévia
 */
function App() {

	const appDiv = document.getElementById("app");
	
	
	appDiv.innerHTML = `
		<div id="objetivo"></div>
		<div id="base-legal"></div>
		<div id="analises"></div>
		<div id="req-pur-view"></div>
	`;
	
}
// Modelos de classes internos da pasta templates
var userModel, addressModel, documentModel, purpouseModel, interferenceModel, requestedPurpouseView;
// Visualizações internas
var objectiveView, legalBasisView, analysisView, wellInfoView;
// Objetos do serviço
var documento;

document.addEventListener('DOMContentLoaded', function () {

	App();
	
	userModel = new UsuarioModel();
	addressModel = new EnderecoModel();
	purpouseModel = new FinalidadeModel();
	interferenceModel = new InterferenciaModel();
	
	objectiveView = new ObjectiveView();
	legalBasisView = new LegalBasisView();
	analysisView = new AnalysisView();
	wellInfoView = new WellInfoView();
	requestedPurpouseView = new RequestedPurpouseView();
	
	new ActionsView();

});