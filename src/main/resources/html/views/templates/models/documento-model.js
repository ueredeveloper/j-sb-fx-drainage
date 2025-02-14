/**
 * @nome Documento
 * @diretorio models
 * @descricao Modelos de Tabelas
 * @arquivo documento-model.js
 * @id 
 *
 *
 */

class DocumentoModel {

	constructor() { }

	getSample() {
		return {
			"id": 22,
			"numero": "158073681",
			"numeroSei": "158073681",
			"processo": {
				"id": 17,
				"numero": "0197-000673/2006",
				"anexo": {
					"id": 24,
					"numero": "0197-000673/2006",
					"processos": []
				},
				"usuario": null,
				"documentos": []
			},
			"tipoDocumento": {
				"id": 3,
				"descricao": "Requerimento"
			},
			"endereco": {
				"id": 29,
				"logradouro": "Fazenda Barreiro, Número 01, Sítio Jatobá ",
				"cidade": null,
				"bairro": "Brazlândia",
				"cep": null,
				"interferencias": [
					{
						"id": 21,
						"latitude": -15.581116,
						"longitude": -48.172956,
						"geometry": null,
						"endereco": {
							"id": 29,
							"logradouro": "Fazenda Barreiro, Número 01, Sítio Jatobá ",
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
							"id": 6,
							"descricao": "",
							"interferencias": []
						},
						"situacaoProcesso": {
							"id": 2,
							"descricao": "Em Análise",
							"interferencias": []
						},
						"tipoAto": {
							"id": 1,
							"descricao": "Despacho",
							"interferencias": []
						},
						"baciaHidrografica": {
							"objectid": 6,
							"shapeLeng": null,
							"baciaCod": null,
							"baciaNome": "Rio Maranhão",
							"gdbGeomattrData": null,
							"shape": null
						},
						"unidadeHidrografica": {
							"objectid": 10,
							"idBacia": null,
							"baciaNome": null,
							"baciaCodi": null,
							"subbaciaN": null,
							"uhNome": "Rio do Sal",
							"uhLabel": null,
							"uhCodigo": null,
							"subbciaCo": null,
							"shapeLeng": null,
							"shape": null,
							"areaKmSq": null,
							"qmmJan": null,
							"qmmFev": null,
							"qmmMar": null,
							"qmmAbr": null,
							"qmmMai": null,
							"qmmJun": null,
							"qmmJul": null,
							"qmmAgo": null,
							"qmmSet": null,
							"qmmOut": null,
							"qmmNov": null,
							"qmmDez": null,
							"rgHidro": null,
							"gdbGeomattrData": null,
							"interferencias": []
						},
						"finalidades": [
							{
								"id": 57,
								"finalidade": "Criação/dessedentação animal",
								"subfinalidade": "frango de corte",
								"quantidade": 56000.0,
								"consumo": 15000.0,
								"total": 15000.0,
								"interferencia": null,
								"tipoFinalidade": {
									"id": 1,
									"descricao": null,
									"finalidades": [],
									"demandas": []
								}
							},
							{
								"id": 58,
								"finalidade": "Criação/dessedentação animal",
								"subfinalidade": "frango de corte",
								"quantidade": 56000.0,
								"consumo": 15000.0,
								"total": 15000.0,
								"interferencia": null,
								"tipoFinalidade": {
									"id": 2,
									"descricao": null,
									"finalidades": [],
									"demandas": []
								}
							},
							{
								"id": 59,
								"finalidade": "Abastecimento humano",
								"subfinalidade": "área rural",
								"quantidade": 10.0,
								"consumo": 50.0,
								"total": 500.0,
								"interferencia": null,
								"tipoFinalidade": {
									"id": 2,
									"descricao": null,
									"finalidades": [],
									"demandas": []
								}
							},
							{
								"id": 60,
								"finalidade": "Abastecimento Humano",
								"subfinalidade": "área rural",
								"quantidade": 10.0,
								"consumo": 50.0,
								"total": 500.0,
								"interferencia": null,
								"tipoFinalidade": {
									"id": 1,
									"descricao": null,
									"finalidades": [],
									"demandas": []
								}
							}
						],
						"demandas": [
							{
								"id": 346,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 30,
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
								"id": 347,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 30,
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
								"id": 348,
								"vazao": 15500.0,
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
								"id": 349,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 30,
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
								"id": 350,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 31,
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
								"id": 351,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 30,
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
								"id": 352,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 30,
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
								"id": 353,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 31,
								"mes": 7,
								"tipoFinalidade": {
									"id": 1,
									"descricao": null,
									"finalidades": [],
									"demandas": []
								},
								"interferencia": null
							},
							{
								"id": 354,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 28,
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
								"id": 355,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 31,
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
								"id": 356,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 31,
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
								"id": 357,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 31,
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
								"id": 358,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 31,
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
								"id": 359,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 31,
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
								"id": 360,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 31,
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
								"id": 361,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 31,
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
								"id": 362,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 28,
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
								"id": 363,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 30,
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
								"id": 364,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 31,
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
								"id": 365,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 31,
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
								"id": 366,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 30,
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
								"id": 367,
								"vazao": 15500.0,
								"tempo": 1,
								"periodo": 31,
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
								"id": 368,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 30,
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
								"id": 369,
								"vazao": 15500.0,
								"tempo": 3,
								"periodo": 31,
								"mes": 1,
								"tipoFinalidade": {
									"id": 2,
									"descricao": null,
									"finalidades": [],
									"demandas": []
								},
								"interferencia": null
							}
						],
						"caesb": false,
						"nivelEstatico": "0",
						"nivelDinamico": "0",
						"profundidade": "0",
						"vazaoOutorgavel": 5200,
						"vazaoSistema": 6500,
						"vazaoTeste": 0,
						"sistema": null,
						"subsistema": null,
						"codPlan": null,
						"tipoPoco": {
							"id": 3,
							"descricao": "Tubular Profundo"
						}
					}
				],
				"estado": null
			},
			"usuarios": [{
				nome: "Carlos Drummond de Andrade",
				cpfCnpj: 12345678955
			}]
		}

	}
}