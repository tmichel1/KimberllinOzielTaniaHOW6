package br.com.dlweb.lvm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import br.com.dlweb.lvm.R;
import br.com.dlweb.lvm.objeto.Objeto;
import br.com.dlweb.lvm.unidade.Unidade;
import br.com.dlweb.lvm.conteudo.Conteudo;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Definição das variáveis globais
    private static final String DATABASE_NAME = "lvmat";
    private static final String TABLE_UNIDADE = "unidade";
    private static final String TABLE_OBJETO = "objeto";
    private static final String TABLE_CONTEUDO = "conteudo";

    private static final String CREATE_TABLE_UNIDADE = "CREATE TABLE " + TABLE_UNIDADE + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100))";


    private static final String CREATE_TABLE_OBJETO = "CREATE TABLE " + TABLE_OBJETO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100), " +
            "nivel VARCHAR(100), " +
            "site VARCHAR(150));";
    private static final String CREATE_TABLE_CONTEUDO = "CREATE TABLE " + TABLE_CONTEUDO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_unidade INTEGER, " +
            "id_objeto INTEGER, " +
            "nome VARCHAR(100));";

            "CONSTRAINT fk_conteudo_unidade FOREIGN KEY (id_unidade) REFERENCES unidade (id), " +
                    "CONSTRAINT fk_conteudo_objeto FOREIGN KEY (id_objeto) REFERENCES objeto (id))";
    private static final String DROP_TABLE_UNIDADE = "DROP TABLE IF EXISTS " + TABLE_UNIDADE;
    private static final String DROP_TABLE_OBJETO = "DROP TABLE IF EXISTS " + TABLE_OBJETO;
    private static final String DROP_TABLE_CONTEUDO = "DROP TABLE IF EXISTS " + TABLE_CONTEUDO;

    // Método construtor da classe, passando por parâmetro a versão do banco de dados
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Se ainda não existir o banco de dados, cria
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_UNIDADE);
        db.execSQL(CREATE_TABLE_OBJETO);
        db.execSQL(CREATE_TABLE_CONTEUDO);
    }

    // Se já existir e a versão for diferente, atualiza.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_UNIDADE);
        db.execSQL(DROP_TABLE_OBJETO);
        db.execSQL(DROP_TABLE_CONTEUDO);
        onCreate(db);
    }

    /* Início CRUD Objeto */
    public long createObjeto (Objeto m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        cv.put("nivel", m.getNivel());
        cv.put("site", m.getSite());
        long id = db.insert(TABLE_OBJETO, null, cv);
        db.close();
        return id;
    }
    public long updateObjeto (Objeto m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        cv.put("nivel", m.getNivel());
        cv.put("site", m.getSite());
        long id = db.update(TABLE_OBJETO, cv,
                "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }
    public long deleteObjeto (Objeto m) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_OBJETO, "_id = ?",
                new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }
    public void getAllObjeto (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadablenivelDatabase();
        String[] columns = {"_id", "nome", "nivel", "site"};
        Cursor data = db.query(TABLE_OBJETO, columns, null, null,
                null, null, "nome");
        int[] to = {R.id.textViewIdListarObjeto, R.id.textViewNomeListarObjeto,
                R.id.textViewsiteListarObjeto};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.objeto_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }
    public Objeto getByIdObjeto (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "nivel", "site"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_OBJETO, columns, "_id = ?", args, null, null, null);
        data.moveToFirst();
        Objeto m = new Objeto();
        m.setId(data.getInt(0));
        m.setNome(data.getString(1));
        m.setNivel(data.getString(2));
        m.setSite(data.getString(3));
        data.close();
        db.close();
        return m;
    }
    public void getAllNameObjeto (ArrayList<Integer> listObjetoId, ArrayList<String> listObjetoName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_OBJETO, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listObjetoId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listObjetoName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }
    /* Fim CRUD 1 */
    /* Início CRUD 2 */


    public long createUnidade (Unidade m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());

        long id = db.insert(TABLE_UNIDADE, null, cv);
        db.close();
        return id;
    }

    public long updateUnidade (Unidade m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());

        long id = db.update(TABLE_UNIDADE, cv, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }

    public long deleteUnidade (Unidade m) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_UNIDADE, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }

    public Unidade getByIdUnidade (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_UNIDADE, columns, "_id = ?", new String);
        data.moveToFirst();
        Unidade m = new Unidade();
        m.setId(data.getInt(0));
        m.setNome(data.getString(1));
        data.close();
        db.close();
        return m;
    }

    public void getAllUnidade (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Defini as colunas da consulta do SELECT
        String[] columns = {"_id", "nome"};
        // Recupera os valores do banco de dados
        Cursor data = db.query(TABLE_UNIDADE, columns, null, null, null, null, null);
        // Campos do XML de destino para os valores do BD
        int[] to = {R.id.textViewIdListUnidade, R.id.textViewNomeListUnidade};
        // Cria um adaptador utilizando o XML da pasta layout (unidade_item_list_view) como padrão, para mostrar em cada item as informações presentes no banco de dados
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.unidade_item_list_view, data, columns, to, 0);
        // Seta o adaptador no ListView. Por isso, não há retorno, é apenas modificado a instância do ListView recebido por parâmetro.
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    // Recupera apenas os IDs e nomes das Unidades para o Spinner.
    public void getAllNameUnidade (ArrayList<Integer> listMUnidadeId, ArrayList<String> listUnidadeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_UNIDADE, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listUnidadeId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listUnidadeName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    /* Fim CRUD unidades */

    /* Início CRUD Conteudo*/
    public long createConteudo(Conteudo b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_unidade", b.getId_unidade());
        cv.put("id_objeto", b.getId_objeto());
        cv.put("nome", b.getNome());

        long id = db.insert(TABLE_CONTEUDO, null, cv);
        db.close();
        return id;
    }

    public long updateConteudo(Conteudo b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_unidade", b.getId_unidade());
        cv.put("id_objeto", b.getId_objeto());
        cv.put("nome", b.getNome());

        long rows = db.update(TABLE_CONTEUDO, cv, "_id = ?", new String[]{String.valueOf(b.getId())});
        db.close();
        return rows;
    }

    public long deleteConteudo(Conteudo b) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_CONTEUDO, "_id = ?", new String[]{String.valueOf(b.getId())});
        db.close();
        return rows;
    }

    public void getAllConteudo (Context context, ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_CONTEUDO, columns, null, null, null,
                null, "nome");
        int[] to = {R.id.textViewIdListarConteudo, R.id.textViewNomeListarConteudo};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.conteudo_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public Conteudo getByIdConteudo (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "id_Unidade", "id_Objeto", "nome"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_CONTEUDO, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();

        b = new conteudo();
        b.setId(data.getInt(0));
        b.setId_unidade(data.getInt(1));
        b.setId_objeto(data.getInt(2));
        b.setNome(data.getString(3));

        data.close();
        db.close();
        return b;
    }

}
    /* Fim CRUD 3 */
    /* Fim CRUD unidade */

    /* Início CRUD Conteúdo */
    public long createConteudo(Conteudo b){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_unidade", b.getId_unidade());
        cv.put("id_objeto", b.getId_objeto());
        cv.put("nome", b.getNome());
        long id = db.insert(TABLE_CONTEUDO, null, cv);
        db.close();
        return id;
    }

    public long updateConteudo(Conteudo b){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_unidade", b.getId_unidade());
        cv.put("id_objeto", b.getId_objeto());
        cv.put("nome", b.getNome());
        long rows = db.update(TABLE_CONTEUDO, cv, "_id = ?", new String[]{String.valueOf(b.getId())});
        db.close();
        return rows;
    }

    public long deleteConteudo(Unidade b) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_CONTEUDO, "_id = ?", new String[]{String.valueOf(b.getId())});
        db.close();
        return rows;
    }

    public void getAllConteudo (Context context, ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_CONTEUDO, columns, null, null, null,
                null, "nome");
        int[] to = {R.id.textViewIdListarconteudo, R.id.textViewNomeListarConteudo};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.conteudo_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }


    public Unidade getByIdunidade (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_CONTEUDO, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Conteudo b = new Conteudo();
        b.setId(data.getInt(0));
        b.setId_unidade(data.getInt(1));
        b.setId_objeto(data.getInt(2));
        b.setNome(data.getString(3));
        data.close();
        db.close();
        return b;
    }

    public Conteudo getByIdConteudo(int id_conteudo) {
    }
    /* Fim CRUD Conteúdo */
}