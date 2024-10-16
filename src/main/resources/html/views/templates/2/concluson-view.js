/**
 * @descricao Parecer de Outorga Prévia
 * @diretorio 2
 * @arquivo concluson-view.js
 * @id 6
 * 
 * 
 * 
 *
 */

 
class ConclusionView {
    constructor() {
        this.render();
    }

    render() {
        let html = `
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

        document.getElementById('conclusion').innerHTML = html;
    }
}
