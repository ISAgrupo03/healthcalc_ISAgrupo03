package healthcalc;

public abstract class BaseDecoratorIdioma implements HealthHospital {
    
    private HealthHospital hospitalCalc;

    public BaseDecoratorIdioma(HealthHospital h) {
        this.hospitalCalc = h;
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws Exception {
        return hospitalCalc.indiceMasaCorporal(altura, peso);
    }

    @Override
    public int pesoCorporalIdeal(char genero, float altura) throws Exception {
        return hospitalCalc.pesoCorporalIdeal(genero, altura);
    }
}