package com.example.kdonoel;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AjoutKdo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_kdo);
        Spinner spinCategorie =  findViewById(R.id.addProduit_Categorie);
        //le contexte est l'instance (this) de l'activity APIActivity
        RequestQueue queue = Volley.newRequestQueue(this);

        //url du service à consommer
        String url = "http://10.0.10.137:8082/PPE4/public/api/categorie";
        // String url = "http://10.0.2.2/PPE4/public/api/categorie";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Categorie> categories = new ArrayList<>();
                        ArrayAdapter<Categorie> adapter;
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Categorie uneCategorie = new Categorie(
                                        item.getInt("id"),
                                        item.getString("libelle"),
                                        item.getString("descriptif")
                                );
                                categories.add(uneCategorie);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Remplissage de la listview
                        adapter = new ArrayAdapter<Categorie>(AjoutKdo.this, android.R.layout.simple_spinner_item, categories);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinCategorie.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {        // CAS d’ERREUR
            @Override
            public void onErrorResponse(VolleyError error) {
//                TextView textApiAll = (TextView) findViewById(R.id.text_api_all);
//                textApiAll.setText("Erreur, " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_ajoutKdo_valider){

            int duration = Toast.LENGTH_SHORT;
            Toast toast;

            try {
                EditText etLibelle = findViewById(R.id.addProduit_Libelle);
                EditText etMarque = findViewById(R.id.addProduit_Marque);
                EditText etTarif = findViewById(R.id.addProduit_Tarif);
                EditText etStock = findViewById(R.id.addProduit_Stock);
                Spinner spinCategorie =  findViewById(R.id.addProduit_Categorie);

                if(String.valueOf(etLibelle.getText()).length()>0 && String.valueOf(etMarque.getText()).length()>0 && String.valueOf(etTarif.getText()).length()>0 && String.valueOf(etStock.getText()).length()>0){
                    Intent iMainActivity = this.getIntent();
                    Bundle monBundle = new Bundle();

                    String libelleProduit = String.valueOf(etLibelle.getText());
                    String marqueProduit = String.valueOf(etMarque.getText());
                    Float tarifProduit = Float.parseFloat(String.valueOf(etTarif.getText()));
                    Integer stockProduit = Integer.parseInt(etStock.getText().toString());

                    String postUrl = "http://10.0.10.137:8082/PPE4/public/api/produit/add";
                    // String postUrl = "http://10.0.2.2/PPE4/public/api/produit/add";
                    RequestQueue requestQueue = Volley.newRequestQueue(this);

                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("libelle", libelleProduit);
                        postData.put("marque", marqueProduit);
                        postData.put("tarif", tarifProduit);
                        postData.put("stock", stockProduit);
                        postData.put("idCategorie", ((Categorie) spinCategorie.getSelectedItem()).getId());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    requestQueue.add(jsonObjectRequest);

                    monBundle.putInt("page", 0);
//                    monBundle.putString("libelle", libelleProduit);
//                    monBundle.putString("marque", marqueProduit);
//                    monBundle.putFloat("tarif", tarifProduit);
//                    monBundle.putInt("stock", stockProduit);
//                    monBundle.putInt("idCategorie", ((Categorie) spinCategorie.getSelectedItem()).getId());
                    iMainActivity.putExtras(monBundle);
                    setResult(Activity.RESULT_OK, iMainActivity);
                    this.finish();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                toast = Toast.makeText(AjoutKdo.this, "Veuillez remplir tous les champs obligatoires" , duration );
                toast.show();
            }
        }
    }
}