package org.fw.testcases;

import com.microsoft.appcenter.appium.Factory;
import org.fw.pages.ExamplePage;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class ExampleTest extends BaseTest {

    @Rule
    public TestWatcher watcher = Factory.createWatcher();

    @Test
    public void exampleTestCase() {
        ExamplePage examplePage = new ExamplePage();

        examplePage.clickFirstCheckbox();
        examplePage.clickSecondCheckbox();
        examplePage.clickClickMeButton();
    }
}
