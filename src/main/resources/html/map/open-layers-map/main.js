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

// Base layers
var satelliteLayer = new ol.layer.Tile({
	source: new ol.source.XYZ({
		url: 'https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}'
	})
});

var roadLayer = new ol.layer.Tile({
	source: new ol.source.OSM()
});

var terrainLayer = new ol.layer.Tile({
	source: new ol.source.XYZ({
		url: 'https://{s}.tile.opentopomap.org/{z}/{x}/{y}.png',
		attributions: '&copy; <a href="https://opentopomap.org">OpenTopoMap</a> contributors'
	})
});

function setMapType(type) {
	map.getLayers().clear(); // Remove existing layers
	if (type === 'satellite') {
		map.addLayer(satelliteLayer);
	} else if (type === 'road') {
		map.addLayer(roadLayer);
	} else if (type === 'terrain') {
		map.addLayer(terrainLayer);
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
	
	console.log(latLng.lat)

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
	
}




