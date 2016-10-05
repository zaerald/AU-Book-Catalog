package zero.zd.aubookcatalog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zero.zd.aubookcatalog.R;
import zero.zd.aubookcatalog.model.SettingsModel;

public class SettingsAdapter extends ArrayAdapter<SettingsModel> {

    private Context context;
    private List<SettingsModel> settingsModels;

    public SettingsAdapter(Context context, int resource, List<SettingsModel> settingsModels) {
        super(context, resource, settingsModels);
        this.context = context;
        this.settingsModels = settingsModels;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.settings_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imgView = (ImageView) convertView.findViewById(R.id.imgView);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            viewHolder.txtSubtitle = (TextView) convertView.findViewById(R.id.txtSubTitle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SettingsModel settingsModel = settingsModels.get(position);
        viewHolder.imgView.setImageResource(settingsModel.getImgResource());
        viewHolder.txtTitle.setText(settingsModel.getTitle());
        viewHolder.txtSubtitle.setText(settingsModel.getSubtitle());

        return convertView;
    }

    private class ViewHolder {
        private ImageView imgView;
        private TextView txtTitle;
        private TextView txtSubtitle;
    }
}
