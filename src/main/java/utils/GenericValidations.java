package utils;

import driver.DriverContext;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reporter.EstadoPrueba;
import reporter.PdfBciReports;


public class GenericValidations {
    /*
    PARA eSTA CLASE CORRESPONDEN LAS SIGUIENTES REGLAS CUANDO SEA NECESARIO CREAR ALGÚN MeTODOS:
         1 - MANTENER UNA ESTRUCTURA DE COMENTARIOS SEGÚN CORRESPONDA
                + @author: [Torre correspondiente] - [usuario del editor]
                + @@param: parametroDeEntrada describir funcionalidad de la variable declarada (agregar tantos @param como datos de entrada tenga el metodo)
                + @return: Descripcion del valor a retornar y su aplicacion
                + * Comentario descriptivo de ser necesario (opcional)
         2 - LOS METODOS DEBEN MANIPULAR ALGÚN *WEBELEMENT*
         3 - DEBEN DECLARARSE COMO MeTODOS *STATIC*
         4 - LOS METODOS DEBEN LLEVAR REPORTES.
         5 - DEBEN POSEER UNA VARIABLE BOOLEAN DE ENTRADA LLAMADA *imageReport* PARA REALIZAR VALIDACIoN DE IMAGEN
    */

    /**************************************************************************************************
     * @author: Holanda tower - exgmart
     * @param objeto Elemento esperado de una pagina
     * @param imageReport booleano que indica si se realizara un reporte con imagen
     * @return: retorna un valor booleano en caso de encontrar el elemento dentro de la pagina (Arrojara una exception si este no esta en la pagina).
     **************************************************************************************************/
    @Step("Validacion de objeto en pantalla")
    public static boolean existElementReport(WebElement objeto, boolean imageReport){
        if(ControlledActions.exists(objeto, 1)) {
            reporteConYSinImagen(imageReport, "Validacion de elemento en pantalla", "El elemento se muestra correctamente en la pagina.", EstadoPrueba.PASSED, false);
            return true;
        } else{
            reporteConYSinImagen(imageReport, "Validacion de elemento en pantalla", "El elemento no es visible en la pagina.", EstadoPrueba.FAILED, false);
            return false;
        }
    }

    @Step("Validacion de objeto en pantalla")
    public static boolean existElementReport(WebElement objeto, boolean imageReport, String step){
        if(ControlledActions.exists(objeto, 1)) {
            reporteConYSinImagen(imageReport, step, "El elemento se muestra correctamente en la pagina.", EstadoPrueba.PASSED, false);
            return true;
        } else{
            reporteConYSinImagen(imageReport, step, "El elemento no es visible en la pagina.", EstadoPrueba.FAILED, false);
            return false;
        }
    }


    /**************************************************************************************************
     * @author: Holanda tower - exgmart
     * @param objeto Elemento a validar
     * @param estadoEsperado Indica el valor esperado del elemento.
     * @param imageReport booleano que indica si se realizara un reporte con imagen
     * @return: retorna un valor booleano en caso de que el elemento cumpla o no con el estado esperado
     **************************************************************************************************/
    @Step("Validacion de objeto habilitado")
    public static boolean isEnableWithReport(WebElement objeto, boolean estadoEsperado, boolean imageReport){
        String esperado = "", defaul = "";

        if(estadoEsperado){
            esperado = "habilitado";
            defaul = "deshabilitado";
        }else{
            esperado = "deshabilitado";
            defaul = "habilitado";
        }

        if(objeto.isEnabled() == estadoEsperado){
            reporteConYSinImagen(imageReport, "Validacion de elemento habilitado", "El elemento se muestra " + esperado + ".", EstadoPrueba.PASSED, false);
            return true;
        }else{
            reporteConYSinImagen(imageReport,"Validacion de elemento habilitado", "El elemento actualmente se presenta " + defaul + ".", EstadoPrueba.FAILED, false);
            return false;
        }
    }

    @Step("Validacion de objeto habilitado")
    public static boolean isEnableWithReport(WebElement objeto, boolean estadoEsperado, boolean imageReport, String step){
        String esperado = "", defaul = "";

        if(estadoEsperado){
            esperado = "habilitado";
            defaul = "deshabilitado";
        }else{
            esperado = "deshabilitado";
            defaul = "habilitado";
        }

        if(objeto.isEnabled() == estadoEsperado){
            reporteConYSinImagen(imageReport, step, "El elemento se muestra " + esperado + ".", EstadoPrueba.PASSED, false);
            return true;
        }else{
            reporteConYSinImagen(imageReport,step, "El elemento actualmente se presenta " + defaul + ".", EstadoPrueba.FAILED, false);
            return false;
        }
    }

    /**************************************************************************************************
     * @author: Holanda tower - exgmart
     * @param elemento Elemento del que se obtendra el texto desde el front
     * @param texto Valor con el cual sera comparado el texto obtenido del elemento
     * @param imageReport booleano que indica si se realizara un reporte con imagen
     * @return: retorna un valor booleano en caso de que los textos coincidan o no lo hagan
     **************************************************************************************************/
    @Step("Validacion de texto")
    public static boolean compareTextWithReport(WebElement elemento, String texto, boolean imageReport){
        String textoObtenido = elemento.getText();
        if(textoObtenido.equals(texto)){
            reporteConYSinImagen(imageReport, "Los textos coinciden: " + elemento.getText(), "El texto obtenido del elemento es el texto esperado.", EstadoPrueba.PASSED, false);
            return true;
        }else {
            reporteConYSinImagen(imageReport, "Los textos no coinciden.", "Los textos comparados no coinciden.", EstadoPrueba.FAILED, false);
            return false;
        }
    }

    @Step("Validacion de texto")
    public static boolean compareTextWithReport(WebElement elemento, String texto, boolean imageReport, String step){
        String textoObtenido = elemento.getText();
        if(textoObtenido.equals(texto)){
            reporteConYSinImagen(imageReport, step, "El texto obtenido del elemento es el texto esperado.", EstadoPrueba.PASSED, false);
            return true;
        }else {
            reporteConYSinImagen(imageReport, step, "Los textos comparados no coinciden.", EstadoPrueba.FAILED, false);
            return false;
        }
    }


    /**************************************************************************************************
     * @author: Holanda tower - exgmart
     * @param imageReport booleano que indica si se realizara un reporte con imagen
     * @param nombrePaso nombre del paso que se esta realizando (parametro agregado dependiendo del metodo donde se utilice)
     * @param descripcionPaso descripcion del paso que seta validando (parametro agregado dependiendo del metodo donde se utilice)
     * @param estadoPrueba Estado que se agregara al reporte
     * @param fatal Indica si la prueba se detendra o no
     * @return: No tiene retorno ya que su funcionalidad es generar un reporte en base a los metodos de validacion
     **************************************************************************************************/
    @Step("Eleccion de reporte con o sin imagen")
    private static void reporteConYSinImagen(boolean imageReport, String nombrePaso, String descripcionPaso, EstadoPrueba estadoPrueba, boolean fatal){
        if(!imageReport){
            PdfBciReports.addReport(nombrePaso, descripcionPaso, estadoPrueba, fatal);
        }else{
            PdfBciReports.addWebReportImage(nombrePaso, descripcionPaso, estadoPrueba, fatal);
        }
    }
}