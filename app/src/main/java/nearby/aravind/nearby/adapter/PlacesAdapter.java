package nearby.aravind.nearby.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import nearby.aravind.nearby.R;
import nearby.aravind.nearby.activity.MainActivity;
import nearby.aravind.nearby.model.Places;

public class PlacesAdapter extends BaseAdapter {

    private MainActivity activity;
    private Places places;

    public PlacesAdapter(MainActivity activity, Places places) {
        this.activity = activity;
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.custom_list_view, null);
        TextView place = view.findViewById(R.id.tv_place);
        place.setText(places.getList().get(position).getName());

        TextView vicinity = view.findViewById(R.id.tv_vicinity);
        vicinity.setText(places.getList().get(position).getVicinity());

        return view;
    }
}
