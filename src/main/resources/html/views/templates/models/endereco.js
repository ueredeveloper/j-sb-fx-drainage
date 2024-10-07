/**
 * @id 18
 * @nome endereco.js
 * @pasta models
 * @descricao Tabelas Modelos
 */
class Endereco {
    constructor() {
    }

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