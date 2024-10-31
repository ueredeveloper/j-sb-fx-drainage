/**
 * 
 * @arquivo conclusion-view.js
 * @diretorio 2
 * @descricao Parecer de Outorga Prévia
 * @nome Parecer de Outorga Prévia
 * @id 7
 * 
 * 
 * 
 * 
 * 
 * 
 *
 */


class ConclusionView {
    constructor() {
        this.div = document.getElementById('conclusion-view');
        this.render();
    }

    render() {
        let innerHTML = `
        <div>
            <p>11. A documentação solicitada foi atendida e sustenta a solicitação de outorga prévia de água subterrânea.</p>
            <p>12. Com base nas informações deste processo e análise do mesmo, recomendo o DEFERIMENTO do pedido e a emissão do 
            ato de outorga prévia, com prazo de validade de 03(três) anos, contados da publicação do extrato no Diário Oficial 
            do Distrito Federal.
            </p>
            <div style="display: flex; flex-direction: column;justify-content: center; align-items:center;">
                <p><strong>JULIANA SANTOS VIANNA</strong></p>
                <p><strong>Reguladora de Serviços Públicos</strong></p>
            </div>
        </div>`;
        if (this.div !== null) this.div.innerHTML = innerHTML;
    }
}
