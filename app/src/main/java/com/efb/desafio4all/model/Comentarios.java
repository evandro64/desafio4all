package com.efb.desafio4all.model;

/**
 * Created by Evandro on 16/03/2016.
 */
public class Comentarios {

    private String urlFoto;
    private String nome;
    private String titulo;
    private int nota;
    private String comentario;

    public Comentarios(String urlFoto, String nome, String titulo, int nota, String comentario) {
        this.urlFoto = urlFoto;
        this.nome = nome;
        this.titulo = titulo;
        this.nota = nota;
        this.comentario = comentario;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
