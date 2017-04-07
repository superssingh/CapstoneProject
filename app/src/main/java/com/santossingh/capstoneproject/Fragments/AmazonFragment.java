package com.santossingh.capstoneproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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

import com.santossingh.capstoneproject.Adatpers.AmazonRecyclerAdapter;
import com.santossingh.capstoneproject.Amazon.AsyncTask.AsyncResponse;
import com.santossingh.capstoneproject.Amazon.AsyncTask.MyAsyncTask;
import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.AutofitGridlayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AmazonFragment extends Fragment implements AsyncResponse {

    private static final String STATE_BOOKS = "paid_books";
    private static final String MENU_POSITION = "menu_item";
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
    int searchID = 0;
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
        outState.putParcelableArrayList(STATE_BOOKS, new ArrayList<>(recyclerViewAdapter.getBooksList()));
        outState.putInt(MENU_POSITION, menuPosition);
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
        itemsList = new ArrayList<>();
        configRecycleView();

        if (savedInstanceState == null) {
            startQueryTask(getString(R.string.Business));
            menuPosition = R.id.Business;
        } else {
            progressBar.setVisibility(View.GONE);
            itemsList = savedInstanceState.getParcelableArrayList(STATE_BOOKS);
            menuPosition = savedInstanceState.getInt(MENU_POSITION);
            recyclerViewAdapter.addList(itemsList);
        }

        return view;
    }

    private void configRecycleView() {
        AutofitGridlayout autofitGridlayout = new AutofitGridlayout(getActivity(), Integer.parseInt(getString(R.string.Image_Width)));
        recyclerViewAdapter = new AmazonRecyclerAdapter(mListener);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(autofitGridlayout);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_amazon, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_search_bar:
                searchID = 1;
                searchbarQuery();
                return true;

            case R.id.Business:
                item.setChecked(true);
                menuPosition = R.id.Business;
                searchID = 1;
                startQueryTask(getString(R.string.Business));
                return true;

            case R.id.Fantasy:
                item.setChecked(true);
                menuPosition = R.id.Fantasy;
                searchID = 1;
                startQueryTask(getString(R.string.Fantasy));
                return true;

            case R.id.Fiction:
                item.setChecked(true);
                menuPosition = R.id.Fiction;
                searchID = 1;
                startQueryTask(getString(R.string.Fiction));
                return true;

            case R.id.NonFiction:
                item.setChecked(true);
                menuPosition = R.id.NonFiction;
                searchID = 1;
                startQueryTask(getString(R.string.NonFiction));
                return true;

            case R.id.Love:
                item.setChecked(true);
                menuPosition = R.id.Love;
                searchID = 1;
                startQueryTask(getString(R.string.Love));
                return true;

            case R.id.Comics:
                item.setChecked(true);
                menuPosition = R.id.Comics;
                searchID = 1;
                startQueryTask(getString(R.string.Comics));
                return true;

            case R.id.Adventure:
                item.setChecked(true);
                menuPosition = R.id.Adventure;
                searchID = 1;
                startQueryTask(getString(R.string.Adventure));
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
                    startQueryTask(inputName.getText().toString());
                    inputName.setText("");
                } else {
                    Snackbar.make(view, R.string.FillSearchBox, Snackbar.LENGTH_LONG).show();
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

    @Override
    public void processFinish(List<AmazonBook> result) {
        progressBar.setVisibility(View.GONE);
        if (!result.equals(null)) {
            itemsList = result;
            recyclerView.setVisibility(View.VISIBLE);
            recyclerViewAdapter.addList(itemsList);
            mListener.onFragmentInteraction(result.get(0));
        } else if (result.equals(null) && searchID == 0) {
            startQueryTask(getString(R.string.Business));
        } else if (result.equals(null) && searchID == 1) {
            recyclerView.setVisibility(View.GONE);
            Snackbar.make(view, R.string.Retry, Snackbar.LENGTH_LONG).show();
        }
    }

    public void startQueryTask(String query) {
        if (query != null) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            MyAsyncTask newTask = new MyAsyncTask(this);
            newTask.execute(query);
        }
    }

    public void runTask(final String query) {
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startQueryTask(query);
            }
        }.start();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(AmazonBook book);
        void onTabletIntraction(AmazonBook book);
    }

}