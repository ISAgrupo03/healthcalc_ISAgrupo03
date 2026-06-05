package healthcalc;

public class Patient implements Person {
    private float weight;
    private float height;
    private Gender gender;
    private int age;

    public Patient(float weight, float height, Gender gender, int age) {
        this.weight=weight;
        this.height=height;
        this.gender=gender;
        this.age=age;
    }

    @Override
    public float weight() {
        return this.weight;
    }

    @Override
    public float height() {
        return this.height;
    }

    @Override
    public Gender gender() {
        return this.gender;
    }

    @Override
    public int age() {
        return this.age;
    }
}
