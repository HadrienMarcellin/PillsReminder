package com.example.pillsreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pillsreminder.R;
import com.example.pillsreminder.entities.Pain;
import com.example.pillsreminder.entities.Pill;
import com.example.pillsreminder.fragments.AlertDialogFragment;
import com.example.pillsreminder.fragments.FoodTabFragment;
import com.example.pillsreminder.fragments.PainTabFragment;
import com.example.pillsreminder.fragments.PillTabFragment;
import com.example.pillsreminder.fragments.SportTabFragment;
import com.example.pillsreminder.tabAdapters.SITabAdapter;
import com.example.pillsreminder.viewModels.PainViewModel;
import com.example.pillsreminder.viewModels.PillViewModel;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements PillTabFragment.OnPillFragmentInteractionListener, PainTabFragment.OnPainFragmentInteractionListener, AlertDialogFragment.OnAlertDialogInteractionInterface {

    private SITabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PillViewModel mPillViewModel;
    private PainViewModel mPainViewModel;

    private Pain painToDelete;
    private Pill pillToDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new SITabAdapter(getSupportFragmentManager());
        adapter.addFragment(new PillTabFragment(), getString(R.string.pill_tab_fragment_title));
        adapter.addFragment(new PainTabFragment(), getString(R.string.pain_tab_fragment_title));
//        adapter.addFragment(new SportTabFragment(), SportTabFragment.title);
//        adapter.addFragment(new FoodTabFragment(), FoodTabFragment.title);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public void onListClickPillDelete(Pill pill) {
        pillToDelete = pill;
        Bundle bundle = new Bundle();
        bundle.putString(AlertDialogFragment.ID_MESSAGE, "Are you sure to delete this pill entry ?");
        bundle.putString(AlertDialogFragment.ID_DATABASE, "pill");

        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "AlertDialog");
    }

    @Override
    public void onListClickPainDelete(Pain pain) {
        painToDelete = pain;
        Bundle bundle = new Bundle();
        bundle.putString(AlertDialogFragment.ID_MESSAGE, "Are you sure to delete this pain entry ?");
        bundle.putString(AlertDialogFragment.ID_DATABASE, "pain");

        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "AlertDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment, String databsase) {

        String msg = "";
        switch (databsase) {
            case "pain":
                PainViewModel painViewModel = ViewModelProviders.of(this).get(PainViewModel.class);
                painViewModel.deleteItem(painToDelete);
                msg = "Pain was deleted.";
                break;
            case "pill":
                PillViewModel pillViewModel = ViewModelProviders.of(this).get(PillViewModel.class);
                pillViewModel.deleteItem(pillToDelete);
                msg = "Pill was deleted.";
                break;
            default:
                msg = "An error occurred while deleting";


        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {
        Toast.makeText(this, "Operation cancelled", Toast.LENGTH_LONG).show();
    }


}
