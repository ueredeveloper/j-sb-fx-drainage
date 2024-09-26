/**
 * Assinatura da chefia
 */
class Buttons {
    constructor(geoTab) {
        this.div = document.getElementById('buttons');
        this.geoTab = geoTab;
        this.render();
        this.updateGeographicTable();
    }

    render() {

        let index = `
            <div>
                <button id='btn-update-lat'>update interferencia</button>
            </div>
            `;
        this.div.innerHTML = index;
    }
    updateGeographicTable () {
        let button = document.getElementById('btn-update-lat');

        button.onclick = function () {
            //  this.geoTab.updateTableInfo({'latitude': '1'})

            new GeographicTable().updateTableInfo(
                {
                    'latitude': -15.123456789, 'longitude': -16.78456123,
                    'baciaHidrografica': { 'id': 1, 'descricao': 'Bacia do Maranhão' },
                    'unidadeHidrografica': { 'id': 1, 'descricao': 'Unidade do Maranhão' }
                }
            );
        };
    }



}