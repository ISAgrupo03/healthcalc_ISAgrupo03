package healthcalc.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import healthcalc.BasalMetabolicRate;
import healthcalc.Gender;
import healthcalc.HealthCalcImpl;
import healthcalc.Patient;
import healthcalc.Person;
import healthcalc.exceptions.InvalidHealthDataException;

/**
 * @author antoniodoa9 (ISAgrupo03)
 */
public class HarrisBenedictSteps {

    private BasalMetabolicRate healthCalc;
    private Person paciente;
    private double resultado;
    private boolean exceptionThrown;
    private String generoIngresado;

    @Given("la calculadora de salud está iniciada")
    public void la_calculadora_de_salud_esta_iniciada() {
        healthCalc = HealthCalcImpl.getInstance();
    }

    @Given("los datos del paciente son peso {double} kg, altura {double} cm, edad {int} años y género {string}")
    public void los_datos_del_paciente_son(Double peso, Double altura, Integer edad, String genero) {
        this.generoIngresado = genero;
        Gender genderEnum = genero.equals("M") ? Gender.MALE : Gender.FEMALE;
        this.paciente = new Patient(peso.floatValue(), (float)(altura / 100.0), genderEnum, edad);
    }

    @When("ejecuto el cálculo de la TMB")
    public void ejecuto_el_calculo_de_la_tmb() {
        try {
            if (!generoIngresado.equals("M") && !generoIngresado.equals("W")) {
                throw new InvalidHealthDataException("Género inválido");
            }
            resultado = healthCalc.basalMetabolicRate(paciente);
            exceptionThrown = false;
        } catch (InvalidHealthDataException e) {
            exceptionThrown = true;
        }
    }

    @Then("el resultado de la TMB debe ser {double} con una tolerancia de {double}")
    public void el_resultado_de_la_tmb_debe_ser(Double esperado, Double tolerancia) {
        assertEquals(esperado, resultado, tolerancia);
    }

    @Then("el sistema debe lanzar una excepción indicando que los datos de salud son inválidos")
    public void el_sistema_debe_lanzar_una_excepcion() {
        assertEquals(true, exceptionThrown);
    }
}
