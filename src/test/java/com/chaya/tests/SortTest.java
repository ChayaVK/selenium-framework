package com.chaya.tests;

import com.chaya.base.BaseTest;
import com.chaya.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTest extends BaseTest {

    @Test(groups = {"regression"})
    public void sortByPriceLowToHigh() {
        ProductsPage products = loginAsStandardUser();
        products.sortBy("Price (low to high)");

        List<Double> actual = products.getProductPrices();   // what the page shows
        List<Double> expected = new ArrayList<>(actual);     // 1. copy
        Collections.sort(expected);                          // 2. sort the copy

        Assert.assertEquals(actual, expected, "Prices are not sorted low to high");  // 3. compare
    }

    @Test(groups = {"regression"})
    public void sortByNameZtoA() {
        ProductsPage products = loginAsStandardUser();
        products.sortBy("Name (Z to A)");

        List<String> actual = products.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());           // Z -> A

        Assert.assertEquals(actual, expected, "Names are not sorted Z to A");
    }
}
