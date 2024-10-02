/**
 * Main
 * @id 5
 * @descricao Despacho de Outorga Prévia
 * @pasta 1
 * @nome script.js

 */

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
			"interferencias": [{
				"id": 2,
				"latitude": -15.123456,
				"longitude": -47.123456,
				"geometry": null,
				"endereco": {
					"id": 1,
					"logradouro": "Rua dos Barris, 123",
					"cidade": null,
					"bairro": null,
					"cep": null,
					"interferencias": [],
					"estado": null
				},
				"tipoInterferencia": {
					"id": 2,
					"descricao": "Subterrânea",
					"interferencias": []
				},
				"tipoOutorga": {
					"id": 1,
					"descricao": "Outorga",
					"interferencias": []
				},
				"subtipoOutorga": {
					"id": 1,
					"descricao": "Renovação",
					"interferencias": []
				},
				"situacaoProcesso": {
					"id": 1,
					"descricao": "Arquivado",
					"interferencias": []
				},
				"tipoAto": {
					"id": 1,
					"descricao": "Despacho",
					"interferencias": []
				},
				"baciaHidrografica": { "id": 1, "descricao": "Bacia do Maranhão" },
				"unidadeHidrografica": { "id": 1, "descricao": "Unidade do Maranhão" },
				"finalidades": [
					{
						"id": 4,
						"finalidade": "Criação de Animais",
						"subfinalidade": "Aves",
						"quantidade": 300.0,
						"consumo": 30.0,
						"total": 9000.0,
						"interferencia": null,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						}
					},
					{
						"id": 4,
						"finalidade": "Irrigação",
						"subfinalidade": "Aves",
						"quantidade": 300.0,
						"consumo": 30.0,
						"total": 9000.0,
						"interferencia": null,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						}
					},
					{
						"id": 4,
						"finalidade": "Paisagismo",
						"subfinalidade": "Aves",
						"quantidade": 300.0,
						"consumo": 30.0,
						"total": 9000.0,
						"interferencia": null,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						}
					},
					{
						"id": 5,
						"finalidade": "Comercial",
						"subfinalidade": "Lavar Veículos",
						"quantidade": 100.0,
						"consumo": 110.0,
						"total": 11000.0,
						"interferencia": null,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						}
					},
					{
						"id": 6,
						"finalidade": "Abastecimento Humano",
						"subfinalidade": "Rural",
						"quantidade": 10.0,
						"consumo": 110.0,
						"total": 1100.0,
						"interferencia": null,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						}
					}
				],
				"demandas": [
					{
						"id": 29,
						"vazao": 55.0,
						"tempo": 55,
						"periodo": 55,
						"mes": 4,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 34,
						"vazao": 22.0,
						"tempo": 22,
						"periodo": 22,
						"mes": 1,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 32,
						"vazao": 99.0,
						"tempo": 99,
						"periodo": 99,
						"mes": 8,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 48,
						"vazao": 44.0,
						"tempo": 44,
						"periodo": 44,
						"mes": 3,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 28,
						"vazao": 2000.0,
						"tempo": 1,
						"periodo": 31,
						"mes": 1,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 42,
						"vazao": 77.0,
						"tempo": 77,
						"periodo": 77,
						"mes": 6,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 30,
						"vazao": 2008.0,
						"tempo": 9,
						"periodo": 23,
						"mes": 9,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 43,
						"vazao": 2004.0,
						"tempo": 5,
						"periodo": 27,
						"mes": 5,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 33,
						"vazao": 33.0,
						"tempo": 33,
						"periodo": 33,
						"mes": 2,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 46,
						"vazao": 88.0,
						"tempo": 88,
						"periodo": 88,
						"mes": 7,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 31,
						"vazao": 12.0,
						"tempo": 121,
						"periodo": 12,
						"mes": 11,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 35,
						"vazao": 2005.0,
						"tempo": 6,
						"periodo": 26,
						"mes": 6,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 27,
						"vazao": 1010.0,
						"tempo": 10,
						"periodo": 10,
						"mes": 9,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 47,
						"vazao": 66.0,
						"tempo": 66,
						"periodo": 66,
						"mes": 5,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 44,
						"vazao": 13.0,
						"tempo": 13,
						"periodo": 13,
						"mes": 12,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 38,
						"vazao": 2010.0,
						"tempo": 11,
						"periodo": 21,
						"mes": 11,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 39,
						"vazao": 2007.0,
						"tempo": 8,
						"periodo": 24,
						"mes": 8,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 41,
						"vazao": 2011.0,
						"tempo": 12,
						"periodo": 20,
						"mes": 12,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 25,
						"vazao": 2001.0,
						"tempo": 2,
						"periodo": 30,
						"mes": 2,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 26,
						"vazao": 2003.0,
						"tempo": 4,
						"periodo": 28,
						"mes": 4,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 40,
						"vazao": 11.0,
						"tempo": 1101,
						"periodo": 111,
						"mes": 10,
						"tipoFinalidade": {
							"id": 2,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 36,
						"vazao": 2009.0,
						"tempo": 10,
						"periodo": 22,
						"mes": 10,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 37,
						"vazao": 2002.0,
						"tempo": 3,
						"periodo": 29,
						"mes": 3,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					},
					{
						"id": 45,
						"vazao": 2006.0,
						"tempo": 7,
						"periodo": 25,
						"mes": 7,
						"tipoFinalidade": {
							"id": 1,
							"descricao": null,
							"finalidades": [],
							"demandas": []
						},
						"interferencia": null
					}
				],
				"caesb": true,
				"nivelEstatico": "123",
				"nivelDinamico": "123",
				"tipoPoco": {
					"id": 1,
					"descricao": "Manual"
				}
			}],
			"estado": null
		},
		"usuarios": [{
			"id": 1,
			"nome": "Carlos Coqueiro",
			"cpfCnpj": 12345678000100
		}]
	};

	let usuario = {
		"id": 1,
		"nome": "Carlos Coqueiro",
		"cpfCnpj": 12345678000100
	}


	/**
 * @id 44



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

	let finalidades = []


	/**
	 * Converte uma array de finalidades em um string
	 * Tag anterior: <finalidades_tag></finalidades_tag>
	 * @param {*} finalidades 
	 * @returns 
	 */
	function createPurpouseString(finalidades) {

		// Filtra as finalidades autorizadas, transforma em string com a apenas o atributo finalidade e converte tudo em minúsculo
		let arrayToString = finalidades.filter(f => f.tipoFinalidade.id === 2).map(f => f.finalidade).toString().toLowerCase();

		// Substituir a última vírgula por 'e'
		result = arrayToString.replace(/,(?=[^,]*$)/, ' e ');

		// Adicionar espaço após todas as vírgulas
		result = result.replace(/,/g, ', ');

		return result;
	}



	finalidades = interferencia.getTableExample().finalidades;

	const appDiv = document.getElementById("app");

	appDiv.innerHTML = `
		<div>
			<div style="text-align: center;">&nbsp;</div>

			<p style="margin-left: 400px;">Emite outorga prévia para reservar o direito de uso de água subterrânea a ${usuario.nome}, para fins de ${createPurpouseString(finalidades)} .

			</p><p>&nbsp;</p>
		

			<div align="justify">
			<p>O SUPERINTENDENTE DE RECURSOS HÍDRICOS DA AGÊNCIA REGULADORA DE ÁGUAS, ENERGIA E SANEAMENTO BÁSICO DO DISTRITO FEDERAL – ADASA, no uso de suas atribuições regimentais e com base na competência que lhe foi delegada pela Diretoria Colegiada, nos termos da Resolução nº 02, de 25 de janeiro de 2019, c/c Portaria nº 49, de 02 de maio de 2019 e com base no art. 12 da Lei nº 2.725, de 13 de junho de 2001, e inciso VII do art. 23 da Lei nº 4.285, de 26 de dezembro de 2008, tendo em vista o que consta do Processo SEI N.º <b>${documento.processo.anexo.numero}</b>, resolve:</p>

			<p>Art. 1º Emitir outorga prévia para reservar o direito de uso de água subterrânea a <b>${usuario.nome}</b>, <b>CPF/CNPJ n.º ${formatCpfCnpj(usuario.cpfCnpj)}</b>, mediante a perfuração de 01 (um) poço <inter_tipo_poco_tag></inter_tipo_poco_tag>, para fins de ${createPurpouseString(finalidades)}, localizado no endereço: ${addressToString(documento.endereco)} - Distrito Federal, tendo a seguinte característica:</p>
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
var geographicTable, limitsTable, interferencia;

// Função para formatar o número com ponto separador de milhar
function formatNumber(value) {
	return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

// Converter litros por hora (L/h) para metros cúbicos por hora (M³/h)
function convertFlowToM3(value) {
	return value / 1000;
}

// Muda o ponto do valor double para vígula do float. Ex: 20.00 para 20,00
function maskDoubleToFloat(value) {
	return parseFloat(value).toFixed(2).toString().replace('.', ',');
}

/**
 * Calcula a vazão em metros cúbicos por dia.
 * 
 * @param {number} litrosHora - A vazão em litros por hora.
 * @param {number} time - O número de horas por dia.
 * @returns {number} - A vazão em metros cúbicos por dia.
 */
function calculateM3Day(litrosHora, time) {
	// Converte a vazão de litros por hora para metros cúbicos por hora e multiplica pelas horas por dia
	return (litrosHora / 1000) * time;
}

// Calcula m³ por mês
function calculateM3Month(litrosHora, time, periodo) {
	return ((litrosHora / 1000) * time) * periodo;
}

document.addEventListener('DOMContentLoaded', function () {

	interferencia = new Interferencia();

	App();

	geographicTable = new GeographicTable();
	limitsTable = new LimitsTable()
	new GrantRequirements();
	new ChiefSignature();
	//new Actions();

});
