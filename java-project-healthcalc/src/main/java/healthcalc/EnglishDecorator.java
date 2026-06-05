package healthcalc;

public class EnglishDecorator extends BaseDecoratorIdioma {

    public EnglishDecorator(HealthHospital h) {
        super(h);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws Exception {
        Tuple<Float, String> res = super.indiceMasaCorporal(altura, peso);
        System.out.println(String.format("The person with height %.2f meters and %.2f Kg has a BMI of %.2f.", altura, (peso / 1000f), res.x));
        return res;
    }
}