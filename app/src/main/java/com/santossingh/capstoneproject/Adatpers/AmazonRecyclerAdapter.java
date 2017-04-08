package com.santossingh.capstoneproject.Adatpers;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.santossingh.capstoneproject.Amazon.Models.AmazonBook;
import com.santossingh.capstoneproject.Fragments.AmazonFragment;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.AnimationUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AmazonRecyclerAdapter extends RecyclerView.Adapter<AmazonRecyclerAdapter.ViewHolder> {

    int preposition;
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

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.book = booksList.get(position);
        holder.title.setText(holder.book.getTitle());
        Picasso.with(holder.mView.getContext()).load(holder.book.getImage())
                .fit().error(R.mipmap.placeholder).into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Toast.makeText(v.getContext(), String.valueOf(R.string.into) + " " + holder.book.getTitle(), Toast.LENGTH_LONG).show();
                    mListener.onFragmentInteraction(holder.book);
                }
            }
        });

        if (position > preposition) {
            AnimationUtil.animate(holder, true);
        } else {
            AnimationUtil.animate(holder, false);
        }
        preposition = position;
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
