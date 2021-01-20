package com.olea.aritz.covidtrackingeuskadi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElement> towns;
    private LayoutInflater inflater;
    private Context context;
    private ListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListElement item);
    }

    public ListAdapter(List<ListElement> townItems, Context context, ListAdapter.OnItemClickListener
     listener) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.towns = townItems;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return towns.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.town_element, null);

        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(towns.get(position));
    }

    public void setItems(List<ListElement> townItems) {
        towns = townItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView backgroundElement;
        TextView town, incidence;
        ImageView incidenceCircle;

        ViewHolder(View itemView) {
            super(itemView);

            backgroundElement = itemView.findViewById(R.id.backgroundElement);
            town = itemView.findViewById(R.id.townText);
            incidence = itemView.findViewById(R.id.incidenceText);
            incidenceCircle = itemView.findViewById(R.id.incidenceCircle);
        }

        void bindData(final ListElement item) {
            if (towns.indexOf(item) % 2 == 0)
                backgroundElement.setCardBackgroundColor(Color.parseColor("#F5F5F5"));
            else
                backgroundElement.setCardBackgroundColor(Color.parseColor("#E2F2FF"));

            town.setText(item.getTown());
            incidence.setText(String.valueOf(item.getIncidence()));
            incidenceCircle.setColorFilter(item.getColor(), PorterDuff.Mode.SRC_IN);

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

}
