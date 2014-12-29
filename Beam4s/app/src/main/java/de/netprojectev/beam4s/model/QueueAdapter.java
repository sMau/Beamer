package de.netprojectev.beam4s.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.netprojectev.beam4s.R;
import de.netprojectev.client.model.MediaModelClient;

/**
 * Created by samu on 24.12.14.
 */
public class QueueAdapter extends BaseAdapter {

    private final MediaModelClient mediaModel;

    public QueueAdapter(MediaModelClient mediaModel) {

        this.mediaModel = mediaModel;
        this.mediaModel.setCustomQueueListener(new MediaModelClient.UpdateCustomQueueDataListener() {
            @Override
            public void update() {
                Log.d("DATA Change", "data change registered");
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return mediaModel.getCustomQueue().size();
    }

    @Override
    public Object getItem(int position) {
        return mediaModel.getAllMedia().get(mediaModel.getCustomQueue().get(position));
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
        textView.setText(mediaModel.getAllMedia().get(mediaModel.getCustomQueue().get(position)).getName());
        return rowView;
    }
}
