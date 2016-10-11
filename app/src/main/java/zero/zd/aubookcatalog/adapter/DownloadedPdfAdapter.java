package zero.zd.aubookcatalog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import zero.zd.aubookcatalog.R;

public class DownloadedPdfAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private List<String> pdfList;

    public DownloadedPdfAdapter(Context context, int resource, List<String> pdfList) {
        super(context, resource, pdfList);
        this.context = context;
        this.resource = resource;
        this.pdfList = pdfList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(pdfList.get(position));
        return convertView;
    }

    private class ViewHolder {
        private TextView txtName;
    }
}
