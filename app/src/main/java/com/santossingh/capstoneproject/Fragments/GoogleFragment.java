package com.santossingh.capstoneproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.santossingh.capstoneproject.Adatpers.GoogleRecyclerAdapter;
import com.santossingh.capstoneproject.Models.Amazon.Constants;
import com.santossingh.capstoneproject.Models.Google.BooksLibrary;
import com.santossingh.capstoneproject.Models.Google.Item;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Services.DataManager;
import com.santossingh.capstoneproject.Utilities.AutofitGridlayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoogleFragment extends Fragment {

    private static final String BOOKS_STATE = "free_books";
    @BindView(R.id.base_Progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    private DataManager dataManager;
    private List<Item> itemsList;
    private GoogleRecyclerAdapter recyclerViewAdapter;
    private View view;
    private OnFragmentInteractionListener mListener;
    private int menuPosition;

    public GoogleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BOOKS_STATE, new ArrayList<Item>(recyclerViewAdapter.getBooksList()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this, view);
        itemsList = new ArrayList<Item>();
        configRecycleView();

        if (savedInstanceState == null) {
            makeService(Constants.Business);
        } else {
            itemsList = savedInstanceState.getParcelableArrayList(BOOKS_STATE);
            recyclerViewAdapter.addList(itemsList);
        }

        return view;
    }

    private void configRecycleView() {
        AutofitGridlayout autofitGridlayout = new AutofitGridlayout(getActivity(), 330);
        recyclerViewAdapter = new GoogleRecyclerAdapter(mListener);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(autofitGridlayout);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void makeService(String string) {
        progressBar.setVisibility(View.VISIBLE);
        dataManager = new DataManager();
        Call<BooksLibrary> listCall = null;

        if (string.equalsIgnoreCase(Constants.Business)) {
            listCall = dataManager.getJSONData().getFreeBusinessBooks();
        } else if (string.equalsIgnoreCase(Constants.Fiction)) {
            listCall = dataManager.getJSONData().getFreeFictionBooks();
        } else if (string.equalsIgnoreCase(Constants.NonFiction)) {
            listCall = dataManager.getJSONData().getFreeNonFictionBooks();
        } else if (string.equalsIgnoreCase(Constants.Fantasy)) {
            listCall = dataManager.getJSONData().getFreeFantasyBooks();
        } else if (string.equalsIgnoreCase(Constants.Romance)) {
            listCall = dataManager.getJSONData().getFreeRomanceBooks();
        }

        listCall.enqueue(new Callback<BooksLibrary>() {
            @Override
            public void onResponse(Call<BooksLibrary> call, Response<BooksLibrary> response) {
                if (response.isSuccessful()) {
                    Item[] items = response.body().getItems();
                    itemsList = new ArrayList<Item>(Arrays.asList(items));
                    if (itemsList != null) {
                        recyclerViewAdapter.addList(itemsList);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Null Value", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BooksLibrary> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
        inflater.inflate(R.menu.menu_category, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Business:
                item.setChecked(true);
                menuPosition = R.id.Business;
                makeService(Constants.Business);
                return true;

            case R.id.Fantasy:
                item.setChecked(true);
                menuPosition = R.id.Fantasy;
                makeService(Constants.Fantasy);
                return true;

            case R.id.Fiction:
                item.setChecked(true);
                menuPosition = R.id.Fiction;
                makeService(Constants.Fiction);
                return true;

            case R.id.NonFiction:
                item.setChecked(true);
                menuPosition = R.id.NonFiction;
                makeService(Constants.NonFiction);
                return true;

            case R.id.Romance:
                item.setChecked(true);
                menuPosition = R.id.Romance;
                makeService(Constants.Romance);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Item mData);
    }
}