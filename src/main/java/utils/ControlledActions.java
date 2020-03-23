package utils;

import driver.DriverContext;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ControlledActions {
    /*
    PARA eSTA CLASE CORRESPONDEN LAS SIGUIENTES REGLAS CUANDO SEA NECESARIO CREAR ALGuN MeTODOS:
         1 - MANTENER UNA ESTRUCTURA DE COMENTARIOS SEGuN CORRESPONDA
                + @author: [Torre correspondiente] - [usuario del editor]
                + @@param: parametroDeEntrada describir funcionalidad de la variable declarada (agregar tantos @param como datos de entrada tenga el metodo)
                + @return: Descripcion del valor a retornar y su aplicacion
                + * Comentario descriptivo de ser necesario (opcional)
         2 - LOS METODOS DEBEN MANIPULAR ALGuN *WEBELEMENT*
         3 - DEBEN DECLARARSE COMO MeTODOS *STATIC*
         4 - LOS METODOS NO DEBEN TENER NINGuN TIPO DE REPORTE.
         5 - PUEDEN TENER MENSAJES POR CONSOLA
    */

    /**************************************************************************************************
     * @author: Alcantara Tower
     * @param objeto Elemento esperado de una pagina
     * @param segundos Tiempo de espera para detener el driver
     * @return: retorna un valor booleano en caso de que el elemento exista
     **************************************************************************************************/
    public static boolean exists(WebElement objeto, long segundos){
        try{
            WebDriverWait wait = new WebDriverWait(DriverContext.getDriver(), segundos);
            wait.until(ExpectedConditions.visibilityOf(objeto));
            System.out.println("Objeto Encontrado: ["+objeto+"].");
            return true;
        }catch(Exception e){
            System.out.println("No fue posible reconocer el objeto...");
            return false;
        }
    }


    /**************************************************************************************************
     * @author: Alcantara Tower
     * @param objeto Elemento visible dentro de la pagina
     * @return: retorna un valor booleano en caso de que el elemento exista
     **************************************************************************************************/
    public static boolean isDisplayedElement(WebElement objeto){
        try{
            if(objeto.isDisplayed()){
                System.out.println("El elemento es visible --> [" + objeto + "]");
                return true;
            }else {
                System.out.println("El elemento no es visible, pero existe dentro de la pagina.");
                return false;
            }
        }catch(Exception e){
            System.out.println("No fue posible saber si el elemento esta presente. " + e.getMessage());
            return false;
        }
    }


    /**************************************************************************************************
     * @author: Alcantara Tower
     * @param objeto Elemento visible dentro de la pagina
     * Realiza ciclos de espera hasta que el elemento se presente en pantalla
     **************************************************************************************************/
    public static void loading(WebElement objeto, int tiempo){
        try{
            long tiempoStart = System.currentTimeMillis();
            long tiempoEnd = tiempoStart + 60*1000; // 60 seconds * 1000 ms/sec

            do {
                if (System.currentTimeMillis() >= tiempoEnd){
                    System.out.println("El tiempo de espera supera el tiempo limite.");
                }
                Duration.ofMillis(100);
            }while (exists(objeto, tiempo));
        }catch (NoSuchElementException ex){
            System.out.println("[ControlledActions] Error: No se pudo encontrar elemento "+ex.getMessage());
        }
    }


    /**************************************************************************************************
     * @author: Alcantara Tower
     * @param objeto Elemento visible dentro de la pagina
     * Realiza ciclos de espera hasta que el elemento desaparezca de la pantalla
     **************************************************************************************************/
    public static void loadingNegative(WebElement objeto, int tiempo){
        try{
            long tiempoStart = System.currentTimeMillis();
            long tiempoEnd = tiempoStart + 60*1000; // 60 seconds * 1000 ms/sec

            do {
                if (System.currentTimeMillis() >= tiempoEnd){
                    System.out.println("El tiempo de espera supera el tiempo limite.");
                }
                Duration.ofMillis(100);
            }while (!exists(objeto, tiempo));

        }catch (NoSuchElementException ex){
            System.out.println("[ControlledActions] Error: No se pudo encontrar elemento "+ex.getMessage());
        }
    }


    /**************************************************************************************************
     * @author: Holanda Tower - exgmart
     * @param objeto Elemento donde sera arrastrado el cursor del mouse
     **************************************************************************************************/
    public static void movePointerOverElement(WebElement objeto){
        Point coordenadas = objeto.getLocation();
        Actions mouse = new Actions(DriverContext.getDriver());
        mouse.moveByOffset(coordenadas.getX(), coordenadas.getY());
    }


    /**************************************************************************************************
     * @author: Holanda Tower - exgmart
     * @param objeto que se encuentra fuera de la pantalla visible del navegador
     **************************************************************************************************/
    public static void moveScreenToElement(WebElement objeto){
        Actions pantalla = new Actions(DriverContext.getDriver());
        pantalla.moveToElement(objeto);
    }


    /**************************************************************************************************
     * @author: Holanda Tower - exgmart
     * @param iframe Frame al que se realizara el cambio
     * Recordar volver al frame principal para continuar con la prueba
     **************************************************************************************************/
    public static void switchToIFrame(WebElement iframe){
        DriverContext.getDriver().switchTo().frame(iframe);
    }
}