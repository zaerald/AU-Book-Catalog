package zero.zd.aubookcatalog;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class AllBooksFragment extends Fragment{

    int[] resourceTest = new int[]{R.drawable.book_dummy, R.drawable.book_dummy, R.drawable.book_dummy, R.drawable.book_dummy, R.drawable.book_dummy};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_books, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        gridView.setAdapter(new BookGridViewAdapter(inflater, resourceTest));

        return view;
    }
}
