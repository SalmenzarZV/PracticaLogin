package com.di.almenzarjimenezsergio.practicalogin;


import android.os.Bundle;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.di.almenzarjimenezsergio.practicalogin.databinding.FragmentSecondBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SecondFragment extends Fragment implements View.OnClickListener {

    private FragmentSecondBinding binding;
    ArrayList<IZVAccount> izvAccounts;
    Bundle bundle;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = getArguments();
        Log.v("jamaica", bundle.toString());
        izvAccounts = bundle.getParcelableArrayList("accounts");

        binding.btCreateAc.setOnClickListener(this);
        binding.tietEmailSign.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //control de caracteres especiales
                emailControl();
            }

            private void emailControl() {
                String email = binding.tietEmailSign.getText().toString();
                if (email.contains("@")){
                    String[] emailParts = email.split("@");
                    if (emailParts.length == 2){
                        if (!specialChars(email)){
                            String domain = emailParts[1];
                            if (domain.equalsIgnoreCase("ieszaidinvergeles.org")){
                                binding.etEmailSign.setHelperText("Email Valid");
                                binding.etEmailSign.setEndIconDrawable(R.drawable.ic_baseline_check_circle_outline_24);
                            } else {
                                binding.etEmailSign.setError("Domain not valid");
                            }
                        } else {
                            binding.etEmailSign.setError("Email can't contain special chars");
                        }
                    } else {
                        //TODO DUPLICATED "@"
                        binding.etEmailSign.setError("Duplicated \"@\"");
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tietPasswordSign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass  = binding.tietPasswordSign.getText().toString();
                //Log.v("jamaica", pass);
                if (!specialChars(pass)){
                    binding.etPasswordSign.setHelperText(" ");
                } else {
                    binding.etPasswordSign.setError("Password can't contain special chars");
                }
            }



            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tietConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass = binding.tietPasswordSign.getText().toString();
                String confirmPass = binding.tietConfirmPassword.getText().toString();
                if (pass.equals(confirmPass)){
                    binding.etConfirmPassword.setHelperText(" ");
                } else {
                    binding.etConfirmPassword.setError("Passwords must be the same");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public boolean specialChars(String string){
        String validChars = "1234567890qwertyuiopasdfghjklzxcvbnm-_.@";
        for (int i = 0; i < string.length(); i++){
            if (!validChars.contains(Character.toString(string.charAt(i)))){
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

    @Override
    public void onClick(View view) {
        if(view.equals(binding.btCreateAc)){
            if (correctFields()){
                Snackbar mySnackbar;
                if (!izvAccounts.isEmpty()){
                    String email = binding.tietEmailSign.getText().toString();
                    if (repeatedEmail(email)){
                        mySnackbar = Snackbar.make(binding.btCreateAc, "Este usuario ya existe", Snackbar.LENGTH_SHORT);
                    } else {
                        String pass = binding.tietPasswordSign.getText().toString();
                        IZVAccount newUser = new IZVAccount(email, pass);
                        izvAccounts.add(newUser);
                        imprimeArray();
                        mySnackbar = Snackbar.make(binding.btCreateAc, "Te has registrado correctamente", Snackbar.LENGTH_SHORT);
                        fragmentManagement();
                        NavHostFragment.findNavController(SecondFragment.this)
                                .navigate(R.id.action_SecondFragment_to_FirstFragment, bundle);
                    }
                } else {
                    String pass = binding.tietPasswordSign.getText().toString();
                    String email = binding.tietEmailSign.getText().toString();
                    IZVAccount newUser = new IZVAccount(email, pass);
                    izvAccounts.add(newUser);
                    Log.v("jamaica", izvAccounts.toString());
                    bundle.putParcelableArrayList("accounts", izvAccounts);
                    mySnackbar = Snackbar.make(binding.btCreateAc, "Te has registrado correctamente", Snackbar.LENGTH_SHORT);
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment, bundle);
                }

                mySnackbar.show();


            } else {
                Snackbar mySnackbar;
                mySnackbar = Snackbar.make(binding.btCreateAc, "Tienes algun error en los campos", Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }

        }
    }

    private void imprimeArray() {
        for (IZVAccount account: izvAccounts) {
            Log.v("jamaica", account.getEmail().toString());
        }
    }

    private boolean repeatedEmail(String email) {
        for (int i=0; i< izvAccounts.size(); i++){
            if (izvAccounts.get(i).getEmail().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;
    }

    private boolean emailControl(String email){
        if (email.contains("@")){
            String[] emailParts = email.split("@");
            Log.v("jamaica", "1");
            if (emailParts.length == 2){
                Log.v("jamaica","2" );
                if (!specialChars(email)){
                    Log.v("jamaica", "3");
                    String domain = emailParts[1];
                    if (domain.equalsIgnoreCase("ieszaidinvergeles.org")){
                        Log.v("jamaica", "4");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean correctFields() {
        //CONTROL DE CONTRASEÑA
        String email = binding.tietEmailSign.getText().toString();
        String pass = binding.tietPasswordSign.getText().toString();
        String confirmPass = binding.tietConfirmPassword.getText().toString();
        if (specialChars(pass) || !pass.equals(confirmPass)){
            Log.e("jamaica", "no coincide pass");
            return false;
        }

        if (!emailControl(email)){
            Log.e("jamaica", "email tamal");
            return false;
        }

        if(emptyFields()){
            return false;
        }

        return true;
    }

    private boolean emptyFields() {
        return  binding.tietEmailSign.getText().toString().length() == 0 ||
                binding.tietEmailSign.getText().toString().length() == 0 ||
                binding.tietEmailSign.getText().toString().length() == 0;
    }

    private void fragmentManagement() {
        bundle.putParcelableArrayList("accounts", izvAccounts);
        Fragment firstFragment = new FirstFragment();
        firstFragment.setArguments(bundle);
    }
}