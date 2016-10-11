package com.test.tt101103;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   ArrayList<Map<String, String>> data;
   ListView lv;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      data = new ArrayList<>();
      lv = (ListView) findViewById(R.id.listView);

      RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
      final StringRequest request = new StringRequest("http://data.ntpc.gov.tw/od/data/api/BF90FA7E-C358-4CDA-B579-B6C84ADC96A1?$format=json",
         new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Log.d("NET", response);
               try {
                  JSONArray array = new JSONArray(response);
                  for (int i = 0; i < array.length(); i++) {
                     JSONObject obj = array.getJSONObject(i);
                     Map m = new HashMap();
                     m.put("district", obj.getString("district"));
                     m.put("address", obj.getString("address"));
                     data.add(m);
                  }
                  SimpleAdapter adapter = new SimpleAdapter(
                     MainActivity.this,
                     data,
                     android.R.layout.simple_list_item_2,
                     new String[]{"district", "address"},
                     new int[]{android.R.id.text1, android.R.id.text2}
                  );
                  lv.setAdapter(adapter);
               } catch (JSONException e) {
                  e.printStackTrace();
               }
            }
         }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {

         }
      });
      queue.add(request);
      queue.start();
   }

}
