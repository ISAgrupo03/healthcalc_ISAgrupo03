package healthcalc;

public class AmericanDecorator extends BaseDecoratorVersion {

    public AmericanDecorator(HealthHospital h) {
        super(h);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float alturaPies, int pesoLibras) throws Exception {
        float metros = (alturaPies * 12) / 39.37f;
        int gramos = (int) ((pesoLibras / 2.20462f) * 1000);
        return super.indiceMasaCorporal(metros, gramos);
    }

    @Override
    public int pesoCorporalIdeal(char genero, float alturaPies) throws Exception {
        float metros = (alturaPies * 12) / 39.37f;
        return super.pesoCorporalIdeal(genero, metros);
    }
}