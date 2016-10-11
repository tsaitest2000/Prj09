package com.test.tt101102;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

   ListView mListView;
   MyDataHandler dataHandler;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      dataHandler = new MyDataHandler();
      mListView = (ListView) findViewById(R.id.listView);

      new Thread() {
         @Override
         public void run() {
            super.run();
            URL url = null;
            InputStream inputStream;
            try {
               url = new URL("http://udn.com/rssfeed/news/1");
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setRequestMethod("GET");
               conn.connect();
               inputStream = conn.getInputStream();
               ByteArrayOutputStream result = new ByteArrayOutputStream();
               byte[] buffer = new byte[1024];
               int length;
               while ((length = inputStream.read(buffer)) != -1) {
                  result.write(buffer, 0, length);
               }
               String str = result.toString("UTF-8");
               Log.d("NET", str);

               SAXParserFactory spf = SAXParserFactory.newInstance();
               SAXParser sp = spf.newSAXParser();
               XMLReader xr = sp.getXMLReader();
               xr.setContentHandler(dataHandler);
               xr.parse(new InputSource(new StringReader(str)));

               runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_list_item_1, dataHandler.XMLData);
                     mListView.setAdapter(adapter);
                     mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           Intent it = new Intent(MainActivity.this, DetailActivity.class);
                           it.putExtra("link", dataHandler.XMLLink.get(position));
                           startActivity(it);
                        }
                     });
                  }
               });
            } catch (MalformedURLException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            } catch (ParserConfigurationException e) {
               e.printStackTrace();
            } catch (SAXException e) {
               e.printStackTrace();
            }
         }
      }.start();
   }

}
