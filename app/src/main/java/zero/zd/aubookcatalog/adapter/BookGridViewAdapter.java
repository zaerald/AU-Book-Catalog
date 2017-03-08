package zero.zd.aubookcatalog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
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

    private Context mContext;
    private int mResource;
    private List<BookGridModel> mBookList;

    public BookGridViewAdapter(Context context, int resource, List<BookGridModel> bookList) {
        super(context, resource, bookList);
        this.mContext = context;
        this.mBookList = bookList;
        this.mResource = resource;
    }

    @Override
    public int getCount() {
        return mBookList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mBookList.get(position).getBookId();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            viewHolder.txtBookTitle = (TextView) convertView.findViewById(R.id.txtBookTitle);
            viewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.txtAuthor);
            viewHolder.txtBookType = (TextView) convertView.findViewById(R.id.txtBookType);

            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();


        final ViewHolder holder = viewHolder;
        ImageLoader.getInstance().displayImage(mBookList.get(position).getBookImage(),
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
        viewHolder.txtBookTitle.setText(mBookList.get(position).getBookTitle());
        viewHolder.txtAuthor.setText(mBookList.get(position).getAuthor());
        viewHolder.txtBookType.setText(mBookList.get(position).getBookType());

        return convertView;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private ProgressBar progressBar;
        private TextView txtBookTitle;
        private TextView txtAuthor;
        private TextView txtBookType;

    }
}
