// create a red polygon from an array of LatLng points
/*var latlngs = [[37, -109.05],[41, -109.03],[41, -102.05],[37, -102.04]];

var polygon = L.polygon(latlngs, {color: 'red'}).addTo(map);

// zoom the map to the polygon
map.fitBounds(polygon.getBounds());*/


map.on('click', function(e) {
    console.log(e.latlng);
} );

let json = {
	"docId":1,
	"docNumero":"12/2015",
	"docProcesso":{"procId":2,"procNumero":"456/2015"},
	"docSei":455,"docTipo":{"dtId":1,"dtDescricao":"Requerimento"},
	"docEndereco":{
		"endId":3,"endLogradouro":"Rua das Flores, Apartamento 5","endCidade":"br","endCEP":"22"
	},
	"intLatitude": -15.710520042184267,
	"intLongitude": -47.76868726015565
	}

	
	L.marker([json.intLatitude, json.intLongitude]).addTo(map);
	
	
	function setZoom(){
		alert('zoom');
	}	
	
	app.setZoom();
	
	