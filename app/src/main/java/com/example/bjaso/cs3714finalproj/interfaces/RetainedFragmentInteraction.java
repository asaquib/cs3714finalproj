package com.example.bjaso.cs3714finalproj.interfaces;

/**
 * Created by Andrey on 2/17/2017.
 */

public interface RetainedFragmentInteraction {
    public String getActiveFragmentTag();
    public void setActiveFragmentTag(String s);
    public void checkIfLoggedIn();
    public void loginResult(String result);
    public void startBackgroundServiceNeeded();
}
