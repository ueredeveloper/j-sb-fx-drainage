/**
 * 
 * @nome Parecer de Outorga Prévia
 * @descricao Dados de demanda de água e localização
 * @diretorio 2
 * @arquivo water-data-view.js
 * @id 
 *
 *
 */

class WaterDataView {
  constructor() {
    this.div = document.getElementById('water-data-view');
    this.render();
  }

  render() {
    const innerHTML = `
        <div>
          <p>10. Considerando que o ponto de captação está localizado no subsistema <span class="inter-sistema"></span>, o limite a ser outorgado é de 80% da 
          vazão média do subsistema, pois o empreendimento está localizado em área rural. A demanda solicitada pelo usuário, 
          ajustada segundo os valores de referência da Resolução nº 18/2020, é de 
          <b><span class="dem-l-dia"></span> L/dia</b>, 
          vazão de <b><span class="inter-vazao-outorgavel"></span> (L/h)</b>, 
          sendo estimado tempo de captação 
          máximo de <b><span class="dem-h-dia"></span>h/dia</b>. O ato de outorga seguirá as seguintes características:
          </p>
          <p>I -  Dados da captação:</p>

          <div id="geographic-table-view"></div>
          <p>II - Demanda a ser outorgada:</p>

          <div id="limits-table-view"></div>
        </div>
        
      `;
    if (this.div !== null) this.div.innerHTML = innerHTML;

  }

  update(interferencia) {

	// Captura a demanda de abril, que sempre está preenchida. As vazões de jan, fev, mar, nov, dez podem
	//não estar preenchidas
    let aprilFlow4 = interferencia?.demandas.find(dem => dem.mes = 4);

    let _items = document.getElementsByClassName('dem-l-dia');
    // Converte o resultado para array e atualiza
    Array.from(_items).forEach(item => {
      item.innerHTML = aprilFlow4?.vazao || 'XXX';

    });

    let vazaoOutorgavel = interferencia?.vazaoOutorgavel || 'XXX'

    let __items = document.getElementsByClassName('inter-vazao-outorgavel');
    // Converte o resultado para array e atualiza
    Array.from(__items).forEach(item => {
      item.innerHTML = vazaoOutorgavel;

    });

    let ___items = document.getElementsByClassName('dem-h-dia');
    // Converte o resultado para array e atualiza
    Array.from(___items).forEach(item => {
      item.innerHTML = aprilFlow4?.tempo || 'XXX';

    });

  }
}