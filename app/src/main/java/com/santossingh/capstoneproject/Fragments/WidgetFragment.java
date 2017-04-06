package com.santossingh.capstoneproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santossingh.capstoneproject.Adatpers.WidgetRecyclerAdapter;
import com.santossingh.capstoneproject.Database.Firebase.TopBooks;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.AutofitGridlayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WidgetFragment extends Fragment {

    @BindView(R.id.WidgetRecycleView)
    RecyclerView recyclerView;
    WidgetRecyclerAdapter recycleAdapter;
    private View view;
    private OnFragmentInteractionListener mListener;

    public WidgetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_widget, container, false);
        ButterKnife.bind(this, view);
        configRecycleView();
        return view;
    }

    private void configRecycleView() {
        AutofitGridlayout autofitGridlayout = new AutofitGridlayout(getActivity(), Integer.getInteger(getString(R.string.Image_Width)));
        recycleAdapter = new WidgetRecyclerAdapter(mListener);
        recyclerView.setLayoutManager(autofitGridlayout);
        recyclerView.setAdapter(recycleAdapter);
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
        void onFragmentInteraction(TopBooks book);
    }
}
