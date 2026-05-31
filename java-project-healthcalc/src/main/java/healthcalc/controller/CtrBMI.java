package healthcalc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import healthcalc.BMICategory;
import healthcalc.BodyMassIndex;
import healthcalc.Gender;
import healthcalc.Patient;
import healthcalc.Person;
import healthcalc.exceptions.InvalidHealthDataException;
import healthcalc.view.ViewBMI;

public class CtrBMI implements ActionListener {

    private BodyMassIndex model;
    private ViewBMI view;

    public CtrBMI(BodyMassIndex model, ViewBMI view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("CalcularBMI")) {
            try {
                float weight = (float) view.getWeight();
                float height = (float) view.getHeightValue();

                Person patient = new Patient(weight, height, Gender.MALE, 30);
                
                float result = model.bodyMassIndex(patient);
                BMICategory category = model.category(patient);

                String clasificacion = "";
                switch (category) {
                    case SEVERE_THINNESS: clasificacion = "Delgadez severa"; break;
                    case MODERATE_THINNESS: clasificacion = "Delgadez moderada"; break;
                    case MILD_THINNESS: clasificacion = "Delgadez leve"; break;
                    case NORMAL: clasificacion = "Normal"; break;
                    case OVERWEIGHT: clasificacion = "Sobrepeso"; break;
                    case OBESE_CLASS_I: clasificacion = "Obesidad Clase I"; break;
                    case OBESE_CLASS_II: clasificacion = "Obesidad Clase II"; break;
                    case OBESE_CLASS_III: clasificacion = "Obesidad Clase III"; break;
                    default: clasificacion = category.toString();
                }
                
                view.setResult(result, clasificacion);
                
            } catch (NumberFormatException ex) {
                view.setMessage("Error: por favor, introduzca números válidos.");
            } catch (InvalidHealthDataException ex) {
                view.setMessage("Error: " + ex.getMessage());
            }
        }
    }
}