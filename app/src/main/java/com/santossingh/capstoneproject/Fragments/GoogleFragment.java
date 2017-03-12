package com.santossingh.capstoneproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.santossingh.capstoneproject.Adatpers.GoogleRecyclerAdapter;
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

    private static final String BOOKS_STATE = "books";
    @BindView(R.id.base_Progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    private DataManager dataManager;
    private List<Item> itemsList;
    private GoogleRecyclerAdapter recyclerViewAdapter;
    private View view;
    private OnFragmentInteractionListener mListener;
    private int menuItemPosition;


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
        outState.putParcelableArrayList(BOOKS_STATE, (ArrayList<? extends Parcelable>) itemsList);
        outState.putInt("menu_item", menuItemPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this, view);
        itemsList = new ArrayList<Item>();
        configRecycleView();

        if (savedInstanceState == null) {
            makeService("POPULAR");
            menuItemPosition = R.id.nav_amazon;
        } else {
            itemsList = savedInstanceState.getParcelableArrayList(BOOKS_STATE);
            menuItemPosition = savedInstanceState.getInt("menu_item");
            recyclerViewAdapter.addList(itemsList);
        }

        makeService("free");
        return view;
    }

    private void configRecycleView() {
        recyclerViewAdapter = new GoogleRecyclerAdapter(mListener);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        AutofitGridlayout autofitGridlayout = new AutofitGridlayout(getActivity(), 300);
        recyclerView.setLayoutManager(autofitGridlayout);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void makeService(String string) {
        progressBar.setVisibility(View.VISIBLE);
        dataManager = new DataManager();
        Call<BooksLibrary> listCall;

        if (string.equalsIgnoreCase("free")) {

        } else {

        }

        listCall = dataManager.getJSONData().getFreeTopBooks();
        listCall.enqueue(new Callback<BooksLibrary>() {
            @Override
            public void onResponse(Call<BooksLibrary> call, Response<BooksLibrary> response) {
                if (response.isSuccessful()) {
                    Item[] items = response.body().getItems();
                    itemsList = new ArrayList<Item>(Arrays.asList(items));
                    if (itemsList != null) {
                        recyclerViewAdapter.addList(itemsList);
                        Toast.makeText(getActivity(), "Successfully fetched", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), "Null Value", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BooksLibrary> call, Throwable t) {
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Item mData);
    }
}