package com.example.android.bluetoothchat.entities;

/**
 * Created by victoredoardo on 16/05/2015.
 */
public class Usuario {

    private String nome;
    private String idFB;
    private String idBT;

    Usuario() {
        // TODO pegar dados da API
    }

    public String getNome() {
        return nome;
    }

    public String getIdFB() {
        return idFB;
    }

    public String getIdBT() {
        return idBT;
    }

}
