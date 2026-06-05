package healthcalc.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import healthcalc.BMICategory;
import healthcalc.BodyMassIndex;
import healthcalc.Gender;
import healthcalc.HealthCalcImpl;
import healthcalc.Patient;
import healthcalc.Person;
import healthcalc.exceptions.InvalidHealthDataException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author palomamtnz13 (ISAgrupo03)
 */
public class BMISteps {
    private BodyMassIndex healthCalc;
    private Person paciente;
    private double imcResultado;    
    private BMICategory categoriaResultado;
    private boolean exceptionThrown;

    @Given("la calculadora de salud está iniciada para el IMC")
    public void la_calculadora_de_salud_esta_iniciada_para_el_imc() {
        healthCalc = HealthCalcImpl.getInstance();
    }

    // CÁLCULO DEL IMC

    @Given("los datos del paciente son peso {double} kg y altura {double} m")
    public void los_datos_del_paciente_son(Double peso, Double altura) {
        this.paciente = new Patient(peso.floatValue(), altura.floatValue(), Gender.MALE, 30);
    }

    @When("ejecuto el cálculo del IMC")
    public void ejecuto_el_calculo_del_imc() {
        try {
            imcResultado = healthCalc.bodyMassIndex(paciente);
            exceptionThrown = false;
        } catch (InvalidHealthDataException e) {
            exceptionThrown = true;
        }
    }

    @Then("el resultado del IMC debe ser {double} con una tolerancia de {double}")
    public void el_resultado_del_imc_debe_ser(Double imcEsperado, Double tolerancia) {
        assertEquals(imcEsperado, imcResultado, tolerancia);
    }

    // CLASIFICACIÓN DEL IMC

    @Given("el valor calculado de IMC es {double}")
    public void el_valor_calculado_de_imc_es(Double imc) {
        // Truco: para pasarle un Person al método, creamos un paciente ficticio 
        // con altura 1.0m. Así, si su peso es igual al imc, el cálculo dará exacto.
        this.paciente = new Patient(imc.floatValue(), 1.0f, Gender.MALE, 30);
    }

    @When("ejecuto la clasificación del IMC")
    public void ejecuto_la_clasificacion_del_imc() {
        try {
            categoriaResultado = healthCalc.category(paciente);
            exceptionThrown = false;
        } catch (InvalidHealthDataException e) {
            exceptionThrown = true;
        }
    }

    @Then("la categoría devuelta debe ser {string}")
    public void la_categoria_del_imc_debe_ser(String categoriaEsperada) {
        String esperadoFormateado = categoriaEsperada.toUpperCase().replace(" ", "_");
        assertEquals(esperadoFormateado, categoriaResultado.name().toUpperCase());
    }


    // EXCEPCIONES

    @Then("el sistema debe lanzar una excepción indicando que los datos de salud son inválidos para el IMC")
    public void el_sistema_debe_lanzar_una_excepcion_en_imc() {
        assertEquals(true, exceptionThrown);
    }
}
