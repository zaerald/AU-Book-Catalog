package zero.zd.aubookcatalog;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import zero.zd.aubookcatalog.adapter.BookGridViewAdapter;
import zero.zd.aubookcatalog.model.BookGridModel;

public class AllBooksFragment extends Fragment{

    private List<BookGridModel> bookGridList;
    private GridView gridView;

    private String serverIp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_books, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        bookGridList = new ArrayList<>();

        serverIp = getArguments().getString("serverIp");

        // parse JSON result
        String JsonResult = getArguments().getString("result");
        parseBookResult(JsonResult);

        // search bar
        final View viewClone = view;
        EditText etSearch = (EditText) view.findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(getActivity().getApplicationContext(), "Will add search", Toast.LENGTH_SHORT).show();
                    bookGridList = new ArrayList<>();

                    View v = viewClone.findViewById(R.id.fragment_all_books_layout);
                    new GetBookTask(v).execute();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    private void parseBookResult(String JsonResult)  {
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
                intent.putExtra("bookType", bookGridList.get(position).getBookType());
                startActivity(intent);
            }
        });
    }

    private class GetBookTask extends AsyncTask<Object, Object, String> {

        //Dialog mLoadingDialog;
        View view;

        GetBookTask(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //mLoadingDialog = ProgressDialog.show(getActivity().getApplicationContext(), "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(Object... strings) {
            String server = "http://" + serverIp + "/aubookcatalog/";
            String getBook = server + "getbook.php";
            ZConstants.getInstance().setServer(server);

            try {

                URL url = new URL(getBook);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);



                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZConstants.DB_ENCODE_TYPE));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.i("NFO", "no err");

                return builder.toString();

            } catch(IOException e) {
                Log.e("ERR", "Error in getting book: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //mLoadingDialog.dismiss();

            if (result == null) {
                Snackbar.make(view, "Please make sure that you are connected to the Internet.",
                        Snackbar.LENGTH_LONG).show();
            }

            FragmentManager fragmentManager = getFragmentManager();
            AllBooksFragment allBooksFragment = new AllBooksFragment();
            Bundle args = new Bundle();
            args.putString("result", result);
            allBooksFragment.setArguments(args);
            fragmentManager.beginTransaction()
                    .replace(R.id.rootView, allBooksFragment).commit();
        }
    }


}
