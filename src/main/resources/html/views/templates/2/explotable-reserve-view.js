/**
 * @diretorio 2
 * @descricao 
 * @id 7
class ExploitableReserveView {
    constructor() { this.render(); }
    render() {
        let html = `
        <div>
        <p>8. A reserva explotável e balanço hídrico do subsistema subterrâneo apresenta dados favoráveis, 
        considerando a inclusão das demandas requeridas, conforme tabelas abaixo:
        </p>

        <p>Figura 04: Reserva explotável e balanço hídrico do subsistema <span id="subsistema"></span>.</p>
        </div>
        
         `

        document.getElementById('reserva-explotavel').innerHTML = html;
    }

    updateInfo(documento){

        let subsistema = documento.endereco?.interferencias[0]?.subsistema || "Não encontrado";
        document.getElementById('subsistema').innerHTML = subsistema
    }
}