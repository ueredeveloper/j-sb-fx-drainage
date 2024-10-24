/**
 * @descricao Assinatura compartilhada
 * @nome Assinatura do Técnico
 * @diretorio shared
 * @arquivo signature-view.js
 * @id 39
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


class SignatureView {

    constructor() {
        this.div = document.getElementById('signature-view');
        this.render();
    }

    render() {

        let index = `
            <div>
                <p style="text-align: center;"><strong>SAULO GREGORY LUZZI</strong></p>
                <p style="text-align: center;">Cordenador de Outorgas - COUT/SRH</p>
            </div>
            `;
            this.div.innerHTML = index;
    }

}