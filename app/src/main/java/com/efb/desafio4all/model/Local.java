package com.efb.desafio4all.model;

import java.util.ArrayList;

/**
 * Created by Evandro on 16/03/2016.
 */
public class Local {

    private String cidade;
    private String bairro;
    private String urlFoto;
    private String titulo;
    private String telefone;
    private String texto;
    private String endereco;
    private double latitude;
    private double longitude;
    private ArrayList<Comentarios> comentarios;

    public Local(String cidade, String bairro, String urlFoto, String titulo, String telefone, String texto, String endereco, long latitude, long longitude, ArrayList<Comentarios> comentarios) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.urlFoto = urlFoto;
        this.titulo = titulo;
        this.telefone = telefone;
        this.texto = texto;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comentarios = comentarios;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Comentarios> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentarios> comentarios) {
        this.comentarios = comentarios;
    }
}
