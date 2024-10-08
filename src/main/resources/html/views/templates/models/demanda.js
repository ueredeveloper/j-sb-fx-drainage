/**
 * @id 16
 * @nome demanda.js
 * @pasta models
 * @descricao Tabelas Modelos
 */
class Demanda {

    constructor() {}

    getSample() {}

    // Converter litros por hora (L/h) para metros cúbicos por hora (M³/h)
    convertFlowToM3(value) {
        return value / 1000;
    }


    /**
     * Calcula a vazão em metros cúbicos por dia.
     * 
     * @param {number} litrosHora - A vazão em litros por hora.
     * @param {number} time - O número de horas por dia.
     * @returns {number} - A vazão em metros cúbicos por dia.
     */
    calculateM3Day(litrosHora, time) {
        // Converte a vazão de litros por hora para metros cúbicos por hora e multiplica pelas horas por dia
        return (litrosHora / 1000) * time;
    }

    // Calcula m³ por mês
    calculateM3Month(litrosHora, time, periodo) {
        return ((litrosHora / 1000) * time) * periodo;
    }

}