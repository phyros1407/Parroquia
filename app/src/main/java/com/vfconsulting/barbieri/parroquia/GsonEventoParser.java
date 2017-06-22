package com.vfconsulting.barbieri.parroquia;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.vfconsulting.barbieri.parroquia.Beans.EventoBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 22/06/2017.
 */

public class GsonEventoParser {

    public List leerFlujoJson(InputStream in) throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List eventos = new ArrayList();

        reader.beginArray();

        while (reader.hasNext()) {
            EventoBean animal = gson.fromJson(reader, EventoBean.class);
            eventos.add(animal);
        }


        reader.endArray();
        reader.close();
        return eventos;
    }


}
