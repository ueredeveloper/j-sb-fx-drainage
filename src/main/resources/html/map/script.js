// create a red polygon from an array of LatLng points
/*var latlngs = [[37, -109.05],[41, -109.03],[41, -102.05],[37, -102.04]];

var polygon = L.polygon(latlngs, {color: 'red'}).addTo(map);

// zoom the map to the polygon
map.fitBounds(polygon.getBounds());*/



map.on('click', function(e) {
    console.log(JSON.stringify(e.latlng));
    app.printCoords(JSON.stringify(e.latlng))
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
	

// Definindo as camadas
        var streetLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap contributors'
        });

        var satelliteLayer = L.tileLayer('https://{s}.tile.opentopomap.org/{z}/{x}/{y}.png', {
            maxZoom: 17,
            attribution: '© OpenTopoMap contributors'
        });

        var hybridLayer = L.layerGroup([streetLayer, satelliteLayer]);

// Função para definir a camada do mapa
        function setMapLayer(layer) {
            map.eachLayer(function (l) {
                map.removeLayer(l);
            });
            map.addLayer(layer);
        }
        
  // Funções para definir tipos específicos de camadas
        function setStreetMap() {
            setMapLayer(streetLayer);
        }

        function setSatelliteMap() {
            setMapLayer(satelliteLayer);
        }

        function setHybridMap() {
            setMapLayer(hybridLayer);
        }   
        
        function setMapZoomPlus(){
			var currentZoom = map.getZoom(); // Obtém o nível de zoom atual
		    var newZoom = currentZoom + 1; // Incrementa o nível de zoom em 1
		    map.setZoom(newZoom);
	
		}

		function setMapZoomMinus(){
			var currentZoom = map.getZoom(); // Obtém o nível de zoom atual
		    var newZoom = currentZoom - 1; // Decrementa o nível de zoom em 1
		    map.setZoom(newZoom);
			
		}
	
	
	
	