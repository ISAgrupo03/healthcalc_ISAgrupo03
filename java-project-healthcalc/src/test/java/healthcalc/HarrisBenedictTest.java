package healthcalc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import healthcalc.exceptions.InvalidHealthDataException;

public class HarrisBenedictTest {

    private HealthCalc healthCalc;

    @BeforeEach
    void setup(){
        healthCalc = new HealthCalcImpl();
    }
    
    @ParameterizedTest(name = "Hombre: peso {0} kg, altura {1} cm, edad {2} años, BMR esperado {3}")
    @CsvSource({"70.0, 175.0, 25, 1724.052",
                "85.0, 180.0, 30, 1920.617",
                "60.0, 160.0, 40, 1432.942"})
    @DisplayName("Cálculo válido de la métrica Harris-Benedict para hombres")
    void testHarrisBenedictHombre(double weight, double height, int age, double expected) throws InvalidHealthDataException {
        double result = healthCalc.harrisBenedict(weight, height, 'M', age);
        assertEquals(expected, result, 0.01);
    }

    @ParameterizedTest(name = "Mujer: peso {0} kg, altura {1} cm, edad {2} años, BMR esperado {3}")
    @CsvSource({"60.0, 165.0, 25, 1405.333",
                "75.0, 170.0, 30, 1537.878",
                "55.0, 155.0, 40, 1263.168"})   
    @DisplayName("Cálculo válido de la métrica Harris-Benedict para mujeres")
    void testHarrisBenedictMujer(double weight, double height, int age, double expected) throws InvalidHealthDataException {
        double result = healthCalc.harrisBenedict(weight, height, 'W', age);
        assertEquals(expected, result, 0.01);
    }

    @Test
    @DisplayName("Lanzar excepción para sexo no válido")
    void testHarrisBenedictSexoInvalido() {
        assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, 175.0, 'X', 25));
    }

    @ParameterizedTest(name = "Edad inválida: {0} años")
    @ValueSource(ints = {-1, -5, 121, 200})
    @DisplayName("Lanzar excepción para edad fuera de los límites válidos (0-120 años)")
    void testHarrisBenedictEdadInvalida(int age) {
        assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, 175.0, 'M', age));
    }

    @ParameterizedTest(name = "Peso inválido: {0} kg")
    @ValueSource(doubles = {-10.5, -1.0, 0.0, 0.99, 700.1, 1000.0})
    @DisplayName("Lanzar excepción para peso fuera de los límites válidos (1-700 kg)")
    void testHarrisBenedictPesoInvalido(double weight) {
        assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(weight, 175.0, 'M', 25));
    }

    @ParameterizedTest(name = "Altura inválida: {0} cm")
    @ValueSource(doubles = {-10.0, -1.0, 0.0, 29.9, 300.1, 500.0})
    @DisplayName("Lanzar excepción para altura fuera de los límites válidos (30-300 cm)")
    void testHarrisBenedictAlturaInvalida(double height) {
        assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, height, 'M', 25));
    }