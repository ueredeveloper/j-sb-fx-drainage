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
						{ "caesb": true, "nivelEstatico": "12", "nivelDinamico": "0", "profundidade": "10", "vazaoOutorgavel": 14000, "vazaoSistema": 13000, "vazaoTeste": 15000, "tipoPoco": { "id": 1, "descricao": "Manual" }, "sistema": "P1", "id": 32, "latitude": -15.55245275071159, "longitude": -47.4260946018407, "endereco": { "id": 68, "logradouro": "Campos do Jordão, Pedra Nova, 12", "documentos": [], "interferencias": [] }, "tipoInterferencia": { "id": 2, "descricao": "Subterrânea" }, "tipoOutorga": { "id": 2, "descricao": "Outorga Prévia" }, "subtipoOutorga": { "id": 2, "descricao": "Modificação" }, "situacaoProcesso": { "id": 2, "descricao": "Em Análise" }, "tipoAto": { "id": 2, "descricao": "Portaria" }, "baciaHidrografica": { "objectid": 8, "baciaNome": "Rio Preto" }, "unidadeHidrografica": { "objectid": 4, "uhNome": "Ribeirão Santa Rita" }, "finalidades": [{ "id": 112, "finalidade": "Criação de Animais", "subfinalidade": "Touro", "quantidade": 85.0, "consumo": 96.0, "total": 8160.0, "tipoFinalidade": { "id": 1 } }, { "id": 111, "finalidade": "Criação de Animais", "subfinalidade": "Touro", "quantidade": 85.0, "consumo": 96.0, "total": 7644.0, "tipoFinalidade": { "id": 2 } }], "demandas": [{ "id": 649, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 1, "tipoFinalidade": { "id": 1 } }, { "id": 652, "vazao": 8160.0, "tempo": 9, "periodo": 30, "mes": 6, "tipoFinalidade": { "id": 1 } }, { "id": 655, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 3, "tipoFinalidade": { "id": 1 } }, { "id": 659, "vazao": 8160.0, "tempo": 9, "periodo": 30, "mes": 11, "tipoFinalidade": { "id": 1 } }, { "id": 661, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 12, "tipoFinalidade": { "id": 1 } }, { "id": 662, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 8, "tipoFinalidade": { "id": 1 } }, { "id": 663, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 5, "tipoFinalidade": { "id": 1 } }, { "id": 650, "vazao": 8160.0, "tempo": 9, "periodo": 30, "mes": 9, "tipoFinalidade": { "id": 2 } }, { "id": 651, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 3, "tipoFinalidade": { "id": 2 } }, { "id": 653, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 1, "tipoFinalidade": { "id": 2 } }, { "id": 654, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 7, "tipoFinalidade": { "id": 2 } }, { "id": 656, "vazao": 8160.0, "tempo": 9, "periodo": 28, "mes": 2, "tipoFinalidade": { "id": 2 } }, { "id": 657, "vazao": 8160.0, "tempo": 9, "periodo": 30, "mes": 6, "tipoFinalidade": { "id": 2 } }, { "id": 658, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 10, "tipoFinalidade": { "id": 2 } }, { "id": 660, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 8, "tipoFinalidade": { "id": 2 } }, { "id": 664, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 7, "tipoFinalidade": { "id": 1 } }, { "id": 666, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 10, "tipoFinalidade": { "id": 1 } }, { "id": 667, "vazao": 8160.0, "tempo": 9, "periodo": 28, "mes": 2, "tipoFinalidade": { "id": 1 } }, { "id": 668, "vazao": 8160.0, "tempo": 9, "periodo": 30, "mes": 9, "tipoFinalidade": { "id": 1 } }, { "id": 670, "vazao": 8160.0, "tempo": 9, "periodo": 30, "mes": 4, "tipoFinalidade": { "id": 1 } }, { "id": 665, "vazao": 8160.0, "tempo": 9, "periodo": 30, "mes": 4, "tipoFinalidade": { "id": 2 } }, { "id": 669, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 5, "tipoFinalidade": { "id": 2 } }, { "id": 671, "vazao": 8160.0, "tempo": 9, "periodo": 30, "mes": 11, "tipoFinalidade": { "id": 2 } }, { "id": 672, "vazao": 8160.0, "tempo": 9, "periodo": 31, "mes": 12, "tipoFinalidade": { "id": 2 } }] }

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