/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 * Turma: LEIT4
 */

import Core.AidBoxImp;
import Core.ContainerImp;
import Core.MeasurementImp;
import com.estg.core.exceptions.*;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        LocalDateTime date = LocalDateTime.now();

        MeasurementImp measurement1 = new MeasurementImp(10, date);
        MeasurementImp measurement2 = new MeasurementImp(20, date);

        ContainerImp container1 = new ContainerImp("C1", 100, null);
        ContainerImp container2 = new ContainerImp("C2", 200, null);

        AidBoxImp aidBox = new AidBoxImp("A1", "Z1");

        try {
            container1.addMeasurement(measurement1);
            container1.addMeasurement(measurement2);

            container2.addMeasurement(measurement1);
            container2.addMeasurement(measurement2);
        } catch (MeasurementException e) {
            e.printStackTrace();
        }

        try {
            aidBox.addContainer(container1);
            aidBox.addContainer(container2);
        } catch (ContainerException e) {
            e.printStackTrace();
        }

        System.out.println(aidBox.toString());

    }
}