package com.chaya.tests;

import com.chaya.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {
    @Test(groups = {"smoke"})
    public void openSite() {
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs");
    }
}