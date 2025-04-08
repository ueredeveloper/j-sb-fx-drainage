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
					{ "id": 49, "latitude": -15.889507, "longitude": -47.919337, "geometry": null, "endereco": { "id": 52, "logradouro": "SMPW/SUL, Unidade C, Quadra 23, Lote 02, Conjunto 04", "cidade": null, "bairro": null, "cep": null, "interferencias": [], "estado": null }, "tipoInterferencia": { "id": 2, "descricao": "Subterrânea", "interferencias": [] }, "tipoOutorga": { "id": 1, "descricao": "Outorga", "interferencias": [] }, "subtipoOutorga": { "id": 6, "descricao": "", "interferencias": [] }, "situacaoProcesso": { "id": 2, "descricao": "Em Análise", "interferencias": [] }, "tipoAto": { "id": 1, "descricao": "Despacho", "interferencias": [] }, "baciaHidrografica": { "objectid": 7, "shapeLeng": null, "baciaCod": null, "baciaNome": "Rio Paranoá", "gdbGeomattrData": null, "shape": null }, "unidadeHidrografica": { "objectid": 23, "idBacia": null, "baciaNome": null, "baciaCodi": null, "subbaciaN": null, "uhNome": "Ribeirão do Gama", "uhLabel": null, "uhCodigo": null, "subbciaCo": null, "shapeLeng": null, "shape": null, "areaKmSq": null, "qmmJan": null, "qmmFev": null, "qmmMar": null, "qmmAbr": null, "qmmMai": null, "qmmJun": null, "qmmJul": null, "qmmAgo": null, "qmmSet": null, "qmmOut": null, "qmmNov": null, "qmmDez": null, "rgHidro": null, "gdbGeomattrData": null, "interferencias": [] }, "finalidades": [{ "id": 184, "finalidade": "Irrigação paisagística", "subfinalidade": "gramínea", "quantidade": 10, "consumo": 150.0, "total": 1500.0, "interferencia": null, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] } }, { "id": 185, "finalidade": "Irrigação paisagística", "subfinalidade": "gramínea", "quantidade": 10, "consumo": 180.0, "total": 1800.0, "interferencia": null, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] } }], "demandas": [{ "id": 862, "vazao": 1500.0, "tempo": 6, "periodo": 31, "mes": 10, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 863, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 1, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 864, "vazao": 1500.0, "tempo": 6, "periodo": 31, "mes": 7, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 865, "vazao": 1500.0, "tempo": 6, "periodo": 30, "mes": 4, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 866, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 3, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 867, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 11, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 868, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 3, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 869, "vazao": 1500.0, "tempo": 6, "periodo": 31, "mes": 7, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 870, "vazao": 1500.0, "tempo": 6, "periodo": 31, "mes": 8, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 871, "vazao": 1500.0, "tempo": 6, "periodo": 31, "mes": 10, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 872, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 12, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 873, "vazao": 1500.0, "tempo": 6, "periodo": 31, "mes": 5, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 874, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 12, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 875, "vazao": 1500.0, "tempo": 6, "periodo": 30, "mes": 6, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 876, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 11, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 877, "vazao": 1500.0, "tempo": 6, "periodo": 31, "mes": 8, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 878, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 2, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 879, "vazao": 1500.0, "tempo": 6, "periodo": 30, "mes": 4, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 880, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 1, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 881, "vazao": 1500.0, "tempo": 6, "periodo": 30, "mes": 9, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 882, "vazao": 1500.0, "tempo": 6, "periodo": 30, "mes": 6, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 883, "vazao": 0.0, "tempo": 0, "periodo": 0, "mes": 2, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 884, "vazao": 1500.0, "tempo": 6, "periodo": 31, "mes": 5, "tipoFinalidade": { "id": 2, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }, { "id": 885, "vazao": 1500.0, "tempo": 6, "periodo": 30, "mes": 9, "tipoFinalidade": { "id": 1, "descricao": null, "finalidades": [], "demandas": [] }, "interferencia": null }], "caesb": true, "nivelEstatico": "11", "nivelDinamico": "37", "profundidade": "80", "vazaoOutorgavel": 250, "vazaoSistema": 800, "vazaoTeste": 500, "sistema": null, "subsistema": null, "codPlan": null, "tipoPoco": { "id": 2, "descricao": "Tubular" } }
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