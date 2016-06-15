package com.example.chris.ddcomercial.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chris.ddcomercial.Adaptadores.AdaptadorDetalleCombo;
import com.example.chris.ddcomercial.Adaptadores.AdaptadorInicio;
import com.example.chris.ddcomercial.Clases.Combos;
import com.example.chris.ddcomercial.Clases.Conexion;
import com.example.chris.ddcomercial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 14/06/2016.
 */
public class FragmentoDetalle extends Fragment {

    private static final String EXTRA_ID = "IDMETA";
    private List<Combos> ListaCombos;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private String extra;
    private int requestCount = 1;

    public FragmentoDetalle() {
    }

    public static FragmentoDetalle createInstance(String idCombo) {

        FragmentoDetalle detailFragment = new FragmentoDetalle();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, idCombo);
        detailFragment.setArguments(bundle);
        return detailFragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_detalle, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.reciclador_detalle_combo);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        ListaCombos = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        //Calling method to get data to fetch data
        //initializing our adapter
        adapter = new AdaptadorDetalleCombo(ListaCombos, getActivity());

        recyclerView.setAdapter(adapter);

        extra = getArguments().getString(EXTRA_ID);
        getData();
        return v;
    }
    private JsonArrayRequest getDataFromServer(int requestCount) {



        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Conexion.DATA_GET_COMB + extra,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //If an error occurs that means end of the list has reached
                        Toast.makeText(getActivity(), "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            Combos superCombo = new Combos();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);
                //Adding data to the superhero object
                superCombo.setId_combo(json.getString(Conexion._ID));
                superCombo.setNom_comb(json.getString(Conexion._NOMBRE));
                superCombo.setDesc_comb(json.getString(Conexion._DESCRP));
                superCombo.setPrecio_comb(json.getString(Conexion._PRE));
                superCombo.setImg_comb(json.getString(Conexion._IMG));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            ListaCombos.add(superCombo);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }
}
