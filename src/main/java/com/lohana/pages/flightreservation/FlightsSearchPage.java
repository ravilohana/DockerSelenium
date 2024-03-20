package com.lohana.pages.flightreservation;

import com.lohana.pages.AbstractPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class FlightsSearchPage extends AbstractPage {

    @FindBy(id = "passengers")
    private WebElement passengerSelect;

    @FindBy(id = "search-flights")
    private WebElement searchFlightsButton;

    public FlightsSearchPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        this.wait.until(ExpectedConditions.visibilityOf(this.passengerSelect));
        return this.passengerSelect.isDisplayed();
    }

    public void selectPassengers(String noOfPassengers){
        Select passengers = new Select(this.passengerSelect);
        passengers.selectByValue(noOfPassengers);
    }

    public FlightsSelectionPage searchFlights(){
        this.wait.until(ExpectedConditions.elementToBeClickable(this.searchFlightsButton));
       this.searchFlightsButton.click();

       return new FlightsSelectionPage(driver);

//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.searchFlightsButton);
//        this.searchFlightsButton.click();

    }

}
