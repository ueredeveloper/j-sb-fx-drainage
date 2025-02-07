/**
 * @nome Endereco
 * @diretorio models
 * @descricao Modelos de Tabelas
 * @arquivo endereco-model.js
 * @id 29
 * 
 *
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