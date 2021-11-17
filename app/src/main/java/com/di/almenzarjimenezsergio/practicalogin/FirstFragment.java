package com.di.almenzarjimenezsergio.practicalogin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.di.almenzarjimenezsergio.practicalogin.databinding.FragmentFirstBinding;

import java.util.Locale;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

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
        String tietEmail = binding.tietEmail.getText().toString();
        TextWatcher twEmail = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //control de caracteres especiales

                if (!(specialChars(tietEmail)) && tietEmail.contains("@")){
                    String domain = tietEmail.split("@")[1];
                    if (domain.equalsIgnoreCase("ieszaidinvergeles.org")){

                    } else {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public boolean specialChars(String string){
        String validChars = "qwertyuiopasdfghjklzxcvbnm-_.@";
        for (int i = 0; i < string.length(); i++){
            if (validChars.contains(Character.toString(string.charAt(i)))){
                return true;
            }
        }

        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}