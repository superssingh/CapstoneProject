package com.santossingh.capstoneproject.Adatpers;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santossingh.capstoneproject.Fragments.FavoriteFragment;
import com.santossingh.capstoneproject.Models.Database.FavoriteBooks;
import com.santossingh.capstoneproject.R;
import com.santossingh.capstoneproject.Utilities.DynamicHeightNetworkImageView;
import com.santossingh.capstoneproject.Utilities.ImageLoaderHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {

    private RealmResults<FavoriteBooks> booksList;
    private FavoriteFragment.OnFragmentInteractionListener mListener;
    private float AspectRatio = 0.73f;

    public FavoriteRecyclerAdapter(FavoriteFragment.OnFragmentInteractionListener listener, RealmResults<FavoriteBooks> books) {
        mListener = listener;
        booksList = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.book = booksList.get(position);

        holder.dynamicImageView.setImageUrl((holder.book.getImage()),
                ImageLoaderHelper.getInstance(holder.dynamicImageView.getContext()).getImageLoader());
        holder.dynamicImageView.setAspectRatio(AspectRatio);
        holder.title.setText(holder.book.getTitle());

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

            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.Fav_BookTitle)
        TextView title;
        @BindView(R.id.Fav_delete)
        FloatingActionButton delete;
        @BindView(R.id.Fav_thumbnail)
        DynamicHeightNetworkImageView dynamicImageView;
        private FavoriteBooks book;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, mView);
        }
    }
}
