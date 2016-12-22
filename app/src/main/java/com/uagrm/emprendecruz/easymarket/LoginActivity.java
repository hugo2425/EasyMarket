package com.uagrm.emprendecruz.easymarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.uagrm.emprendecruz.easymarket.utils.Enviador;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Cosio on 20/10/2016.
 */
public class LoginActivity extends AppCompatActivity {

    EditText etUser, etPassword;
    Button btIngresar;
    String idEnviador;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_repartidor);

        etUser = (EditText)findViewById(R.id.et_user_login);
        etPassword = (EditText)findViewById(R.id.et_password_login);
        btIngresar = (Button)findViewById(R.id.bt_ingresar_login);
        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etUser.getText().toString().isEmpty()) {
                    if (!etPassword.getText().toString().isEmpty()) {
                        VerificarDatos(etUser.getText().toString(), etPassword.getText().toString());
                    }else {
                        Toast.makeText(getApplicationContext(), "Ingrese su Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Ingrese su Usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void VerificarDatos(String correo, String passw){

        final ProgressDialog progresdialog=new ProgressDialog(this);
        progresdialog.setTitle("Verificando Usuario ...");
        progresdialog.show();
        AsyncHttpClient client=new AsyncHttpClient();

        boolean b = false;
        //+"&contrasena="+passw+""

        client.get("http://54.201.162.73/EasyMarket2/enviador.php?correo="+correo, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Enviador enviador = new Enviador();
                if (statusCode==200){
                    progresdialog.dismiss();
                    try{
                        JSONArray jsonarray=new JSONArray(new String(responseBody));
                        if (jsonarray.isNull(0)){
                            Toast.makeText(getApplicationContext(),"Usuario o Contraseña Incorrecta" ,Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            enviador.setIdEnviador(jsonarray.getJSONObject(0).getString("idEnviador"));
                            idEnviador = jsonarray.getJSONObject(0).getString("idEnviador");
                            enviador.setNombre(jsonarray.getJSONObject(0).getString("nombre"));
                            enviador.setImgEnv(jsonarray.getJSONObject(0).getString("imgEnv"));
                            enviador.setCorreo(jsonarray.getJSONObject(0).getString("correo"));
                            enviador.setContrasena(jsonarray.getJSONObject(0).getString("contrasena"));
                            Toast.makeText(getApplicationContext(),"Sesion correcta" ,Toast.LENGTH_SHORT).show();
                            iniciarActividad();
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void iniciarActividad() {
        Intent i = new Intent(getApplicationContext(), ListPedidosActivity.class);
        i.putExtra("idEnviador", idEnviador);
        startActivity(i);
    }

}
