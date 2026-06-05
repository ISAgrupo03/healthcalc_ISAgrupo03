package healthcalc;

public interface HealthHospital {
    Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws Exception;
    int pesoCorporalIdeal(char genero, float altura) throws Exception;
}