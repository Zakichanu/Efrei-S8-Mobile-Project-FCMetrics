package fr.android.fcmetrics.modules;

import static kong.unirest.MimeTypes.JSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Controller {

    public static String getUser(String email, String password){
        String jsonBodyReq = "{\"mail\":\"" + email + "\",\"password\":\"" + password + "\"}";

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(jsonBodyReq, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("http://10.0.2.2:3000/user")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
