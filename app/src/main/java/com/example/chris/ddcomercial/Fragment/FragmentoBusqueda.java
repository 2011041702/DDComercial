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
import com.example.chris.ddcomercial.Adaptadores.AdapterBusqueda;
import com.example.chris.ddcomercial.Clases.Busqueda;
import com.example.chris.ddcomercial.Clases.Conexion;
import com.example.chris.ddcomercial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 01/07/2016.
 */
public class FragmentoBusqueda extends Fragment implements RecyclerView.OnScrollChangeListener, AdapterBusqueda.EscuchaEventosClick {

    private List<Busqueda> ListaBusqueda;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;
    private String extra;

    private static final String EXTRA_ID = "IDMETA";

    public FragmentoBusqueda() {
    }

    public static FragmentoBusqueda createInstance(String idMeta) {
        FragmentoBusqueda detailFragment = new FragmentoBusqueda();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, idMeta);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_busqueda, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.reciclador_busqueda);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ListaBusqueda = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        extra = getArguments().getString(EXTRA_ID);
        getData();
        //adapter = new AdaptadorCervezas(ListaCervezas, getActivity());
        adapter = new AdapterBusqueda(ListaBusqueda, getActivity(), this);
        recyclerView.setAdapter(adapter);
        return v;
    }

    private JsonArrayRequest getDataFromServer(int requestCount) {

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Conexion.DATA_BUSQUEDA + extra,
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
            Busqueda busqueda = new Busqueda();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                busqueda.setId_Producto(json.getString(Conexion.ID_PROD));
                busqueda.setNombre_producto(json.getString(Conexion.NOMBRE_PROD));
                busqueda.setPrecio_producto(json.getString(Conexion.PRECIO_PROD));
                busqueda.setImg_producto(json.getString(Conexion.IMAGE_PROD));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            ListaBusqueda.add(busqueda);
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
    public void onItemClick(AdapterBusqueda.ViewHolder holder, int posicion) {

    }
}
