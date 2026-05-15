package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class Main {
    public static void main(String[] args) {
        
        HealthCalc baseCalc = HealthCalcImpl.getInstance();
        ProxyHealthCalc objetoProxy = new ProxyHealthCalc(baseCalc);
        HealthCalc calculadora = objetoProxy;   
        HealthStats estadisticas = objetoProxy; 
        
        try {
            double weight = 75.0; 
            double height = 1.75; 
            char gender = 'M'; 
            int age = 30; 
            
            System.out.println("Data: Weight=" + weight +"kg, Height=" + height + "m, Gender=" + gender + ", Age=" + age + " years");
            
            double bmiValue = calculadora.bmi(weight, height);
            System.out.println("BMI: " + String.format("%.2f", bmiValue));
            
            String bmiClass = calculadora.bmiClassification(bmiValue);
            System.out.println("BMI classification: " + bmiClass);
            
            double ibw = calculadora.idealBodyWeight(height*100, gender); 
            System.out.println("Ideal body weight (IBW): " + String.format("%.2f", ibw) + " kg");
            
            double tmb = calculadora.harrisBenedict(weight, height*100, gender, age); 
            System.out.println("Basal metabolic rate (Harris-Benedict): " + String.format("%.2f", tmb) + " kcal/day");
            
        } catch (InvalidHealthDataException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("\nPATRÓN ADAPTER");
        HealthHospital hospitalCalc = new AdapterHospital(calculadora);
        
        try {
            float alturaHospital = 1.75f;
            int pesoGramos = 75000; 
            char generoHospital = 'M';
            
            System.out.println("Paciente registrado: Altura=" + alturaHospital + "m, Peso=" + pesoGramos + "g, Género=" + generoHospital);  
            
            Tuple<Float, String> bmiHospital = hospitalCalc.indiceMasaCorporal(alturaHospital, pesoGramos);
            System.out.println("BMI Hospital: " + String.format("%.2f", bmiHospital.x) + " kg/m^2 - " + bmiHospital.y);
            
            int ibwHospital = hospitalCalc.pesoCorporalIdeal(generoHospital, alturaHospital);
            System.out.println("IBW Hospital: " + ibwHospital + " g");
            
        } catch (Exception e) {
            System.err.println("Error en el sistema del hospital: " + e.getMessage());
        }

        System.out.println("\nESTADÍSTICAS DEL PROXY");
        System.out.println("Total pacientes: " + estadisticas.numTotalPacientes());
        System.out.println("Hombres: " + estadisticas.numSexoH());
        System.out.println("Mujeres: " + estadisticas.numSexoM());
        System.out.println("Peso medio: " + String.format("%.2f", estadisticas.pesoMedio()) + " kg");
        System.out.println("Altura media: " + String.format("%.2f", estadisticas.alturaMedia()) + " m");
        System.out.println("IMC medio: " + String.format("%.2f", estadisticas.imcMedio()));
    }
}