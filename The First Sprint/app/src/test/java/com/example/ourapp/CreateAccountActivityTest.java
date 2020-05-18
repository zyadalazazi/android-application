package com.example.ourapp;


import android.content.Context;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
@Config(maxSdk = 28)
public class CreateAccountActivityTest {

    FirebaseAuth auth;

    @Before
    public void init() {
        Context appContext = RuntimeEnvironment.application;
        FirebaseApp.initializeApp(appContext);
    }

    @Test
    public void shouldReturnTrueWhenPasswordIsValidLength() {
        ActivityController<createAccount> activityController = Robolectric.buildActivity(createAccount.class);
        activityController.setup();

        createAccount activity = activityController.get();

        // input password with valid length
        String password = "password12348";

        // set the text value of password adn confirm password
        EditText passwordEt = activity.findViewById(R.id.PasswordET);
        EditText confirmPasswordEt = activity.findViewById(R.id.confirmPasswordET);

        passwordEt.setText(password);
        confirmPasswordEt.setText(password);


        boolean isValidPassword = activity.validatePassword();
        assertTrue("password should be valid", isValidPassword);
    }


    @Test
    public void shouldReturnFalseWhenPasswordHasInvalidLengthOrConfirmPasswordIsNotSame() {
        ActivityController<createAccount> activityController = Robolectric.buildActivity(createAccount.class);
        activityController.setup();

        createAccount activity = activityController.get();

        // input password with valid length
        String password = "prd12";

        // set the text value of password adn confirm password
        EditText passwordEt = activity.findViewById(R.id.PasswordET);
        EditText confirmPasswordEt = activity.findViewById(R.id.confirmPasswordET);

        passwordEt.setText(password);
        confirmPasswordEt.setText(password);


        boolean isValidPassword = activity.validatePassword();
        assertFalse("password should be invalid", isValidPassword);



        // test for different passwords
        String newpassword = "passweroe1234";
        String confirmPass = "password1234";


        passwordEt.setText(newpassword);
        confirmPasswordEt.setText(confirmPass);

        isValidPassword = activity.validatePassword();
        assertFalse("password should be invalid", isValidPassword);
    }


}
