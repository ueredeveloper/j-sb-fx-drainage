package utilities;

import fi.iki.elonen.NanoHTTPD;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * No momento não está sendo utilizado.
 *
 */

public class SimpleWebServer extends NanoHTTPD {

    public SimpleWebServer() throws IOException {
        super(3000);
        start(SOCKET_READ_TIMEOUT, false);
        System.out.println("Servidor iniciado em http://localhost:8080");
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            // Caminho solicitado (ex: /styles.css)
            String uri = URLDecoder.decode(session.getUri(), "UTF-8");
            if (uri.equals("/")) {
                uri = "/index.html"; // Página principal
            }

            // Caminho do arquivo dentro de resources
            String resourcePath = "/html/map/open-layers" + uri;

            // Detectar MIME type
            String mime = getMimeTypeForFile(uri);

            // Ler o arquivo como stream
            InputStream stream = getClass().getResourceAsStream(resourcePath);
            if (stream == null) {
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Arquivo não encontrado: " + uri);
            }

            return newChunkedResponse(Response.Status.OK, mime, stream);

        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Erro interno do servidor.");
        }
    }
}
