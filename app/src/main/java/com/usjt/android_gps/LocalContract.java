package com.usjt.android_gps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.sql.Types.INTEGER;


public class LocalContract {

    private static List<Localizacao> lista;

    static{
        lista = new ArrayList<>();
        lista.add(new Localizacao(1, 23.4,29.7));
        lista.add(new Localizacao(2, 43.4,39.7));
        lista.add(new Localizacao (3, 53.4,49.7));
        lista.add(new Localizacao (4, 63.4,59.7));
        lista.add(new Localizacao (5, 73.4,69.7));
    }

    private LocalContract (){

    }
    public static class Local implements BaseColumns{
        public static final String TABLE_NAME = "local";
        public static final String COLUMN_NAME_ID = "id_local";
        public static final String COLUMN_NAME_LATITUDE = "local_latitude";
        public static final String COLUMN_NAME_LONGITUDE = "local_latitude";
        public static final String DROP_TABLE = String.format("DROP TABLE %s", Local.TABLE_NAME);

    }
    public static String createTableLocal () {

        return String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER, %s INTEGER);",
                Local.TABLE_NAME,
                Local.COLUMN_NAME_ID,
                Local.COLUMN_NAME_LATITUDE,
                Local.COLUMN_NAME_LONGITUDE
        );
    }
    public static String insertChamados(){
        String t =
                "INSERT INTO %s (%s, %s, %s) VALUES (%d, %d, %d); ";
        StringBuilder sb = new StringBuilder("");
        for (Localizacao localizacao : lista){
            sb.append(
                    String.format(
                            Locale.getDefault(),
                            t,
                            Local.TABLE_NAME,
                            Local.COLUMN_NAME_ID,
                            Local.COLUMN_NAME_LATITUDE,
                            Local.COLUMN_NAME_LONGITUDE,
                            localizacao.getId(),
                            localizacao.getLatitude(),
                            localizacao.getLongitude()
                    )
            );
        }
        return sb.toString();
    }
}



