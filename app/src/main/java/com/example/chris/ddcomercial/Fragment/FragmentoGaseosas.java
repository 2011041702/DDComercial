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
import com.example.chris.ddcomercial.Adaptadores.AdapterGaseosa;
import com.example.chris.ddcomercial.Adaptadores.AdapterPisco;
import com.example.chris.ddcomercial.Clases.Conexion;
import com.example.chris.ddcomercial.Clases.Gaseosas;
import com.example.chris.ddcomercial.Clases.Pisco;
import com.example.chris.ddcomercial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Chris on 27/06/2016.
 */
public class FragmentoGaseosas extends Fragment implements RecyclerView.OnScrollChangeListener, AdapterGaseosa.EscuchaEventosClick {

    private List<Gaseosas> ListaGaseosas;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;

    public FragmentoGaseosas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_gaseosas, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.reciclador_gaseosas);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ListaGaseosas = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        getData();
        //adapter = new AdaptadorCervezas(ListaCervezas, getActivity());
        adapter = new AdapterGaseosa(ListaGaseosas, getActivity(), this);
        recyclerView.setAdapter(adapter);
        return v;
    }

    private JsonArrayRequest getDataFromServer(int requestCount) {

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Conexion.DATA_GASEOSAS,
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
            Gaseosas superGaseosas = new Gaseosas();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                superGaseosas.setId_marca(json.getString(Conexion._ID_MARCA));
                superGaseosas.setNom_marca(json.getString(Conexion._NOMBRE_MARCA));
                superGaseosas.setImg_marc(json.getString(Conexion._IMG_MARCA));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            ListaGaseosas.add(superGaseosas);
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

    @Override
    public void onItemClick(AdapterGaseosa.ViewHolder holder, int posicion) {

    }
}
