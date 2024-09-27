
/**
 * Tabela de Limites Outorgados
 * Tag anterior: <tabela_limites_outorgados_tag></tabela_limites_outorgados_tag>
 * 
 */
class LimitsTable {
    constructor() {
        this.div = document.getElementById('authorized-limits-table');
        this.render();
    }

    render() {
        let index =  `
        <div style="overflow:auto;margin-left:auto;margin-right:auto;max-width: 800px;">
            <table border="1" cellspacing="0" style="margin-left:auto;margin-right:auto;max-width: 800px;width:90%">
                <tbody>
                    <tr>
                        <td colspan="2" style="height:10px; text-align: center;"><span style="background-color: rgb(255, 255, 255);">Limites outorgados</span></td>
                        <td style="height:10px; text-align: center;">Jan</td>
                        <td style="height:10px; text-align: center;">Fev</td>
                        <td style="height:10px; text-align: center;">Mar</td>
                        <td style="height:10px; text-align: center;">Abr</td>
                        <td style="height:10px; text-align: center;">Mai</td>
                        <td style="height:10px; text-align: center;">Jun</td>
                        <td style="height:10px; text-align: center;">Jul</td>
                        <td style="height:10px; text-align: center;">Ago</td>
                        <td style="height:10px; text-align: center;">Set</td>
                        <td style="height:10px; text-align: center;">Out</td>
                        <td style="height:10px; text-align: center;">Nov</td>
                        <td style="height:10px; text-align: center;">Dez</td>
                    </tr>
                    <tr>
                        <td rowspan="2" style="height:10px; text-align: center;">
                        <p>&nbsp;&nbsp;&nbsp; Q Max</p>
                        </td>
                        <td style="height:10px; text-align: center;">(L/h)</td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_jan_tag></q_litros_hora_jan_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_fev_tag></q_litros_hora_fev_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_mar_tag></q_litros_hora_mar_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_abr_tag></q_litros_hora_abr_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_mai_tag></q_litros_hora_mai_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_jun_tag></q_litros_hora_jun_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_jul_tag></q_litros_hora_jul_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_ago_tag></q_litros_hora_ago_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_set_tag></q_litros_hora_set_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_out_tag></q_litros_hora_out_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_nov_tag></q_litros_hora_nov_tag></td>
                        <td style="height:10px; text-align: center;"><q_litros_hora_dez_tag></q_litros_hora_dez_tag></td>
                    </tr>
                    <tr>
                        <td style="height:10px; text-align: center;">(m<sup>3</sup>/h)</td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_jan_tag></q_metros_hora_jan_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_fev_tag></q_metros_hora_fev_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_mar_tag></q_metros_hora_mar_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_abr_tag></q_metros_hora_abr_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_mai_tag></q_metros_hora_mai_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_jun_tag></q_metros_hora_jun_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_jul_tag></q_metros_hora_jul_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_ago_tag></q_metros_hora_ago_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_set_tag></q_metros_hora_set_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_out_tag></q_metros_hora_out_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_nov_tag></q_metros_hora_nov_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_hora_dez_tag></q_metros_hora_dez_tag></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="height:10px;">T. max. (h/dia)</td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_jan_tag></t_horas_dia_jan_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_fev_tag></t_horas_dia_fev_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_mar_tag></t_horas_dia_mar_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_abr_tag></t_horas_dia_abr_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_mai_tag></t_horas_dia_mai_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_jun_tag></t_horas_dia_jun_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_jul_tag></t_horas_dia_jul_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_ago_tag></t_horas_dia_ago_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_set_tag></t_horas_dia_set_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_out_tag></t_horas_dia_out_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_nov_tag></t_horas_dia_nov_tag></td>
                        <td style="height:10px; text-align: center;"><t_horas_dia_dez_tag></t_horas_dia_dez_tag></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="height:10px;">V. max. dia (m<sup>3</sup>/dia)</td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_jan_tag></q_metros_dia_jan_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_fev_tag></q_metros_dia_fev_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_mar_tag></q_metros_dia_mar_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_abr_tag></q_metros_dia_abr_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_mai_tag></q_metros_dia_mai_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_jun_tag></q_metros_dia_jun_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_jul_tag></q_metros_dia_jul_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_ago_tag></q_metros_dia_ago_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_set_tag></q_metros_dia_set_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_out_tag></q_metros_dia_out_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_nov_tag></q_metros_dia_nov_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_dia_dez_tag></q_metros_dia_dez_tag></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="height:10px;">P. (dias/mês)</td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_jan_tag></t_dias_mes_jan_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_fev_tag></t_dias_mes_fev_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_mar_tag></t_dias_mes_mar_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_abr_tag></t_dias_mes_abr_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_mai_tag></t_dias_mes_mai_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_jun_tag></t_dias_mes_jun_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_jul_tag></t_dias_mes_jul_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_ago_tag></t_dias_mes_ago_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_set_tag></t_dias_mes_set_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_out_tag></t_dias_mes_out_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_nov_tag></t_dias_mes_nov_tag></td>
                        <td style="height:10px; text-align: center;"><t_dias_mes_dez_tag></t_dias_mes_dez_tag></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="height:10px;">V. max. mês (m<sup>3</sup>/mês)</td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_jan_tag></q_metros_mes_jan_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_fev_tag></q_metros_mes_fev_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_mar_tag></q_metros_mes_mar_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_abr_tag></q_metros_mes_abr_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_mai_tag></q_metros_mes_mai_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_jun_tag></q_metros_mes_jun_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_jul_tag></q_metros_mes_jul_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_ago_tag></q_metros_mes_ago_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_set_tag></q_metros_mes_set_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_out_tag></q_metros_mes_out_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_nov_tag></q_metros_mes_nov_tag></td>
                        <td style="height:10px; text-align: center;"><q_metros_mes_dez_tag></q_metros_mes_dez_tag></td>
                    </tr>
                </tbody>
            </table>

            <table border="0" style="margin-left:auto;margin-right:auto;max-width: 800px; width:90%">
                <tbody>
                    <tr border="0">
                        <td border="0">
                        <div border="0" style="font-size:12px"><em>Q. max: Vazão máxima em litros por hora e em metros cúbicos por hora;&nbsp;<br>
                        T. max: Tempo máximo de captação em horas por dia;&nbsp;&nbsp;<br>
                        V. max. dia: Volume máximo em metros cúbicos por dia;&nbsp;<br>
                        P: Dias de captação por mês; e&nbsp;&nbsp;<br>
                        V. max. mês: Volume máximo em metros cúbicos por mês.&nbsp;<br>
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
   
}