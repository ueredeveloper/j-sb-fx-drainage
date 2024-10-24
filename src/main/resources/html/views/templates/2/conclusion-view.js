/**
 * @nome Parecer de Outorga Prévia
 * @descricao Conclusão do Parecer
 * @diretorio 2
 * @arquivo conclusion-view.js
 * @id 5
 * 
 * 
 * 
 * 
 * 
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
        </div>`;

        document.getElementById('conclusion').innerHTML = html;
    }
}
