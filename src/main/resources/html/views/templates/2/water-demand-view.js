/**
 * @nome Parecer de Outorga Prévia
 * @descricao Demanda a ser outorgada
 * @diretorio 2
 * @arquivo water-demand-view.js
 * @id 14
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

 
class WaterDemandView {
    constructor() {
      this.div = document.getElementById('water-demand-view');
      this.render();
    }
  
    render() {
      const innerHTML = `
        <div>
          <p>9. As demandas poderão ser outorgadas pela Adasa, desde que observados os limites estabelecidos pela 
          Resolução/ADASA nº 16/2018 e valores da demanda ajustados na tabela 2.</p>
          <p>Resolução/ADASA nº 16/2018</p>
          <p>Art. 5º. Ficam estabelecidos os seguintes limites a serem outorgados para as captações de água subterrânea:</p>
          <ul>
            <li>I - Até 80% da vazão do teste de bombeamento nas porções dos aquíferos localizadas em áreas rurais, com tempo 
            de captação máximo de 20 h por dia;</li>
            <li>II - Até 50% da vazão do teste de bombeamento nas porções dos aquíferos localizados em áreas urbanas, com tempo 
            de captação máximo de 20 h por dia;</li>
            <li>III - Nos casos de abastecimento humano, os limites dos incisos I e II poderão atingir até 90% da vazão nominal 
            do poço;</li>
            <li>IV - Na ausência de dados de testes de bombeamento, serão consideradas as vazões médias regionais e período 
            máximo de captação de 20 (vinte) horas por dia.
            </li>
          </ul>
        </div>
      `;

      if (this.div !== null) this.div.innerHTML = innerHTML;
    }
  }