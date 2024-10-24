/**
 * @descricao Parecer de Outorga Prévia
 * @diretorio 2
 * @arquivo script.js
 * @id 
 * 
 * 
 * 
 *
 */

 
function App() {

	const appDiv = document.getElementById("app");

	appDiv.innerHTML = `
		<div id="objetivo"></div>
		<div id="base-legal"></div>
		<div id="analises"></div>
			<div id="dados-poco"></div>
		<div id="req-pur-view"></div>
		<div id="reserva-explotavel"></div>
		<div id="water-demand-view"></div>
		<div id="water-data-view"></div>
		<div id="conclusion"></div>
	`;

}
// Objetos do serviço
var documento;
// Modelos de classes internos da pasta templates
//var userModel, addressModel, documentModel, purpouseModel, interferenceModel, demandModel;
// Visualizações internas
var objectiveView, legalBasisView, analysisView, wellInfoView, purpouseView,
	exploitableReserveView, geographicTableView, limitsTableView;
var utils;

document.addEventListener('DOMContentLoaded', function () {

	App();

	utils = new Utils();

	/*userModel = new UsuarioModel();
	addressModel = new EnderecoModel();
	purpouseModel = new FinalidadeModel();
	interferenceModel = new InterferenciaModel();
	demandModel = new DemandaModel();*/

	//objectiveView = 
		new ObjectiveView();
	//legalBasisView = 
		new LegalBasisView();
	//analysisView = 
		new AnalysisView();
	//wellInfoView = 
		new WellInfoView();
	//purpouseView = 
		new PurpouseView();

	//exploitableReserveView = 
		new ExploitableReserveView();
	new WaterDemandView();
	new WaterDataView();

	// Visualizações relacionadas a classe WaterDataView
	//geographicTableView = 
		new GeographicTableView();
	//limitsTableView = 
		new LimitsTableView();

	new ConclusionView();

	new ActionsView();

});