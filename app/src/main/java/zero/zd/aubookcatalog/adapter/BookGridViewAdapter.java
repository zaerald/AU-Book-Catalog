package zero.zd.aubookcatalog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
        return bookList.get(position).getBookId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            viewHolder.txtBookTitle = (TextView) convertView.findViewById(R.id.txtBookTitle);
            viewHolder.txtBookType = (TextView) convertView.findViewById(R.id.txtBookType);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ViewHolder holder = viewHolder;
        ImageLoader.getInstance().displayImage(bookList.get(position).getBookImage(),
                viewHolder.imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        viewHolder.txtBookTitle.setText(bookList.get(position).getBookTitle());
        viewHolder.txtBookType.setText(bookList.get(position).getBookType());

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private ProgressBar progressBar;
        private TextView txtBookTitle;
        private TextView txtBookType;

    }
}
