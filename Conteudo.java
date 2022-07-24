package br.com.dlweb.lvm.conteudo;

import br.com.dlweb.lvm.unidade.Unidade;

public class Conteudo extends Unidade implements java.io.Serializable {

    private int id;
    private int id_unidade;
    private int id_objeto;
    private String nome;

    public Conteudo() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_unidade() {
        return id_unidade;
    }

    public void setId_unidade(int id_unidade) {
        this.id_unidade = id_unidade;
    }

    public int getId_objeto() {
        return id_objeto;
    }

    public void setId_objeto(int id_objeto) {
        this.id_objeto = id_objeto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}