package com.haksoy.exchangealarm.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.haksoy.exchangealarm.R;
import com.haksoy.exchangealarm.adapter.GenericAdapter;
import com.haksoy.exchangealarm.adapter.NavigationDrawerAdapter;
import com.haksoy.exchangealarm.application.ThisApplication;
import com.haksoy.exchangealarm.base.GenericPresenter;
import com.haksoy.exchangealarm.model.Constants;
import com.haksoy.exchangealarm.model.Exchange;
import com.haksoy.exchangealarm.model.ExchangeCategory;
import com.haksoy.exchangealarm.model.Market;
import com.haksoy.exchangealarm.model.MarketScreen;
import com.haksoy.exchangealarm.model.NavItem;
import com.haksoy.exchangealarm.model.NavSeperator;
import com.haksoy.exchangealarm.service.AppService;
import com.haksoy.exchangealarm.service.ServiceViewInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ServiceViewInterface {

    @Inject
    AppService mAppService;

    @BindView(R.id.rclMainList)
    RecyclerView mainList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.lst_menu_items)
    ListView leftMenuList;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.navHeader)
    View navHeader;
    HeaderHolder headerHolder;

    ActionBarDrawerToggle toggle;
    private GenericAdapter mAdapter;
    private List<Object> objectList;
    private List<NavigationDrawerAdapter.Item> navigationList;
    private List<String> category;
    private GenericPresenter<Object> mGenericPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolveDependency();

        ButterKnife.bind(this);

        generateCategory();
        registerDrawer();
        configureViews();
        registerHeader();
        mGenericPresenter = new GenericPresenter<>(MainActivity.this);
    }

    private void registerHeader() {
        headerHolder = new HeaderHolder(navHeader);
    }

    private void generateCategory() {
        ExchangeCategory.createCategoty();
        category = new ArrayList<>();
        for (String s : ExchangeCategory.getExchangeCategoryList().keySet()) {
            category.add(s);
        }

    }

    private void configureViews() {
        mainList.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mainList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainList.setHasFixedSize(false);
        mainList.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new GenericAdapter(getLayoutInflater());
        mainList.setAdapter(mAdapter);
    }

    private void registerDrawer() {
        setTitle("Borsa Hisseleri");
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationList = new ArrayList<>();

        for (String categoryName : ExchangeCategory.getExchangeCategoryList().values()) {
            navigationList.add(new NavItem(categoryName));
        }
        navigationList.add(0, new NavSeperator("Bana Özel"));
        navigationList.add(3, new NavSeperator("Piyasa"));
        navigationList.add(7, new NavSeperator("Endeksler"));
        leftMenuList.setAdapter(new NavigationDrawerAdapter(this, navigationList));
        leftMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i) instanceof NavSeperator) return;
                selectNavigationItem(i);
            }
        });
    }

    private void selectNavigationItem(int i) {
        callService(i);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void callService(int i) {
        if (i > 0 && i <= 2) {
            i--;
            callSpecialList(i);
        } else if (i > 2 && i <= 7) {
            i = i - 2;
            mGenericPresenter.callService(mAppService.getMarketScreen(category.get(i)));
        } else {
            i = i - 3;
            mGenericPresenter.callService(mAppService.getExchange(category.get(i)));
        }

        setTitle(ExchangeCategory.getValue(category.get(i)));
    }

    private void callSpecialList(int i) {
        System.out.println(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGenericPresenter.onResume();
        mGenericPresenter.callAndIterateService(mAppService.getMarket(), Constants.MarketDataUpdateTime);
        mGenericPresenter.callService(mAppService.getExchange("XU100"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGenericPresenter.onDestroy();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof Market) {
            headerHolder.setData((Market) object);
        } else if (object instanceof ArrayList) {
            objectList = (List<Object>) object;
            mAdapter.setData((List<Object>) object);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.setData(filter(objectList, newText));
//                mAdapter.setData(filteredModelList);
                return true;
            }
        });

        return true;
    }

    private List<Object> filter(List<Object> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Object> filteredModelList = new ArrayList<>();
        if (models.get(0) instanceof Exchange)
            for (Object model : models) {
                Exchange exchange = (Exchange) model;
                final String text = exchange.getName().toLowerCase();
                if (text.contains(lowerCaseQuery)) {
                    filteredModelList.add(model);
                }
            }
        else if (models.get(0) instanceof MarketScreen)
            for (Object model : models) {
                MarketScreen marketScreen = (MarketScreen) model;
                final String text = marketScreen.getName().toLowerCase();
                if (text.contains(lowerCaseQuery)) {
                    filteredModelList.add(model);
                }
            }
        return filteredModelList;
    }

    private void resolveDependency() {
        ((ThisApplication) getApplication())
                .getApiComponent()
                .inject(MainActivity.this);
    }

    private class HeaderHolder {
        TextView mUsdValue, mUsdPercentage;
        TextView mEuroValue, mEuroPercentage;
        TextView mGoldValue, mGoldPercentage;
        TextView mBrentValue, mBrentPercentage;
        TextView mBistValue, mBistPercentage;

        ImageView mUsdStatus, mEuroStatus, mGoldStatus, mBrentStatus, mBistStatus;

        HeaderHolder(View view) {

            mUsdValue = (TextView) view.findViewById(R.id.txtMarketUSDValue);
            mUsdPercentage = (TextView) view.findViewById(R.id.txtMarketUSDPercantage);
            mEuroValue = (TextView) view.findViewById(R.id.txtMarketEUROValue);
            mEuroPercentage = (TextView) view.findViewById(R.id.txtMarketEUROPercantage);
            mGoldValue = (TextView) view.findViewById(R.id.txtMarketGOLDValue);
            mGoldPercentage = (TextView) view.findViewById(R.id.txtMarketGOLDPercantage);
            mBrentValue = (TextView) view.findViewById(R.id.txtMarketBRENTValue);
            mBrentPercentage = (TextView) view.findViewById(R.id.txtMarketBRENTPercantage);
            mBistValue = (TextView) view.findViewById(R.id.txtMarketBISTValue);
            mBistPercentage = (TextView) view.findViewById(R.id.txtMarketBISTPercentage);

            mUsdStatus = (ImageView) view.findViewById(R.id.imgMarketUSDStatus);
            mEuroStatus = (ImageView) view.findViewById(R.id.imgMarketEUROStatus);
            mGoldStatus = (ImageView) view.findViewById(R.id.imgMarketGOLDStatus);
            mBrentStatus = (ImageView) view.findViewById(R.id.imgMarketBRENTStatus);
            mBistStatus = (ImageView) view.findViewById(R.id.imgMarketBISTStatus);
        }

        public void setData(Market market) {
            setMarketTextsAndImage(mUsdValue, market.getUsdValue(), mUsdPercentage, market.getUsdPercentage(), mUsdStatus);
            setMarketTextsAndImage(mEuroValue, market.getEuroValue(), mEuroPercentage, market.getEuroPercentage(), mEuroStatus);
            setMarketTextsAndImage(mGoldValue, market.getGoldValue(), mGoldPercentage, market.getGoldPercentage(), mGoldStatus);
            setMarketTextsAndImage(mBrentValue, market.getBrentValue(), mBrentPercentage, market.getBrentPercentage(), mBrentStatus);
            setMarketTextsAndImage(mBistValue, market.getBist100Value(), mBistPercentage, market.getBist100Percentage(), mBistStatus);
        }

        private void setMarketTextsAndImage(TextView valueText, float valueData, TextView percantageText, float percantageData, ImageView status) {

            valueText.setText(valueData + "");
            percantageText.setText(percantageData + "");

            if (percantageData < 0) {
                percantageText.setTextColor(getResources().getColor(R.color.redTextColor));
                status.setImageResource(R.drawable.ic_down_d32f2f);
            } else if (percantageData > 0) {
                percantageText.setTextColor(getResources().getColor(R.color.greenTextColor));
                status.setImageResource(R.drawable.ic_up_2e7d32);
            } else {
                percantageText.setTextColor(getResources().getColor(R.color.blueTextColor));
                status.setImageResource(R.drawable.ic_stabil_0277bd);
            }
        }

    }
}
