package com.di.almenzarjimenezsergio.practicalogin;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.di.almenzarjimenezsergio.practicalogin.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

public class FirstFragment extends Fragment implements View.OnClickListener {

    private FragmentFirstBinding binding;
    Bundle bundle;
    ArrayList<IZVAccount> izvAccounts;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setButtonListeners();

        try{
            bundle = getArguments();
            izvAccounts = bundle.getParcelableArrayList("accounts");
            Log.v("jamaica", "a");
        } catch (Exception e) {
            bundle = new Bundle();
            izvAccounts = new ArrayList<>();
            Log.v("jamaica", "b");
        }
        Log.v("jamaica", bundle.toString());
    }

    private void setButtonListeners() {
        binding.btLogin.setOnClickListener(this);
        binding.btSignin.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btLogin:
                String email = binding.tietEmail.getText().toString();
                String pass = binding.tietPassword.getText().toString();
                    if (checkLogin(email, pass)){
                        bundle.putParcelableArrayList("accounts", izvAccounts);
                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.action_FirstFragment_to_thirdFragment, bundle);
                    } else {
                        Snackbar mySnackbar;
                        mySnackbar = Snackbar.make(binding.btLogin, "Usuario y/o cContraseña incorrectos", Snackbar.LENGTH_SHORT);
                        mySnackbar.show();
                    }
                break;
            case R.id.btSignin:
                
                bundle.putParcelableArrayList("accounts", izvAccounts);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                break;
        }
    }

    private boolean checkLogin(String email, String pass) {
        for (IZVAccount account: izvAccounts) {
            if (account.getEmail().equals(email) &&
                account.getPassword().equals(pass)){
                return true;
            }
        }

        return false;
    }


    @Override
    public String toString() {
        return "FirstFragment{" +
                "izvAccounts=" + izvAccounts +
                '}';
    }
}