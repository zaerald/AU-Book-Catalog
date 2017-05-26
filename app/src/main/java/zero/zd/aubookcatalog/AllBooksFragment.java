package zero.zd.aubookcatalog;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import zero.zd.aubookcatalog.adapter.BookGridViewAdapter;
import zero.zd.aubookcatalog.model.BookGridModel;

import static android.content.Context.MODE_PRIVATE;

public class AllBooksFragment extends Fragment {

    View view;
    private List<BookGridModel> mGridModelList;
    private GridView mGridView;
    private SharedPreferences mPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_books, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridView);

        mPreferences = getActivity().getSharedPreferences(ZHelper.PREFS, MODE_PRIVATE);

        // parse JSON result
        String JsonResult = getArguments().getString("result");

        parseBookResult(JsonResult);

        // search bar
        final View viewClone = view;
        final EditText etSearch = (EditText) view.findViewById(R.id.etSearch);
        final ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
        btnClose.setVisibility(View.GONE);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //Toast.makeText(getActivity().getApplicationContext(), "IP used: " +  mPreferences.getString("serverIp", ZHelper.SERVER_IP), Toast.LENGTH_SHORT).show();
                    String keyword = textView.getText().toString();
                    View v = viewClone.findViewById(R.id.fragment_all_books_layout);
                    new GetBookTask(v).execute(keyword);
                    return true;
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSearch.getText().length() > 0)
                    btnClose.setVisibility(View.VISIBLE);
                else
                    btnClose.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });

        return view;
    }

    private void parseBookResult(String JsonResult) {
        mGridModelList = new ArrayList<>();

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
                    bookGridModel.setAuthor(finalObject.getString("author"));
                    bookGridModel.setBookType(finalObject.getString("type"));
                    mGridModelList.add(bookGridModel);
                }

                BookGridViewAdapter adapter =
                        new BookGridViewAdapter(getActivity().getApplicationContext(),
                                R.layout.grid_book_layout, mGridModelList);

                mGridView.setAdapter(adapter);
            } catch (JSONException e) {
                Log.e("JSON ERR", e.getMessage());
                e.printStackTrace();
            }
        }

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mGridModelList.isEmpty()) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), BookInformationActivity.class);
                    intent.putExtra("bookId", mGridModelList.get(position).getBookId());
                    intent.putExtra("bookType", mGridModelList.get(position).getBookType());
                    startActivity(intent);
                }
            }
        });
    }

    private class GetBookTask extends AsyncTask<String, Object, String> {

        Dialog mLoadingDialog;
        View view;

        GetBookTask(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(getActivity(), "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String server = "http://" + mPreferences.getString("serverIp", ZHelper.SERVER_IP) +
                    "/aubookcatalog/getbooksearch.php";

            String keyword = strings[0];

            try {

                URL url = new URL(server);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, ZHelper.DB_ENCODE_TYPE));

                String postData =
                        URLEncoder.encode("keyword", ZHelper.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(keyword, ZHelper.DB_ENCODE_TYPE);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZHelper.DB_ENCODE_TYPE));

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

            } catch (IOException e) {
                Log.e("ERR", "Error in getting book search: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mLoadingDialog.dismiss();

            if (result == null) {
                Snackbar.make(view, "Please make sure that you are connected to the Internet.",
                        Snackbar.LENGTH_SHORT).show();
                return;
            }

            // if result length = 2, there is no result
            if (result.length() == 2) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity())
                        .setMessage("No Results Found.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
                return;
            }

            parseBookResult(result);
            view.invalidate();
        }
    }


}
