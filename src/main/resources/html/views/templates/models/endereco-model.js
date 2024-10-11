/**
 * @descricao 
 * @diretorio models
 * @arquivo endereco-model.js
 * @id 25
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