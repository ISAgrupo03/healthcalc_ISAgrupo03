package healthcalc;

public class EnglishDecorator extends BaseDecoratorIdioma {

    public EnglishDecorator(HealthHospital h) {
        super(h);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws Exception {
        Tuple<Float, String> res = super.indiceMasaCorporal(altura, peso);
        System.out.println("Patient: Height= " + altura + ", Weight= " + peso + " -> BMI= " + String.format("%.2f", res.x) + " kg/m^2.");
        return res;
    }
}