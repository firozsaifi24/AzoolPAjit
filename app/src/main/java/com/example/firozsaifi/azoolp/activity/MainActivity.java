package com.example.firozsaifi.azoolp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.fragments.AccidentCarDetailsFragment;
import com.example.firozsaifi.azoolp.fragments.AccidentCarFragment;
import com.example.firozsaifi.azoolp.fragments.AccountFragment;
import com.example.firozsaifi.azoolp.fragments.AddProductFragment;
import com.example.firozsaifi.azoolp.fragments.ChangePasswordFragment;
import com.example.firozsaifi.azoolp.fragments.DealerFragment;
import com.example.firozsaifi.azoolp.fragments.EditProductFragment;
import com.example.firozsaifi.azoolp.fragments.HomeFragment;
import com.example.firozsaifi.azoolp.fragments.MyAccountFragment;
import com.example.firozsaifi.azoolp.fragments.ProfileEditFragment;
import com.example.firozsaifi.azoolp.fragments.RequestPartFragment;
import com.example.firozsaifi.azoolp.fragments.RequestedPartsFragment;
import com.example.firozsaifi.azoolp.fragments.ShopFragment;
import com.example.firozsaifi.azoolp.fragments.SparePartsDetailsFragment;
import com.example.firozsaifi.azoolp.fragments.SparePartsFragment;
import com.example.firozsaifi.azoolp.fragments.ViewProductFragment;
import com.example.firozsaifi.azoolp.other.BottomNavigationViewHelper;
import com.example.firozsaifi.azoolp.other.Constants;
import com.example.firozsaifi.azoolp.other.SessionManager;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView navigation;
    private Fragment fragment;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        navigation= findViewById(R.id.navigation);
        navigation.getMenu().clear();
        navigation.inflateMenu(R.menu.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);//disable BottomNavigationView shift mode
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Session manager
        session = new SessionManager(this);




        // remove all the fragment from the stack
        popupAllFragments();
        // load the home fragment by default
        loadFragment(new HomeFragment());

    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment=new HomeFragment();
                    // This will clean the stack first and then load a new fragment, so at any given point you'll have only single fragment in stack.
                    //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    // remove all the fragment from the stack
                    popupAllFragments();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_shop:
                    fragment=new ShopFragment();
                    popupAllFragments();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_account:

                    if(session.isLoggedIn())
                    {
                        fragment=new MyAccountFragment();
                    }
                    else
                    {
                        fragment=new AccountFragment();
                    }
                    popupAllFragments();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        //// load fragment
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    public void visitShop(String dealer_id)
    {
        DealerFragment dealerFragment= new DealerFragment();
        //passing data to fragment
        Bundle bundle = new Bundle();
        bundle.putString("dealer_id", dealer_id);
        Log.d("dealer id", dealer_id);
        // set MyFragment Arguments
        dealerFragment.setArguments(bundle);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, dealerFragment, Constants.FRAG_TAG_DEALER).addToBackStack(null).commit();
    }

    public void dealerAccidentCarDetails(String product_id)
    {
        Log.e("Accident Car details ", product_id);
        AccidentCarDetailsFragment accidentCarDetailsFragment= new AccidentCarDetailsFragment();
        //passing data to fragment
        Bundle bundle = new Bundle();
        bundle.putString("product_id", product_id);
        // set MyFragment Arguments
        accidentCarDetailsFragment.setArguments(bundle);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, accidentCarDetailsFragment, Constants.FRAG_TAG_ACCIDENT_CAR_DETAILS).addToBackStack(null).commit();

        /*SparePartsDetailsFragment sparePartDetailsFragment= new SparePartsDetailsFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, sparePartDetailsFragment, Constants.FRAG_TAG_SPARE_PARTS_DETAILS).addToBackStack(null).commit();*/

    }

    public void dealerSparePartDetails(String product_id)
    {
        Log.e("Spare Part details ", product_id);
        SparePartsDetailsFragment sparePartsDetailsFragment= new SparePartsDetailsFragment();
        //passing data to fragment
        Bundle bundle = new Bundle();
        bundle.putString("product_id", product_id);
        // set MyFragment Arguments
        sparePartsDetailsFragment.setArguments(bundle);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, sparePartsDetailsFragment, Constants.FRAG_TAG_SPARE_PARTS_DETAILS).addToBackStack(null).commit();

        /*SparePartsDetailsFragment sparePartDetailsFragment= new SparePartsDetailsFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, sparePartDetailsFragment, Constants.FRAG_TAG_SPARE_PARTS_DETAILS).addToBackStack(null).commit();*/

    }

    public void editProduct(String product_id)
    {
        Log.e("Edit product id ", product_id);
        EditProductFragment editProductFragment= new EditProductFragment();
        //passing data to fragment
        Bundle bundle = new Bundle();
        bundle.putString("product_id", product_id);
        // set MyFragment Arguments
        editProductFragment.setArguments(bundle);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, editProductFragment, Constants.FRAG_TAG_EDIT_PRODUCT).addToBackStack(null).commit();

        /*SparePartsDetailsFragment sparePartDetailsFragment= new SparePartsDetailsFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, sparePartDetailsFragment, Constants.FRAG_TAG_SPARE_PARTS_DETAILS).addToBackStack(null).commit();*/

    }

    public void shops()
    {
        //loadFragment(new DealerFragment(),Constants.FRAG_TAG_DEALER);
        ShopFragment shopFragment= new ShopFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, shopFragment, Constants.FRAG_TAG_SHOP).addToBackStack(null).commit();
    }

    public void addProduct()
    {
        AddProductFragment addProductFragment= new AddProductFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addProductFragment, Constants.FRAG_TAG_ADD_PRODUCT).addToBackStack(null).commit();
    }

    public void viewProduct()
    {
        ViewProductFragment viewProductFragment= new ViewProductFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewProductFragment, Constants.FRAG_TAG_VIEW_PRODUCT).addToBackStack(null).commit();
    }

    public void notification()
    {
        //loadFragment(new DealerFragment(),Constants.FRAG_TAG_DEALER);
        /*ViewProductFragment viewProductFragment= new ViewProductFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, viewProductFragment, Constants.FRAG_TAG_VIEW_PRODUCT).addToBackStack(null).commit();*/
    }

    public void changePaswword()
    {
        ChangePasswordFragment changePasswordFragment= new ChangePasswordFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, changePasswordFragment, Constants.FRAG_TAG_CHANGE_PASS).addToBackStack(null).commit();
    }

    public void profileEdit()
    {
        ProfileEditFragment profileEditFragment= new ProfileEditFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, profileEditFragment, Constants.FRAG_TAG_PROFILE).addToBackStack(null).commit();
    }

    public void logout()
    {
        fragment=new HomeFragment();
        loadFragment(fragment);
    }

    public void accidentCar()
    {
        AccidentCarFragment accidentCarFragment= new AccidentCarFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, accidentCarFragment, Constants.FRAG_TAG_ACCIDENT_CAR).addToBackStack(null).commit();
    }

    public void spareParts()
    {
        SparePartsFragment sparePartsFragment= new SparePartsFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, sparePartsFragment, Constants.FRAG_TAG_SPARE_PARTS).addToBackStack(null).commit();
    }

    public void requestedParts()
    {
        RequestedPartsFragment requestedPartsFragment= new RequestedPartsFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, requestedPartsFragment, Constants.FRAG_TAG_REQUESTED_PARTS).addToBackStack(null).commit();
    }

    public void requestPart()
    {
        RequestPartFragment requestPartFragment= new RequestPartFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, requestPartFragment, Constants.FRAG_TAG_REQUEST_PART).addToBackStack(null).commit();
    }


    //to remove all the fragments from the stack
    private void popupAllFragments(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        if (fragmentManager != null) {
            int backstackCount = fragmentManager.getBackStackEntryCount();
            if (backstackCount > 0) {
                for (int i = 0; i < backstackCount; i++) {
                    // fragmentManager.popBackStackImmediate();
                    fragmentManager.popBackStack();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager=getSupportFragmentManager();
        if (fragmentManager != null) {
            int backstackCount = fragmentManager.getBackStackEntryCount();
            if (backstackCount > 0) {
                MainActivity.super.onBackPressed();
            }
            else
            {
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are You Sure Want To Exit?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        }

    }
}



