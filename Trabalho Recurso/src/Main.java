/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 * Turma: LEIT4
 */

import Core.AidBoxImp;
import Core.ContainerImp;
import Core.MeasurementImp;
import PickingManagement.RouteImp;
import PickingManagement.VehicleImp;

import com.estg.core.AidBox;
import com.estg.core.exceptions.*;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.exceptions.RouteException;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args){

        LocalDateTime date = LocalDateTime.now();

        MeasurementImp measurement1 = new MeasurementImp(10, date);
        MeasurementImp measurement2 = new MeasurementImp(20, date);

        ContainerImp container1 = new ContainerImp("C1", 100, null);
        ContainerImp container2 = new ContainerImp("C2", 200, null);

        AidBoxImp aidBox1 = new AidBoxImp("A1", "Z1");
        AidBoxImp aidBox2 = new AidBoxImp("A2", "Z1");
        AidBoxImp aidBox3 = new AidBoxImp("A3", "Z1");

        Route route1 = new RouteImp(new VehicleImp("VEHICLE"), null);

        try {
            container1.addMeasurement(measurement1);
            //container1.addMeasurement(measurement2);

            container2.addMeasurement(measurement1);
            //container2.addMeasurement(measurement2);
        } catch (MeasurementException e) {
            e.printStackTrace();
        }

        try {
            aidBox1.addContainer(container1);
            aidBox2.addContainer(container1);
            aidBox3.addContainer(container1);
        } catch (ContainerException e) {
            e.printStackTrace();
        }

        try {
            route1.addAidBox(aidBox1);
            route1.addAidBox(aidBox2);
            route1.addAidBox(aidBox3);
        } catch (RouteException e) {
            System.out.println("There was an error adding the AidBox to the Route");
        }

        AidBox[] route = route1.getRoute();


        System.out.println(aidBox1.toString());

    }
}