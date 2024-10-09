/**
 * @id
 * @arquivo well-info-view.js
 * @diretorio shared
 * @descricao Visualizações compartilhadas pelos templates
 */

class WellInfoView {

    constructor() {
        this.render();
    }

    render() {
        let html = `
    	<h3>II. Dados do poço:</h3>
        <ul>
            <li>Coordenadas SIRGAS 2000: 
            <span id="inter-latitude" class="highlight"></span>, 
            <span id="inter-longitude" class="highlight"></span>
            </li>
            <li>Tipo de Poço: <span id="inter-tipo-poco" class="highlight"></span></li>
            <!-- Cálculos do poço em metros -->
            <li>Profundidade: <span id="inter-profundidade" class="highlight"></span></li>
            <li>Nível Estático N.E: <span id="inter-nivel-estatico" class="highlight"></span></li>
            <li>Nível Dinâmico N.D: <span id="inter-nivel-dinamico" class="highlight"></span></li>
            <!-- Vazões --> 
            <li>Vazão média do Subsistema (L/h): <span id="inter-vazao-sistema" class="highlight"></span></li>
            <li>Vazão teste (L/h): <span id="inter-vazao-teste" class="highlight"></span></li>
            <li>Vazão autorizada (L/h): <span id="inter-vazao-autorizada" class="highlight"></span></li>
        </ul>`;

        document.getElementById('dados-poco').innerHTML = html;


    }
    updateInfo(interferencia) {

        document.getElementById('inter-latitude').innerText = interferencia.latitude;
        document.getElementById('inter-longitude').innerText = interferencia.longitude;
        // Tipo de Poço, quando outorga subterrânea, se tubular ou manual(cisterna)
        document.getElementById('inter-tipo-poco').innerText = interferencia.tipoPoco.descricao;
        // Poço
        document.getElementById('inter-profundidade').innerText = interferencia.profundidade;
        document.getElementById('inter-nivel-estatico').innerText = interferencia.nivelEstatico;
        document.getElementById('inter-nivel-dinamico').innerText = interferencia.nivelDinamico;
        // Vazões
        document.getElementById('inter-vazao-sistema').innerText = interferencia.vazaoSistema;
        document.getElementById('inter-vazao-teste').innerText = interferencia.vazaoTeste;
        document.getElementById('inter-vazao-autorizada').innerText = interferencia.vazaoAutorizada;

    }

}