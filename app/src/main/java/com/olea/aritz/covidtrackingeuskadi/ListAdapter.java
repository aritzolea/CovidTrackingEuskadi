package com.olea.aritz.covidtrackingeuskadi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        ViewHolder(View itemView) {
            super(itemView);

            backgroundElement = itemView.findViewById(R.id.backgroundElement);
            town = itemView.findViewById(R.id.townText);
            incidence = itemView.findViewById(R.id.incidenceText);
        }

        void bindData(final ListElement item) {
            //backgroundElement.setCardBackgroundColor(item.getColor());
            if (towns.indexOf(item) % 2 == 0)
                backgroundElement.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            else
                backgroundElement.setCardBackgroundColor(Color.parseColor("#E2F2FF"));
            town.setText(item.getTown());
            incidence.setText(item.getIncidence());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

}
