// Initialize the map
const map = new ol.Map({
	target: 'map',
	layers: [
		new ol.layer.Tile({
			source: new ol.source.OSM()
		})
	],
	view: new ol.View({
		center: ol.proj.fromLonLat([-47.742635303776794, -15.800341849307241]), // Convert lon/lat to map projection
		zoom: 10
	})

});

map.on('singleclick', function(event) {  // Use 'singleclick' instead of 'click'
	var coord = ol.proj.toLonLat(event.coordinate);
	var lng = coord[0].toFixed(6);
	var lat = coord[1].toFixed(6);

	let latLng = { lat: parseFloat(lat), lng: parseFloat(lng) };

	addMarker(latLng);
	// Send coordinates to JavaFX WebView
	app.sendCoordinates(JSON.stringify(latLng));
});

// Create a vector layer for markers
var vectorSource = new ol.source.Vector();
var vectorLayer = new ol.layer.Vector({
	source: vectorSource
});
map.addLayer(vectorLayer);

// Satellite Layer (Using Carto)
var satelliteLayer = new ol.layer.Tile({
	source: new ol.source.XYZ({
		url: 'https://basemaps.cartocdn.com/rastertiles/satellite/{z}/{x}/{y}{r}.png'
	})
});

// Road Map Layer (Using OpenStreetMap)
var roadLayer = new ol.layer.Tile({
	source: new ol.source.OSM()
});

// Terrain Layer (Using Stamen Terrain)
var terrainLayer = new ol.layer.Tile({
	source: new ol.source.XYZ({
		url: 'https://stamen-tiles.a.ssl.fastly.net/terrain/{z}/{x}/{y}.jpg',
		attributions: '&copy; <a href="http://maps.stamen.com">Stamen Design</a>'
	})
});

// Camada Híbrida (Combinação de Satélite e Rua)
var hybridLayer = new ol.layer.Group({
	layers: [
		new ol.layer.Tile({
			source: new ol.source.XYZ({
				url: 'http://mt1.google.com/vt/lyrs=s&x={x}&y={y}&z={z}',
				attributions: '&copy; Google Maps'
			})
		}),
		new ol.layer.Tile({
			source: new ol.source.OSM()
		})
	]
});

function setMapType(type) {
	map.getLayers().clear(); // Remove existing layers
	if (type === 'road') {
		map.addLayer(roadLayer);
	} else if (type === 'satellite') {
		map.addLayer(satelliteLayer);
	} else if (type === 'terrain') {
		map.addLayer(terrainLayer);
	} else if (type === 'hybrid') {
		map.addLayer(hybridLayer);
	}
}
function zoomOut() {
	const view = map.getView();
	view.setZoom(view.getZoom() - 1);
}

function zoomIn() {
	const view = map.getView();
	view.setZoom(view.getZoom() + 1);
}
function addMarker(latLng) {

	let latitude = latLng.lat;
	let longitude = latLng.lng;

	var marker = new ol.Feature({
		geometry: new ol.geom.Point(ol.proj.fromLonLat([longitude, latitude]))
	});

	// Set marker style (red icon)
	marker.setStyle(new ol.style.Style({
		image: new ol.style.Icon({
			anchor: [0.5, 1],
			src: 'https://maps.google.com/mapfiles/ms/icons/blue-dot.png',
			scale: 1
		})
	}));

	vectorSource.clear(); // Remove previous markers (optional)
	vectorSource.addFeature(marker);
	
	// Set the map view to the marker's location with zoom level 12
    map.getView().setCenter(ol.proj.fromLonLat([longitude, latitude]));
    map.getView().setZoom(12);

}




