/**
 * @id
 * @arquivo grant-requirements-view.js
 * @diretorio 1
 * @descricao Despacho de Outorga Prévia
 */
class GrantRequirementsView {
    constructor() {
        this.div = document.getElementById('grant-requirements');
        this.render();
    }

    render() {
        let index = `
                    <p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 3º
            Ao término da perfuração do poço e previamente à captação definitiva de água, o
            outorgado deverá requerer à Adasa a respectiva outorga de direito de uso de
            água subterrânea, em formulário próprio, quando apresentará:<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">a) ensaio
            de bombeamento (contendo planilhas, gráficos e relatórios);<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">b) perfil
            construtivo litológico do poço; e<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">c)
            registro fotográfico que comprove o cumprimento do disposto no art. 7º, incisos
            II, IV, V, VI e VII, desta outorga prévia.<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 4º Esta
            outorga prévia não substitui a outorga de direito de uso de recursos hídricos,
            necessária para operação do poço e captação de água.<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 5º A
            outorga prévia terá validade de <b>03 (três) anos</b>, a contar da data de
            publicação do extrato no Diário Oficial do Distrito Federal, podendo ser renovada
            mediante solicitação do outorgado, ou prorrogada, observada a legislação
            vigente.<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">§ 1º O
            pedido de renovação desta outorga prévia poderá ser requerido à Adasa com
            antecedência mínima de 90 (noventa) dias do término do prazo de vigência fixado
            no <i>caput</i>.<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">§ 2º Na
            análise do pedido para prorrogação da presente outorga serão observadas as
            normas, os critérios e as prioridades de usos vigentes à época da renovação.<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">§ 3º A
            outorga prévia será automaticamente prorrogada até deliberação da Adasa sobre o
            referido pedido de renovação, se cumpridos os termos previstos no §1º.<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 6º A
            outorga prévia poderá ser suspensa, parcial ou totalmente, revogada ou revista,
            por prazo determinado, nos seguintes casos, previstos nos artigos 29 e 30 da
            Resolução nº 350, de 23 de junho de 2006:<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">I –
            quando o outorgado descumprir quaisquer condições e termos fixados no presente
            ato de outorga;<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">II –
            &nbsp;diante da necessidade de:<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">a) água
            para atender situações de calamidade, inclusive decorrentes de condições
            climáticas adversas;<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">b)
            prevenir ou reverter grave degradação ambiental; e<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">c)
            atender usos prioritários, de interesse coletivo, para os quais não se disponha
            de fontes alternativas.<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">III –
            racionamento de recursos hídricos, conforme regulamento específico; e<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">IV – indeferimento
            ou cassação da licença ambiental, se for o caso.<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">§ 1º A
            suspensão total ou parcial da outorga prévia não implica em indenização a
            qualquer título.<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">§ 2º A
            outorga prévia para abastecimento humano será revogada ou modificada quando
            ocorrer a ligação da rede de abastecimento de água pela concessionária de
            saneamento básico.<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 7º
            Constituem obrigações do outorgado:<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">I -
            observar os limites estabelecidos no art. 2º deste ato de outorga;<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">II -
            proteger a porção do poço perfurado executada sobre material inconsolidado e
            com possibilidade de desmoronamento, para prevenção de contaminação dos
            aquíferos por meio de percolação de águas superficiais indesejáveis;<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">III -
            construir uma laje de concreto envolvendo o tubo de revestimento, com
            declividade do centro para a borda, com espessura mínima de 10 cm (dez
            centímetros) e área não inferior a 1 m² (um metro quadrado);<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">IV -
            manter a parte externa do poço com 30 cm (trinta centímetros), no mínimo, acima
            da laje de concreto, a qual deverá ter proteção de alvenaria e cobertura
            removível;<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">V -&nbsp;
            manter área de proteção com raio de, pelo menos, 5 m (cinco metros), a partir
            dos limites do poço, que deverá ser cercado e mantido limpo;<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">VI -
            desativar e tamponar as fossas posicionadas no raio de 30 m (trinta metros) do
            poço, a fim de evitar a contaminação do aquífero;<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">VII -
            instalar hidrômetro na saída do poço, num prazo máximo de 90 (noventa) dias a
            partir da perfuração ou da publicação do extrato de outorga;<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">VIII -
            após a instalação do dispositivo de medição dos volumes extraídos, o outorgado
            deverá enviar à Adasa o resultado de sua leitura, bem como a respectiva
            planilha com os volumes mensais extraídos;<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">IX -
            responsabilizar-se pelo controle e vigilância da qualidade da água e seu padrão
            de potabilidade, conforme estabelece a Portaria do Ministério da Saúde nº
            2.914, de 12 de dezembro de 2011 e obter junto à Diretoria de Vigilância
            Ambiental da Secretaria de Saúde do Distrito Federal as autorizações cabíveis;
            e<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">X -
            construir e manter sistema de adução, reservação e distribuição, completamente
            independente do sistema de abastecimento da concessionária de água, caso o uso
            de água de poço ocorra em área atendida pela rede de abastecimento de água.<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Parágrafo
            único. Em situações especiais, a Adasa poderá reduzir o tamanho do raio de que
            trata o inciso V deste artigo, não podendo ser o raio inferior a 1 m (um
            metro).<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 8º
            Fica o outorgado sujeito à fiscalização da Adasa, por intermédio de seus
            agentes ou prepostos indicados, devendo franquear-lhes o acesso ao
            empreendimento e à documentação respectiva, como projetos, contratos,
            relatórios, registros e quaisquer outros documentos referentes à presente
            outorga.<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 9º
            Fica o outorgado sujeito às penalidades previstas na legislação em vigor em
            caso de descumprimento das disposições legais e regulamentares decorrentes da
            reserva do direito de uso da água subterrânea e pelo não atendimento das
            solicitações, recomendações e determinações da fiscalização.<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 10.
            A transferência do direito previsto neste ato, bem como qualquer alteração nas
            características do empreendimento sujeito à esta outorga prévia, deverá ser
            precedida de anuência formal da Adasa.<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 11.
            A presente outorga não dispensa ou substitui a obtenção, pelo outorgado, de
            certidões, alvarás ou licenças de qualquer natureza, exigidos pela legislação
            vigente.<o:p></o:p></span></p><p class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Parágrafo
            único. O outorgado deverá respeitar a legislação ambiental e articular-se com o
            órgão competente, com vistas à obtenção de licenças ambientais, quando couber,
            cumprir as exigências nelas contidas e responder pelas consequências do descumprimento
            das leis, regulamentos e licenças.<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 12.
            O outorgado responderá civil, penal e administrativamente, por danos causados à
            vida, à saúde, ao meio ambiente, bem como a terceiros, pelo uso inadequado que
            vier a fazer da presente outorga, na forma da Lei.<o:p></o:p></span></p><p class="MsoNormal" 
            style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">&nbsp;<o:p></o:p></span></p><p 
            class="MsoNormal" style="margin-bottom:0cm;text-align:justify;line-height:
            normal"><span style="font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;
            mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:PT-BR">Art. 13.
            Esta outorga prévia entra em vigor na data de sua publicação.<o:p></o:p></span></p>`;

        this.div.innerHTML = index;


    }


}