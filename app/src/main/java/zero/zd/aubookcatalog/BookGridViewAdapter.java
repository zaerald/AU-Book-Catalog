package zero.zd.aubookcatalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class BookGridViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    // test
    private int[] resourceTest;

    BookGridViewAdapter(LayoutInflater inflater, int[] resourceTest) {
        this.inflater = inflater;
        this.resourceTest = resourceTest;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return resourceTest[position];
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
        ImageView imageView;
        TextView txtBookTitle;
        TextView txtBookType;
    }
}
