package vn.com.lap.sqlite2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MyArrayAdapter extends ArrayAdapter<Author> {
    private Activity context;
    private int lauoutId;
    private ArrayList<Author> arrayList;

    public MyArrayAdapter(@NonNull Activity context, int resource, ArrayList<Author> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.lauoutId = resource;
        this.arrayList = arrayList;
    }

    public ArrayList<Author> getArrayList() {
        return arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(lauoutId, null);

        final TextView tv_id = convertView.findViewById(R.id.textView_Id);
        final TextView tv_name = convertView.findViewById(R.id.textView_Name);
        final TextView tv_address = convertView.findViewById(R.id.textView_Address);
        final TextView tv_email = convertView.findViewById(R.id.textView_Email);

        Author author = arrayList.get(position);

        tv_id.setText(author.getId() + "");
        tv_name.setText(author.getName());
        tv_address.setText(author.getAddress());
        tv_email.setText(author.getEmail());

        return convertView;
    }
}
