/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 * Turma: LEIT4
 */

package Core;

import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;

import java.time.LocalDate;
import java.util.Objects;

public class ContainerImp implements Container {

    private final int ARRAY_EXPANSION_FACTOR = 2;

    private double capacity;
    private String code;
    private ContainerType type;

    private int nMeasurements = 0;
    private Measurement[] measurements = new MeasurementImp[ARRAY_EXPANSION_FACTOR];

    public ContainerImp(String code, double capacity, ContainerType type) {
        this.code = code;
        this.capacity = capacity;
        this.type = type;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public double getCapacity() {
        return this.capacity;
    }

    @Override
    public ContainerType getType() {
        return this.type;
    }

    @Override
    public Measurement[] getMeasurements() {
        Measurement[] measurementsDeepCopy = new MeasurementImp[this.nMeasurements];

        for (int i = 0; i < this.nMeasurements; i++) {
            measurementsDeepCopy[i] = new MeasurementImp(this.measurements[i].getValue(), this.measurements[i].getDate());
        }

        return measurementsDeepCopy;
    }

    @Override
    public Measurement[] getMeasurements(LocalDate localDate) {
        int nMeasurementsOnDate = 0;
        for (Measurement measurement : this.measurements) {
            if (measurement.getDate().toLocalDate().equals(localDate)) {
                nMeasurementsOnDate++;
            }
        }

        Measurement[] newMeasurements = new MeasurementImp[nMeasurementsOnDate];

        int i = 0;
        for (MeasurementImp measurement : (MeasurementImp[]) this.measurements) {
            if (measurement.getDate().toLocalDate().equals(localDate)) {
                newMeasurements[i] = measurement;
                i++;
            }
        }

        return newMeasurements;
    }

    private void resizeMeasurements() {
        Measurement[] newMeasurements = new MeasurementImp[this.measurements.length * ARRAY_EXPANSION_FACTOR];

        for(int i = 0; i < this.measurements.length; i++) {
            newMeasurements[i] = this.measurements[i];
        }

        this.measurements = newMeasurements;
    }

    @Override
    public boolean addMeasurement(Measurement measurement) throws MeasurementException {
        if(this.nMeasurements == this.measurements.length) {
            resizeMeasurements();
        }

        if (measurement == null) {
            throw new MeasurementException("Measurement is null");
        }

        if (measurement.getValue() < 0) {
            throw new MeasurementException("Invalid value");
        }

        if (this.nMeasurements > 0) {
            if (this.measurements[this.nMeasurements - 1].getDate().isAfter(measurement.getDate())) {
                throw new MeasurementException("Measurement date is before the most recent measurement");
            }

            if (this.measurements[this.nMeasurements - 1].getDate().isEqual(measurement.getDate()) && this.measurements[this.nMeasurements - 1].getValue() != measurement.getValue()) {
                throw new MeasurementException("Measurement date is the same as the most recent measurement");
            }

            if (this.measurements[this.nMeasurements - 1].equals(measurement)) {
                return false;
            }
        }

        this.measurements[this.nMeasurements] = measurement;
        this.nMeasurements++;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ContainerImp)) {
            return false;
        }

        ContainerImp that = (ContainerImp) o;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public String toString() {
        String s = "";

        s += "  - CONTAINER " + this.code + " (" + this.type + ")\n";
        s += "    - Capacity: " + this.capacity + "\n";

        if (this.nMeasurements > 0) {
            s += "      - Measurements:\n";
            for (int i = 0; i < this.nMeasurements; i++) {
                s += this.measurements[i].toString() + "\n";
            }
        } else {
            s += "    - No measurements\n";
        }

        return s;
    }
}
