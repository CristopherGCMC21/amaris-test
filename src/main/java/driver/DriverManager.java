package driver;

import constants.Navegador;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import reporter.EstadoPrueba;
import reporter.PdfBciReports;

import java.io.File;

public class DriverManager {
    private DesiredCapabilities capabilities = new DesiredCapabilities();
    private WebDriver webDriver;
    private File root = new File("DriverNavegador");
    private String extensionDriver = "";

    protected void resolveDriver(Navegador nav,String ambURL) {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("\nSistema operativo ->"+System.getProperty("os.name").toLowerCase()+"\n");
        File driverPath;
        //windows o mac
        if(!os.contains("mac")){
            extensionDriver = ".exe";
        }

        switch(nav) {
            case Chrome:
                System.out.println("Se selecciona Chrome");
                driverPath = new File(root, "chromedriver"+extensionDriver);
                System.setProperty("webdriver.chrome.driver", driverPath.getAbsolutePath());
                webDriver = new ChromeDriver();
                capabilities.setBrowserName("Chrome");
                break;
            case IExplorer:
                System.out.println("Se selecciona Explorer");
                driverPath = new File(root, "IEDriverServer"+extensionDriver);
                System.setProperty("webdriver.ie.driver", driverPath.getAbsolutePath());
                webDriver = new FirefoxDriver();
                capabilities.setBrowserName("Explorer");
                break;
            case Firefox:
                System.out.println("Se selecciona Firefox");
                driverPath = new File(root, "geckodriver"+extensionDriver);
                System.setProperty("webdriver.gecko.driver", driverPath.getAbsolutePath());
                webDriver = new FirefoxDriver();
                capabilities.setBrowserName("Firefox");
                break;
            case Edge:
                System.out.println("Se selecciona Edge");
                driverPath = new File(root, "falta"+extensionDriver);
                System.setProperty("webdriver.edge.driver", driverPath.getAbsolutePath());
                webDriver = new EdgeDriver();
                capabilities.setBrowserName("Microsoft Edge");
                break;
            default:
                System.out.println("No es posible lanzar el navegador, no se reconoce el navegador: "+nav);
                PdfBciReports.addReport("resolveDriver", "No se reconoce el navegador: "+nav, EstadoPrueba.FAILED, true);
                break;
        }
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();

        PdfBciReports.addReport("resolveDriver", "Selecciona el navegador: "+nav, EstadoPrueba.DONE, false);
        webDriver.get(ambURL);
    }

    protected WebDriver getDriver() { return webDriver; }
    protected void setDriver(WebDriver webDriver) { this.webDriver = webDriver; }
    protected Dimension getScreenSize() {
        return webDriver.manage().window().getSize();
    }
}