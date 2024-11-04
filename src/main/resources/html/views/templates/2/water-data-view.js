/**
 * 
 * @nome Parecer de Outorga Prévia
 * @descricao Dados de demanda de água e localização
 * @diretorio 2
 * @arquivo water-data-view.js
 * @id 13
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
          <p>10. Considerando que o ponto de captação está localizado no subsistema R4, o limite a ser outorgado é de 80% da 
          vazão média do subsistema, pois o empreendimento está localizado em área rural. A demanda solicitada pelo usuário, 
          ajustada segundo os valores de referência da Resolução nº 18/2020, é de 920 L/dia, sendo estimado tempo de captação 
          máximo de 1 h/dia. O ato de outorga seguirá as seguintes características:
          </p>
          <p>I -  Dados da captação:</p>

          <div id="geographic-table-view"></div>
          <p>II - Demanda a ser outorgada:</p>

          <div id="limits-table-view"></div>
        </div>
        
      `;
    if (this.div !== null) this.div.innerHTML = innerHTML;

  }
}