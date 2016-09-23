package zero.zd.aubookcatalog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zero.zd.aubookcatalog.R;

public class BookGridViewAdapter extends ArrayAdapter {

    private LayoutInflater inflater;

    public BookGridViewAdapter(Context context, int resource, List objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.grid_book_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.txtBookTitle = (TextView) convertView.findViewById(R.id.txtBookTitle);
            viewHolder.txtBookType = (TextView) convertView.findViewById(R.id.txtBookType);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(R.drawable.book_dummy);
        viewHolder.txtBookTitle.setText("SOOOOOO LOOOONGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
        viewHolder.txtBookType.setText("PDF");

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView txtBookTitle;
        private TextView txtBookType;
    }
}
