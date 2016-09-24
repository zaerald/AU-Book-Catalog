package zero.zd.aubookcatalog.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zero.zd.aubookcatalog.R;
import zero.zd.aubookcatalog.model.BookGridModel;

public class BookGridViewAdapter extends ArrayAdapter {

    Context context;
    private int resource;
    private List<BookGridModel> bookList;

    public BookGridViewAdapter(Context context, int resource, List<BookGridModel> bookList) {
        super(context, resource, bookList);
        this.context = context;
        this.bookList = bookList;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.txtBookTitle = (TextView) convertView.findViewById(R.id.txtBookTitle);
            viewHolder.txtBookType = (TextView) convertView.findViewById(R.id.txtBookType);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(R.drawable.book_dummy);
        viewHolder.txtBookTitle.setText(bookList.get(position).getBookTitle());
        viewHolder.txtBookType.setText(bookList.get(position).getBookType());

        Log.i("NFO", "Book TYPE: " + bookList.get(0).getBookType());

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView txtBookTitle;
        private TextView txtBookType;
    }
}
