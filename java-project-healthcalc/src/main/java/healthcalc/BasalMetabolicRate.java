package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public interface BasalMetabolicRate {

    float basalMetabolicRate(Person person) throws InvalidHealthDataException;
    
}
