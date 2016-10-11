package zero.zd.aubookcatalog;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;


public class ReadBookFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // delete test
        File f = new File(ZHelper.getInstance().getPdf().getAbsolutePath() + "/Head First Android Development.pdf");
        Log.i("NFO", "File: " + f);
        if (f.delete()) {
            Log.i("NFO", "deleted");
        } else {
            Log.i("NFO", "sad");
        }


        return inflater.inflate(R.layout.fragment_read_book, container, false);
    }
}
