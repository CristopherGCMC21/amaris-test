package utils.shadowDOM;

import io.github.sukgu.Shadow;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import reporter.EstadoPrueba;
import reporter.PdfBciReports;

import java.util.List;


public class ShadowContext {
    private static ShadowManager shadowManager = new ShadowManager();
    private static WebElement tempWebElement;
    private static List<WebElement> tempWebElements;

    public ShadowContext(){}


    private static Shadow getShadowManager(){ return shadowManager.getShadowDOM(); }


    public static WebElement getElement(String cssSelector){
        try {
            tempWebElement = shadowManager.getShadowDOM().findElement(cssSelector);
            if (tempWebElement == null) {
                System.out.println("<<No se encuentra el objeto.>>");
            }
        }catch(InvalidElementStateException ie){
            tempWebElement = null;
        }
        return tempWebElement;
    }

    public static List<WebElement> getElements(String cssSelector){
        try {
            tempWebElements = shadowManager.getShadowDOM().findElements(cssSelector);
            if ((tempWebElements.isEmpty()) || (tempWebElements.size() < 1)) {
                System.out.println("<<No se encuentra el objeto.>>");
            }
        }catch(InvalidElementStateException ie){
            tempWebElement = null;
        }
        return tempWebElements;
    }

    public static void clickButton(String cssSelector){
        tempWebElement = shadowManager.getShadowDOM().findElement(cssSelector);
        if(tempWebElement == null) { PdfBciReports.addReport("Shadow DOM", "No fue posible encontrar el objeto a clickear.", EstadoPrueba.FAILED, true); }
    }

    public static void InText(String cssSelector, String inText){
        tempWebElement = shadowManager.getShadowDOM().findElement(cssSelector);
        if(tempWebElement == null) { PdfBciReports.addReport("Shadow DOM", "No fue posible encontrar el objeto.", EstadoPrueba.FAILED, true); }
        tempWebElement.click();
        tempWebElement.clear();
        tempWebElement.sendKeys(inText);
    }

    public static void validateText(String cssSelector, String compareText){
        tempWebElement = shadowManager.getShadowDOM().findElement(cssSelector);
        if(tempWebElement == null) { PdfBciReports.addReport("Shadow DOM", "No fue posible encontrar el objeto a comparar.", EstadoPrueba.FAILED, true); }

        if(tempWebElement.getText().compareTo(compareText) != 0) { PdfBciReports.addReport("Shadow DOM", "Los textos no coinciden.", EstadoPrueba.FAILED, true); }
        else{ PdfBciReports.addReport("Shadow DOM", "El textodel elemento coincide con el texto esperado.", EstadoPrueba.FAILED, true); }
    }

    private static void selectItemDropDownList(String cssSelector, String item){
        tempWebElement = shadowManager.getShadowDOM().findElement(cssSelector);
        if(tempWebElement == null) { PdfBciReports.addReport("Shadow DOM", "No fue posible encontrar la lista.", EstadoPrueba.FAILED, true); }
        tempWebElement.getTagName();
        shadowManager.getShadowDOM().selectDropdown(tempWebElement, item);
    }

    private static void selectRadioButton(String cssSelector, String item){
        tempWebElement = shadowManager.getShadowDOM().findElement(cssSelector);
        if(tempWebElement == null) { PdfBciReports.addReport("Shadow DOM", "No fue posible encontrar el radio button.", EstadoPrueba.FAILED, true); }
        shadowManager.getShadowDOM().selectRadio(tempWebElement, item);
    }

    private static void selectCheckBox(String cssSelector, String item){
        tempWebElement = shadowManager.getShadowDOM().findElement(cssSelector);
        if(tempWebElement == null) { PdfBciReports.addReport("Shadow DOM", "No fue posible encontrar el check.", EstadoPrueba.FAILED, true); }
        shadowManager.getShadowDOM().selectCheckbox(tempWebElement, item);
    }
}