/**
 * @nome Finalidade
 * @diretorio models
 * @descricao Modelos de Tabelas
 * @arquivo finalidade-model.js
 * @id 28
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *
 */
 
class FinalidadeModel {

    constructor() {}

    getSample() {return []}
    
	/**
	 * Converte uma array de finalidades em um string
	 * Tag anterior: <finalidades_tag></finalidades_tag>
	 * @param {*} finalidades 
	 * @returns 
	 */
    getPurpouseString(finalidades) {
		// Filtra as finalidades autorizadas, transforma em string com a apenas o atributo finalidade e converte tudo em minúsculo
		let arrayToString = finalidades.filter(f => f.tipoFinalidade.id === 2).map(f => f.finalidade).toString().toLowerCase();
		// Substituir a última vírgula por 'e'
		let result = arrayToString.replace(/,(?=[^,]*$)/, ' e ');
		// Adicionar espaço após todas as vírgulas
		result = result.replace(/,/g, ', ');
		return result;
	}

}