/**
 * @id
 * @arquivo endereco-model.js
 * @diretorio models
 * @descricao Tabelas Modelos
 */
class EnderecoModel {
    constructor() {}

    // Método para obter a interferência
    getSample () {
        return {
            id: 1,
            logradouro: "Rua Borges, 3, Lote 0"
        }
    }
    getLogradouro (endereco){
       let logradouro =  endereco?.logradouro;

       return logradouro;
    }

}