/**
 * 
 * @nome Parecer de Outorga Prévia
 * @descricao Análise da outorga
 * @diretorio 2
 * @arquivo analyse-view.js
 * @id 6
 * 
 * 
 * 
 * 
 * 
 * 
 *
 */

class AnalyseView {
	constructor() {
		this.div = document.getElementById('analyse-view');
		this.render();
	}

	render() {
		let innerHTML = `
        <h2>III. DA ANÁLISE</h2>
			<p>3. Existe outorga anterior: </p>
	    	<p>4. O ponto de captação analisado está localizado no subsistema <span class="inter-subsistema" class="highlight"></span>, 
	    	Unidade Hidrográfica 
	        <span class="inter-uh" class="highlight"></span>, 
	        Bacia Hidrográfica do 
	        <span class="inter-bh" class="highlight"></span>.</p>
	        <h3>I. Localização da propriedade e do poço:</h3>
	        <p>Figura 01: croqui de localização da propriedade.</p>
	        <p>Figura 02: croqui de localização do poço.</p>
	        <p>Figura 03: Croqui da área com existência de irrigação (Frutífera) em 31/05/2016.</p>
	        <p>Figura 04: Croqui da área do sistema de abastecimento da Caesb - (Portal Atlas Caesb).</p> 
			
			
            <!-- Informações do poço -->
			<div id="well-info-view"></div>


			<p>4. As finalidades de uso e respectivas demandas solicitadas estão dispostos na tabela abaixo, organizadas por tipo de processo:</p>

			<p>&nbsp;</p>

			<p><br></p><p>Tabela 01: Finalidades e demandas requeridas.</p>
			<!-- Finalidades Requeridas -->
			<div style="display:flex; justify-content: center;" id="tbl-request-purpouse-view"></div>

			<div style="align:center" id="purpouse-legal-basis-view"></div>

			<p>7. As demandas solicitadas e devidamente justificadas para cada finalidade de uso foram analisadas 
			considerando a disponibilidade hídrica da reserva explotável dos subsistemas subterrâneos. As demandas 
			que apresentaram ausência de justificativas para as estimativas de uso, foram ajustadas às recomendações 
			da Resolução nº 18/2020:
			</p>

			<p><br></p><p>Tabela 02: Finalidades e demandas ajustadas, conforme a Resolução nº 18/2020:</p>

			<!-- Finalidades Autorizadas -->
            <div id="tbl-authorized-purpouse-view" style="display:flex; justify-content: center;"></div>

			<div id="exploitable-reserve-view"></div>

			<!-- Demanda --> 
			 <div id="water-demand-view"></div>

			<!-- Ponto de Captação e Limites Outorgados -->
			<div id="water-data-view"></div>

			<!-- Conclusão -->
			<div id="conclusion-view"></div>

			<!-- Assinatura -->
			<div id="signature-view"></div>
			
        `;

		if (this.div !== null) this.div.innerHTML = innerHTML;

		new WellInfoView();
		new PurpouseLegalBasisView();
		new ExploitableReserveView();

		new WaterDemandView();
		new WaterDataView();

		new ConclusionView();

	}

	update(documento) {

		let items = document.getElementsByClassName('inter-subsistema');

		// Converte o resultado para array e atualiza
		Array.from(items).forEach(element => {
			let interferencia = documento.endereco.interferencias[0];
			element.innerHTML = new InterferenciaModel().getNomeSubsistema(interferencia) || 'XXX';
		});
	}

}