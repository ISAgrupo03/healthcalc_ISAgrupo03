package healthcalc;

public class SpanishDecorator extends BaseDecoratorIdioma {

    public SpanishDecorator(HealthHospital h) {
        super(h);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws Exception {
        Tuple<Float, String> res = super.indiceMasaCorporal(altura, peso);
        System.out.println("Paciente: Altura= " + altura + ", Peso= " + peso + " -> IMC= " + String.format("%.2f", res.x) + " kg/m^2.");
        return res;
    }
}