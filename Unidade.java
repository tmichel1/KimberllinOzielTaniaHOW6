package br.com.dlweb.lvm.unidade;

public class Unidade implements java.io.Serializable {

    private int id;

    private String nome;
    
	 public void setNome(String nome) {
        this.nome = nome;
    }

	public String getNome() {
        return nome;
    }
    public Unidade() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

  }