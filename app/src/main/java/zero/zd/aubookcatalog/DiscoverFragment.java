package zero.zd.aubookcatalog;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class DiscoverFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ImageView btnDiscover = (ImageView) view.findViewById(R.id.btnDiscover);
        TextView txtView = (TextView) view.findViewById(R.id.txtView);
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDiscover();
            }
        });

        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDiscover();
            }
        });
        return view;
    }

    private void loadDiscover() {
        Intent intent = new Intent(getActivity().getApplicationContext(), BookInformationActivity.class);
        long[] bookId = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        String[] bookType = {"Book", "Book", "Book", "Book", "Book", "Book" ,"Book" ,"Book" ,"Book" ,"Book",
                "PDF", "PDF", "PDF", "PDF", "PDF"};

        Random rand = new Random();
        int r  = rand.nextInt(14) + 1;

        // id
        intent.putExtra("bookId", bookId[r]);
        // type
        intent.putExtra("bookType", bookType[r]);
        startActivity(intent);
    }
}
