/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oxilo.oioindia.view.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.oxilo.oioindia.R;
import com.oxilo.oioindia.view.activity.MainActivity;
import com.oxilo.oioindia.view.fragments.AccountFragment;
import com.oxilo.oioindia.view.fragments.AddBusinessFragment;
import com.oxilo.oioindia.view.fragments.BusinessDetailFragment;
import com.oxilo.oioindia.view.fragments.BusinessListFragment;
import com.oxilo.oioindia.view.fragments.CallNowFragment;
import com.oxilo.oioindia.view.fragments.HelpFragment;
import com.oxilo.oioindia.view.fragments.ListFragment;
import com.oxilo.oioindia.view.fragments.LocationFragment;
import com.oxilo.oioindia.view.fragments.MainFragment;
import com.oxilo.oioindia.view.fragments.MapFragment;
import com.oxilo.oioindia.view.fragments.MessageFragment;
import com.oxilo.oioindia.view.fragments.RatingFragment;
import com.oxilo.oioindia.view.fragments.SubCategorieFragment;
import com.oxilo.oioindia.vo.Category;
import com.oxilo.oioindia.vo.SubCategory;


/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.content;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToMain(String city,String adress) {
        String tag = "repo" + "/"  + "/" + "main";
        MainFragment searchFragment =  MainFragment.newInstance(city,adress);
        fragmentManager.beginTransaction()
                .replace(containerId, searchFragment,tag)
                .commitAllowingStateLoss();
    }

    public void navigateToLocation(String address, String city) {
        LocationFragment fragment = LocationFragment.newInstance(address, city);
        String tag = "repo" + "/"  + "/" + "location";
        String hide = "repo" + "/"  + "/" + "main";
        fragmentManager.beginTransaction()
                .hide(fragmentManager.findFragmentByTag(hide))
                .add(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToSubCategory(String serviceId, Category name) {
        SubCategorieFragment fragment = SubCategorieFragment.newInstance(serviceId, name);
        String tag = "repo" + "/"  + "/" + "subcategory";
        String hide = "repo" + "/"  + "/" + "main";
        fragmentManager.beginTransaction()
                .hide(fragmentManager.findFragmentByTag(hide))
                .add(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToBusinessListing(String cat_id, SubCategory name) {
        BusinessListFragment fragment = BusinessListFragment.newInstance(cat_id, name);
        String tag = "repo" + "/"  + "/" + "blist";
        String hide = "repo" + "/"  + "/" + "subcategory";
        fragmentManager.beginTransaction()
                .hide(fragmentManager.findFragmentByTag(hide))
                .add(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToBusinessDetails(String product_id) {
        BusinessDetailFragment fragment = BusinessDetailFragment.newInstance(product_id, "");
        String tag = "repo" + "/"  + "/" + "detail";
        String hide = "repo" + "/"  + "/" + "blist";
        fragmentManager.beginTransaction()
                .hide(fragmentManager.findFragmentByTag(hide))
                .add(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }


    public void navigateToBusinessDetails1(String product_id) {
        BusinessDetailFragment fragment = BusinessDetailFragment.newInstance(product_id, "");
        String tag = "repo" + "/"  + "/" + "detail";
        String hide = "repo" + "/"  + "/" + "blist";
        fragmentManager.beginTransaction()
              //  .hide(fragmentManager.findFragmentByTag(hide))
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }


    public void navigateToRating(String product_id,String product_name) {
        RatingFragment fragment = RatingFragment.newInstance(product_id,product_name);
        String tag = "repo" + "/"  + "/" + "detail";
        String hide = "repo" + "/"  + "/" + "blist";
        fragmentManager.beginTransaction()
                //  .hide(fragmentManager.findFragmentByTag(hide))
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToMessage(String product_id,String product_name) {
        MessageFragment fragment = MessageFragment.newInstance(product_id,product_name);
        String tag = "repo" + "/"  + "/" + "detail";
        String hide = "repo" + "/"  + "/" + "blist";
        fragmentManager.beginTransaction()
                //  .hide(fragmentManager.findFragmentByTag(hide))
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }


    public void navigateToMyAccout() {
        String tag = "repo" + "/"  + "/" + "main";
        AccountFragment accountFragment =  AccountFragment.newInstance("city","adress");
        fragmentManager.beginTransaction()
                .replace(containerId, accountFragment,tag)
               // .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToAddBusiness() {
        String tag = "repo" + "/"  + "/" + "main";
        AddBusinessFragment accountFragment =  AddBusinessFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(containerId, accountFragment,tag)
                //.addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToList() {
        String tag = "repo" + "/"  + "/" + "main";
        ListFragment accountFragment =  ListFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(containerId, accountFragment,tag)
                //.addToBackStack(null)
                .commitAllowingStateLoss();
    }


    public void navigateToHelp() {
        HelpFragment fragment = HelpFragment.newInstance();
        String tag = "repo" + "/"  + "/" + "detail";
        String hide = "repo" + "/"  + "/" + "blist";
        fragmentManager.beginTransaction()
                //  .hide(fragmentManager.findFragmentByTag(hide))
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }


    public void navigateToMap(double lat, double lng, String name) {
        MapFragment fragment = MapFragment.newInstance( lat,  lng,  name);
        String tag = "repo" + "/"  + "/" + "detail";
        String hide = "repo" + "/"  + "/" + "blist";
        fragmentManager.beginTransaction()
                //  .hide(fragmentManager.findFragmentByTag(hide))
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToCall(String ph1,String ph2) {
        CallNowFragment fragment = CallNowFragment.newInstance( ph1,  ph2);
        String tag = "repo" + "/"  + "/" + "detail";
        String hide = "repo" + "/"  + "/" + "blist";
        fragmentManager.beginTransaction()
                //  .hide(fragmentManager.findFragmentByTag(hide))
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
//    public void navigateToUser(String login) {
//        String tag = "user" + "/" + login;
//        UserFragment userFragment = UserFragment.create(login);
//        fragmentManager.beginTransaction()
//                .replace(containerId, userFragment, tag)
//                .addToBackStack(null)
//                .commitAllowingStateLoss();
//    }
}
