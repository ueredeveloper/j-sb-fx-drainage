/**
 * @nome Assinatura do Superintendente
 * @descricao Assinatura compartilhada pelos atos
 * @diretorio shared
 * @arquivo chief-signature-view.js
 * @id 32
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


class ChiefSignatureView {

    constructor() {
        this.div = document.getElementById('chief-signature-view');
        this.render();
    }

    render() {

        let index = `
            <div>
                <p style="text-align: center;"><strong>GUSTAVO ANTONIO CARNEIRO</strong></p>
                <p style="text-align: center;">Superintendente de Recursos Hídricos</p>
            </div>
            `;
            this.div.innerHTML = index;
    }

}