package com.uagrm.emprendecruz.easymarket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.uagrm.emprendecruz.easymarket.utils.Detalle;
import com.uagrm.emprendecruz.easymarket.utils.Pedido;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Cosio on 17/10/2016.
 */
public class DetallePedidoActivity extends AppCompatActivity{

    String idCliente, idPedido, latitude, longitude, imgCli;
    TextView nombre, telefono, direccion, tvmonto;
    ImageView imageView;
    Button btPagar;

    private ListView listView;

    ArrayList<Detalle> detalles = new ArrayList<Detalle>();
    Detalle[] productos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        imageView = (ImageView)findViewById(R.id.iv_ver_ruta);
        nombre = (TextView)findViewById(R.id.tv_name_client_detalle);
        telefono = (TextView)findViewById(R.id.tv_phone_client_detalle);
        direccion = (TextView)findViewById(R.id.tv_address_client_detalle);
        tvmonto = (TextView)findViewById(R.id.tv_monto_ultimate);

        Intent i = getIntent();
        idPedido = i.getStringExtra("idPedido");
        latitude = i.getStringExtra("latitud");
        longitude = i.getStringExtra("longitud");
        idCliente = i.getStringExtra("idCliente");
        imgCli = i.getStringExtra("imgCli");

        nombre.setText("Nombre: " + i.getStringExtra("nombre"));
        direccion.setText("Dir: " +i.getStringExtra("direccion"));
        telefono.setText("Telf: "+i.getStringExtra("telefono"));
       // tvfecha.setText(i.getStringExtra("fecha"));
        tvmonto.setText("TOTAL A PAGAR: " + i.getStringExtra("monto") + " BS." );

        listView = (ListView) findViewById(R.id.lvListaDetalle);
        descargarDetalle();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
            }
        });

        btPagar = (Button)findViewById(R.id.bt_pagar_pedido);
        btPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagarPedido();
            }
        });

    }

    void descargarDetalle(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando el Pedido...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        String urlClientes = "http://54.201.162.73/EasyMarket2/detalle.php?id=" + idPedido;
        cliente.get(urlClientes, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0; i<jsonArray.length(); i++){
                            detalles.add(i, new Detalle(
                                    jsonArray.getJSONObject(i).getString("descripcion"),
                                    jsonArray.getJSONObject(i).getString("precio"),
                                    jsonArray.getJSONObject(i).getString("cantidad")));
                        }
                        productos = new Detalle[detalles.size()];
                        transferir();
                        DetalleAdapter detalleAdapter = new DetalleAdapter(getApplicationContext(), productos);
                        listView.setAdapter(detalleAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    private void pagarPedido() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Pagando el Pedido...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        String urlClientes = "http://54.201.162.73/EasyMarket/pagar.php?idPedido=" + idPedido + "$idCliente=" + idCliente;
        cliente.get(urlClientes, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    progressDialog.dismiss();
                    try {
                        Toast.makeText(getApplicationContext(), "Pedido Pagado con Exito", Toast.LENGTH_SHORT).show();
                        btPagar.setClickable(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    private class DetalleAdapter extends ArrayAdapter<Detalle> {
        public DetalleAdapter(Context context, Detalle[] datos) {
            super(context, R.layout.detalle_item , datos);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.detalle_item, null);

            TextView descripcion = (TextView)item.findViewById(R.id.tv_descripcion_detalle);
            descripcion.setText(productos[position].getDescripcion());
            TextView precio = (TextView)item.findViewById(R.id.tv_precio_detalle);
            precio.setText(productos[position].getPrecio() + " Bs.");
            TextView cantidad = (TextView)item.findViewById(R.id.tv_cantidad_detalle);
            cantidad.setText(productos[position].getCantidad() + " Unidades");
            return(item);
        }
    }

    private void transferir(){
        for (int i = 0; i < detalles.size(); i++){
            productos[i] = detalles.get(i);
        }
    }
}

