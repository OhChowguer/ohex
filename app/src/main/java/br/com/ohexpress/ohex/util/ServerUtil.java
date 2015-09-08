package br.com.ohexpress.ohex.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import br.com.ohexpress.ohex.LojaActivity;
import br.com.ohexpress.ohex.MyApplication;
import br.com.ohexpress.ohex.interfaces.UserService;
import br.com.ohexpress.ohex.model.Usuario;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rafaelchowman on 07/09/15.
 */
public class ServerUtil{




    public void getUser(final Usuario user, final Context context){


        RestAdapter restAdapterPedido = new RestAdapter.Builder().setEndpoint(Constant.SERVER_URL).build();

        UserService userService = restAdapterPedido.create(UserService.class);

        userService.getuser(user.getToken(),
                new Callback<Usuario>() {


                    @Override
                    public void success(Usuario usuario, Response response) {

                        ((MyApplication) context.getApplicationContext()).setUser(usuario);



                        //Toast.makeText(context, user.getToken(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        //Toast.makeText(context, "Deu errado" + error, Toast.LENGTH_LONG).show();


                    }
                }

        );
    }
}
