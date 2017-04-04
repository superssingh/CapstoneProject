package com.santossingh.capstoneproject.Adatpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.capstoneproject.Database.Firebase.TopBooks;
import com.santossingh.capstoneproject.Fragments.WidgetFragment;
import com.santossingh.capstoneproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */

public class WidgetRecyclerAdapter extends RecyclerView.Adapter<WidgetRecyclerAdapter.ViewHolder> {

    private List<TopBooks> booksList = new ArrayList<>();
    private WidgetFragment.OnFragmentInteractionListener listener;
    private DatabaseReference databaseReference;

    public WidgetRecyclerAdapter(WidgetFragment.OnFragmentInteractionListener listener) {
        this.listener = listener;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("topbooks");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TopBooks topBooks = dataSnapshot.getValue(TopBooks.class);
                topBooks.setKey(dataSnapshot.getKey());
                booksList.add(0, topBooks);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                booksList.clear();
                TopBooks topBooks = dataSnapshot.getValue(TopBooks.class);
                topBooks.setKey(dataSnapshot.getKey());
                booksList.add(0, topBooks);
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (booksList != null) {
                    booksList.clear();
                }
                TopBooks topBooks = dataSnapshot.getValue(TopBooks.class);
                topBooks.setKey(dataSnapshot.getKey());
                booksList.add(0, topBooks);
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.widget_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.book = booksList.get(position);
        Picasso.with(holder.mView.getContext()).load(holder.book.getImage())
                .fit()
                .error(R.mipmap.placeholder)
                .into(holder.imageView);
        holder.title.setText(holder.book.getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener) {
                    listener.onFragmentInteraction(holder.book);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.widgetImage)
        ImageView imageView;
        @BindView(R.id.widgetTitle)
        TextView title;
        View mView;
        private TopBooks book;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, mView);
        }
    }
}
