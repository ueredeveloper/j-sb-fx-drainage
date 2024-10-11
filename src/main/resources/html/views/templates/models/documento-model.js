/**
 * @descricao 
 * @diretorio models
 * @arquivo documento-model.js
 * @id 24
class DocumentoModel {

	constructor() { }

	getSample() {
		return {
			"id": 1,
			"numero": "123456/58",
			"processo": {
				"id": 1,
				"numero": "123.456",
				"anexo": {
					"id": 3,
					"numero": "Anexo 789",
					"processos": [

					]
				}
			},
			"numeroSei": 123456789,
			"tipoDocumento": {
				"id": 2,
				"descricao": "Despacho"
			},
			"endereco": {
				"id": 4,
				"logradouro": "Rua 10, Vicente Pires",
				"documentos": [

				],
				"interferencias": [
					{
						"id": 1,

						"nome": null,// "Poço 1",

						"subsistema": "R3/Q3",
	
						"vazaoSistema": 9000,
						"vazaoTeste": 8000,
						"vazaoAutorizada": 7500,

						"profundidade": "100", 
						"nivelEstatico": "120",
						"nivelDinamico": "110",

						"baciaHidrografica": null,// {"id": 1, "nome": "Bacia do Maranhão"},
						"unidadeHidrografica": null,//{"id": 1, "nome": "UH 1"},

						"latitude": -15.123456,
						"longitude": -47.123456,

						"endereco": {
							"id": 4,
							"logradouro": "Exemplo Demandas com Endereço 1",
							"documentos": [

							],
							"interferencias": [

							]
						},
						"tipoPoco": {
							"id": 1,
							"descricao": "Manual"
						},
						"tipoInterferencia": {
							"id": 2,
							"descricao": "Subterrânea"
						},
						"tipoOutorga": {
							"id": 1,
							"descricao": "Outorga"
						},
						"subtipoOutorga": {
							"id": 6,
							"descricao": ""
						},
						"situacaoProcesso": {
							"id": 1,
							"descricao": "Arquivado"
						},
						"tipoAto": {
							"id": 1,
							"descricao": "Despacho"
						},
						"finalidades": [
							{
								"id": 1,
								"finalidade": "Abastecimento Humano",
								"subfinalidade": "Rural",
								"quantidade": 10.0,
								"consumo": 110.0,
								"total": 1100.0,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 2,
								"finalidade": "Uso Comercial",
								"subfinalidade": "Lavar Veículos",
								"quantidade": 100.0,
								"consumo": 110.0,
								"total": 11000.0,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 3,
								"finalidade": "Criação de Animais",
								"subfinalidade": "Aves",
								"quantidade": 300.0,
								"consumo": 30.0,
								"total": 9000.0,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 4,
								"finalidade": "Criação de Animais",
								"subfinalidade": "Aves",
								"quantidade": 300.0,
								"consumo": 30.0,
								"total": 9000.0,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 5,
								"finalidade": "Uso Industrial",
								"subfinalidade": "Aves",
								"quantidade": 400.0,
								"consumo": 200.0,
								"total": 80000.0,
								"tipoFinalidade": {
									"id": 1
								}
							}
						],
						"demandas": [
							{
								"id": 1,
								"vazao": 22.0,
								"tempo": 22,
								"periodo": 22,
								"mes": 1,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 10,
								"vazao": 2010.0,
								"tempo": 11,
								"periodo": 21,
								"mes": 11,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 11,
								"vazao": 66.0,
								"tempo": 66,
								"periodo": 66,
								"mes": 5,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 12,
								"vazao": 2003.0,
								"tempo": 4,
								"periodo": 28,
								"mes": 4,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 13,
								"vazao": 2000.0,
								"tempo": 1,
								"periodo": 31,
								"mes": 1,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 14,
								"vazao": 11.0,
								"tempo": 1101,
								"periodo": 111,
								"mes": 10,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 15,
								"vazao": 88.0,
								"tempo": 88,
								"periodo": 88,
								"mes": 7,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 16,
								"vazao": 1010.0,
								"tempo": 10,
								"periodo": 10,
								"mes": 9,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 17,
								"vazao": 2011.0,
								"tempo": 12,
								"periodo": 20,
								"mes": 12,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 18,
								"vazao": 44.0,
								"tempo": 44,
								"periodo": 44,
								"mes": 3,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 19,
								"vazao": 77.0,
								"tempo": 77,
								"periodo": 77,
								"mes": 6,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 2,
								"vazao": 2006.0,
								"tempo": 7,
								"periodo": 25,
								"mes": 7,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 20,
								"vazao": 2002.0,
								"tempo": 3,
								"periodo": 29,
								"mes": 3,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 21,
								"vazao": 13.0,
								"tempo": 13,
								"periodo": 13,
								"mes": 12,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 22,
								"vazao": 55.0,
								"tempo": 55,
								"periodo": 55,
								"mes": 4,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 23,
								"vazao": 2009.0,
								"tempo": 10,
								"periodo": 22,
								"mes": 10,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 24,
								"vazao": 99.0,
								"tempo": 99,
								"periodo": 99,
								"mes": 8,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 3,
								"vazao": 2001.0,
								"tempo": 2,
								"periodo": 30,
								"mes": 2,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 4,
								"vazao": 33.0,
								"tempo": 33,
								"periodo": 33,
								"mes": 2,
								"tipoFinalidade": {
									"id": 2
								}
							},
							{
								"id": 5,
								"vazao": 2005.0,
								"tempo": 6,
								"periodo": 26,
								"mes": 6,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 6,
								"vazao": 2008.0,
								"tempo": 9,
								"periodo": 23,
								"mes": 9,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 7,
								"vazao": 2007.0,
								"tempo": 8,
								"periodo": 24,
								"mes": 8,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 8,
								"vazao": 2004.0,
								"tempo": 5,
								"periodo": 27,
								"mes": 5,
								"tipoFinalidade": {
									"id": 1
								}
							},
							{
								"id": 9,
								"vazao": 12.0,
								"tempo": 121,
								"periodo": 12,
								"mes": 11,
								"tipoFinalidade": {
									"id": 2
								}
							}
						]
					}
				]
			},
			"usuarios": [
				{
					"id": 1,
					"nome": "Carlos Drummond de Andrade",
					"cpfCnpj": 12345678900,
					"documentos": [

					]
				}
			]
		}
	}
}