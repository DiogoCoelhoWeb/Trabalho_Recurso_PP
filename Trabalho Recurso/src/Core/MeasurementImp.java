/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 * Turma: LEIT4
 */

package Core;

import com.estg.core.Measurement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MeasurementImp implements Measurement {

    private double value;
    private LocalDateTime date;

    public MeasurementImp(double value, LocalDateTime date) {
        this.value = value;
        this.date = date;
    }

    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return 7;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof MeasurementImp)) {
            return false;
        }

        MeasurementImp that = (MeasurementImp) o;

        return this.getValue() == that.getValue() && this.date.equals(that.getDate());
    }

    @Override
    public String toString() {
        String s = "";

        s += "        - MEASUREMENT " + this.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n";
        s += "            - Value: " + this.value + "\n";

        return s;
    }
}
