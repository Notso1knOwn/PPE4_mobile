package com.example.kdonoel;

import java.io.Serializable;

public class Produit implements Serializable {

    private int id, id_categorie;
    private String libelle, marque, lienImage;
    private float tarif, note;
    private Integer stock;

    /**
     *
     * @param pLibelle
     * @param pTarif
     * @param pStock
     */
    public Produit(String pLibelle,Float pTarif, Integer pStock){
        this.libelle = pLibelle;
        this.tarif = pTarif;
        this.stock = pStock;
    }

    /**
     *
     * @param pId
     * @param pMarque
     * @param pLibelle
     * @param pLienImage
     * @param pTarif
     * @param pStock
     * @param pNote
     * @param pId_categorie
     */
    public Produit(Integer pId, String pMarque, String pLibelle, String pLienImage, Float pTarif, Integer pStock, Float pNote, Integer pId_categorie){
        this.id = pId;
        this.marque = pMarque;
        this.libelle = pLibelle;
        this.lienImage = pLienImage;
        this.stock = pStock;
        this.note = pNote;
        this.tarif = pTarif;
        this.id_categorie = pId_categorie;
    }



    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getLienImage() {
        return lienImage;
    }

    public void setLienImage(String lienImage) {
        this.lienImage = lienImage;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return this.libelle + " - " + this.tarif + "€";
    }
}
