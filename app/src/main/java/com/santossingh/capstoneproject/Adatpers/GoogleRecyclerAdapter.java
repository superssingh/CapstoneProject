package com.santossingh.capstoneproject.Adatpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santossingh.capstoneproject.Fragments.GoogleFragment;
import com.santossingh.capstoneproject.Models.Google.Item;
import com.santossingh.capstoneproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Item} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class GoogleRecyclerAdapter extends RecyclerView.Adapter<GoogleRecyclerAdapter.ViewHolder> {

    private List<Item> itemList = new ArrayList<Item>();
    private GoogleFragment.OnFragmentInteractionListener mListener;
    private float AspectRatio = 0.73f;

    public GoogleRecyclerAdapter(GoogleFragment.OnFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mData = itemList.get(position);

        holder.textView.setText(holder.mData.getVolumeInfo().getTitle() == null ? "Unknown" : holder.mData.getVolumeInfo().getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFragmentInteraction(holder.mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<Item> getBooksList() {
        return itemList;
    }

    public void addList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private final TextView textView;
        public Item mData;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textView = (TextView) view.findViewById(R.id.book_Title);
        }
    }
}
