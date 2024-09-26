// script.js

function App() {

	/* Documento */
	const documento = {
		"id": 2,
		"numero": "123/456",
		"numeroSei": "456789",
		"processo": {
			"id": 2,
			"numero": "45.789.456",
			"anexo": {
				"id": 4,
				"numero": "789.456",
				"processos": []
			},
			"documentos": []
		},
		"tipo": {
			"id": 3,
			"descricao": "Despacho"
		},
		"endereco": {
			"id": 1,
			"logradouro": "Rua dos Barris, 123",
			"cidade": null,
			"bairro": null,
			"cep": null,
			"interferencias": [],
			"estado": null
		},
		"usuarios": []
	};

	const usuario = {
		"id": 1,
		"nome": "Carlos Coqueiro",
		"cpfCnpj": 12345678000100
	};

	const interferencia = new Interferencia().getInterference();

	/**
	 * Converte uma array de finalidades em um string
	 * Tag anterior: <finalidades_tag></finalidades_tag>
	 * @param {*} finalidades 
	 * @returns 
	 */
	function createPurpouseString(finalidades) {
		let arrayToString = finalidades.map(f => {
			if (f.tipoFinalidade.id === 2) return f.finalidade
			return '';
			// converte array para string e coloca tudo em minúsculo
		}).toString().toLowerCase();

		// Remover a primeira vírgula
		let result = arrayToString.replace(/^,/, '');

		// Substituir a última vírgula por 'e'
		result = result.replace(/,(?=[^,]*$)/, ' e ');
		// Adicionar espaço após todas as vírgulas
		result = result.replace(/,/g, ', ');

		return result;
	}
	/**
	 * Cria máscara para CPF e CPNJ
	 * Tag anterior: <us_cpfcnpj_tag></us_cpfcnpj_tag> 
	 * @param {*} value 
	 * @returns 
	 */
	function formatCpfCnpj(value) {
		value = value.toString();

		// Verifica se é CPF (11 dígitos) ou CNPJ (14 dígitos)
		if (value.length === 11) {
			// Formatar CPF: 123.456.789-12
			return value.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
		} else if (value.length === 14) {
			// Formatar CNPJ: 12.345.678/0001-00
			return value.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, '$1.$2.$3/$4-$5');
		} else {
			// Se não for CPF ou CNPJ válido, retorna o valor sem formatação
			return value;
		}
	}

	/**
	 * Cria uma string com o tipo de poço
	 * Tag anterior: <inter_tipo_poco_tag></inter_tipo_poco_tag>.
	 */
	function typeOfWellToString() {
		return interferencia
	}

	/**
	 * Cria string com endereço
	 * Tag anterior: <end_log_tag></end_log_tag> 
	 */

	function addressToString(endereco) {
		return endereco.logradouro;
	}

	const appDiv = document.getElementById("app");

	appDiv.innerHTML = `
		<div>
			<div style="text-align: center;">&nbsp;</div>

			<p style="margin-left: 400px;">Emite outorga prévia para reservar o direito de uso de água subterrânea a&nbsp;${usuario.nome}, para fins de ${createPurpouseString(interferencia.finalidades)} .

			</p><p>&nbsp;</p>
		

			<div align="justify">
			<p>O SUPERINTENDENTE DE RECURSOS HÍDRICOS DA AGÊNCIA REGULADORA DE ÁGUAS, ENERGIA E SANEAMENTO BÁSICO DO DISTRITO FEDERAL – ADASA, no uso de suas atribuições regimentais e com base na competência que lhe foi delegada pela Diretoria Colegiada, nos termos da Resolução nº 02, de 25 de janeiro de 2019, c/c Portaria nº 49, de 02 de maio de 2019 e com base no art. 12 da Lei nº 2.725, de 13 de junho de 2001, e inciso VII do art. 23 da Lei nº 4.285, de 26 de dezembro de 2008, tendo em vista o que consta do Processo SEI N.º <b>${documento.processo.anexo.numero}</b>, resolve:</p>

			<p>Art. 1º Emitir outorga prévia para reservar o direito de uso de água subterrânea a&nbsp;<b>${usuario.nome}</b>, <b>CPF/CNPJ n.º ${formatCpfCnpj(usuario.cpfCnpj)}</b>, mediante a perfuração de 01 (um) poço <inter_tipo_poco_tag></inter_tipo_poco_tag>, para fins&nbsp; de ${createPurpouseString(interferencia.finalidades)}, localizado no endereço: ${addressToString(documento.endereco)} - Distrito Federal, tendo a seguinte característica:</p>
			</div>

			<p>&nbsp;</p>

			<div align="justify" id="geographic-table"></div>

			<p style="text-align: center;">&nbsp;</p>

			<p>Art. 2º A reserva de disponibilidade hídrica para cada um dos poços tubulares mencionados no art. 1º é a seguinte:</p>

			<p>&nbsp;</p>

			<p>I – Tabela dos limites outorgados.</p>

			<p>&nbsp;</p>
			
			<div id="authorized-limits-table"></div>

			<p>&nbsp;</p>

			<div id="grant-requirements"></div>

			<div id="chief-signature"></div>
		</div>`;
	}
var geoTable, authLimTable;

document.addEventListener('DOMContentLoaded', function () {
	App();

	geoTable = new GeographicTable();
	authLimTable = new AuthorizedLimitsTable()
	new GrantRequirements();
	new ChiefSignature();

	new Buttons();
});
