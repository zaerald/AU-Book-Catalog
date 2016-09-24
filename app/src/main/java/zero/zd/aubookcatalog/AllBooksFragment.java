package zero.zd.aubookcatalog;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zero.zd.aubookcatalog.adapter.BookGridViewAdapter;
import zero.zd.aubookcatalog.model.BookGridModel;

public class AllBooksFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_books, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        String JsonResult = getArguments().getString("result");

        try {
            JSONObject jsonObject = new JSONObject(JsonResult);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            List<BookGridModel> bookGridList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject finalObject = jsonArray.getJSONObject(i);
                BookGridModel bookGridModel = new BookGridModel();
                bookGridModel.setBookImage(finalObject.getString("bookImage"));
                bookGridModel.setBookTitle(finalObject.getString("title"));
                bookGridModel.setBookType(finalObject.getString("type"));
                bookGridList.add(bookGridModel);
            }

            BookGridViewAdapter adapter =
                    new BookGridViewAdapter(getActivity().getApplicationContext(),
                            R.layout.grid_book_layout, bookGridList);

            gridView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

}
