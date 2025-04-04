package services;

import fi.iki.elonen.NanoHTTPD;
import java.io.*;

public class MyHttpServer extends NanoHTTPD {

    public MyHttpServer(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Server running at http://localhost:" + port + "/");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        if (uri.equals("/")) uri = "/html/map/leaflet/index.html"; // Default file

        InputStream fileStream = getClass().getClassLoader().getResourceAsStream(uri.substring(1));
        if (fileStream == null) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 Not Found");
        }

        return newChunkedResponse(Response.Status.OK, getMimeType(uri), fileStream);
    }

    private String getMimeType(String uri) {
        if (uri.endsWith(".html")) return "text/html";
        if (uri.endsWith(".css")) return "text/css";
        if (uri.endsWith(".js")) return "application/javascript";
        return "application/octet-stream";
    }

   
}
