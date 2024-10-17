/**
 * @diretorio 2
 * @descricao 
 * @id 5

class AnalysisView {
    constructor() {
        this.render();
    }

    render() {
      let html = `
        <h2>III. DA ANÁLISE</h2>
			<p>3. Existe outorga anterior: </p>
	    	<p>4. O ponto de captação analisado está localizado no subsistema 
	    	<span class="inter-subsistema" class="highlight"></span>, 
	    	Unidade Hidrográfica 
	        <span class="inter-uh" class="highlight"></span>, 
	        Bacia Hidrográfica do 
	        <span class="inter-bh" class="highlight"></span>.</p>
	        <h3>I. Localização da propriedade e do poço:</h3>
	        <p>Figura 01: croqui de localização da propriedade.</p>
	        <p>Figura 02: croqui de localização do poço.</p>
	        <p>Figura 03: Croqui da área com existência de irrigação (Frutífera) em 31/05/2016.</p>
	        <p>Figura 04: Croqui da área do sistema de abastecimento da Caesb - (Portal Atlas Caesb).</p> 
        `;
      
      document.getElementById('analises').innerHTML = html;
    }
    
    updateInfo (interferencia){
    	let subsistemas = document.getElementsByClassName('inter-subsistema');
    	// Converte o resultado para array e atualiza
    	Array.from(subsistemas).forEach(element => {
    		element.innerHTML = new InterferenciaModel().getNomeSubsistema(interferencia);
    	});
    }
    
}