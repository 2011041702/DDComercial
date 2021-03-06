package com.example.chris.ddcomercial.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.chris.ddcomercial.Adaptadores.AdapterCerveza;
import com.example.chris.ddcomercial.Clases.Cervezas;
import com.example.chris.ddcomercial.Clases.Conexion;
import com.example.chris.ddcomercial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 15/06/2016.
 */
public class FragmentoCervezas extends Fragment implements AdapterCerveza.EscuchaEventosClick {

    private List<Cervezas> ListaCervezas;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;
    private SwipeRefreshLayout refreshLayout;
    private AdapterCerveza adapter;

    public FragmentoCervezas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_cervezas, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.reciclador_cervezas);


        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);


        ListaCervezas = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        getData();


        adapter = new AdapterCerveza(ListaCervezas, getActivity(), this);
        recyclerView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );
        return v;
    }


    private class HackingBackgroundTask extends AsyncTask<Void, Void, List<Cervezas>> {

        static final int DURACION = 1 * 1000; // 3 segundos de carga

        @Override
        protected List<Cervezas> doInBackground(Void... params) {
            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retornar en nuevos elementos para el adaptador
            return new ArrayList<Cervezas>(ListaCervezas);
        }

        @Override
        protected void onPostExecute(List<Cervezas> result) {
            super.onPostExecute(result);

            // Limpiar elementos antiguos
            adapter.clear();

            // Añadir elementos nuevos
            adapter.addAll(result);

            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }

    private JsonArrayRequest getDataFromServer(int requestCount) {

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Conexion.DATA_CERVEZAS,
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
            Cervezas superCervezas = new Cervezas();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                superCervezas.setId_marca(json.getString(Conexion._ID_MARCA));
                superCervezas.setNom_marca(json.getString(Conexion._NOMBRE_MARCA));
                superCervezas.setImg_marc(json.getString(Conexion._IMG_MARCA));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            ListaCervezas.add(superCervezas);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterCerveza.ViewHolder holder, int posicion) {

        String id = ListaCervezas.get(posicion).getId_marca().toString();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentManager.beginTransaction().replace(R.id.contenedor_principal, FragmentoListaCervezas.createInstance(id), "FragmentoListaCervezas").addToBackStack("tag").commit();
        //fragmentManager.beginTransaction().replace(R.id.contenedor_principal, fragment).addToBackStack("tag").commit();
        //Toast.makeText(getActivity(), ListaCervezas.get(posicion).getId_marca(), Toast.LENGTH_SHORT).show();
    }
}
