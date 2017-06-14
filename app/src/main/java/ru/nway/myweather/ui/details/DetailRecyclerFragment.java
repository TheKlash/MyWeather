package ru.nway.myweather.ui.details;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.model.weather.Datum__;
import ru.nway.myweather.ui.IconPicker;

public abstract class DetailRecyclerFragment extends Fragment
{
    private RecyclerView mDetailRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private DetailRecyclerFragment.DetailRecyclerAdapter adapter;

    protected abstract void setRecycler(ArrayList<HourlyDailyData> dataset);

    protected class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailRecyclerAdapter.ViewHolder>
    {
        private ArrayList<HourlyDailyData> mDataset;

        DetailRecyclerAdapter(ArrayList<HourlyDailyData> dataset) {
            mDataset = dataset;
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView mDetailCardTime;
            TextView mDetailCardTemp;
            ImageView mDetailCardIcon;
            CardView cardView;

            ViewHolder(View v) {
                super(v);
                cardView = (CardView) v.findViewById(R.id.detailCardView);
                mDetailCardTime = (TextView) v.findViewById(R.id.detailCardTime);
                mDetailCardTemp = (TextView) v.findViewById(R.id.detailCardTemp);
                mDetailCardIcon = (ImageView) v.findViewById(R.id.detailCardIcon);
            }
        }

        @Override
        public DetailRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_detail, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            HourlyDailyData data = mDataset.get(position);

            holder.mDetailCardTime.setText(data.getTime());
            holder.mDetailCardTemp.setText(data.getTemp());
            holder.mDetailCardIcon.setImageResource(new IconPicker().pick(data.getIcon()));
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
