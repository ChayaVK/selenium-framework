package com.chaya.tests;

import com.chaya.base.BaseTest;
import com.chaya.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DropDowns extends BaseTest {

    @Test(groups = {"regression"})
    public void verifySortDropdownOptions() {
        ProductsPage products = loginAsStandardUser();

        // Default selection
        Assert.assertEquals(products.getSelectedSortOption(), "Name (A to Z)");

        // All options, in order
        List<String> expected = List.of("Name (A to Z)", "Name (Z to A)",
                                        "Price (low to high)", "Price (high to low)");
        Assert.assertEquals(products.getSortOptions(), expected);
    }

    @Test(groups = {"regression"})
    public void selectOptionChangesSelection() {
        ProductsPage products = loginAsStandardUser();
        products.sortBy("Price (high to low)");
        Assert.assertEquals(products.getSelectedSortOption(), "Price (high to low)");
    }
}
