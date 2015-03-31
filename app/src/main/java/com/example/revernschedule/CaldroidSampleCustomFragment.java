package com.example.revernschedule;

/**
 * Created by Алмаз on 25.03.2015.
 */
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

public class CaldroidSampleCustomFragment extends CaldroidFragment {

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        // TODO Auto-generated method stub
        return new CaldroidSampleCustomAdapter(getActivity(), month, year,
                getCaldroidData(), extraData);
    }

}
