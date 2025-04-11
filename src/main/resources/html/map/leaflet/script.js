
map.on('click', function(e) {
	const latlng = e.latlng;

	var lat = latlng.lat.toFixed(6);
	var lng = latlng.lng.toFixed(6);

	app.sendCoordinates(JSON.stringify({ lat: lat, lng: lng }));
});


let json = {
	"id": 1,
	"numero": "12/2015",
	"processo": { "id": 2, "numero": "456/2015" },
	"numeroSei": 455, "tipo": { "id": 1, "descricao": "Requerimento" },
	"endereco": {
		"id": 3, "logradouro": "Rua das Flores, Apartamento 5", "endCidade": "br", "cep": "22"
	},
	"latitude": -15.710520042184267,
	"longitude": -47.76868726015565
}


L.marker([json.latitude, json.longitude]).addTo(map);


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

var hebrydLayerGoogle = L.tileLayer('http://mt1.google.com/vt/lyrs=y&x={x}&y={y}&z={z}', {
	attribution: '&copy; Google Maps'
});

// Define layers
var layers = {
	"road": L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		attribution: '&copy; OpenStreetMap contributors'
	}),
	"satellite": L.tileLayer('http://mt1.google.com/vt/lyrs=s&x={x}&y={y}&z={z}', {
		attribution: '&copy; Google Maps'
	}),
	"terrain": L.tileLayer('http://mt1.google.com/vt/lyrs=p&x={x}&y={y}&z={z}', {
		attribution: '&copy; Google Maps'
	}),
	"hybrid": L.tileLayer('http://mt1.google.com/vt/lyrs=y&x={x}&y={y}&z={z}', {
		attribution: '&copy; Google Maps'
	})
};

// Set default layer
layers["road"].addTo(map);

// Function to switch layers using a string parameter
function setMapType(type) {
	if (layers[type]) {
		map.eachLayer(layer => map.removeLayer(layer));
		layers[type].addTo(map);
	} else {
		console.error("Invalid map type: " + type);
	}
}

function zoomIn() {
	var currentZoom = map.getZoom(); // Obtém o nível de zoom atual
	var newZoom = currentZoom + 1; // Incrementa o nível de zoom em 1
	map.setZoom(newZoom);

}

function zoomOut() {
	var currentZoom = map.getZoom(); // Obtém o nível de zoom atual
	var newZoom = currentZoom - 1; // Decrementa o nível de zoom em 1
	map.setZoom(newZoom);

}

let currentMarker = null;

function addMarker(json) {
	
	console.log('leaflet add marker')

	// Remove existing marker if present
	if (currentMarker) {
		map.removeLayer(currentMarker);
	}

	// Add new marker
	currentMarker = L.marker([json.lat, json.lng]).addTo(map);

	// Center the map on the new marker
	map.setView([json.lat, json.lng], 11);
}

