package com.santossingh.capstoneproject.fragments;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.adatpers.GoogleRecyclerAdapter;
import com.santossingh.capstoneproject.google.models.BooksLibrary;
import com.santossingh.capstoneproject.google.models.Item;
import com.santossingh.capstoneproject.google.services.DataManager;
import com.santossingh.capstoneproject.utilities.AutofitGridlayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleFragment extends Fragment {

    @BindView(R.id.base_Progress_bar)
    LinearLayout progressBar;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    private List<Item> itemsList;
    private GoogleRecyclerAdapter recyclerViewAdapter;
    private View view;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.FREE_STATE), new ArrayList<>(recyclerViewAdapter.getBooksList()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this, view);
        itemsList = new ArrayList<>();
        configRecycleView();

        if (savedInstanceState == null) {
            makeService(String.valueOf(R.string.Business));
        } else {
            itemsList = savedInstanceState.getParcelableArrayList(getString(R.string.FREE_STATE));
            recyclerViewAdapter.addList(itemsList);
        }

        return view;
    }

    private void configRecycleView() {
        AutofitGridlayout autofitGridlayout = new AutofitGridlayout(getActivity(), Integer.parseInt(getString(R.string.Image_Width)));
        recyclerViewAdapter = new GoogleRecyclerAdapter(mListener);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(autofitGridlayout);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void makeService(String string) {
        progressBar.setVisibility(View.VISIBLE);
        DataManager dataManager = new DataManager();
        Call<BooksLibrary> listCall = null;

        if (string.equalsIgnoreCase(String.valueOf(R.string.Business))) {
            listCall = dataManager.getJSONData().getFreeBusinessBooks();
        } else if (string.equalsIgnoreCase(String.valueOf(R.string.Fiction))) {
            listCall = dataManager.getJSONData().getFreeFictionBooks();
        } else if (string.equalsIgnoreCase(String.valueOf(R.string.NonFiction))) {
            listCall = dataManager.getJSONData().getFreeNonFictionBooks();
        } else if (string.equalsIgnoreCase(String.valueOf(R.string.Fantasy))) {
            listCall = dataManager.getJSONData().getFreeFantasyBooks();
        } else if (string.equalsIgnoreCase(String.valueOf(R.string.Love))) {
            listCall = dataManager.getJSONData().getFreeLoveBooks();
        } else if (string.equalsIgnoreCase(String.valueOf(R.string.Adventure))) {
            listCall = dataManager.getJSONData().getFreeAdventureBooks();
        } else if (string.equalsIgnoreCase(String.valueOf(R.string.Comics))) {
            listCall = dataManager.getJSONData().getFreeComicsBooks();
        }

        //noinspection ConstantConditions
        listCall.enqueue(new Callback<BooksLibrary>() {
            @Override
            public void onResponse(Call<BooksLibrary> call, Response<BooksLibrary> response) {
                if (response.isSuccessful()) {
                    Item[] items = response.body().getItems();
                    itemsList = new ArrayList<>(Arrays.asList(items));
                    //noinspection ConstantConditions
                    if (itemsList != null) {
                        recyclerViewAdapter.addList(itemsList);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), R.string.NULL_Value, Toast.LENGTH_SHORT).show();
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
                    + getString(R.string.ImplementFragmentListener));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_google, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Business:
                item.setChecked(true);
                makeService(String.valueOf(R.string.Business));
                return true;

            case R.id.Fantasy:
                item.setChecked(true);
                makeService(String.valueOf(R.string.Fantasy));
                return true;

            case R.id.Fiction:
                item.setChecked(true);
                makeService(String.valueOf(R.string.Fiction));
                return true;

            case R.id.NonFiction:
                item.setChecked(true);
                makeService(String.valueOf(R.string.NonFiction));
                return true;

            case R.id.Love:
                item.setChecked(true);
                makeService(String.valueOf(R.string.Love));
                return true;

            case R.id.Adventure:
                item.setChecked(true);
                makeService(String.valueOf(R.string.Adventure));
                return true;

            case R.id.Comics:
                item.setChecked(true);
                makeService(String.valueOf(R.string.Comics));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Item mData);
    }
}