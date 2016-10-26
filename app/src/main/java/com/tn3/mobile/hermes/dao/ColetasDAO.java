package com.tn3.mobile.hermes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.tn3.mobile.hermes.models.Coletas;
import com.tn3.mobile.hermes.models.Endereco;
import com.tn3.mobile.hermes.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ColetasDAO {

    public static final String TABELA = "coletas";

    public static final String ID = "_id";
    public static final String ID_MANIFESTO = "id_manifesto";
    public static final String NOME_CLIENTE = "nome_cliente";
    public static final String CNPJ_CLIENTE = "cnpj_cliente";
    public static final String HORA_FIXA = "horafixa";
    public static final String HORA_LIMITE = "horalimite";
    public static final String QTD_NFES = "qtdnfes";
    public static final String QTD_VOLS = "qtdvol";
    public static final String CARGA_TOTAL = "cargatotal";
    public static final String VALOR_NFE = "valornfe";
    public static final String CUBAGEM = "cubagem";
    public static final String TIPO_CANCELAMENTO = "tipo_cancelamento";
    public static final String MOTIVO_CANCELAMENTO = "motivo_cancelamento";
    public static final String STATUS_COLETA = "status_coleta";
    public static final String STATUS_APP = "status_app";//1-aberto, 2-concluido, 3-cancelado
    public static final String FOTO = "foto";
    public static final String SINCRONIZADO = "sincronizado";
    public static final String DH_SINCRONIZADO = "dhsincronizado";
    public static final String FK_MANIFESTO_COLETA = "fk_manifesto_coleta";

    private Context context;
    private Utils utils = new Utils();

    public ColetasDAO(Context context) {
        this.context = context;
    }

    public List<Coletas> findColetaManifestoById (SQLiteDatabase db, Long id){

        boolean toClose = false;

        if(db == null) {
            db = DBHelper.getInstance(context).getReadableDatabase();
            toClose = true;
        }

        Cursor cursor = null;
        List<Coletas> lista = new ArrayList<>();

        try {

            String query = "SELECT * FROM " + TABELA + " WHERE " + ID_MANIFESTO + "=" + id +" ORDER BY " + STATUS_APP + " ASC";

            Log.d("query", query);
            cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()) {

                Coletas coletas = new Coletas();
                coletas.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                coletas.setNomeCliente(cursor.getString(cursor.getColumnIndex(NOME_CLIENTE)));
                coletas.setCnpjCpfCliente(cursor.getString(cursor.getColumnIndex(CNPJ_CLIENTE)));
                coletas.setHoraFixa(cursor.getString(cursor.getColumnIndex(HORA_FIXA)));
                coletas.setHoraLimite(cursor.getString(cursor.getColumnIndex(HORA_LIMITE)));
                coletas.setQtdNotas(cursor.getInt(cursor.getColumnIndex(QTD_NFES)));
                coletas.setVolumes(cursor.getColumnIndex(QTD_VOLS));
                coletas.setCubagem(cursor.getString(cursor.getColumnIndex(CUBAGEM)));
                if(cursor.getString(cursor.getColumnIndex(CARGA_TOTAL)) != null) {
                    coletas.setTotalCarga((double) cursor.getColumnIndex(CARGA_TOTAL));
                }
                if(cursor.getString(cursor.getColumnIndex(VALOR_NFE)) != null) {
                    coletas.setValorTotal((double) cursor.getColumnIndex(VALOR_NFE));
                }
                coletas.setMotivoTipoCancelamento(cursor.getInt(cursor.getColumnIndex(TIPO_CANCELAMENTO)));
                coletas.setMotivoCancelamento(cursor.getString(cursor.getColumnIndex(MOTIVO_CANCELAMENTO)));
                coletas.setStatus(cursor.getString(cursor.getColumnIndex(STATUS_COLETA)));
                coletas.setStatusApp(cursor.getString(cursor.getColumnIndex(STATUS_APP)));
                coletas.setFotoCancelamentoColeta(cursor.getBlob(cursor.getColumnIndex(FOTO)));
                boolean sync = cursor.getInt(cursor.getColumnIndex(SINCRONIZADO)) > 0;
                coletas.setSincronizado(sync);
                String data = cursor.getString(cursor.getColumnIndex(DH_SINCRONIZADO));
                if(data != null) {
                    Date d = utils.dbStringToDateForDb(data);
                    coletas.setDhsincronizado(d);
                }

                //ENDERECO
                Endereco endereco = new EnderecoDAO(context).findEnderecoPelaColetaId(db, coletas.getId());
                coletas.setEndereco(endereco);

                //CHAVES DAS NFES DA COLETA
                List<String> chaves = new ChaveNfeDAO(context).findChavesDaColetaById(db, coletas.getId());
                coletas.setChavesNfe(chaves);

                lista.add(coletas);
            }

        } catch (SQLiteException e) {
            Log.d("ColetasDAO_findColetas", e.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
            if(toClose) {
                //db.close();
            }
        }

        return lista;
    }

    public Coletas findColetaById(String id){

        Coletas coletas = new Coletas();
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = null;

        try {

            String query = "SELECT * FROM " + TABELA + " WHERE " + ID + "=" + id;
            Log.d("query", query);
            cursor = db.rawQuery(query, null);

            if(cursor.moveToNext()) {

                coletas.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                coletas.setNomeCliente(cursor.getString(cursor.getColumnIndex(NOME_CLIENTE)));
                coletas.setCnpjCpfCliente(cursor.getString(cursor.getColumnIndex(CNPJ_CLIENTE)));
                coletas.setHoraFixa(cursor.getString(cursor.getColumnIndex(HORA_FIXA)));
                coletas.setHoraLimite(cursor.getString(cursor.getColumnIndex(HORA_LIMITE)));
                coletas.setQtdNotas(cursor.getInt(cursor.getColumnIndex(QTD_NFES)));
                coletas.setVolumes(cursor.getColumnIndex(QTD_VOLS));
                coletas.setCubagem(cursor.getString(cursor.getColumnIndex(CUBAGEM)));
                if(cursor.getString(cursor.getColumnIndex(CARGA_TOTAL)) != null) {
                    coletas.setTotalCarga((double) cursor.getColumnIndex(CARGA_TOTAL));
                }
                if(cursor.getString(cursor.getColumnIndex(VALOR_NFE)) != null) {
                    coletas.setValorTotal((double) cursor.getColumnIndex(VALOR_NFE));
                }

                coletas.setMotivoTipoCancelamento(cursor.getInt(cursor.getColumnIndex(TIPO_CANCELAMENTO)));
                coletas.setMotivoCancelamento(cursor.getString(cursor.getColumnIndex(MOTIVO_CANCELAMENTO)));
                coletas.setStatus(cursor.getString(cursor.getColumnIndex(STATUS_COLETA)));
                coletas.setStatusApp(cursor.getString(cursor.getColumnIndex(STATUS_APP)));
                coletas.setFotoCancelamentoColeta(cursor.getBlob(cursor.getColumnIndex(FOTO)));
                boolean sync = cursor.getInt(cursor.getColumnIndex(SINCRONIZADO)) > 0;
                coletas.setSincronizado(sync);
                String data = cursor.getString(cursor.getColumnIndex(DH_SINCRONIZADO));
                if(data != null) {
                    coletas.setDhsincronizado(utils.dbStringToDateForDb(data));
                }

                //ENDERECO
                Endereco endereco = new EnderecoDAO(context).findEnderecoPelaColetaId(db, coletas.getId());
                coletas.setEndereco(endereco);

                //CHAVES DAS NFES DA COLETA
                List<String> chaves = new ChaveNfeDAO(context).findChavesDaColetaById(db, coletas.getId());
                coletas.setChavesNfe(chaves);

            }
        } catch (SQLiteException e) {
            Log.d("ColetasDAO_findColeta", e.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
                //db.close();
            }
        }

        return coletas;
    }

    public void updateConcluiColeta(String idColeta, String volume) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        try {

            ContentValues coleta = new ContentValues();
            coleta.put(ColetasDAO.QTD_VOLS, volume);
            coleta.put(ColetasDAO.STATUS_APP, "2");//concluida
            db.update(ColetasDAO.TABELA, coleta, ID + "=?", new String[]{idColeta});

            Log.d("updateConcluiColeta", idColeta);

        } catch (SQLiteException e) {
            Log.d("ColetasDAO_Conclui", e.getMessage());
        } finally {
            //db.close();
        }
    }

    public void updateColetasSincronizadas(String idColeta) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        try {

            ContentValues coleta = new ContentValues();
            coleta.put(ColetasDAO.DH_SINCRONIZADO, utils.currentDate());
            coleta.put(ColetasDAO.SINCRONIZADO, 1);
            db.update(ColetasDAO.TABELA, coleta, ID + "=?", new String[]{idColeta});

            Log.d("updateColetasSync", idColeta);

        } catch (SQLiteException e) {
            Log.d("ColetasDAO_Sincroniza", e.getMessage());
        } finally {
            //db.close();
        }
    }

    public void resetColetasSync() {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        try {

            ContentValues coleta = new ContentValues();
            coleta.put(ColetasDAO.DH_SINCRONIZADO, "");
            coleta.put(ColetasDAO.SINCRONIZADO, 0);
            db.update(ColetasDAO.TABELA, coleta, SINCRONIZADO + "=?", new String[]{"1"});

            Log.d("resetColetasSync", "Resetando coletas sincronizadas..");

        } catch (SQLiteException e) {
            Log.d("ColetasDAO_reset", e.getMessage());
        } finally {
            //db.close();
        }
    }

    public void updateCancelaColeta(String idColeta, int tipo, String motivo) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        try {

            ContentValues coleta = new ContentValues();
            coleta.put(ColetasDAO.TIPO_CANCELAMENTO, tipo);
            coleta.put(ColetasDAO.MOTIVO_CANCELAMENTO, motivo);
            coleta.put(ColetasDAO.STATUS_APP, "3");//cancelada
            db.update(ColetasDAO.TABELA, coleta, ID + "=?", new String[]{idColeta});

            Log.d("updateCancelaColeta", idColeta);

        } catch (SQLiteException e) {
            Log.d("ColetasDAO_Cancela", e.getMessage());
        } finally {
            //db.close();
        }
    }

    public void updateCancelaColetaFoto(Long idColeta, byte[] foto) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();

        try {

            ContentValues coleta = new ContentValues();
            coleta.put(ColetasDAO.FOTO, foto);
            //coleta.put(ColetasDAO.STATUS_APP, "3");
            db.update(ColetasDAO.TABELA, coleta, ID + "=?", new String[]{String.valueOf(idColeta)});

            Log.d("updateCancelaColetaFoto", String.valueOf(idColeta));

        } catch (SQLiteException e) {
            Log.d("ColetasDAO_Cancelafoto", e.getMessage());
        } finally {
            //db.close();
        }
    }

    public String findColetasStatusApp(Long idManifesto, String status) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = null;
        String retorno = "0";

        try {

            String query = "SELECT COUNT(*) AS TOTAL FROM "+TABELA+" WHERE " + ID_MANIFESTO + "=" + idManifesto +" AND "+ STATUS_APP + "=" + status;
            Log.d("query", query);
            cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                Log.d("TOT", cursor.getString(cursor.getColumnIndex("TOTAL")));
                retorno = cursor.getString(cursor.getColumnIndex("TOTAL"));
            }

        } catch (SQLiteException e) {
            Log.d("ColetasDAO_ColetasStat", e.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
                //db.close();
            }
        }

        return retorno;
    }

    public List<Coletas> findColetasConcluidas() {

        List<Coletas> coletas = new ArrayList<>();
        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = null;

        try {

            String query = "SELECT * FROM " + TABELA + " WHERE "+ STATUS_APP +" IN (2,3) AND "+ SINCRONIZADO +"=0";
            Log.d("query", query);

            cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()) {
                Coletas coleta = new Coletas();
                coleta.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                coleta.setVolumes(cursor.getInt(cursor.getColumnIndex(QTD_VOLS)));
                coleta.setMotivoTipoCancelamento(cursor.getInt(cursor.getColumnIndex(TIPO_CANCELAMENTO)));
                coleta.setMotivoCancelamento(cursor.getString(cursor.getColumnIndex(MOTIVO_CANCELAMENTO)));
                List<String> chaves = new ChaveNfeDAO(context).findChavesDaColetaById(db, coleta.getId());
                coleta.setChavesNfe(chaves);
                coletas.add(coleta);
            }

        } catch (SQLiteException e) {
            Log.d("ColetasDAO_ColetasConcl", e.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
                //db.close();
            }
        }

        return coletas;
    }

    public Coletas findStatusAppColeta(String id) {

        SQLiteDatabase db = DBHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = null;
        Coletas coleta = new Coletas();

        try {

            String query = "SELECT * FROM " + TABELA + " WHERE "+ ID +"=" + id;
            Log.d("query", query);
            cursor = db.rawQuery(query, null);

            if(cursor.moveToNext()) {
                coleta.setId(cursor.getLong(cursor.getColumnIndex(ID)));
                coleta.setStatusApp(cursor.getString(cursor.getColumnIndex(STATUS_APP)));
            }

        } catch (SQLiteException e) {
            Log.d("ColetasDAO_findStatusAp", e.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
                //db.close();
            }
        }

        return coleta;
    }
}
