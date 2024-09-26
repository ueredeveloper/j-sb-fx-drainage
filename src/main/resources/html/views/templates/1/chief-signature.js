/**
 * Assinatura da chefia
 */
class ChiefSignature {
    constructor() {
        this.div = document.getElementById('chief-signature');
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