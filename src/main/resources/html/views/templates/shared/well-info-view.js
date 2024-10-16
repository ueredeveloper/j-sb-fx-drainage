/**
 * @descricao Visualizações Compartilhadas
 * @diretorio shared
 * @arquivo well-info-view.js
 * @id 32
 * 
 * 
 * 
 *
 */


 class WellInfoView {

    constructor() {this.render();}

    render() {
        let html = `
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

        document.getElementById('dados-poco').innerHTML = html;


    }
    updateInfo(interferencia) {
        // Latitude
        let lats = document.getElementsByClassName('inter-latitude');
        Array.from(lats).forEach(element => {
            element.textContent = interferencia.latitude?.toFixed(6) || 'Desconhecido';
        });

        // Longitude
        let lngs = document.getElementsByClassName('inter-longitude');
        Array.from(lngs).forEach(element => {
            element.textContent = interferencia.longitude?.toFixed(6) || 'Desconhecido';
        });

        // Tipo de Poço
        let tiposPoço = document.getElementsByClassName('inter-tipo-poco');
        Array.from(tiposPoço).forEach(element => {
            element.textContent = interferencia?.tipoPoco?.descricao || 'Desconhecido';
        });

        // Profundidade do Poço
        let profundidades = document.getElementsByClassName('inter-profundidade');
        Array.from(profundidades).forEach(element => {
            element.textContent = interferencia?.profundidade || 'Desconhecido';
        });

        // Nível Estático e Dinâmico
        let niveis = [
            { className: 'inter-nivel-estatico', value: interferencia.nivelEstatico },
            { className: 'inter-nivel-dinamico', value: interferencia.nivelDinamico }
        ];

        niveis.forEach(nivel => {
            let elements = document.getElementsByClassName(nivel.className);
            Array.from(elements).forEach(element => {
                element.textContent = nivel.value || 'Desconhecido';
            });
        });

        // Vazões
        let vazoes = [
            { className: 'inter-vazao-sistema', value: interferencia.vazaoSistema },
            { className: 'inter-vazao-teste', value: interferencia.vazaoTeste },
            { className: 'inter-vazao-autorizada', value: interferencia.vazaoAutorizada }
        ];

        vazoes.forEach(vazao => {
            let elements = document.getElementsByClassName(vazao.className);
            Array.from(elements).forEach(element => {
                element.textContent = vazao.value || 'Desconhecido';
            });
        });


    }

}