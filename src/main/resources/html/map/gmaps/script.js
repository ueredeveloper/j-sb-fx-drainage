function initMap() {
            const myLatLng = { lat: -15.8058894, lng: -48.0391984 }; // Brasília, Brazil
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 15,
                center: myLatLng,
                mapTypeId: "roadmap", // Try hybrid, roadmap, etc.
                disableDefaultUI: true,
  				mapTypeControl: false
            });
            
           

            // Add a marker
            new google.maps.Marker({
                position: myLatLng,
                map: map,
                title: "Hello World!"
            });

            // Add Drawing Manager
            const drawingManager = new google.maps.drawing.DrawingManager({
                drawingMode: null, // Start with no mode active
                drawingControl: true,
                drawingControlOptions: {
                    position: google.maps.ControlPosition.TOP_CENTER,
                    drawingModes: [
                        google.maps.drawing.OverlayType.MARKER,
                        google.maps.drawing.OverlayType.POLYLINE,
                        google.maps.drawing.OverlayType.POLYGON,
                        google.maps.drawing.OverlayType.CIRCLE,
                        google.maps.drawing.OverlayType.RECTANGLE
                    ]
                },
                markerOptions: { draggable: true },
                polylineOptions: { strokeColor: "#FF0000", strokeWeight: 2 },
                polygonOptions: { fillColor: "#FF0000", fillOpacity: 0.5, strokeWeight: 2 },
                circleOptions: { fillColor: "#FFFF00", fillOpacity: 0.5, strokeWeight: 2 },
                rectangleOptions: { fillColor: "#00FF00", fillOpacity: 0.5, strokeWeight: 2 }
            });
            drawingManager.setMap(map);
        }

        window.initMap = initMap;