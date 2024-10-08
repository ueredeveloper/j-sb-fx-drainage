/**
 * @id 1
 * @nome chief-signature.js
 * @pasta 1
 * @descricao Despacho de Outorga Prévia
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