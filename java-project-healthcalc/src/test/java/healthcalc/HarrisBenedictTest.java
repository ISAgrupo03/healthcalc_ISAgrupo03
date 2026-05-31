package healthcalc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import healthcalc.exceptions.InvalidHealthDataException;

@DisplayName("Tests para la métrica BMR (Harris-Benedict)")
public class HarrisBenedictTest {

    private HealthCalcImpl healthCalc;

    @BeforeEach
    void setup() {
        healthCalc = HealthCalcImpl.getInstance();
    }

    @Nested
    @DisplayName("Cálculos válidos de la métrica Harris-Benedict")
    class HarrisBenedictValidosTests {

        @ParameterizedTest(name = "Hombre: peso {0} kg, altura {1} m, edad {2} años, BMR esperado {3}")
        @CsvSource({
                "70.0, 1.75, 25, 1724.05",
                "85.0, 1.80, 30, 1920.62",
                "60.0, 1.60, 40, 1432.94"
        })
        @DisplayName("Cálculo válido de la métrica Harris-Benedict para hombres")
        void testHarrisBenedictHombre(float weight, float height, int age, float expected) throws InvalidHealthDataException {
            Person patient = new Patient(weight, height, Gender.MALE, age);
            float result = healthCalc.basalMetabolicRate(patient);
            assertEquals(expected, result, 0.01f);
        }

        @ParameterizedTest(name = "Mujer: peso {0} kg, altura {1} m, edad {2} años -> BMR esperado {3}")
        @CsvSource({
                "60.0, 1.65, 25, 1405.33",
                "75.0, 1.70, 30, 1537.88",
                "55.0, 1.55, 40, 1263.17"
        })
        @DisplayName("Cálculo válido de la métrica Harris-Benedict para mujeres")
        void testHarrisBenedictMujer(float weight, float height, int age, float expected) throws InvalidHealthDataException {
            Person patient = new Patient(weight, height, Gender.FEMALE, age);
            float result = healthCalc.basalMetabolicRate(patient);
            assertEquals(expected, result, 0.01f);
        }
    }

    @Nested
    @DisplayName("Cálculos inválidos de la métrica Harris-Benedict")
    class HarrisBenedictInvalidosTests {

        @Test
        @DisplayName("Lanzar excepción cuando el sexo es nulo")
        void testHarrisBenedictSexoInvalido() {
            Person patient = new Patient(70.0f, 1.75f, null, 25);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(patient));
        }

        @Test
        @DisplayName("Lanzar excepción cuando las variables son cero")
        void testHarrisBenedictVariablesCero() {
            assertAll(
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(new Patient(0.0f, 1.75f, Gender.MALE, 25))),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(new Patient(70.0f, 0.0f, Gender.MALE, 25)))
            );
        }

        @Test
        @DisplayName("Lanzar excepción cuando los valores son negativos")
        void testHarrisBenedictNegativos() {
            assertAll(
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(new Patient(-70.0f, 1.75f, Gender.MALE, 25))),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(new Patient(70.0f, -1.75f, Gender.MALE, 25))),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(new Patient(70.0f, 1.75f, Gender.MALE, -25)))
            );
        }

        @ParameterizedTest(name = "Peso mínimo inválido: {0} kg")
        @ValueSource(floats = {0.99f, 0.50f})
        @DisplayName("Bloqueo de pesos inferiores al límite biológico mínimo (1 kg)")
        void testHarrisBenedictPesoMinimoImposible(float weight) {
            Person patient = new Patient(weight, 1.75f, Gender.MALE, 25);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(patient));
        }

        @ParameterizedTest(name = "Peso máximo inválido: {0} kg")
        @ValueSource(floats = {700.1f, 1000.0f, 5000.0f})
        @DisplayName("Bloqueo de pesos superiores al límite biológico máximo (700 kg)")
        void testHarrisBenedictPesoMaximoImposible(float weight) {
            Person patient = new Patient(weight, 1.75f, Gender.MALE, 25);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(patient));
        }

        @ParameterizedTest(name = "Altura mínima inválida: {0} m")
        @ValueSource(floats = {0.299f, 0.15f})
        @DisplayName("Bloqueo de alturas inferiores al límite biológico mínimo (30 cm)")
        void testHarrisBenedictAlturaMinimaImposible(float height) {
            Person patient = new Patient(70.0f, height, Gender.MALE, 25);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(patient));
        }

        @ParameterizedTest(name = "Altura máxima inválida: {0} m")
        @ValueSource(floats = {3.001f, 3.50f, 5.00f})
        @DisplayName("Bloqueo de alturas superiores al límite biológico máximo (300 cm)")
        void testHarrisBenedictAlturaMaximaImposible(float height) {
            Person patient = new Patient(70.0f, height, Gender.MALE, 25);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(patient));
        }

        @ParameterizedTest(name = "Edad máxima inválida: {0} años")
        @ValueSource(ints = {121, 150, 200})
        @DisplayName("Bloqueo de edades superiores al límite biológico máximo (120 años)")
        void testHarrisBenedictEdadMaximaImposible(int age) {
            Person patient = new Patient(70.0f, 1.75f, Gender.MALE, age);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.basalMetabolicRate(patient));
        }
    }
}