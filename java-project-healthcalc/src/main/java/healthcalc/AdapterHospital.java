package healthcalc;

public class AdapterHospital implements HealthHospital {
	
    private ProxyHealthCalc calc;

    public AdapterHospital(ProxyHealthCalc calc) {
        this.calc = calc;
    }

    @Override
    public int pesoCorporalIdeal(char genero, float altura) throws Exception {
    	
        Gender genEnum= (Character.toUpperCase(genero)=='M') ? Gender.MALE : Gender.FEMALE;

        Person patient= new Patient(70.0f, altura, genEnum, 30); 

        float ibwKilos = calc.idealBodyWeight(patient);

        return (int) (ibwKilos * 1000);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws Exception {
    	
        float pesoKilos = (float) (peso / 1000.0);

        Person patient = new Patient(pesoKilos, altura, Gender.MALE, 30);
        
        float valorBmi = calc.bodyMassIndex(patient);
        String clasificacion = calc.category(patient).toString();
        
        return new Tuple<>((float) valorBmi, clasificacion);
    }
}