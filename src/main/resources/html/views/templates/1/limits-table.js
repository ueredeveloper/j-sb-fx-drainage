
/**
 * Tabela de Limites Outorgados
 * Tag anterior:<tabela_limites_outorgados_tag></tabela_limites_outorgados_tag>
 * @id 5
 * @descricao Despacho de Outorga Prévia
 * @pasta 1
 * @nome limits-table.js
 */
class LimitsTable {
    constructor() {
        this.div = document.getElementById('authorized-limits-table');
        this.render();
    }

    render() {
        let index = `
        <div style="overflow:auto;margin-left:auto;margin-right:auto;">
            <table border="1" cellspacing="0" style="margin-left:auto;margin-right:auto;width:90%">
                <tbody>
                    <tr>
                        <td colspan="2" style="text-align:center;"><span style="background-color:rgb(255, 255, 255);">Limites outorgados</span></td>
                        <td style="text-align:center;">Jan</td>
                        <td style="text-align:center;">Fev</td>
                        <td style="text-align:center;">Mar</td>
                        <td style="text-align:center;">Abr</td>
                        <td style="text-align:center;">Mai</td>
                        <td style="text-align:center;">Jun</td>
                        <td style="text-align:center;">Jul</td>
                        <td style="text-align:center;">Ago</td>
                        <td style="text-align:center;">Set</td>
                        <td style="text-align:center;">Out</td>
                        <td style="text-align:center;">Nov</td>
                        <td style="text-align:center;">Dez</td>
                    </tr>
                    <tr>
                        <td rowspan="2" style="text-align:center;">
                        <p style="text-align:center;"> Q Max</p>
                        </td>
                        <td style="text-align:center;">(L/h)</td>
                        <td style="text-align:center;"><span id="q-litros-hora-jan"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-fev"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-mar"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-abr"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-mai"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-jun"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-jul"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-ago"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-set"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-out"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-nov"></span></td>
                        <td style="text-align:center;"><span id="q-litros-hora-dez"></span></td>

                    </tr>
                    <tr>
                        <td style="text-align:center;">(m<sup>3</sup>/h)</td>
                        <td style="text-align:center;"><span id="q-m3-hora-jan"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-fev"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-mar"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-abr"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-mai"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-jun"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-jul"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-ago"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-set"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-out"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-nov"></span></td>
                        <td style="text-align:center;"><span id="q-m3-hora-dez"></span></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align:center;">T. max. (h/dia)</td>
                        <td style="text-align:center;"><span id="t-horas-dia-jan"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-fev"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-mar"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-abr"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-mai"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-jun"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-jul"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-ago"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-set"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-out"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-nov"></span></td>
                        <td style="text-align:center;"><span id="t-horas-dia-dez"></span></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align:center;">V. max. dia (m<sup>3</sup>/dia)</td>
                        <td style="text-align:center;"><span id="q-m3-dia-jan"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-fev"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-mar"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-abr"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-mai"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-jun"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-jul"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-ago"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-set"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-out"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-nov"></span></td>
                        <td style="text-align:center;"><span id="q-m3-dia-dez"></span></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align:center;">P. (dias/mês)</td>
                        <td style="text-align:center;"><span id="p-dias-mes-jan"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-fev"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-mar"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-abr"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-mai"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-jun"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-jul"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-ago"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-set"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-out"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-nov"></span></td>
                        <td style="text-align:center;"><span id="p-dias-mes-dez"></span></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align:center;">V. max. mês (m<sup>3</sup>/mês)</td>
                        <td style="text-align:center;"><span id="q-m3-mes-jan"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-fev"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-mar"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-abr"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-mai"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-jun"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-jul"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-ago"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-set"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-out"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-nov"></span></td>
                        <td style="text-align:center;"><span id="q-m3-mes-dez"></span></td>
                    </tr>
                </tbody>
            </table>

            <table border="0" style="margin-left:auto;margin-right:auto; width:90%">
                <tbody>
                    <tr border="0">
                        <td border="0">
                        <div border="0" style="font-size:12px"><em>Q. max:Vazão máxima em litros por hora e em metros cúbicos por hora;&nbsp;<br>
                        T. max:Tempo máximo de captação em horas por dia;&nbsp;&nbsp;<br>
                        V. max. dia:Volume máximo em metros cúbicos por dia;&nbsp;<br>
                        P:Dias de captação por mês; e&nbsp;&nbsp;<br>
                        V. max. mês:Volume máximo em metros cúbicos por mês.&nbsp;<br>
                        * 1 m³ (um metro cúbico) corresponde a 1.000 L (mil litros)&nbsp;</em></div>
                        </td>
                    </tr>
                </tbody>
                <tbody>
                </tbody>
            </table>
        </div>   
        `
        this.div.innerHTML = index;

    }
    updateAuthorizedLimits(interferencia) {
        // Atualizar vazão (l/h)
        const months = [
            "jan", "fev", "mar", "abr", "mai", "jun",
            "jul", "ago", "set", "out", "nov", "dez"
        ];
        // Filtra as finalidades autorizadas e organiza por mês, do 1 ao 12.
        let authorizedDemands = interferencia.demandas
            .filter(dem => dem.tipoFinalidade.id === 2)  // Filtra por tipoFinalidade com id igual a 2
            .sort((a, b) => a.mes - b.mes);  // Ordena por mês (de 1 a 12)

        months.forEach((month, index) => {
            document.getElementById(`q-litros-hora-${month}`).innerText = formatNumber(interferencia.vazaoAutorizada);
            document.getElementById(`q-m3-hora-${month}`).innerText = maskDoubleToFloat(convertFlowToM3(interferencia.vazaoAutorizada));
            document.getElementById(`t-horas-dia-${month}`).innerText = authorizedDemands[index]?.tempo || 'N/A'; // Adiciona verificação de índice
            document.getElementById(`q-m3-dia-${month}`).innerText = maskDoubleToFloat(calculateM3Day(interferencia.vazaoAutorizada, authorizedDemands[index].tempo));
            document.getElementById(`p-dias-mes-${month}`).innerText = authorizedDemands[index].periodo
            document.getElementById(`q-m3-mes-${month}`).innerText = maskDoubleToFloat(calculateM3Month(interferencia.vazaoAutorizada, authorizedDemands[index].tempo, authorizedDemands[index].periodo));
        });

    }

}