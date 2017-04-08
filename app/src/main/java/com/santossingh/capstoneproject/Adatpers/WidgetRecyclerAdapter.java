package com.santossingh.capstoneproject.Adatpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santossingh.capstoneproject.Database.Firebase.TopBooks;
import com.santossingh.capstoneproject.Fragments.WidgetFragment;
import com.santossingh.capstoneproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WidgetRecyclerAdapter extends RecyclerView.Adapter<WidgetRecyclerAdapter.ViewHolder> {

    private List<TopBooks> booksList = new ArrayList<>();
    private WidgetFragment.OnFragmentInteractionListener listener;

    public WidgetRecyclerAdapter(WidgetFragment.OnFragmentInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.widget_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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

    public void setBooksList(List<TopBooks> booksList) {
        this.booksList = booksList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        @BindView(R.id.widgetImage)
        ImageView imageView;
        @BindView(R.id.widgetTitle)
        TextView title;
        private TopBooks book;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, mView);
        }
    }

}
