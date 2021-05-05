package com.example.kdonoel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Produit> produits = new ArrayList<Produit>();
    private ArrayAdapter<Produit> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewProduit = (ListView) findViewById(R.id.listView_Kdo);
        listViewProduit.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //le contexte est l'instance (this) de l'activity APIActivity
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        //url du service à consommer
        // String url = "http://10.0.2.2/PPE4/public/api/produit";
        String url = "http://10.0.10.137:8082/PPE4/public/api/produit";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ListView listViewProduit = (ListView) findViewById(R.id.listView_Kdo);
                        listViewProduit.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Produit produit = new Produit(
                                        item.getInt("id"),
                                        item.getString("marque"),
                                        item.getString("libelle"),
                                        item.getString("lienImage"),
                                        Float.parseFloat(String.valueOf( item.getDouble("tarif") )),
                                        item.getInt("stock"),
//                                        Float.parseFloat(String.valueOf(item.getDouble("note"))),
                                        0.00f,
                                        item.getJSONObject("idCategorie").getInt("idCategorie")
                                        );
                                produits.add(produit);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Remplissage de la listview
                        arrayAdapter = new ArrayAdapter<Produit>(MainActivity.this, android.R.layout.simple_list_item_checked, produits);
                        listViewProduit.setAdapter(arrayAdapter);

                        //Si on veut obtenir une action lorsque l’on clique sur un élément de la listview !
                        listViewProduit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                TextView tv = (TextView) view;
                                Toast.makeText(MainActivity.this, tv.getText() + "  " + position, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }, new Response.ErrorListener() {        // CAS d’ERREUR
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Erreur :".concat(error.toString()));
//                TextView textApiAll = (TextView) findViewById(R.id.text_api_all);
//                textApiAll.setText("Erreur, " + error.getMessage());
            }
        });
        queue.add(stringRequest);




//        Produit produit1 = new Produit(1,"Asus","ROG Huracan (G21CN-FR102T)", "",1299.99f, 5, 9.00f,1);
//        Produit produit2 = new Produit(2, "HP", "Pavilion Gaming 690-0117nf (6ZM33EA)", "",799.99f, 10, 7.50f , 2);
//        Produit produit3 = new Produit(3, "Apple", "iMac Pro 27 pouces Rétina 5K", "",5000f, 1, 5.00f, 1);
//        Produit produit4 = new Produit(4, "MSI", "Trident A (9SD-664EU)", "",1699.99f, 5, 6.50f, 2);
//
//        produits.add(produit1);
//        produits.add(produit2);
//        produits.add(produit3);
//        produits.add(produit4);
//
//        arrayAdapter = new ArrayAdapter<Produit>(this, android.R.layout.simple_list_item_checked, produits);
//        listViewProduit.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.boutton_modifKdo){

            int duration = Toast.LENGTH_SHORT;
            Toast toast;

            ListView listViewProduit = (ListView) findViewById(R.id.listView_Kdo);
            int produitPosition = listViewProduit.getCheckedItemPosition();

            if (produitPosition != -1) {
                Produit produitChecked = (Produit) listViewProduit.getItemAtPosition(produitPosition);

                Intent iModifKdo = new Intent(this, ModifKdo.class);
                Bundle iModifKdoBundle = new Bundle();
                iModifKdoBundle.putInt("page", 1 );
                iModifKdoBundle.putInt("position", produitPosition );
                iModifKdoBundle.putInt("id", produitChecked.getId() );
                iModifKdoBundle.putString("libelle", produitChecked.getLibelle() );
                iModifKdoBundle.putString("marque", produitChecked.getMarque() );
                iModifKdoBundle.putFloat("tarif", produitChecked.getTarif() );
                iModifKdoBundle.putInt("stock", produitChecked.getStock() );
                iModifKdoBundle.putInt("idCategorie", produitChecked.getId_categorie() );
                iModifKdo.putExtras(iModifKdoBundle);

                startActivityForResult(iModifKdo,0);
            } else {
                toast = Toast.makeText(MainActivity.this, "Aucun produit n'a été sélectionné" , duration );
                toast.show();
            }

        } else if(v.getId() == R.id.button_addKdo){
            Intent iaddKdo = new Intent(this, AjoutKdo.class);
            startActivityForResult(iaddKdo,0);

        } else if (v.getId() == R.id.boutton_suppKdo) {
            int duration = Toast.LENGTH_SHORT;
            Toast toast;

            ListView listViewProduit = (ListView) findViewById(R.id.listView_Kdo);
            int produitPosition = listViewProduit.getCheckedItemPosition();

            if (produitPosition != -1) {
                Produit produitChecked = (Produit) listViewProduit.getItemAtPosition(produitPosition);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "http://10.0.10.137:8082/PPE4/public/api/produit/supp/" + produitChecked.getId();
                // String url = "http://10.0.2.2/PPE4/public/api/produit/supp/"+ produitChecked.getId();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(stringRequest);

            } else {
                toast = Toast.makeText(MainActivity.this, "Aucun produit n'a été sélectionné" , duration );
                toast.show();
            }
        }
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode,resultCode,data);
            ListView listViewProduit = (ListView) findViewById(R.id.listView_Kdo);

            if (resultCode == Activity.RESULT_OK){

                int duration = Toast.LENGTH_SHORT;
                Toast toast;

                if (data.getExtras().getInt("page") == 0){

                    listViewProduit.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    produits = new ArrayList<Produit>();

                    //le contexte est l'instance (this) de l'activity APIActivity
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                    //url du service à consommer
                    String url = "http://10.0.10.137:8082/PPE4/public/api/produit";
                    // String url = "http://10.0.2.2:8082/PPE4/public/api/produit";

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    ListView listViewProduit = (ListView) findViewById(R.id.listView_Kdo);
                                    listViewProduit.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                                    JSONArray jsonArray = null;
                                    try {
                                        jsonArray = new JSONArray(response);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject item = jsonArray.getJSONObject(i);
                                            Produit produit = new Produit(
                                                    item.getInt("id"),
                                                    item.getString("marque"),
                                                    item.getString("libelle"),
                                                    item.getString("lienImage"),
                                                    Float.parseFloat(String.valueOf( item.getDouble("tarif") )),
                                                    item.getInt("stock"),
//                                        Float.parseFloat(String.valueOf(item.getDouble("note"))),
                                                    0.00f,
                                                    item.getJSONObject("idCategorie").getInt("id")
                                            );
                                            produits.add(produit);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //Remplissage de la listview
                                    arrayAdapter = new ArrayAdapter<Produit>(MainActivity.this, android.R.layout.simple_list_item_checked, produits);
                                    listViewProduit.setAdapter(arrayAdapter);

                                    //Si on veut obtenir une action lorsque l’on clique sur un élément de la listview !
                                    listViewProduit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            TextView tv = (TextView) view;
                                            Toast.makeText(MainActivity.this, tv.getText() + "  " + position, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }, new Response.ErrorListener() {        // CAS d’ERREUR
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                TextView textApiAll = (TextView) findViewById(R.id.text_api_all);
//                textApiAll.setText("Erreur, " + error.getMessage());
                        }
                    });
                    queue.add(stringRequest);

//                    Produit newProduit = new Produit(
//                            data.getExtras().getInt("id"),
//                            data.getExtras().getString("marque"),
//                            data.getExtras().getString("libelle"),
//                            data.getExtras().getString("lienImage"),
//                            data.getExtras().getFloat("tarif"),
//                            data.getExtras().getInt("stock"),
//                            data.getExtras().getFloat("note"),
//                            data.getExtras().getInt("idCategorie")
//                    );
//                    produits.add(newProduit);
//                    arrayAdapter.notifyDataSetChanged();
//                    toast = Toast.makeText(MainActivity.this, "Le produit à été ajouté" , duration );
//                    toast.show();

                } else if (data.getExtras().getInt("page") == 1){
                    Produit unProduit = produits.get(data.getExtras().getInt("position"));
                    unProduit.setLibelle(data.getExtras().getString("libelle"));
                    unProduit.setMarque(data.getExtras().getString("marque"));
                    unProduit.setTarif(data.getExtras().getFloat("tarif"));
                    unProduit.setStock(data.getExtras().getInt("stock"));
                    unProduit.setId_categorie(data.getExtras().getInt("idCategorie"));

                    arrayAdapter.notifyDataSetChanged();
                    toast = Toast.makeText(MainActivity.this, "Le produit à été modifié" , duration );
                    toast.show();
                }
            }
        }
}

