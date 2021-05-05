package com.example.kdonoel;

import java.io.Serializable;

public class Categorie implements Serializable {

    private Integer id;
    private String libelle, descriptif;

    public Categorie(Integer pId, String pLibelle, String pDescriptif){
        this.id = pId;
        this.libelle = pLibelle;
        this.descriptif = pDescriptif;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
