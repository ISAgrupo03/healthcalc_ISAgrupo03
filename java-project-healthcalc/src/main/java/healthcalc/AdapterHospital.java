package healthcalc;

public class AdapterHospital implements HealthHospital {
	
    private HealthCalc calc;

    public AdapterHospital(HealthCalc calc) {
        this.calc = calc;
    }

    @Override
    public int pesoCorporalIdeal(char genero, float altura) throws Exception {
    	
        double alturaCm = altura * 100.0;
        
        double ibwKilos = calc.idealBodyWeight(alturaCm, genero);
        
        return (int) (ibwKilos * 1000);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws Exception {
    	
        double pesoKilos = peso / 1000.0;
        
        double valorBmi = calc.bmi(pesoKilos, altura);
        String clasificacion = calc.bmiClassification(valorBmi);
        
        return new Tuple<>((float) valorBmi, clasificacion);
    }
}