// Replace with your Mapbox token
const accessToken = "pk.eyJ1IjoidWVyZWRldmVsb3BlciIsImEiOiJjbTdldm9vYjUwaHh2MnZvZDI3aXB5bXlrIn0.sQKIvjSCCdw3-vm9smYmfw";

let currentMarker = null; // To store the last marker

const mapStyles = {
    road: "mapbox/streets-v12",
    satellite: "mapbox/satellite-streets-v12",
    terrain: "mapbox/outdoors-v12"
};

// Create the Leaflet map
const map = L.map("map").setView([40.7128, -74.006], 12);

// Function to get tile layer URL
function getTileLayer(type) {
    return `https://api.mapbox.com/styles/v1/${mapStyles[type]}/tiles/256/{z}/{x}/{y}?access_token=${accessToken}`;
}

let currentLayer = L.tileLayer(getTileLayer("road")).addTo(map); // Default road map

// Add Mapbox raster tiles to Leaflet (No WebGL)
L.tileLayer(`https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v12/tiles/256/{z}/{x}/{y}?access_token=${accessToken}`, {
	attribution: '© <a href="https://www.mapbox.com/about/maps/">Mapbox</a>',
	maxZoom: 18
}).addTo(map);

// Add an initial marker
L.marker([40.7128, -74.006])
	.addTo(map)
	.bindPopup("<h3>New York City</h3>")
	.openPopup();

// Handle map click
map.on('click', function (event) {
    var lat = event.latlng.lat.toFixed(6);
    var lng = event.latlng.lng.toFixed(6);
    
    let latLng = { lat: parseFloat(lat), lng: parseFloat(lng) };

    // Remove existing marker
    if (currentMarker) {
        map.removeLayer(currentMarker);
    }

    // Add new marker in Leaflet
    currentMarker = L.marker([lat, lng]).addTo(map);

    // Send coordinates to JavaFX WebView
    if (window.app) {
        window.app.sendCoordinates(JSON.stringify(latLng));
    }

    console.log("Coordinates sent:", latLng);
});


// Function to change map type
function setMapType(type) {
    if (mapStyles[type]) {
        map.removeLayer(currentLayer); // Remove current layer
        currentLayer = L.tileLayer(getTileLayer(type)).addTo(map);
    } else {
        console.error("Invalid map type:", type);
    }
}

// Function to zoom out
function zoomOut() {
	map.setZoom(map.getZoom() - 1);
}

// Function to zoom in
function zoomIn() {
	map.setZoom(map.getZoom() + 1);
}

function addMarker(latLng) {
    let latitude = latLng.lat;
    let longitude = latLng.lng;

    // Remove existing marker if it exists
    if (currentMarker) {
        map.removeLayer(currentMarker);
    }

    // Create a new marker and add to map
    currentMarker = L.marker([latitude, longitude], {
        icon: L.icon({
            iconUrl: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png",
            iconSize: [32, 32], // Adjust size if needed
            iconAnchor: [16, 32]
        })
    }).addTo(map);

    // Center map on the new marker
    map.setView([latitude, longitude], 12);
}
