/**
 * @nome Informações do Poço
 * @descricao Informações como nível estático, nível dinâmico e coordenada
 * @diretorio shared
 * @arquivo well-info-view.js
 * @id 41
 * 
 * 
 */


class WellInfoView {

    constructor() {
        this.div = document.getElementById('well-info-view');
        this.render();
    }

    render() {
        let innerHtml = `
    	<h3>II. Dados do poço:</h3>
        <ul>
            <li>Coordenadas SIRGAS 2000: 
            <span class="inter-latitude" class="highlight"></span>, 
            <span class="inter-longitude" class="highlight"></span>
            </li>
            <li>Tipo de Poço: <span class="inter-tipo-poco" class="highlight"></span></li>
            <!-- Cálculos do poço em metros -->
            <li>Profundidade: <span class="inter-profundidade" class="highlight"></span></li>
            <li>Nível Estático N.E: <span class="inter-nivel-estatico" class="highlight"></span></li>
            <li>Nível Dinâmico N.D: <span class="inter-nivel-dinamico" class="highlight"></span></li>
            <!-- Vazões --> 
            <li>Vazão média do Subsistema (L/h): <span class="inter-vazao-sistema" class="highlight"></span></li>
            <li>Vazão teste (L/h): <span class="inter-vazao-teste" class="highlight"></span></li>
            <li>Vazão autorizada (L/h): <span class="inter-vazao-autorizada" class="highlight"></span></li>
        </ul>`;

        if (this.div !== null) this.div.innerHTML = innerHtml;

    }

    update(interferencia) {
        // Latitude
        let lats = document.getElementsByClassName('inter-latitude');
        Array.from(lats).forEach(element => {
            element.textContent = interferencia.latitude?.toFixed(6) || 'XXX';
        });

        // Longitude
        let lngs = document.getElementsByClassName('inter-longitude');
        Array.from(lngs).forEach(element => {
            element.textContent = interferencia.longitude?.toFixed(6) || 'XXX';
        });

        // Tipo de Poço
        let tiposPoço = document.getElementsByClassName('inter-tipo-poco');
        Array.from(tiposPoço).forEach(element => {
            element.textContent = interferencia?.tipoPoco?.descricao || 'XXX';
        });

        // Profundidade do Poço
        let profundidades = document.getElementsByClassName('inter-profundidade');
        Array.from(profundidades).forEach(element => {
            element.textContent = interferencia?.profundidade || 'XXX';
        });

        // Nível Estático e Dinâmico
        let niveis = [
            { className: 'inter-nivel-estatico', value: interferencia.nivelEstatico },
            { className: 'inter-nivel-dinamico', value: interferencia.nivelDinamico }
        ];

        niveis.forEach(nivel => {
            let elements = document.getElementsByClassName(nivel.className);
            Array.from(elements).forEach(element => {
                element.textContent = nivel.value || 'XXX';
            });
        });

        // Vazões
        let vazoes = [
            { className: 'inter-vazao-sistema', value: interferencia.vazaoSistema },
            { className: 'inter-vazao-teste', value: interferencia.vazaoTeste },
            { className: 'inter-vazao-autorizada', value: interferencia.vazaoOutorgavel }
        ];

        vazoes.forEach(vazao => {
            let elements = document.getElementsByClassName(vazao.className);
            Array.from(elements).forEach(element => {
                element.textContent = vazao.value || 'XXX';
            });
        });


    }

}