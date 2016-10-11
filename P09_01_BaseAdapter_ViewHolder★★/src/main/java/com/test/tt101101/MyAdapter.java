package com.test.tt101101;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

   Context context;
   String data[];
   ArrayList<Student> myList;

   public MyAdapter(Context context, ArrayList<Student> myList) {
      this.context = context;
      this.myList = myList;
   }

   @Override
   public int getCount() {
      return myList.size();
   }

   @Override
   public Object getItem(int position) {
      return null;
   }

   @Override
   public long getItemId(int position) {
      return 0;
   }

   // ★★★★★
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = LayoutInflater.from(context);
      ViewHolder holder;
      if (convertView == null) {
         holder = new ViewHolder();
         convertView = inflater.inflate(R.layout.myitem, null);
         convertView.setTag(holder);
         holder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
         holder.tv2 = (TextView) convertView.findViewById(R.id.textView2);
      } else {
         holder = (ViewHolder) convertView.getTag();
      }

      holder.tv1.setText(myList.get(position).mName);
      holder.tv2.setText(myList.get(position).mTel);
      Log.d("ADAPTER", "position:" + position);
      return convertView;
   }

   static class ViewHolder {
      TextView tv1;
      TextView tv2;
   }

}
