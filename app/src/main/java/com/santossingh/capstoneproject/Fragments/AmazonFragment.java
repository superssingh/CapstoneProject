package com.santossingh.capstoneproject.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.santossingh.capstoneproject.AWS.AWS_URL;
import com.santossingh.capstoneproject.AWS.MyXmlPullParser;
import com.santossingh.capstoneproject.Adatpers.AmazonRecyclerAdapter;
import com.santossingh.capstoneproject.Models.Amazon.AmazonBook;
import com.santossingh.capstoneproject.Models.Amazon.Constants;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.AutofitGridlayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AmazonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */

public class AmazonFragment extends Fragment {

    private static final String STATE_BOOKS = "paid_books";
    @BindView(R.id.AWS_recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.Progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;
    @BindView(R.id.BTN_Cancel)
    ImageButton search_cancel;
    @BindView(R.id.BTN_search)
    ImageButton searchButton;
    @BindView(R.id.input_name)
    EditText inputName;
    int menuPosition;
    private List<AmazonBook> itemsList;
    private AmazonRecyclerAdapter recyclerViewAdapter;
    private View view;
    private OnFragmentInteractionListener mListener;

    public AmazonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_BOOKS,
                new ArrayList<AmazonBook>(recyclerViewAdapter.getBooksList()));
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_amazon, container, false);
        ButterKnife.bind(this, view);
        itemsList = new ArrayList<AmazonBook>();
        configRecycleView();

        if (savedInstanceState != null) {
            progressBar.setVisibility(View.GONE);
            itemsList = savedInstanceState.getParcelableArrayList(STATE_BOOKS);
            recyclerViewAdapter.addList(itemsList);
        } else {
            AWSAsyncTask searchQuery = new AWSAsyncTask();
            searchQuery.execute(Constants.Business);
            menuPosition = R.id.Business;
        }

        return view;
    }

    private void configRecycleView() {
        AutofitGridlayout autofitGridlayout = new AutofitGridlayout(getActivity(), 300);
        recyclerViewAdapter = new AmazonRecyclerAdapter(mListener);
        recyclerView.setLayoutManager(autofitGridlayout);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private InputStream downloadUrl(String urls) throws IOException {
        URL url = new URL(urls);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_amazon, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AWSAsyncTask searchQuery = new AWSAsyncTask();
        switch (item.getItemId()) {

            case R.id.my_search_bar:
                searchbarQuery();
                return true;

            case R.id.Business:
                item.setChecked(true);
                menuPosition = R.id.Business;
                searchQuery.execute(Constants.Business);
                return true;

            case R.id.Fantasy:
                item.setChecked(true);
                menuPosition = R.id.Fantasy;
                searchQuery.execute(Constants.Fantasy);
                return true;

            case R.id.Fiction:
                item.setChecked(true);
                menuPosition = R.id.Fiction;
                searchQuery.execute(Constants.Fiction);
                return true;

            case R.id.NonFiction:
                item.setChecked(true);
                menuPosition = R.id.NonFiction;
                searchQuery.execute(Constants.NonFiction);
                return true;

            case R.id.Romance:
                item.setChecked(true);
                menuPosition = R.id.Romance;
                searchQuery.execute(Constants.Romance);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchbarQuery() {
        searchLayout.setVisibility(View.VISIBLE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputName.getText().toString().equals("")) {
                    searchLayout.setVisibility(View.GONE);
                    AWSAsyncTask searchQuery = new AWSAsyncTask();
                    searchQuery.execute(inputName.getText().toString());
                    inputName.setText("");
                } else {
                    Snackbar.make(view, "Please enter your desire book name", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchLayout.setVisibility(View.GONE);
            }
        });

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(AmazonBook book);
        void onTabletIntraction(AmazonBook book);
    }

    public class AWSAsyncTask extends AsyncTask<String, Void, List<AmazonBook>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            new CountDownTimer(4000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                }
            }.start();
        }

        @Override
        protected List<AmazonBook> doInBackground(String... urls) {
            String url = new AWS_URL().getURLByCategory(urls[0]);
            Log.i("Link", url);
            List<AmazonBook> booksList = new ArrayList<>();
            try {
                MyXmlPullParser myXmlPullParser = new MyXmlPullParser();
                InputStream is = downloadUrl(url);
                booksList = myXmlPullParser.parse(is);
                return booksList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return booksList;
        }

        @Override
        protected void onPostExecute(List<AmazonBook> amazonBooksList) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            itemsList = amazonBooksList;
            recyclerViewAdapter.addList(itemsList);
            mListener.onTabletIntraction(itemsList.get(0));
        }
    }


}