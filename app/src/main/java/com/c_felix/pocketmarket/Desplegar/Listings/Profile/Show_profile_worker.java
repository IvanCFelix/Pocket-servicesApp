package com.c_felix.pocketmarket.Desplegar.Listings.Profile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c_felix.pocketmarket.Desplegar.Listings.Show_list_request;
import com.c_felix.pocketmarket.R;
import com.c_felix.pocketmarket.Utilidades.Uris;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Show_profile_worker extends AppCompatActivity {
    TextView txt_phone, txt_email, txt_description, txt_adress, txt_name, txt_job, txt_average;
    ImageView img_profile;
    Integer workerID;
    private RequestQueue queue;
    String num_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile_worker);
        txt_phone = findViewById(R.id.txt_phone);
        txt_email = findViewById(R.id.txt_email);
        txt_description = findViewById(R.id.txt_description);
        txt_adress = findViewById(R.id.txt_adress);
        txt_name = findViewById(R.id.txt_name);
        txt_job = findViewById(R.id.txt_job);
        txt_average = findViewById(R.id.txt_average);
        img_profile = findViewById(R.id.img_profile);
        workerID = getIntent().getIntExtra("id_worker", 0);
        queue = Volley.newRequestQueue(Show_profile_worker.this);
        getWorker();

        txt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + num_phone));
                Show_profile_worker.this.startActivity(intent);
            }
        });
    }

    private void getWorker() {
        try {
            String url = Uris.API_ENDPOINT + "/worker/" + workerID;
            System.out.println("url:" + url);
            final JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("Data worker:" + response.toString());
                            try {
                                Picasso.get().load(Uris.IMAGES_ENDPOINT + response.getString("image")).into(img_profile);
                                txt_name.setText(response.getString("fullName"));
                                JSONArray jobs = response.getJSONArray("jobs");
                                txt_job.setText(jobs.getJSONObject(0).getString("name"));
                                txt_description.setText(response.getString("description"));
                                txt_email.setText(response.getString("email"));
                                num_phone = response.getString("phone");
                                SpannableString numberphone = new SpannableString(num_phone);
                                numberphone.setSpan(new UnderlineSpan(), 0, numberphone.length(), 0);
                                txt_phone.setText(numberphone);
                                txt_adress.setText(response.getString("address"));
                                setTitle(txt_name.getText());
                            } catch (Exception e) {
                                Log.e("Error", "onResponse: ", e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            System.out.println(error.toString());
                        }
                    });
            queue.add(request);

        } catch (Exception e) {
            Log.e("TAG", "getMyListing: ", e);
        }
    }
}