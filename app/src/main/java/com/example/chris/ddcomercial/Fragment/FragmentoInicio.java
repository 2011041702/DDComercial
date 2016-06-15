package com.example.chris.ddcomercial.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.example.chris.ddcomercial.Adaptadores.AdaptadorInicio;
import com.example.chris.ddcomercial.Clases.Combos;
import com.example.chris.ddcomercial.Clases.Conexion;
import com.example.chris.ddcomercial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class FragmentoInicio extends Fragment implements RecyclerView.OnScrollChangeListener {

    private List<Combos> ListaCombos;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;
    private FloatingActionButton fab;

    public FragmentoInicio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_inicio, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.reciclador);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hola Mundo", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        ListaCombos = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        //Calling method to get data to fetch data
        getData();
        //initializing our adapter
        adapter = new AdaptadorInicio(ListaCombos, getActivity());

        recyclerView.setAdapter(adapter);

        return v;
    }
    private JsonArrayRequest getDataFromServer(int requestCount) {



        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Conexion.DATA_URL,
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
                superCombo.setId_combo(json.getString(Conexion.TAG_ID));
                superCombo.setNom_comb(json.getString(Conexion.TAG_NOMBRE));
                superCombo.setDesc_comb(json.getString(Conexion.TAG_DESCRP));
                superCombo.setPrecio_comb(json.getString(Conexion.TAG_PRE));
                superCombo.setImg_comb(json.getString(Conexion.TAG_IMG));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            ListaCombos.add(superCombo);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }

    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (isLastItemDisplaying(recyclerView)) {
            getData();
        }
    }

}
