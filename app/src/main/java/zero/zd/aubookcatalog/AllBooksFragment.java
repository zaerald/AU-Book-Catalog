package zero.zd.aubookcatalog;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zero.zd.aubookcatalog.adapter.BookGridViewAdapter;
import zero.zd.aubookcatalog.model.BookGridModel;

public class AllBooksFragment extends Fragment{

    private List<BookGridModel> bookGridList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_books, container, false);
        final GridView gridView = (GridView) view.findViewById(R.id.gridView);
        bookGridList = new ArrayList<>();

        // parse JSON result
        String JsonResult = getArguments().getString("result");

        if (JsonResult != null) {
            try {
                JSONObject jsonObject = new JSONObject(JsonResult);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalObject = jsonArray.getJSONObject(i);
                    BookGridModel bookGridModel = new BookGridModel();
                    bookGridModel.setBookId(finalObject.getLong("book_id"));
                    bookGridModel.setBookImage(finalObject.getString("book_img"));
                    bookGridModel.setBookTitle(finalObject.getString("book_title"));
                    bookGridModel.setBookType(finalObject.getString("type"));
                    bookGridList.add(bookGridModel);
                }

                BookGridViewAdapter adapter =
                        new BookGridViewAdapter(getActivity().getApplicationContext(),
                                R.layout.grid_book_layout, bookGridList);

                gridView.setAdapter(adapter);
            } catch (JSONException e) {
                Log.e("JSON ERR", e.getMessage());
                e.printStackTrace();
            }
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), BookInformationActivity.class);
                intent.putExtra("bookId", bookGridList.get(position).getBookId());
                startActivity(intent);
            }
        });

        return view;
    }

}
