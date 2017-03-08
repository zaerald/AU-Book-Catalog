package zero.zd.aubookcatalog;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zero.zd.aubookcatalog.adapter.DownloadedPdfAdapter;


public class ReadBookFragment extends Fragment {

    private List<String> mPdfList;
    private DownloadedPdfAdapter mDownloadedPdfAdapter;
    private TextView mTxtInfo;
    private TextView mTxtNone;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_read_book, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        mTxtInfo = (TextView) view.findViewById(R.id.txtInfo);
        mTxtNone = (TextView) view.findViewById(R.id.txtNone);
        initDownloadedPdf();

        updateList();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String title = mPdfList.get(position);

                File file = ZHelper.getInstance().getPdf();
                File pdf = new File(file.getAbsolutePath() + "/" + title + ".pdf");
                Log.i("NFO", "PATH: " + file);
                Log.i("NFO", "PDF: " + pdf);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(pdf), "application/pdf");
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String title = mPdfList.get(position);
                File f = new File(ZHelper.getInstance().getPdf().getAbsolutePath() + "/" + title + ".pdf");
                Log.i("NFO", "File: " + f);
                if (f.delete()) {
                    Log.i("NFO", "deleted");
                } else {
                    Log.i("NFO", "failed deletion");
                }
                Toast.makeText(getActivity().getApplicationContext(), "PDF Deleted", Toast.LENGTH_SHORT).show();
                initDownloadedPdf();
                updateList();
                mDownloadedPdfAdapter.notifyDataSetChanged();
                return true;
            }
        });

        return view;
    }
    private void initDownloadedPdf() {
        mPdfList = new ArrayList<>();

        File f = new File(ZHelper.getInstance().getPdf().getAbsolutePath());
        File files[] = f.listFiles();

        if (files.length != 0) {
            for (File file : files) {
                String name = file.getName();
                name = name.substring(0, name.length() - 4);
                mPdfList.add(name);
            }
        }

    }

    private void updateList() {
        if(!mPdfList.isEmpty()) {
            mDownloadedPdfAdapter = new DownloadedPdfAdapter(getActivity().getApplicationContext(),
                    R.layout.downloaded_pdf_layout, mPdfList);
            mListView.setAdapter(mDownloadedPdfAdapter);
            mTxtNone.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mTxtInfo.setVisibility(View.VISIBLE);
        } else {
            mTxtNone.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            mTxtInfo.setVisibility(View.GONE);
        }
    }
}
