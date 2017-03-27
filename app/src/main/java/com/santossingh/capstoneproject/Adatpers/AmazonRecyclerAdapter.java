package com.santossingh.capstoneproject.Adatpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santossingh.capstoneproject.Fragments.AmazonFragment;
import com.santossingh.capstoneproject.Models.Amazon.AmazonBook;
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

public class AmazonRecyclerAdapter extends RecyclerView.Adapter<AmazonRecyclerAdapter.ViewHolder> {

    private List<AmazonBook> booksList = new ArrayList<>();
    private AmazonFragment.OnFragmentInteractionListener mListener;

    public AmazonRecyclerAdapter(AmazonFragment.OnFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.amazon_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.book = booksList.get(position);
        Picasso.with(holder.mView.getContext()).load(holder.book.getImage())
                .fit()
                .into(holder.imageView);
        holder.title.setText(holder.book.getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentInteraction(holder.book);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public List<AmazonBook> getBooksList() {
        return booksList;
    }

    public void addList(List<AmazonBook> booksList) {
        this.booksList = booksList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.AWS_thumbnail)
        ImageView imageView;
        @BindView(R.id.AWS_BookTitle)
        TextView title;
        private AmazonBook book;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, mView);
        }
    }
}
