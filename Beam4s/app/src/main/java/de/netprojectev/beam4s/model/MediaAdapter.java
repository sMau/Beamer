package de.netprojectev.beam4s.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import de.netprojectev.beam4s.R;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.exceptions.PriorityDoesNotExistException;

/**
 * Created by samu on 24.12.14.
 */
public class MediaAdapter extends BaseAdapter {

    private final MediaModelClient mediaModel;

    public MediaAdapter(MediaModelClient mediaModel, final Activity parent) {

        this.mediaModel = mediaModel;
        this.mediaModel.setListener(new MediaModelClient.UpdateAllMediaDataListener() {
            @Override
            public void update() {
                Log.d("DATA Change", "data change registered");
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
        return mediaModel.getAllMedia().size();
    }

    @Override
    public Object getItem(int position) {
        return mediaModel.getValueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.media_list_item, parent, false);
        ((ImageButton) rowView.findViewById(R.id.ibMore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });
        TextView tvMediaName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvMediaType = (TextView) rowView.findViewById(R.id.tvMediaType);
        TextView tvShowCount = (TextView) rowView.findViewById(R.id.tvShowCount);
        TextView tvPriority = (TextView) rowView.findViewById(R.id.tvPriority);
        tvMediaName.setText(mediaModel.getValueAt(position).getName());
        tvMediaType.setText(mediaModel.getValueAt(position).getType().toString());
        tvShowCount.setText(String.valueOf(mediaModel.getValueAt(position).getShowCount()));
        try {
            tvPriority.setText(mediaModel.getProxy().getPrefs().getPriorityByID(mediaModel.getValueAt(position).getPriorityID()).getName());
        } catch (PriorityDoesNotExistException e) {
            tvPriority.setText("undefined");
        }
        return rowView;
    }
}
