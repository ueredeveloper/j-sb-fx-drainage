/**
 * Retrieves the element with ID 'chart-container' and initializes an ECharts
 * instance.
 * 
 * @type {HTMLElement}
 */

var dom = document.getElementById('chart-container');
var myChart = echarts.init(dom, null, {
	renderer : 'canvas',
	useDirtyRect : false
});
// Placeholder object for the application
var app = {};

/**
 * The chart options object.
 * 
 * @type {Object}
 */
var option;

myChart.showLoading();
var json;

/**
 * Updates the series data of the chart.
 * 
 * @param {Object}
 *            newData - The new data to be updated in the chart.
 */
function updateSeriesData(json) {
	myChart.setOption({
		series : [ {
			data : json !== undefined ? [ convertDocumentoToEChartTree(json) ]
					: [],
		} ]
	});
}

/**
 * Converts the given JSON data into a suitable format for ECharts tree series.
 * 
 * @param {Object}
 *            jsonData - The JSON data to be converted.
 * @returns {Object} - The converted data for the ECharts tree.
 */
function convertDocumentoToEChartTree(jsonData) {
	const data = {
		name : 'Documento',
		children : [
				{
					name : 'Tipo',
					value : jsonData.docTipo != null ? jsonData.docTipo.dtDescricao != null ? jsonData.docTipo.dtDescricao
							: 'desconhecido'
							: 'desconhecido'
				},
				{
					name : 'Número',
					value : jsonData.docNumero || 'desconhecido'
				},
				{
					name : 'SEI',
					value : jsonData.docSei || 'desconhecido'
				},
				{
					name : 'Processo',
					value : jsonData.docProcesso != null ? jsonData.docProcesso.procNumero != null ? jsonData.docProcesso.procNumero
							: 'desconhecido'
							: 'desconhecido'
				},
				{
					name : 'Endereço',
					value : jsonData.docEndereco != null ? jsonData.docEndereco.endLogradouro != null ? jsonData.docEndereco.endLogradouro
							: 'desconhecido'
							: 'desconhecido'

				} ]
	};

	return data;
}

myChart.hideLoading();
myChart
		.setOption((option = {
			tooltip : {
				trigger : 'item',
				triggerOn : 'mousemove'
			},
			legend : {
				top : '2%',
				left : '3%',
				orient : 'vertical',
				data : [ {
					name : 'Documento',
					icon : 'rectangle'
				} ],
				borderColor : '#c23531'
			},
			series : [
					{
						type : 'tree',
						name : 'Documento',
						data : json !== undefined ? [ convertDocumentoToEChartTree(json) ]
								: [],
						top : '5%',
						left : '20%',
						bottom : '5%',
						right : '30%',
						symbolSize : 7,
						label : {
							position : 'left',
							verticalAlign : 'middle',
							align : 'right',
							formatter : function(params) {
								return params.name + ": "
										+ (params.value || '');
							},
						},
						leaves : {
							label : {
								position : 'right',
								verticalAlign : 'middle',
								align : 'left'
							}
						},
						emphasis : {
							focus : 'descendant'
						},
						expandAndCollapse : false,
						animationDuration : 550,
						animationDurationUpdate : 750
					},

			]
		}));

// Check if the 'option' exists and set it to the chart
if (option && typeof option === 'object') {
	myChart.setOption(option);
}

window.addEventListener('resize', myChart.resize);
