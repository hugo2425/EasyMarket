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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.uagrm.emprendecruz.easymarket.utils.Pedido;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Cosio on 17/10/2016.
 */
public class ListPedidosActivity extends AppCompatActivity{

    private ListView listView;

    ArrayList<Pedido> misPedidos = new ArrayList<Pedido>();
    Pedido[] pedidos;

    String idEnviador;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pedidos);

        Intent i = getIntent();
        idEnviador = i.getStringExtra("idEnviador");

        listView = (ListView) findViewById(R.id.listPedidos);
        descargarPedidos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetallePedidoActivity.class);
                intent.putExtra("idPedido", misPedidos.get(i).getIdPedido().toString());
                intent.putExtra("latitud", misPedidos.get(i).getLatitud().toString());
                intent.putExtra("longitud", misPedidos.get(i).getLongitud().toString());
                intent.putExtra("monto", misPedidos.get(i).getMonto().toString());
                intent.putExtra("fecha", misPedidos.get(i).getFecha().toString());
                intent.putExtra("idCliente", misPedidos.get(i).getIdCliente().toString());
                intent.putExtra("nombre", misPedidos.get(i).getNombre().toString());
                intent.putExtra("direccion", misPedidos.get(i).getDireccion().toString());
                intent.putExtra("telefono", misPedidos.get(i).getTelefono().toString());
                intent.putExtra("imgCli", misPedidos.get(i).getImgCli().toString());
                startActivity(intent);
            }
        });
    }

    private void transferir(){
        for (int i = 0; i < misPedidos.size(); i++){
            pedidos[i] = misPedidos.get(i);
        }
    }

    private void descargarPedidos(){
        misPedidos.clear();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando Clientes...");
        progressDialog.show();

        AsyncHttpClient cliente = new AsyncHttpClient();
        String urlClientes = "http://54.201.162.73/EasyMarket2/clientes.php?idEnviador=" + idEnviador;
        cliente.get(urlClientes, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0; i<jsonArray.length(); i++){
                            misPedidos.add(i, new Pedido(
                                    jsonArray.getJSONObject(i).getString("idPedido"),
                                    jsonArray.getJSONObject(i).getString("latitud"),
                                    jsonArray.getJSONObject(i).getString("longitud"),
                                    jsonArray.getJSONObject(i).getString("monto"),
                                    jsonArray.getJSONObject(i).getString("monto"),
                                    jsonArray.getJSONObject(i).getString("idCliente"),
                                    jsonArray.getJSONObject(i).getString("nombre"),
                                    jsonArray.getJSONObject(i).getString("direccion"),
                                    jsonArray.getJSONObject(i).getString("telefono"),
                                    jsonArray.getJSONObject(i).getString("imgCli")
                                    ));
                        }
                        pedidos = new Pedido[misPedidos.size()];
                        transferir();
                        PedidoAdapter pedidoAdapter = new PedidoAdapter(getApplicationContext(), pedidos);
                        listView.setAdapter(pedidoAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    //Adaptador de Pedidos
    class PedidoAdapter extends ArrayAdapter<Pedido> {
        public PedidoAdapter(Context context, Pedido[] datos) {
            super(context, R.layout.pedido_item , datos);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.pedido_item, null);
            SmartImageView smartImageView = (SmartImageView) item.findViewById(R.id.iv_foto_cliente);
            String urlImages = "http://54.201.162.73/supermercado/images/usuario/" + pedidos[position].getImgCli().toString();
            Rect rectangulo = new Rect(smartImageView.getLeft(), smartImageView.getTop(), smartImageView.getRight(), smartImageView.getBottom());
            smartImageView.setImageUrl(urlImages, rectangulo);
            TextView nombreCli = (TextView)item.findViewById(R.id.tv_name_client);
            nombreCli.setText(pedidos[position].getNombre());
            TextView telefonoCli = (TextView)item.findViewById(R.id.tv_phone_client);
            telefonoCli.setText(pedidos[position].getTelefono());
            TextView direccionCli = (TextView)item.findViewById(R.id.tv_address_client);
            direccionCli.setText(pedidos[position].getDireccion());
            return(item);
        }
    }

}


