package com.test.tt101101;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   ListView mListView;
   String[] data;
   ArrayList<Student> myList;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      data = new String[]{"AA", "BBB", "CC22", "DD44", "LL", "FF1", "GG2", "HH3", "II4", "LL5"};
      myList = new ArrayList<>();
      myList.add(new Student("AAA", "111111"));
      myList.add(new Student("BBB", "222222"));
      myList.add(new Student("CCC", "333333"));
      myList.add(new Student("DDD", "444444"));

      mListView = (ListView) findViewById(R.id.listView);
      MyAdapter adapter = new MyAdapter(MainActivity.this, myList);
      mListView.setAdapter(adapter);
   }

}
