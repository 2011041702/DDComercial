package com.example.chris.ddcomercial.Fragment;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chris.ddcomercial.Adaptadores.AdapterCigarros;
import com.example.chris.ddcomercial.Adaptadores.AdapterListaCervezas;
import com.example.chris.ddcomercial.Clases.Cigarros;
import com.example.chris.ddcomercial.Clases.Conexion;
import com.example.chris.ddcomercial.Clases.ProductoCervezas;
import com.example.chris.ddcomercial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 27/06/2016.
 */
public class FragmentoListaCervezas extends Fragment implements RecyclerView.OnScrollChangeListener, AdapterListaCervezas.EscuchaEventosClick {

    private List<ProductoCervezas> ListaProductoCerveza;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;
    private String extra;

    private static final String EXTRA_ID = "IDMETA";

    public FragmentoListaCervezas() {
    }

    public static FragmentoListaCervezas createInstance(String idMeta) {
        FragmentoListaCervezas detailFragment = new FragmentoListaCervezas();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, idMeta);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_lista_cervezas, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.reciclador_lista_cerveza);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ListaProductoCerveza = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        extra = getArguments().getString(EXTRA_ID);
        getData();
        //adapter = new AdaptadorCervezas(ListaCervezas, getActivity());
        adapter = new AdapterListaCervezas(ListaProductoCerveza, getActivity(), this);
        recyclerView.setAdapter(adapter);
        return v;
    }

    private JsonArrayRequest getDataFromServer(int requestCount) {

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Conexion.DATA_PRODUCTOS + extra,
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
            ProductoCervezas superproductocervezas = new ProductoCervezas();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                superproductocervezas.setId_Producto(json.getString(Conexion.ID_PROD));
                superproductocervezas.setNombre_producto(json.getString(Conexion.NOMBRE_PROD));
                superproductocervezas.setPrecio_producto(json.getString(Conexion.PRECIO_PROD));
                superproductocervezas.setImg_producto(json.getString(Conexion.IMAGE_PROD));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            ListaProductoCerveza.add(superproductocervezas);
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
    public void onItemClick(AdapterListaCervezas.ViewHolder holder, int posicion) {

        //int a = holder.textPrecioProducto.getText().toString();
        String a = holder.textPrecioProducto.getText().toString();

        int aa = Integer.valueOf(a) * 3 ;

        int aaaa = 1;

        Toast.makeText(getActivity(), "hola " + aa , Toast.LENGTH_SHORT).show();


    }
}
