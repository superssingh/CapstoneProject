package com.santossingh.capstoneproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santossingh.capstoneproject.Adatpers.FavoriteRecyclerAdapter;
import com.santossingh.capstoneproject.Database.RealmLocalDB.FavoriteBooks;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.AutofitGridlayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */

public class FavoriteFragment extends Fragment {

    @BindView(R.id.Favorite_recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.Empty_List)
    TextView Empty_textView;
    private RealmResults<FavoriteBooks> booksList;
    private FavoriteRecyclerAdapter recyclerViewAdapter;
    private View view;
    private Realm realm;
    private OnFragmentInteractionListener mListener;

    public FavoriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        getFavoriteList();
        return view;
    }

    private void getFavoriteList() {
        realm = Realm.getDefaultInstance();
        booksList = realm.where(FavoriteBooks.class).findAll();
        if (booksList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            Snackbar.make(view, R.string.Favorite_empty_list, Snackbar.LENGTH_LONG).show();
        } else {
            Empty_textView.setVisibility(View.GONE);
            configRecycleView(booksList);
        }
    }

    private void configRecycleView(RealmResults<FavoriteBooks> results) {
        AutofitGridlayout layoutManager = new AutofitGridlayout(getActivity(), Integer.parseInt(getString(R.string.Image_Width)));
        recyclerViewAdapter = new FavoriteRecyclerAdapter(mListener, results);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
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
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(FavoriteBooks book);
    }
}
