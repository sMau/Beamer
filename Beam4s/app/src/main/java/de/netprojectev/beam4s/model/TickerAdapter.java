package de.netprojectev.beam4s.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.netprojectev.beam4s.R;
import de.netprojectev.client.model.TickerModelClient;

/**
 * Created by samu on 24.12.14.
 */
public class TickerAdapter extends BaseAdapter {

    private final TickerModelClient tickerModel;

    public TickerAdapter(TickerModelClient mediaModel, final Activity parent) {
        this.tickerModel = mediaModel;
        this.tickerModel.setTickerDateListener(new TickerModelClient.UpdateTickerDataListener() {
            @Override
            public void update() {
                Log.d("DATA Change","data change registered");
                parent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getCount() {
        return tickerModel.getAllElementsList().size();
    }

    @Override
    public Object getItem(int position) {
        return tickerModel.getValueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.allmedia_row_view, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvRowViewAllMedia);
        textView.setText(tickerModel.getValueAt(position).getText());
        return rowView;
    }
}
