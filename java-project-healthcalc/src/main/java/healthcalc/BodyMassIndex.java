package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public interface BodyMassIndex {

    float bodyMassIndex(Person person) throws InvalidHealthDataException;

    BMICategory category(Person person) throws InvalidHealthDataException;
    
}
