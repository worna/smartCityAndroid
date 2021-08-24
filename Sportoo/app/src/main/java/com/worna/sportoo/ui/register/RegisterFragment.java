package com.worna.sportoo.ui.register;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.worna.sportoo.R;
import com.worna.sportoo.databinding.RegisterFragmentBinding;
import com.worna.sportoo.models.Customer;
import com.worna.sportoo.models.NetworkError;
import com.worna.sportoo.ui.registered.RegisteredFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterFragment extends Fragment {
    private RegisterFragmentBinding binding;
    private RegisterViewModel viewModel;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding = RegisterFragmentBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);


        ArrayAdapter<String> adapter = new ArrayAdapter(this.getContext(), R.layout.dropdown_menu_item, getResources().getStringArray(R.array.language_array));
        binding.registerLanguageAutoCompleteTextView.setAdapter(adapter);

        binding.alreadyMember.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_registerFragment_to_loginFragment));

        viewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorScreen);

        binding.registerButton.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.registerInfoScroll.setVisibility(View.GONE);
            binding.registerButton.setVisibility(View.GONE);
            binding.alreadyMember.setVisibility(View.GONE);
            checkFormErrors();
            if (!formContainsErrors()) {
                register();
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.registerInfoScroll.setVisibility(View.VISIBLE);
                binding.registerButton.setVisibility(View.VISIBLE);
                binding.alreadyMember.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }


    private void displayErrorScreen(NetworkError error) {
        if (error == null) {
            String email = readValueOf(binding.registerEmailTextInputLayout);
            String password = readValueOf(binding.registerPasswordTextInputLayout);
            Bundle registeredArgs = RegisteredFragment.newArguments(email, password);
            Navigation.findNavController(getView())
                    .navigate(R.id.action_registerFragment_to_registeredFragment, registeredArgs);
            return;
        }
        binding.progressBar.setVisibility(View.GONE);
        binding.registerButton.setVisibility(View.VISIBLE);
        binding.registerInfoScroll.setVisibility(View.VISIBLE);
        binding.alreadyMember.setVisibility(View.VISIBLE);
        binding.errorLayout.getRoot().setVisibility(View.VISIBLE);
        binding.errorLayout.errorText.setText(error.getErrorMessage());
        binding.errorLayout.errorImage.setImageDrawable(getResources().getDrawable(error.getErrorDrawable(),
                getActivity().getTheme()));
        binding.errorLayout.floatingActionButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        binding.errorLayout.floatingActionButton.setOnClickListener(view -> binding.errorLayout.getRoot().setVisibility(View.GONE));
    }

    public void register() {
        String language = readValueOf(binding.registerLanguageTextInputLayout);
        String firstName = readValueOf(binding.registerFirstNameTextInputLayout);
        String lastName = readValueOf(binding.registerLastNameTextInputLayout);
        String phoneNumber = readValueOf(binding.registerPhoneNumberTextInputLayout);
        String email = readValueOf(binding.registerEmailTextInputLayout);
        String password = readValueOf(binding.registerPasswordTextInputLayout);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birtDate = new Date();
        try {
            birtDate = format.parse(readValueOf(binding.registerBirthDateInputLayout));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer gender = getGender();
        String address = readValueOf(binding.registerAddressTextInputLayout);
        Integer zipCode = Integer.valueOf(readValueOf(binding.registerZipCodeTextInputLayout));
        String city = readValueOf(binding.registerCityTextInputLayout);
        String country = readValueOf(binding.registerCountryTextInputLayout);

        Customer customer = new Customer(firstName, lastName, birtDate, gender, phoneNumber, email, password, null, null, null, language, address, city, zipCode, country);
        viewModel.registrationToWeb(customer);
    }

    private void checkFormErrors() {
        checkAndDisplayMandatoryFieldErrorFor(binding.registerLanguageTextInputLayout);
        checkAndDisplayLengthErrorFor(binding.registerFirstNameTextInputLayout, 2, 30);
        checkAndDisplayLengthErrorFor(binding.registerLastNameTextInputLayout, 2, 30);
        checkAndDisplayNotValidPhoneFor(binding.registerPhoneNumberTextInputLayout);
        checkAndDisplayNotValidEmailFor(binding.registerEmailTextInputLayout);
        checkAndDisplayPasswordErrorFor(binding.registerPasswordTextInputLayout);
        checkAndDisplayMatchErrorFor(binding.registerPasswordTextInputLayout, binding.registerConfirmPasswordTextInputLayout);
        checkAndDisplayDateErrorFor(binding.registerBirthDateInputLayout);
        checkAndDisplayRadioSelectedFor(binding.registerGender);
        checkAndDisplayLengthErrorFor(binding.registerAddressTextInputLayout, 2, 100);
        checkAndDisplayLengthErrorFor(binding.registerZipCodeTextInputLayout, 1, 12);
        checkAndDisplayLengthErrorFor(binding.registerCityTextInputLayout, 1, 85);
        checkAndDisplayLengthErrorFor(binding.registerCountryTextInputLayout, 4, 56);
    }

    private void checkAndDisplayMandatoryFieldErrorFor(TextInputLayout textInputLayout) {
        if (isEditTextEmpty(textInputLayout)) {
            textInputLayout.setError(getString(R.string.field_error_mandatory));
        } else {
            textInputLayout.setError(null);
        }
    }

    private void checkAndDisplayDateErrorFor(TextInputLayout textInputLayout) {
        checkAndDisplayMandatoryFieldErrorFor(textInputLayout);
        if (textInputLayout.getError() == null) {
            String stringDate = readValueOf(textInputLayout);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            try {
                Date date = format.parse(stringDate);
                Date today = new Date();
                if (today.before(date)) {
                    textInputLayout.setError(getString(R.string.field_error_future));
                    return;
                }
                Date oldDate = new Date();
                oldDate.setYear(oldDate.getYear() - 150);
                if (date.before(oldDate)) {
                    textInputLayout.setError(getString(R.string.field_error_dinosaur));
                    return;
                }
                textInputLayout.setError(null);
            } catch (ParseException e) {
                textInputLayout.setError(getString(R.string.field_error_bad_format));
                e.printStackTrace();
            }
        }
    }

    private void checkAndDisplayPasswordErrorFor(TextInputLayout textInputLayout) {
        checkAndDisplayLengthErrorFor(textInputLayout, 8, 30);
        if (textInputLayout.getError() == null) {
            if (!checkValidPassword(readValueOf(textInputLayout))) {
                textInputLayout.setError(getString(R.string.field_error_password));
            } else {
                textInputLayout.setError(null);
            }
        }
    }

    private void checkAndDisplayRadioSelectedFor(RadioGroup radioGroup) {
        int last = radioGroup.getChildCount();
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(last - 1);
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            radioButton.setError(getString(R.string.field_error_mandatory));
            return;
        }
        radioButton.setError(null);
    }

    private void checkAndDisplayNotValidEmailFor(TextInputLayout textInputLayout) {
        checkAndDisplayMandatoryFieldErrorFor(textInputLayout);
        if (textInputLayout.getError() == null) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(readValueOf(textInputLayout)).matches()) {
                textInputLayout.setError(getString(R.string.field_error_email));
            } else {
                textInputLayout.setError(null);
            }
        }
    }

    private void checkAndDisplayNotValidPhoneFor(TextInputLayout textInputLayout) {
        checkAndDisplayMandatoryFieldErrorFor(textInputLayout);
        if (textInputLayout.getError() == null) {
            if (!Patterns.PHONE.matcher(readValueOf(textInputLayout)).matches()) {
                textInputLayout.setError(getString(R.string.field_error_phone));
            } else {
                textInputLayout.setError(null);
            }
        }
    }

    private boolean checkValidPassword(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    private void checkAndDisplayMatchErrorFor(TextInputLayout textInputLayout, TextInputLayout textInputLayout2) {
        if (!readValueOf(textInputLayout).equals(readValueOf(textInputLayout2))) {
            textInputLayout2.setError(getString(R.string.field_error_matching));
        } else {
            textInputLayout2.setError(null);
        }
    }

    private void checkAndDisplayLengthErrorFor(TextInputLayout textInputLayout, int minLength, int maxLength) {
        checkAndDisplayMandatoryFieldErrorFor(textInputLayout);
        if (textInputLayout.getError() == null) {
            int length = readValueOf(textInputLayout).length();
            if (length < minLength || length > maxLength) {
                textInputLayout.setError(getString(R.string.field_error_size, minLength, maxLength));
            } else {
                textInputLayout.setError(null);
            }
        }
    }

    private boolean isEditTextEmpty(TextInputLayout textInputLayout) {
        return textInputLayout.getEditText() == null ||
                textInputLayout.getEditText().getText() == null ||
                textInputLayout.getEditText().getText().length() == 0;
    }

    private String readValueOf(TextInputLayout textInputLayout) {
        return textInputLayout.getEditText().getText().toString();
    }

    private boolean formContainsErrors() {
        return binding.registerLanguageTextInputLayout.getError() != null
                || binding.registerFirstNameTextInputLayout.getError() != null
                || binding.registerLastNameTextInputLayout.getError() != null
                || binding.registerPhoneNumberTextInputLayout.getError() != null
                || binding.registerEmailTextInputLayout.getError() != null
                || binding.registerPasswordTextInputLayout.getError() != null
                || binding.registerConfirmPasswordTextInputLayout.getError() != null
                || binding.registerBirthDateInputLayout.getError() != null
                || binding.registerGenderOther.getError() != null
                || binding.registerAddressTextInputLayout.getError() != null
                || binding.registerZipCodeTextInputLayout.getError() != null
                || binding.registerCityTextInputLayout.getError() != null
                || binding.registerCountryTextInputLayout.getError() != null;
    }

    private Integer getGender() {
        int id = binding.registerGender.getCheckedRadioButtonId();
        RadioButton rb = binding.getRoot().findViewById(id);
        if (binding.registerGenderMan.equals(rb)) {
            return 0;
        } else if (binding.registerGenderWoman.equals(rb)) {
            return 1;
        } else if (binding.registerGenderOther.equals(rb)) {
            return 2;
        }
        return null;
    }



}
