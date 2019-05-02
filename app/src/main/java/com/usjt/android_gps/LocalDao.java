package com.usjt.android_gps;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LocalDao {
    private Context context;

    public LocalDao(Context context) {
        this.context = context;
    }

    public List<Localizacao> busca(String chave) {
        LocalDBHelper dbHelper = new LocalDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Localizacao> localizacaoList = new ArrayList<>();

        String sql = String.format(
                "SELECT * FROM %s ",
                LocalContract.Local.TABLE_NAME
        );
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(
                    String.format("%s.%s",
                            LocalContract.Local.TABLE_NAME,
                            LocalContract.Local.COLUMN_NAME_ID)
            ));

            double lat = cursor.getInt(
                    cursor.getColumnIndex(
                            String.format(
                                    "%s.%s",
                                    LocalContract.Local.TABLE_NAME,
                                    LocalContract.Local.COLUMN_NAME_LATITUDE
                            )
                    )
            );
            int log = cursor.getInt(
                    cursor.getColumnIndex(
                            String.format(
                                    "%s.%s",
                                    LocalContract.Local.TABLE_NAME,
                                    LocalContract.Local.COLUMN_NAME_LONGITUDE
                            )
                    )
            );
            while (cursor.moveToNext()) {
                localizacaoList.add(new Localizacao(id,lat,log));
            }

        }
        cursor.close();
        dbHelper.close();
        db.close();
        return localizacaoList;
    }
}