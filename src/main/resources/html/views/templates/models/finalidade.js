/**
 * @id 19
<<<<<<< HEAD
 * @descricao Tabela Finalidade
 * @nome finalidade.js
 * @pasta models
=======
 * @nome finalidade.js
 * @pasta models
 * @descricao Tabelas Modelos
>>>>>>> feat/web-view-and-html-editor
 */
class Finalidade {

    constructor() {

    }

    getSample() {
    	return []
    }
    
	/**
	 * Converte uma array de finalidades em um string
	 * Tag anterior: <finalidades_tag></finalidades_tag>
	 * @param {*} finalidades 
	 * @returns 
	 */
	createPurpouseString(finalidades) {

		// Filtra as finalidades autorizadas, transforma em string com a apenas o atributo finalidade e converte tudo em minúsculo
		let arrayToString = finalidades.filter(f => f.tipoFinalidade.id === 2).map(f => f.finalidade).toString().toLowerCase();

		// Substituir a última vírgula por 'e'
		let result = arrayToString.replace(/,(?=[^,]*$)/, ' e ');

		console.log(result)

		// Adicionar espaço após todas as vírgulas
		result = result.replace(/,/g, ', ');

		console.log(result)

		return result;
	}

}