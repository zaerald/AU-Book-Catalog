package zero.zd.aubookcatalog;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        String bookPath = ZConstants.getInstance().getServer() + "dash_img/";
        String result = getArguments().getString("result");
        String image = "";
        // parse
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            int rand = (new Random().nextInt(jsonArray.length() - 1));
            JSONObject finalObject = jsonArray.getJSONObject(rand);
            image = finalObject.getString("dash_img");
        } catch (JSONException e) {
            Log.i("ERR", "Dash JSON ERRR: " + e.getMessage());
        }
        bookPath += image;

        ImageView imgView = (ImageView) view.findViewById(R.id.imgDash);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        ImageLoader.getInstance().displayImage(bookPath, imgView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.GONE);
            }
        });


        return view;
    }
}
