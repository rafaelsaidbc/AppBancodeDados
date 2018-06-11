package com.example.entomologia.appbancodedados;

public class Contato {

    //criação das strings
    private String nome;
    private String endereco;
    private String empresa;

    //construtor
    Contato(String nome, String endereco, String empresa) {
        this.nome = nome;
        this.endereco = endereco;
        this.empresa = empresa;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

}
