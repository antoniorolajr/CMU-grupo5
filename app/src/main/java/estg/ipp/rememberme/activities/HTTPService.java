package estg.ipp.rememberme.activities;

import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HTTPService extends AsyncTask<Void,Void,String> {

    private final String code;

    public HTTPService(String code) {
        this.code = code;
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        try {
           URL url= new URL("https://world.openfoodfacts.org/api/v0/product/"+this.code+".json");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","application.json");
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner( url.openStream());
            while (scanner.hasNext()){
                System.out.println(scanner.next());
                resposta.append(scanner.next());

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resposta.toString();
    }
}
