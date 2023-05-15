package org.fw.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExamplePage extends BasePage {

    @iOSXCUITFindBy(accessibility = "MakeRandomNumberCheckbox")
    IOSElement makeRandomNumberCheckbox;

    @iOSXCUITFindBy(accessibility = "SimulateDiffsCheckbox")
    IOSElement simulateDiffsCheckbox;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Click me!']")
    IOSElement clickMeBtn;

    public void clickFirstCheckbox() {
        click(makeRandomNumberCheckbox, "Unable to click to First check box");
    }

    public void clickSecondCheckbox() {
        click(simulateDiffsCheckbox, "Unable to click to 2nd check box");
    }

    public void clickClickMeButton() {
        click(clickMeBtn, "Unable to click to Click Me button");
    }
}
