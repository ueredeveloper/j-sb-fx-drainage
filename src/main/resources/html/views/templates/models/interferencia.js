/**
 * Classes modelos de tabelas no banco de dados
 * @id 19
 * @descricao Tabela Interferência
 * @nome interferencia.js
 * @pasta models
 */
class Interferencia {
    constructor() {
    }

    // Método para obter a interferência
    getSample () {

        return {
            "id": 2,
            "latitude": -15.98754321,
            "longitude": -47.123456789,
            "nome": "Poço 1",
            "vazaoAutorizada": 550,
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
                    "vazao": 20000,
                    "tempo": 20,
                    "periodo": 31,
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
        };
    }
    setLatitude (number){
    	this.interferencia.latitude = number;
    }
}