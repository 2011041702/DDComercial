package com.example.chris.ddcomercial.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.chris.ddcomercial.Clases.Conexion;
import com.example.chris.ddcomercial.Fragment.FragmentoDetalle;
import com.example.chris.ddcomercial.R;

public class ActividadDetalleCombo extends AppCompatActivity {

    private String idCombo;
    public static void launch(Activity activity, String idMeta) {
        Intent intent = getLaunchIntent(activity, idMeta);
        activity.startActivityForResult(intent, Conexion.CODIGO_DETALLE);
    }
    public static Intent getLaunchIntent(Context context, String idCombo) {
        Intent intent = new Intent(context, ActividadDetalleCombo.class);
        intent.putExtra("IDEXTRA",idCombo);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detallecombo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        if (getIntent().getStringExtra(Conexion.EXTRA_ID) != null)
            idCombo = getIntent().getStringExtra(Conexion.EXTRA_ID);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_principal_detalle, FragmentoDetalle.createInstance(idCombo), "FragmentoDetalle").commit();
        }
    }
}
