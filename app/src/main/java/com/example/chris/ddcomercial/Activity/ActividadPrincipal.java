package com.example.chris.ddcomercial.Activity;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.chris.ddcomercial.Fragment.FragmentoCervezas;
import com.example.chris.ddcomercial.Fragment.FragmentoCigarros;
import com.example.chris.ddcomercial.Fragment.FragmentoEnergizante;
import com.example.chris.ddcomercial.Fragment.FragmentoGaseosas;
import com.example.chris.ddcomercial.Fragment.FragmentoInicio;
import com.example.chris.ddcomercial.Fragment.FragmentoPisco;
import com.example.chris.ddcomercial.Fragment.FragmentoRon;
import com.example.chris.ddcomercial.Fragment.FragmentoSnacks;
import com.example.chris.ddcomercial.Fragment.FragmentoVinos;
import com.example.chris.ddcomercial.Fragment.FragmentoVodka;
import com.example.chris.ddcomercial.Fragment.FragmentoWhisky;
import com.example.chris.ddcomercial.R;


public class ActividadPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SearchView mSearchView;
    private MenuItem searchMenuItem;
    private SearchView.OnQueryTextListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoInicio();
                break;
            case R.id.nav_Cervezas:
                fragmentoGenerico = new FragmentoCervezas();
                break;
            case R.id.nav_Ron:
                fragmentoGenerico = new FragmentoRon();
                break;
            case R.id.nav_Vinos:
                fragmentoGenerico = new FragmentoVinos();
                break;
            case R.id.nav_Whisky:
                fragmentoGenerico = new FragmentoWhisky();
                break;
            case R.id.nav_Vodkas:
                fragmentoGenerico = new FragmentoVodka();
                break;
            case R.id.nav_Pisco:
                fragmentoGenerico = new FragmentoPisco();
                break;
            case R.id.nav_Energizante:
                fragmentoGenerico = new FragmentoEnergizante();
                break;
            case R.id.nav_Gaseosas:
                fragmentoGenerico = new FragmentoGaseosas();
                break;
            case R.id.nav_Snacks:
                fragmentoGenerico = new FragmentoSnacks();
                break;
            case R.id.nav_Cigarros:
                fragmentoGenerico = new FragmentoCigarros();
                break;
           /* case R.id.item_configuracion:
                startActivity(new Intent(this, ActividadConfiguracion.class));
                break;*/
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
