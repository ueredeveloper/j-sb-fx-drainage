/**
* @nome Parecer de Outorga De Direito de Uso
* @descricao Análise do parecer
* @diretorio 4
* @arquivo analyse-view.js
* @id 17
 * 
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

    //classes: inter-subsistema inter-bh inter-uh

    render() {
        let innerHTML = `
			<div>
			<p><strong>III. DA ANÁLISE</strong></p>
			<p></p><p><br></p>
			<p>4. Outorga anterior: Despacho nºou Regularização</p>
			<p></p>
            <p><br></p>
			<p>5. O ponto de captação analisado está localizado no subsistema <span class="inter-subsistema"></span>, 
            Unidade Hidrográfica <span class="inter-uh"></span>, Bacia Hidrográfica do <span class="inter-bh"></span>.
            </p>
			</div><div>
			<p></p><p><br></p>

			<h3>I. Localização da propriedade e do poço:</h3>
			<p></p><p><br></p>

			<p>Figura 01: croqui de localização da propriedade.</p>
			<p><br></p><p></p>

			<p>Figura 02: croqui de localização do poço.</p>
			<p></p><p><br></p>

			<p>Figura 03: Croqui da área com existência de irrigação (Frutífera) em 31/05/2016.</p>
			<p><br></p><p></p>

			<p>Figura 04: Croqui da área do sistema de abastecimento da Caesb - ( Portal Atlas Caesb).</p>
			<p></p><p><br></p>

			<!--<p>II. Dados do poço:</p>-->
            <!-- Informações do poço -->
			<div id="well-info-view"></div>

			<div>
			<p></p><p><br></p>

			<p>6. As finalidades de uso e respectivas demandas solicitadas estão dispostos na tabela abaixo, organizadas por tipo de processo:</p>

			<p></p><p><br></p>

			<p>Tabela 01: Finalidades e demandas requeridas.</p>

            <!-- Finalidades Requeridas -->
			<div style="display:flex; justify-content: center;" id="tbl-request-purpouse-view"></div>

			<p><br></p>

			<div style="align:center" id="purpouse-legal-basis-view"></div>

			<p>
			8. As demandas solicitadas e devidamente justificadas para cada finalidade de uso foram analisadas considerando a 
			disponibilidade hídrica da reserva explotável dos subsistemas subterrâneos. As demandas que apresentaram ausência de 
			justificativas para as estimativas de uso, foram ajustadas às recomendações da Resolução nº 18/2020:
			</p>

			<p><br></p>

			<p>Tabela 02: Finalidades e demandas ajustadas, conforme a Resolução nº 18/2020.</p>

            <!-- Finalidades Autorizadas -->
            <div id="tbl-authorized-purpouse-view" style="display:flex; justify-content: center;"></div>

            
            <div id="exploitable-reserve-view"></div>


			<p>10.&nbsp;As demandas poderão ser outorgadas pela Adasa, desde que observados os limites estabelecidos pela 
			Resolução/ADASA nº 16/2018 e valores da demanda ajustados na tabela 2.</p>
			<p><br></p>

			<p>Resolução/ADASA nº 16/2018</p>

			<p style="margin-left:30.0pt;">Art. 5º. Ficam estabelecidos os seguintes limites a serem outorgados para as captações de água subterrânea:</p>

			<p style="margin-left:30.0pt;">I - Até 80% da vazão do teste de bombeamento nas porções dos aquíferos localizadas em áreas rurais, com tempo de captação máximo de 20 h por dia;</p>

			<p style="margin-left:30.0pt;">II - Até 50% da vazão do teste de bombeamento nas porções dos aquíferos localizados em áreas urbanas, com tempo de captação máximo de 20 h por dia;</p>

			<p style="margin-left:30.0pt;">III - Nos casos de abastecimento humano, os limites dos incisos I e II poderão atingir até 90% da vazão nominal do poço;</p>

			<p style="margin-left:30.0pt;">IV- Na ausência de dados de testes de bombeamento, serão consideradas as vazões médias regionais e período máximo de captação de 20 (vinte) horas por dia.</p>

			<p style="margin-left:30.0pt;">&nbsp;</p>

			<p><br></p>
			<p>11. Considerando que o ponto de captação está localizado no subsistema <span class="inter-subsistema"></span>, o limite a ser outorgado é de 80% da vazão média do subsistema, pois o empreendimento está localizado em área rural. A demanda solicitada pelo usuário, ajustada segundo os valores de referência da Resolução nº 18/2020 é de <listros_dia_abr_tag></listros_dia_abr_tag> L/dia, sendo estimado tempo de captação máximo de&nbsp; <litros_hora_abr_tag></litros_hora_abr_tag> h/dia. O ato de outorga seguirá as seguintes características:<o:p></o:p></p>

			<p>&nbsp;</p>

		
			<p><br></p><p>I -&nbsp; Dados da captação:</p>

			<div id="geographic-table-view"></div>

			<p>&nbsp;</p>

			<p><br></p><p>II - &nbsp;&nbsp;Demanda a ser outorgada:</p>

			<div id="limits-table-view"></div>

			<p>12. As solicitações de renovação de outorga protocoladas fora do prazo previsto no parágrafo único, art. 27 
			da Resolução/Adasa n° 350/2006 foram consideradas neste parecer como novas outorgas.</p>

 			<p>13. Cabe ressaltar que conforme o art. 39 da Resolução Adasa n° 350/2006, o outorgado deverá se 
			responsabilizar pelo padrão de qualidade e potabilidade da água a partir da retirada do aquífero subterrâneo.</p>

 			<p>14. De acordo com a perfilagem ótica (vídeo inspeção do poço - documento SEI - 153209612, indicando 
			características no domínio freático/poroso (captação da água existente no domínio freático/poroso de um poço 
			tubular raso), em conformidade com a Resolução nº 16, de 03 de fevereiro de 2023.</p>

			<p>15. A documentação solicitada foi atendida e sustenta a solicitação de outorga de direito de uso  de água 
			subterrânea.</p>

			</div>

		`
		if (this.div !== null) this.div.innerHTML = innerHTML;

        new PurpouseLegalBasisView();
        //new PurpouseView();
        new WellInfoView();
		new GeographicTableView();
		new LimitsTableView();

    }
    update (documento, interferencia){

        let finalidades = interferencia.finalidades;
		let usuario = documento.usuarios[0];
       
		let _items = document.getElementsByClassName('inter-finalidades');

        Array.from(_items).forEach(element => {
            let innerHTML = new FinalidadeModel().getPurpouseString(finalidades);
            element.innerHTML = innerHTML
        });

		let __items = document.getElementsByClassName('us-nome');
    	// Converte o resultado para array e atualiza
    	Array.from(__items).forEach(element => {
    		element.innerHTML = new UsuarioModel().getNome(usuario);
    	});
    	let ___items = document.getElementsByClassName('us-cpf-cnpj');
    	// Converte o resultado para array e atualiza
    	Array.from(___items).forEach(element => {
    		element.innerHTML = new UsuarioModel().formatCpfCnpj(usuario.cpfCnpj);
    	});

		let ____items = document.getElementsByClassName('int-tipo-poco');

		Array.from(____items).forEach(element => {
			let innerHTML = interferencia?.tipoPoco?.descricao || 'XXX';
			element.innerHTML = innerHTML;
		});

    }
}