/**
 * @arquivo water-data-view.js
 * @id 
 * @diretorio 2
 * @descricao Parecer de Outorga Prévia
 */
class WaterDataView {
    constructor() {
        this.render();
    }

    render() {
        const html = `
        <div>
          <p>10. Considerando que o ponto de captação está localizado no subsistema R4, o limite a ser outorgado é de 80% da vazão média do subsistema, pois o empreendimento está localizado em área rural. A demanda solicitada pelo usuário, ajustada segundo os valores de referência da Resolução nº 18/2020, é de 920 L/dia, sendo estimado tempo de captação máximo de 1 h/dia. O ato de outorga seguirá as seguintes características:</p>
          <p>I -  Dados da captação:</p>
          <div id="geographic-table"></div>
          <p>II -   Demanda a ser outorgada:</p>
          <div id="authorized-limits-table"></div>
        </div>
        
      `;
        document.getElementById('water-data-view').innerHTML = html;

    }
}