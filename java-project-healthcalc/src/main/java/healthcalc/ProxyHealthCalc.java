package healthcalc;

import java.util.ArrayList;
import java.util.List;
import healthcalc.exceptions.InvalidHealthDataException;

public class ProxyHealthCalc implements HealthCalc, HealthStats {
    private HealthCalc calc;
    
    private List<Float> alturas;
    private List<Float> pesos;
    private List<Character> generos;
    private List<Float> imcs;
    
    private int totalPacientes; 

    public ProxyHealthCalc(HealthCalc calc) {
        this.calc = calc;
        this.alturas = new ArrayList<>();
        this.pesos = new ArrayList<>();
        this.generos = new ArrayList<>();
        this.imcs = new ArrayList<>();
        this.totalPacientes = 0;
    }

    @Override
    public String bmiClassification(double bmi) throws InvalidHealthDataException {
        return calc.bmiClassification(bmi);
    }
    
    @Override
    public double bmi(double weight, double height) throws InvalidHealthDataException {
        double res = calc.bmi(weight, height);

        pesos.add((float) weight);
        alturas.add((float) height);
        totalPacientes++;
        imcs.add((float) res);

        return res;
    }

    @Override
    public double idealBodyWeight(double height, char gender) throws InvalidHealthDataException {
        double res =  calc.idealBodyWeight(height, gender);
        alturas.add((float) (height / 100.0)); 
        generos.add(gender);
        totalPacientes++;
        
        return res;
    }

    @Override
    public double harrisBenedict(double weight, double height, char gender, int age) throws InvalidHealthDataException {
        double res = calc.harrisBenedict(weight, height, gender, age);
        pesos.add((float) weight);
        alturas.add((float) (height / 100.0)); //en m
        generos.add(gender);
        totalPacientes++;
        
        return res;
    }

    @Override
    public float alturaMedia() {
        if (alturas.isEmpty()) return 0f;
        float sum = 0;
        for (Float a : alturas) sum += a;
        return sum / alturas.size();
    }

    @Override
    public float pesoMedio() {
        if (pesos.isEmpty()) return 0f;
        float sum = 0;
        for (Float p : pesos) sum += p;
        return sum / pesos.size();
    }

    @Override
    public float imcMedio() {
        if (imcs.isEmpty()) return 0f;
        float sum = 0;
        for (Float i : imcs) sum += i;
        return sum / imcs.size();
    }

    @Override
    public int numSexoH() {
        int count = 0;
        for (Character g : generos) {
            if (g == 'M') count++;
        }
        return count;
    }

    @Override
    public int numSexoM() {
        int count = 0;
        for (Character g : generos) {
            if (g == 'W') count++;
        }
        return count;
    }

    @Override
    public int numTotalPacientes() {
        return totalPacientes;
    }
}
