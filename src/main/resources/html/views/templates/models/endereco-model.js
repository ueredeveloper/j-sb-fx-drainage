/**
 * @diretorio models
 * @descricao Modelos de Tabelas
 * @arquivo endereco-model.js
 * @id 
 * 
 * 
 * 
 *
 */
 
>>>>>>> a7cfd37d81a56b88035884d504a3026636228d81
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