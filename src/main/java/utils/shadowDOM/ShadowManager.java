package utils.shadowDOM;
import driver.DriverContext;
import io.github.sukgu.Shadow;

public class ShadowManager {
    private Shadow shadowDOM;

    public ShadowManager(){ shadowDOM = new Shadow(DriverContext.getDriver());}

    protected Shadow getShadowDOM(){ return shadowDOM; }
}