package com.tn3.mobile.hermes.dao;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    static final int VERSION = 1;
    private static final String DB_NAME = "tn3_hermes";
    private static DBHelper mInstance = null;

    public static synchronized DBHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DBHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        //super(context, Environment.getExternalStorageDirectory().getPath() +"/TN3Hermes/"+ DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Table de Chaves das Coletas
        db.execSQL("CREATE TABLE ["+ChaveNfeDAO.TABELA+"](\n" +
                "       ["+ChaveNfeDAO.ID+"] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "       ["+ChaveNfeDAO.ID_COLETA+"] INTEGER CONSTRAINT ["+ChaveNfeDAO.FK_COLETAS_CHAVES+"] " +
                "   REFERENCES "+ColetasDAO.TABELA+"(["+ColetasDAO.ID+"]) ON DELETE CASCADE ON UPDATE CASCADE, \n" +
                "       ["+ChaveNfeDAO.CHAVE+"] VARCHAR(44));");

        //Table de Coletas do Manifesto
        db.execSQL("CREATE TABLE ["+ColetasDAO.TABELA+"](\n" +
                "       ["+ColetasDAO.ID+"] INTEGER PRIMARY KEY NOT NULL, \n" +
                "       ["+ColetasDAO.ID_MANIFESTO+"] INTEGER NOT NULL CONSTRAINT ["+ColetasDAO.FK_MANIFESTO_COLETA+"]" +
                "    REFERENCES "+ManifestosDAO.TABELA+"(["+ManifestosDAO.ID+"]) ON DELETE CASCADE ON UPDATE CASCADE, \n" +
                "       ["+ColetasDAO.NOME_CLIENTE+"] VARCHAR(200) NOT NULL, \n" +
                "       ["+ColetasDAO.CNPJ_CLIENTE+"] VARCHAR(14) NOT NULL, \n" +
                "       ["+ColetasDAO.HORA_FIXA+"] VARCHAR(5), \n" +
                "       ["+ColetasDAO.HORA_LIMITE+"] VARCHAR(5), \n" +
                "       ["+ColetasDAO.QTD_NFES+"] INTEGER(100), \n" +
                "       ["+ColetasDAO.QTD_VOLS+"] INTEGER(100), \n" +
                "       ["+ColetasDAO.CARGA_TOTAL+"] DOUBLE(12), \n" +
                "       ["+ColetasDAO.VALOR_NFE+"] DOUBLE(12), \n" +
                "       ["+ColetasDAO.CUBAGEM+"] VARCHAR(20), \n" +
                "       ["+ColetasDAO.TIPO_CANCELAMENTO+"] INTEGER(1), \n" +
                "       ["+ColetasDAO.MOTIVO_CANCELAMENTO+"] TEXT, \n" +
                "       ["+ColetasDAO.STATUS_COLETA+"] VARCHAR(40), \n" +
                "       ["+ColetasDAO.STATUS_APP+"] VARCHAR(1), \n" +
                "       ["+ColetasDAO.FOTO+"] CLOB, \n" +
                "       ["+ColetasDAO.SINCRONIZADO+"] BOOLEAN(1) NOT NULL DEFAULT 0, \n" +
                "       ["+ColetasDAO.DH_SINCRONIZADO+"] DATETIME);");

        //Table de Enderecos das Coletas
        db.execSQL("CREATE TABLE ["+EnderecoDAO.TABELA+"](\n" +
                "       ["+EnderecoDAO.ID+"] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "       ["+EnderecoDAO.ID_COLETA+"] INTEGER CONSTRAINT ["+EnderecoDAO.FK_ENDERECO_COLETA+"] " +
                "   REFERENCES "+ColetasDAO.TABELA+"(["+ColetasDAO.ID+"]) ON DELETE CASCADE ON UPDATE CASCADE, \n" +
                "       ["+EnderecoDAO.LOGRADOURO+"] VARCHAR(200), \n" +
                "       ["+EnderecoDAO.NUMERO+"] VARCHAR(20), \n" +
                "       ["+EnderecoDAO.COMPLEMENTO+"] VARCHAR(100), \n" +
                "       ["+EnderecoDAO.CIDADE+"] VARCHAR(200), \n" +
                "       ["+EnderecoDAO.UF+"] VARCHAR(2), \n" +
                "       ["+EnderecoDAO.BAIRRO+"] VARCHAR(50), \n" +
                "       ["+EnderecoDAO.SETOR+"] VARCHAR(100));");

        //Table de Manifestos
        db.execSQL("CREATE TABLE ["+ManifestosDAO.TABELA+"](\n" +
                "    ["+ManifestosDAO.ID+"] INTEGER PRIMARY KEY NOT NULL, \n" +
                "    ["+ManifestosDAO.DATA_MANIFESTO+"] DATETIME, \n" +
                "    ["+ManifestosDAO.SITUACAO+"] VARCHAR(20), \n" +
                "    ["+ManifestosDAO.TIPO+"] VARCHAR(1), \n" +
                "    ["+ManifestosDAO.ODOMETRO+"] INTEGER(40), \n" +
                "    ["+ManifestosDAO.SINCRONIZADO+"] BOOLEAN(1), \n" +
                "    ["+ManifestosDAO.DH_SINCRONIZADO+"] DATETIME);\n");

        //Table de Rotas dos Manifestos
        db.execSQL("CREATE TABLE ["+RotasDAO.TABELA+"](\n" +
                "    ["+RotasDAO.ID+"] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    ["+RotasDAO.ID_MANIFESTO+"] INTEGER CONSTRAINT ["+RotasDAO.FK_MANIFESTO_ROTAS+"]" +
                " REFERENCES "+ManifestosDAO.TABELA+"(["+ManifestosDAO.ID+"]) ON DELETE CASCADE ON UPDATE CASCADE, \n" +
                "    ["+RotasDAO.DESCRICAO+"] VARCHAR(200));");

        //Table de Rotas dos Manifestos Origem
        db.execSQL("CREATE TABLE ["+RotasOrigemDAO.TABELA+"](\n" +
                "    ["+RotasOrigemDAO.ID+"] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    ["+RotasOrigemDAO.ID_ROTA+"] INTEGER CONSTRAINT ["+RotasOrigemDAO.FK_ROTAS_ORIGEM+"]" +
                " REFERENCES "+RotasDAO.TABELA+"(["+RotasDAO.ID+"]) ON DELETE CASCADE ON UPDATE CASCADE, \n" +
                "    ["+RotasOrigemDAO.NOME+"] VARCHAR(200), \n" +
                "    ["+RotasOrigemDAO.UF+"] VARCHAR(200));");

        //Table de Rotas dos Manifestos Destino
        db.execSQL("CREATE TABLE ["+RotasDestinoDAO.TABELA+"](\n" +
                "    ["+RotasDestinoDAO.ID+"] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    ["+RotasDestinoDAO.ID_ROTA+"] INTEGER CONSTRAINT ["+RotasDestinoDAO.FK_ROTAS_DESTINO+"]" +
                " REFERENCES "+RotasDAO.TABELA+"(["+RotasDAO.ID+"]) ON DELETE CASCADE ON UPDATE CASCADE, \n" +
                "    ["+RotasDestinoDAO.NOME+"] VARCHAR(200), \n" +
                "    ["+RotasDestinoDAO.UF+"] VARCHAR(200));");

        //Table de Rotas dos Manifestos Intermediarias
        db.execSQL("CREATE TABLE ["+RotasIntermediariasDAO.TABELA+"](\n" +
                "    ["+RotasIntermediariasDAO.ID+"] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    ["+RotasIntermediariasDAO.ID_ROTA+"] INTEGER CONSTRAINT ["+RotasIntermediariasDAO.FK_ROTAS_INTERMEDIARIAS+"]" +
                " REFERENCES "+RotasDAO.TABELA+"(["+RotasDAO.ID+"]) ON DELETE CASCADE ON UPDATE CASCADE, \n" +
                "    ["+RotasIntermediariasDAO.NOME+"] VARCHAR(200), \n" +
                "    ["+RotasIntermediariasDAO.UF+"] VARCHAR(200));");

        //Table de Autenticacao do Usuario
        db.execSQL("CREATE TABLE ["+AuthDAO.TABELA+"](\n" +
                "    ["+AuthDAO.ID+"] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    ["+AuthDAO.STATUS+"]  BOOLEAN(1), \n" +
                "    ["+AuthDAO.MSG+"]  VARCHAR(200), \n" +
                "    ["+AuthDAO.URL+"]  VARCHAR(200), \n" +
                "    ["+AuthDAO.TOKEN+"]  VARCHAR(200), \n" +
                "    ["+AuthDAO.IMEI+"]  VARCHAR(200), \n" +
                "    ["+AuthDAO.DHCONSULTA+"] DATETIME);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ChaveNfeDAO.TABELA+"");
        db.execSQL("DROP TABLE IF EXISTS "+RotasDAO.TABELA+"");
        db.execSQL("DROP TABLE IF EXISTS "+RotasOrigemDAO.TABELA+"");
        db.execSQL("DROP TABLE IF EXISTS "+RotasDestinoDAO.TABELA+"");
        db.execSQL("DROP TABLE IF EXISTS "+RotasIntermediariasDAO.TABELA+"");
        db.execSQL("DROP TABLE IF EXISTS "+EnderecoDAO.TABELA+"");
        db.execSQL("DROP TABLE IF EXISTS "+ColetasDAO.TABELA+"");
        db.execSQL("DROP TABLE IF EXISTS "+ManifestosDAO.TABELA+"");
        db.execSQL("DROP TABLE IF EXISTS "+AuthDAO.TABELA+"");

        onCreate(db);
    }
}
