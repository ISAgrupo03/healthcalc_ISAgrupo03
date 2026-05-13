package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class Main {
    public static void main(String[] args) {
        HealthCalc healthCalc = HealthCalcImpl.getInstance();
        
        try {
            double weight = 75.0; // kg
            double height = 1.75; // m
            char gender = 'M'; // male
            int age = 30; // years

            System.out.println("Data: Weight=" + weight +"kg, Height=" + height + "m, Gender=" + gender + ", Age=" + age + " years");

            // BMI 
            double bmiValue = healthCalc.bmi(weight, height);
            System.out.println("BMI: " + String.format("%.2f", bmiValue));

            // BMI classification
            String bmiClass = healthCalc.bmiClassification(bmiValue);
            System.out.println("BMI classification: " + bmiClass);

            // IBW (ideal body weigth)
            double ibw = healthCalc.idealBodyWeight(height*100, gender); // m to cm
            System.out.println("Ideal body weight (IBW): " + String.format("%.2f", ibw) + " kg");

            // Harris-Benedict (BMR)
            double tmb = healthCalc.harrisBenedict(weight, height*100, gender, age); // m to cm
            System.out.println("Basal metabolic rate (Harris-Benedict): " + String.format("%.2f", tmb) + " kcal/day");

        } catch (InvalidHealthDataException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("\n");

        System.out.println("PATRÓN ADAPTER");
        HealthHospital hospitalCalc = new AdapterHospital(healthCalc);

        try{
            float alturaHospital =1.75f;
            int pesoGramos = 75000; // 75kg
            char generoHospital = 'M';

            System.out.println("Paciente registrado: Altura=" + alturaHospital + "m, Peso=" + pesoGramos + "g, Género=" + generoHospital);  

            Tuple<Float, String> bmiHospital = hospitalCalc.indiceMasaCorporal(alturaHospital, pesoGramos);
            System.out.println("BMI Hospital: " + String.format("%.2f", bmiHospital.x) + " kg/m^2 - " + bmiHospital.y);

            int ibwHospital = hospitalCalc.pesoCorporalIdeal(generoHospital, alturaHospital);
            System.out.println("IBW Hospital: " + (ibwHospital) + " g");

            } catch (Exception e) {
            System.err.println("Error en el sistema del hospital: " + e.getMessage());
            }
        }
    }
