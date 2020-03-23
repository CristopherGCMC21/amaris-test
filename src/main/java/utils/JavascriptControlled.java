package utils;

import driver.DriverContext;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class JavascriptControlled {
    /*
    PARA eSTA CLASE CORRESPONDEN LAS SIGUIENTES REGLAS CUANDO SEA NECESARIO CREAR ALGuN MeTODOS:
         1 - MANTENER UNA ESTRUCTURA DE COMENTARIOS SEGuN CORRESPONDA
                + @author: [Torre correspondiente] - [usuario del editor]
                + @@param: parametroDeEntrada describir funcionalidad de la variable declarada (agregar tantos @param como datos de entrada tenga el metodo)
                + @return: Descripcion del valor a retornar y su aplicacion
                + * Comentario descriptivo de ser necesario (opcional)
         3 - DEBEN DECLARARSE COMO MeTODOS *STATIC*
         4 - LOS METODOS DEBEN MANIPULAR ELEMENTOS JAVASCRIPT
    */

    private static WebDriverException exception;
    private static WebDriverWait wait;
    private static CommandExecutor executer;
    private static SessionId sessionId;
    private static ChromeDriver chromeDriver;
    private static FirefoxDriver firfoxDriver;
    private static InternetExplorerDriver ieDriver;


    /**************************************************************************************************
     * @author: Holanda tower - exgmart
     * @param cssSelector Identificador con el cual se hara la busqueda del objeto
     * @return: retorna un webelement o un elemento null si no se logra encontrar el objeto
     **************************************************************************************************/
    public static WebElement getShadowElement(String cssSelector){
        WebElement element = null;
        element = (WebElement) executerGetObject("return getObject(\""+cssSelector+"\");");
        fixLocator(DriverContext.getDriver(), cssSelector, element);
        return element;
    }


    /**************************************************************************************************
     * @author: Holanda tower - exgmart
     * Espera a que la pagina complete su carga javascript
     * (Existen elementos que vienen precargados en las paginas por lo que se debe saber con que tipo de pagina se esta interactuando)
     **************************************************************************************************/
    private static void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        Utils.pausa(1);
        WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), 30);
        wait.until(expectation);
    }


    /**************************************************************************************************
     * @author: Shadow library
     * @param script codigo javascript que se ejecutara en la pagina
     **************************************************************************************************/
    private static Object executerGetObject(String script) {
        String javascript = convertJStoText().toString();
        javascript += script;
        return injectShadowExecuter(javascript);
    }


    /**************************************************************************************************
     * @author: Shadow library
     * @param script codigo javascript que se ejecutara en la pagina (CSS SELECTOR + COMANDO JS)
     **************************************************************************************************/
    private static Object executerGetObject(String script, WebElement element) {
        String javascript = convertJStoText().toString();
        javascript += script;
        return injectShadowExecuter(javascript, element);
    }


    /**************************************************************************************************
     * @author: Shadow library
     * Convierte el codigo javascript en un formato legible
     **************************************************************************************************/
    private static StringBuilder convertJStoText() {
        InputStream in = JavascriptControlled.class.getResourceAsStream("/querySelector.js");
        BufferedReader reader = null;
        StringBuilder text = new StringBuilder();
        reader = new BufferedReader(new InputStreamReader(in));
        if(reader!=null) {
            try {
                while(reader.ready()) {
                    text.append(reader.readLine());
                }
            }
            catch (IOException e) { e.printStackTrace(); }
        }
        if(reader!=null) {
            try { reader.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        return text;
    }


    /**************************************************************************************************
     * @author: Shadow library
     * @param javascript codigo javascript que se injectara al webdriver
     * @return
     **************************************************************************************************/
    private static Object injectShadowExecuter(String javascript) {
        if(DriverContext.getDriver() instanceof ChromeDriver) {
            sessionId  = ((ChromeDriver) DriverContext.getDriver()).getSessionId();
            chromeDriver = (ChromeDriver) DriverContext.getDriver();
            JavascriptExecutor js = (JavascriptExecutor)chromeDriver;
            waitForPageLoaded();
            return js.executeScript(javascript);

        } else if (DriverContext.getDriver() instanceof FirefoxDriver) {
            sessionId  = ((FirefoxDriver) DriverContext.getDriver()).getSessionId();
            firfoxDriver = (FirefoxDriver) DriverContext.getDriver();
            waitForPageLoaded();
            return firfoxDriver.executeScript(javascript);

        } else if (DriverContext.getDriver() instanceof InternetExplorerDriver) {
            sessionId  = ((InternetExplorerDriver) DriverContext.getDriver()).getSessionId();
            ieDriver = (InternetExplorerDriver) DriverContext.getDriver();
            waitForPageLoaded();
            return ieDriver.executeScript(javascript);
        } else {
            return null;
        }
    }


    /**************************************************************************************************
     * @author: Shadow library
     * @param javascript codigo javascript que se injectara al webdriver
     * @return
     **************************************************************************************************/
    private static Object injectShadowExecuter(String javascript, WebElement element) {
        if(chromeDriver!=null) {
            JavascriptExecutor js = (JavascriptExecutor)chromeDriver;
            waitForPageLoaded();
            return js.executeScript(javascript, element);
        } else if (firfoxDriver!=null) {
            waitForPageLoaded();
            return firfoxDriver.executeScript(javascript, element);
        } else if (ieDriver!=null) {
            waitForPageLoaded();
            return ieDriver.executeScript(javascript, element);
        } else {
            return null;
        }
    }


    /**************************************************************************************************
     * @author: Shadow library
     * @param context
     * @param cssLocator
     * @param element
     **************************************************************************************************/
    private static void fixLocator(SearchContext context, String cssLocator, WebElement element) {
        if (element instanceof RemoteWebElement) {
            try {
                Class[] parameterTypes = new Class[] {SearchContext.class, String.class, String.class};
                Method m = element.getClass().getDeclaredMethod("setFoundBy", parameterTypes);
                m.setAccessible(true);
                Object[] parameters = new Object[] {context, "cssSelector", cssLocator};
                m.invoke(element, parameters);
            } catch (Exception e) {
                System.out.println("fixLocator Error --> [" + e.getMessage() + "]");
            }
        }
    }
}
