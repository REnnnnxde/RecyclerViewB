package com.briwen.recyclerviewb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

public class SearchMahasiswaActivity extends AppCompatActivity {

    private Button searchButton;
    private EditText nimEditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mahasiswa);

        nimEditText = findViewById(R.id.nimEditText);
        searchButton = findViewById(R.id.searchButton);
        resultTextView = findViewById(R.id.resultTextView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = nimEditText.getText().toString();

                try {
                    nim = URLEncoder.encode(nim, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(SearchMahasiswaActivity.this, "Encoding error", Toast.LENGTH_SHORT).show();
                    return; // Exit if encoding fails
                }

                String url = "https://stmikpontianak.net/011100862/cariMahasiswa.php?nim=" + nim;

                AsyncHttpClient ahc = new AsyncHttpClient();

                ahc.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        Log.d("Response", response);
                        resultTextView.setText(response);

                        new AlertDialog.Builder(SearchMahasiswaActivity.this)
                                .setTitle("Hasil Pencarian")
                                .setMessage(response)
                                .show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(SearchMahasiswaActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
