/**
 * @id
 * @arquivo geographic-table-view.js
 * @diretorio 1
 * @descricao Despacho de Outorga Prévia
 */
class GeographicTableView {
    constructor() {
        this.div = document.getElementById('geographic-table');
    	// Dynamically assign the updateTableInfo method to the div element
        this.div.updateTableInfo = this.updateTableInfo.bind(this);
        this.render();
    }

    render() {
        let index = `
            <div align="justify" style="margin-left: auto; margin-right: auto; overflow: auto;">
                <table border="1" cellspacing="0" style="margin-left: auto; margin-right: auto; ">
                    <tbody>
                        <tr>
                            <td rowspan="2" style="width: 104px; height: 30px; text-align: center;">Ponto de Captação</td>
                            <td rowspan="2" style="width: 135px; height: 30px; text-align: center;">Bacia Hidrográfica</td>
                            <td rowspan="2" style="width: 173px; height: 30px; text-align: center;">Unidade Hidrográfica</td>
                            <td colspan="2" style="width: 248px; height: 30px;">Ponto de Captação (SIRGAS 2000)</td>
                        </tr>
                        <tr>
                            <td style="width: 125px; height: 16px; text-align: center;">Latitude</td>
                            <td style="width: 123px; height: 16px; text-align: center;">Longitude</td>
                        </tr>
                        <tr>
                            <td style="width: 104px; height: 21px; text-align: center;" id="inter-nome"></td>
                            <td style="width: 135px; height: 21px; text-align: center;" id="inter-bh"></td>
                            <td style="width: 173px; height: 21px; text-align: center;" id="inter-uh"></td>
                            <td style="width: 125px; height: 21px; text-align: center;"><strong><span id="inter-latitude"></span></strong></td>
                            <td style="width: 123px; height: 21px; text-align: center;"><strong><span id="inter-longitude"></span></strong></td>
                        </tr>
                    </tbody>
                </table>
            </div>`;

        this.div.innerHTML = index;

    }

    updateTableInfo (interferencia){
        
      document.getElementById('inter-nome').textContent = interferencia.nome || 'Desconhecido';
      document.getElementById('inter-bh').textContent = interferencia.baciaHidrografica?.descricao || 'Desconhecido';
	  document.getElementById('inter-uh').textContent = interferencia.unidadeHidrografica?.descricao || 'Desconhecido';
	  document.getElementById('inter-latitude').textContent = interferencia.latitude?.toFixed(6) || 'Desconhecido';
	  document.getElementById('inter-longitude').textContent = interferencia.longitude?.toFixed(6) || 'Desconhecido';
    }
   
}