package com.example.android8;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private URL url;
    private HttpsURLConnection myConnection;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(v -> new Thread(() -> {
            try {
                url = new URL("https://random.dog/woof.json");
                myConnection = (HttpsURLConnection) url.openConnection();

                if (myConnection.getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, StandardCharsets.UTF_8);

                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginObject();

                    while (jsonReader.hasNext()) {
                        String key = jsonReader.nextName();

                        if (key.equals("url")) {
                            String value = jsonReader.nextString();
                            Log.d("RRR", "URL: " + value);
                            runOnUiThread(() -> Glide.with(MainActivity.this)
                                    .load(value)
                                    .into(imageView));

                        } else {
                            jsonReader.skipValue();
                        }
                    }

                    jsonReader.endObject();
                    jsonReader.close();

                } else {
                    Log.d("RRR", "FAILED TO ESTABLISH CONNECTION");
                }

            } catch (IOException e) {
                Log.e("RRR", "Error", e);
            }
        }).start());
    }
}

