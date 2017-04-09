package com.santossingh.capstoneproject.adatpers;

import android.annotation.SuppressLint;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.database.realm_database.FavoriteBooks;
import com.santossingh.capstoneproject.fragments.FavoriteFragment;
import com.santossingh.capstoneproject.utilities.AnimationUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {

    private final FavoriteFragment.OnFragmentInteractionListener mListener;
    private RealmResults<FavoriteBooks> booksList;
    private int preposition;

    public FavoriteRecyclerAdapter(FavoriteFragment.OnFragmentInteractionListener mListener, RealmResults<FavoriteBooks> books) {
        this.mListener = mListener;
        booksList = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rcView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(rcView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.book = booksList.get(position);
        holder.title.setText(holder.book.getTitle());
        Picasso.with(holder.mView.getContext()).load(holder.book.getImage())
                .fit()
                .placeholder(R.mipmap.book_cover)
                .into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentInteraction(holder.book);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(holder.getAdapterPosition());
                booksList.size();
                if (booksList.size() == 0) {
                    Snackbar.make(view, R.string.Favorite_empty_list, Snackbar.LENGTH_LONG).show();
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

    private void delete(int position) {
        Realm realm = Realm.getDefaultInstance();
        booksList = realm.where(FavoriteBooks.class).findAll();
        realm.beginTransaction();
        FavoriteBooks fm = booksList.get(position);
        fm.deleteFromRealm();
        realm.commitTransaction();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        @BindView(R.id.Fav_BookTitle)
        TextView title;
        @BindView(R.id.Fav_delete)
        ImageButton delete;
        @BindView(R.id.Fav_thumbnail)
        ImageView imageView;
        private FavoriteBooks book;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, mView);
        }
    }
}
