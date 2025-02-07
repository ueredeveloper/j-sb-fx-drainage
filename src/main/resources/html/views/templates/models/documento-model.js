/**
 * @nome Documento
 * @diretorio models
 * @descricao Modelos de Tabelas
 * @arquivo documento-model.js
 * @id 28
 * 
 *
 */

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
				"interferencias":
					[
						{
							"id": 35,
							"latitude": -15.55476800259187,
							"longitude": -47.51321275064441,
							"geometry": null,
							"endereco": {
								"id": 77,
								"logradouro": "Casa de Sartori, Casa nº 0909",
								"cidade": null,
								"bairro": null,
								"cep": null,
								"interferencias": [
								],
								"estado": null
							},
							"tipoInterferencia": {
								"id": 2,
								"descricao": "Subterrânea",
								"interferencias": [
								]
							},
							"tipoOutorga": {
								"id": 2,
								"descricao": "Outorga Prévia",
								"interferencias": [
								]
							},
							"subtipoOutorga": {
								"id": 2,
								"descricao": "Modificação",
								"interferencias": [
								]
							},
							"situacaoProcesso": {
								"id": 2,
								"descricao": "Em Análise",
								"interferencias": [
								]
							},
							"tipoAto": {
								"id": 2,
								"descricao": "Portaria",
								"interferencias": [
								]
							},
							"baciaHidrografica": {
								"objectid": 4,
								"shapeLeng": null,
								"baciaCod": null,
								"baciaNome": "Rio São Bartolomeu",
								"gdbGeomattrData": null,
								"shape": null
							},
							"unidadeHidrografica": {
								"objectid": 7,
								"idBacia": null,
								"baciaNome": null,
								"baciaCodi": null,
								"subbaciaN": null,
								"uhNome": "Rio Pipiripau",
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
								"interferencias": [
								]
							},
							"finalidades": [
								{
									"id": 119,
									"finalidade": "Irrigação",
									"subfinalidade": "Paisagística",
									"quantidade": 12.0,
									"consumo": 456.0,
									"total": 5472.0,
									"interferencia": null,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									}
								},
								{
									"id": 120,
									"finalidade": "Irrigação",
									"subfinalidade": "Paisagística",
									"quantidade": 12.0,
									"consumo": 456.0,
									"total": 5472.0,
									"interferencia": null,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									}
								},
								{
									"id": 121,
									"finalidade": "",
									"subfinalidade": "",
									"quantidade": 0.0,
									"consumo": 0.0,
									"total": 0.0,
									"interferencia": null,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									}
								}
							],
							"demandas": [
								{
									"id": 721,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 12,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 722,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 30,
									"mes": 9,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 723,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 30,
									"mes": 6,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 724,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 3,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 725,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 28,
									"mes": 2,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 726,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 12,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 727,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 10,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 728,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 30,
									"mes": 6,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 729,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 30,
									"mes": 11,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 730,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 1,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 731,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 7,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 732,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 30,
									"mes": 4,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 733,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 7,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 734,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 30,
									"mes": 9,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 735,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 10,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 736,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 5,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 737,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 1,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 738,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 5,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 739,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 8,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 740,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 8,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 741,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 28,
									"mes": 2,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 742,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 30,
									"mes": 11,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 743,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 30,
									"mes": 4,
									"tipoFinalidade": {
										"id": 2,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								},
								{
									"id": 744,
									"vazao": 5472.0,
									"tempo": 10,
									"periodo": 31,
									"mes": 3,
									"tipoFinalidade": {
										"id": 1,
										"descricao": null,
										"finalidades": [
										],
										"demandas": [
										]
									},
									"interferencia": null
								}
							],
							"caesb": true,
							"nivelEstatico": "7855",
							"nivelDinamico": "7855",
							"profundidade": "65",
							"vazaoOutorgavel": 15000,
							"vazaoSistema": 13000,
							"vazaoTeste": 46000,
							"sistema": null,
							"subsistema": null,
							"codPlan": null,
							"tipoPoco": {
								"id": 2,
								"descricao": null
							}
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