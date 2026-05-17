package healthcalc;

public class SpanishDecorator extends BaseDecoratorIdioma {

    public SpanishDecorator(HealthHospital h) {
        super(h);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws Exception {
        Tuple<Float, String> res = super.indiceMasaCorporal(altura, peso);
        System.out.println(String.format("La persona con altura %.2f metros y %.2f Kg tiene un IMC de %.2f.", altura, (peso / 1000f), res.x));
        return res;
    }
}