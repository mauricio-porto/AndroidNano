package com.braintech.androidnano;


import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Log;

import fi.iki.elonen.NanoHTTPD;

public class AndroidWebServer extends NanoHTTPD {
    private static final String TAG = AndroidWebServer.class.getSimpleName();

    public AndroidWebServer(int port) {
        super(port);
    }

    public AndroidWebServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {

        String uri = session.getUri();       // Para garantia, pode testar se

        Method meth = session.getMethod();  // também podemos testar se é POST, por garantia

        try {
            Map<String, String> files = new HashMap<>();
            session.parseBody(files);
            for (Map.Entry<String, ? extends Object> entry : files.entrySet()) {
                Log.d(TAG, entry.getValue().toString());      // Mostra no log; deveria salvar/enviar a quem tiver interesse
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<body>");
        sb.append("<h1>OK</h1>");
        sb.append("</body>");
        sb.append("</html>");
        return newFixedLengthResponse(sb.toString());
    }

}
