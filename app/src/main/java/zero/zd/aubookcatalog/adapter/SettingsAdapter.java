package zero.zd.aubookcatalog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
            viewHolder.headingLayout = (LinearLayout) convertView.findViewById(R.id.heading_layout);
            viewHolder.txtHeading = (TextView) convertView.findViewById(R.id.txtHeading);

            viewHolder.contentLayout = (RelativeLayout) convertView.findViewById(R.id.content_layout);
            viewHolder.imgView = (ImageView) convertView.findViewById(R.id.imgView);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            viewHolder.txtSubtitle = (TextView) convertView.findViewById(R.id.txtSubTitle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SettingsModel settingsModel = settingsModels.get(position);
        switch (settingsModel.getId()) {
            case SettingsModel.HEADING:
                viewHolder.headingLayout.setVisibility(View.VISIBLE);
                viewHolder.contentLayout.setVisibility(View.GONE);

                viewHolder.txtHeading.setText(settingsModel.getHeading());
                break;

            case SettingsModel.CONTENT:
                viewHolder.contentLayout.setVisibility(View.VISIBLE);
                viewHolder.headingLayout.setVisibility(View.GONE);

                viewHolder.imgView.setImageResource(settingsModel.getImgResource());
                viewHolder.txtTitle.setText(settingsModel.getTitle());
                viewHolder.txtSubtitle.setText(settingsModel.getSubtitle());
                break;
        }

        viewHolder.imgView.setImageResource(settingsModel.getImgResource());
        viewHolder.txtTitle.setText(settingsModel.getTitle());
        viewHolder.txtSubtitle.setText(settingsModel.getSubtitle());

        return convertView;
    }


    private class ViewHolder {
        private LinearLayout headingLayout;
        private TextView txtHeading;

        private RelativeLayout contentLayout;
        private ImageView imgView;
        private TextView txtTitle;
        private TextView txtSubtitle;
    }
}
