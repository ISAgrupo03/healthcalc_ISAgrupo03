package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class HealthCalcImpl {
	
		private static HealthCalcImpl instance;
	
		private HealthCalcImpl() {
		}

		public static HealthCalcImpl getInstance() {
			if (instance == null) {
				instance = new HealthCalcImpl();
			}
			return instance;
		}

    public String bmiClassification(Person person) throws InvalidHealthDataException {
        double bmi=this.bmi(person); 
        
        if (bmi < 0) {
            throw new InvalidHealthDataException("El IMC no puede ser negativo.");
        }
        if (bmi > 150) {
            throw new InvalidHealthDataException("El IMC debe estar dentro de un rango posible [0-150].");
        }
        
        if (bmi < 16.0) {
            return "Delgadez severa";
        } else if (bmi < 17.0) {
            return "Delgadez moderada";
        } else if (bmi < 18.5) {
            return "Delgadez leve";
        } else if (bmi < 25.0) {
            return "Normal";
        } else if (bmi < 30.0) {
            return "Sobrepeso";
        } else if (bmi < 35.0) {
            return "Obesidad Clase I";
        } else if (bmi < 40.0) {
            return "Obesidad Clase II";
        } else {
            return "Obesidad Clase III";
        }
        
    }

    public double bmi(Person person) throws InvalidHealthDataException {
        double weight = person.weight();
        double height = person.height();

        if (weight <= 0) {
            throw new InvalidHealthDataException("El peso debe ser positivo.");
        }
        if (height <= 0) {
            throw new InvalidHealthDataException("La altura debe ser positiva.");
        }
        if (weight < 1 || weight > 700) {
            throw new InvalidHealthDataException("El peso debe estar dentro de un rango posible [1-700] kg.");
        }
        if (height < 0.30 || height > 3.00) {
            throw new InvalidHealthDataException("La altura debe estar dentro de un rango posible [0.30-3.00] m.");
        }
        return weight / Math.pow(height, 2);
    }

    public double harrisBenedict(Person person) throws InvalidHealthDataException {
        double weight = person.weight();
        double height = person.height()*100;
        Gender gender = person.gender();
        int age = person.age();

        if (gender==null) {
            throw new InvalidHealthDataException("El género no puede ser nulo.");
        }
        if (weight <= 0) {
            throw new InvalidHealthDataException("El peso debe ser positivo.");
        }
        if (height <= 0) {
            throw new InvalidHealthDataException("La altura debe ser positiva.");
        }
        if (age < 0) {
            throw new InvalidHealthDataException("La edad no puede ser negativa.");
        }
        if (weight < 1 || weight > 700) {
            throw new InvalidHealthDataException("El peso debe estar dentro de un rango posible [1-700] kg.");
        }
        if (height < 30 || height > 300) {
            throw new InvalidHealthDataException("La altura debe estar dentro de un rango posible [30-300] cm.");
        }
        if (age > 120) {
            throw new InvalidHealthDataException("La edad debe estar dentro de un rango posible [0-120] años.");
        }

        double bmr = 0.0;
        if (gender == Gender.MALE) {
            bmr = 88.362 + (13.397*weight) + (4.799*height) - (5.677*age);
        } else { 
            bmr = 447.593 + (9.247*weight) + (3.098*height) - (4.330*age);
        }
        return bmr;
    }

    public double idealBodyWeight(Person person) throws InvalidHealthDataException {
        double height = person.height()*100;
        Gender gender = person.gender();
        
        if (gender == null) {
            throw new InvalidHealthDataException("El género no puede ser nulo.");
        }
        
        if (height <= 0) {
            throw new InvalidHealthDataException("La altura debe ser positiva.");
        }
        if (height < 30 || height > 300) {
            throw new InvalidHealthDataException("La altura debe estar dentro de un rango posible [30-300] cm.");
        }

        double ibw = 0.0;
        if (gender == Gender.MALE) {
            ibw = (height - 100) - ((height - 150) / 4.0);
        } else { 
            ibw = (height - 100) - ((height - 150) / 2.0);
        }
        
        return ibw;
    }
}
