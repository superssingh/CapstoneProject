package com.santossingh.capstoneproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.capstoneproject.Adatpers.WidgetRecyclerAdapter;
import com.santossingh.capstoneproject.Database.Firebase.TopBooks;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.AutofitGridlayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WidgetFragment extends Fragment {

    @BindView(R.id.WidgetRecycleView)
    RecyclerView recyclerView;
    WidgetRecyclerAdapter recycleAdapter;
    private OnFragmentInteractionListener mListener;
    private List<TopBooks> topBooksList;

    public WidgetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widget, container, false);
        ButterKnife.bind(this, view);
        topBooksList = new ArrayList<>();
        initFirebaseDatabase();

        configRecycleView();
        return view;
    }

    private void initFirebaseDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(getString(R.string.TopBooksTag));
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TopBooks topBook = dataSnapshot.getValue(TopBooks.class);
                topBook.setKey(dataSnapshot.getKey());
                topBooksList.add(0, topBook);
                recycleAdapter.setBooksList(topBooksList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (topBooksList != null) topBooksList.clear();

                TopBooks topBook = dataSnapshot.getValue(TopBooks.class);
                topBook.setKey(dataSnapshot.getKey());
                topBooksList.add(0, topBook);
                recycleAdapter.setBooksList(topBooksList);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void configRecycleView() {
        AutofitGridlayout autofitGridlayout = new AutofitGridlayout(getActivity(), Integer.parseInt(getString(R.string.Image_Width)));
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
                    + R.string.ImplementFragmentListener);
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
