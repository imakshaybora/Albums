package com.akshay.albums;

import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.akshay.albums.utils.AlbumsFetcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity
            .class);
    private View view;
    private AppCompatActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = activityTestRule.getActivity();

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlbumFragment albumFragment = startAlbumFragment();
            }
        });
    }

    @Test
    public void fragment_can_be_instantiated() {
        onView(withId(R.id.albumlist)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerview_havingData() {
        onView(withId(R.id.albumlist)).check(new RecyclerViewItemCountAssertion(0));
        onView(withId(R.id.no_data_tv)).check(matches(not(isDisplayed())));
    }

    private AlbumFragment startAlbumFragment() {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        AlbumFragment albumFragment = new AlbumFragment();
        transaction.add(albumFragment, "albumFragment");
        transaction.commitAllowingStateLoss();
        return albumFragment;
    }

    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), not(is(expectedCount)));
        }
    }
}