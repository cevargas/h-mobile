package com.tn3.mobile.hermes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tn3.mobile.hermes.adapters.ViewPagerAdapter;
import com.tn3.mobile.hermes.fragments.ManifestColetasFragment;
import com.tn3.mobile.hermes.fragments.ManifestDetailsFragment;

public class ManifestoActivity extends AbstractActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    static final int defaultValue = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifesto);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Detalhes do Manifesto");
        }

        //recupera parametro passado pela ManifestFragment
        String id = String.valueOf(getIntent().getLongExtra("idManifesto", defaultValue));
        Bundle args = new Bundle();
        args.putString("idManifesto", id);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, args);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if(tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager, Bundle args) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //detalhes
        ManifestDetailsFragment manifestDetails = new ManifestDetailsFragment();
        manifestDetails.setArguments(args);

        //lista de coletas
        ManifestColetasFragment manifestColetasFragment = new ManifestColetasFragment();
        manifestColetasFragment.setArguments(args);

        adapter.addFragment(manifestDetails, "Detalhes");
        adapter.addFragment(manifestColetasFragment, "Coletas");
        viewPager.setAdapter(adapter);
    }
}
