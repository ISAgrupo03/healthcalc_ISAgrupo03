package healthcalc;

import java.util.ArrayList;
import java.util.List;
import healthcalc.exceptions.InvalidHealthDataException;

public class ProxyHealthCalc implements BodyMassIndex, IdealBodyWeight, BasalMetabolicRate, HealthStats {
    private HealthCalcImpl calc;
    
    private List<Float> alturas;
    private List<Float> pesos;
    private List<Character> generos;
    private List<Float> imcs;
    
    private int totalPacientes; 

    public ProxyHealthCalc(HealthCalcImpl calc) {
        this.calc = calc;
        this.alturas = new ArrayList<>();
        this.pesos = new ArrayList<>();
        this.generos = new ArrayList<>();
        this.imcs = new ArrayList<>();
        this.totalPacientes = 0;
    }

    @Override
    public BMICategory category(Person person)throws InvalidHealthDataException {
        return calc.category(person);
    }
    
    @Override
    public float bodyMassIndex(Person person) throws InvalidHealthDataException {
        float res = calc.bodyMassIndex(person);

        pesos.add(person.weight());
        alturas.add(person.height());
        totalPacientes++;
        imcs.add(res);

        return res;
    }

    @Override
    public float idealBodyWeight(Person person) throws InvalidHealthDataException {
        float res =  calc.idealBodyWeight(person);
        alturas.add(person.height());
        char gen=(person.gender()==Gender.MALE) ? 'M' : 'W';
        generos.add(gen);
        totalPacientes++;
        
        return res;
    }

    @Override
    public float basalMetabolicRate(Person person) throws InvalidHealthDataException {
        float res = calc.basalMetabolicRate(person);
        pesos.add(person.weight());
        alturas.add(person.height());
        char gen=(person.gender()==Gender.MALE) ? 'M' : 'W';
        generos.add(gen);
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
