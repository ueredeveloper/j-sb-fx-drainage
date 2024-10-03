/**
 * Funções compartilhadas
 * @id 17
 * @descricao Funções Compartilhadas
 * @pasta utils
 * @nome script.js
 */

// Função para formatar o número com ponto separador de milhar
function formatNumber(value) {
	return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

// Converter litros por hora (L/h) para metros cúbicos por hora (M³/h)
function convertFlowToM3(value) {
	return value / 1000;
}

// Muda o ponto do valor double para vígula do float. Ex: 20.00 para 20,00
function maskDoubleToFloat(value) {
	return parseFloat(value).toFixed(2).toString().replace('.', ',');
}

/**
 * Calcula a vazão em metros cúbicos por dia.
 * 
 * @param {number} litrosHora - A vazão em litros por hora.
 * @param {number} time - O número de horas por dia.
 * @returns {number} - A vazão em metros cúbicos por dia.
 */
function calculateM3Day(litrosHora, time) {
	// Converte a vazão de litros por hora para metros cúbicos por hora e multiplica pelas horas por dia
	return (litrosHora / 1000) * time;
}

// Calcula m³ por mês
function calculateM3Month(litrosHora, time, periodo) {
	return ((litrosHora / 1000) * time) * periodo;
}