// Inicializa o mapa com uma visão inicial e zoom
var map = L.map('map').setView([-15.80034, -47.74263], 9);

// Definindo as camadas
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

// Adiciona a camada padrão (Road Map)
layers.road.addTo(map);

// Evento de clique no mapa
map.on('click', function(e) {
	// Captura as coordenadas de latitude e longitude
	const latlng = e.latlng;
	//  limita o tamanho das casas decimais
	var lat = latlng.lat.toFixed(6);
	var lng = latlng.lng.toFixed(6);
	app.sendCoordinates(JSON.stringify({ lat: lat, lng: lng })); // Envia as coordenadas para a função printCoords
});

// Função para alternar entre as camadas
function setMapType(layerName) {

	console.log('open stree set map type')
	console.log(layerName)
	// Remove todas as camadas atuais
	map.eachLayer(layer => map.removeLayer(layer));
	// Adiciona a nova camada selecionada
	layers[layerName].addTo(map);
}
function addMarker(latLng) {
    let latitude = latLng.lat;
    let longitude = latLng.lng;

    // Create a marker with Leaflet
    var marker = L.marker([latitude, longitude], {
        icon: L.icon({
            iconUrl: 'https://maps.google.com/mapfiles/ms/icons/blue-dot.png',
            iconSize: [32, 32], // Size of the icon
            iconAnchor: [16, 32], // Anchor point (center bottom of the icon)
            popupAnchor: [0, -32] // Popup offset from the anchor
        })
    });

    // Assuming 'map' is your Leaflet map instance
    marker.addTo(map); // Add marker to the map

    map.setView([latitude, longitude], 12);

    return marker; // Return the marker for further manipulation if needed
}



