/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 * Turma: LEIT4
 */

package PickingManagement;

import Core.AidBoxImp;
import Core.ContainerImp;
import Core.MeasurementImp;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;

public class RouteImp implements com.estg.pickingManagement.Route{
    private final int ARRAY_EXPANSION_FACTOR = 2;

    private Vehicle vehicle;
    private Report report;

    private int nAidBoxes = 0;
    private AidBox[] aidBoxes = new AidBoxImp[ARRAY_EXPANSION_FACTOR];

    public RouteImp(Vehicle vehicle, Report report) {
        this.vehicle = vehicle;
        this.report = report;
    }

    private void expandAidBoxesArray() {
        AidBox[] newAidBoxes = new AidBoxImp[this.aidBoxes.length * ARRAY_EXPANSION_FACTOR];

        for (int i = 0; i < this.aidBoxes.length; i++) {
            newAidBoxes[i] = this.aidBoxes[i];
        }

        this.aidBoxes = newAidBoxes;
    }

    private boolean aidBoxIsCompatible(AidBox aidBox) {
        for (int i = 0; i < aidBox.getContainers().length; i++) {
            if (this.vehicle.getCapacity(aidBox.getContainers()[i].getType()) > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void addAidBox(AidBox aidBox) throws RouteException {
        if (this.nAidBoxes == this.aidBoxes.length) {
            expandAidBoxesArray();
        }

        if (aidBox == null) {
            throw new RouteException("AidBox is null");
        }

        if (this.containsAidBox(aidBox)) {
            throw new RouteException("AidBox already exists in the route");
        }

        if (!aidBoxIsCompatible(aidBox)) {
            throw new RouteException("AidBox does not have the required container types");
        }

        this.aidBoxes[this.nAidBoxes] = aidBox;
        this.nAidBoxes++;
    }

    @Override
    public AidBox removeAidBox(AidBox aidBox) throws RouteException {
        if (aidBox == null) {
            throw new RouteException("AidBox is null");
        }

        if (!this.containsAidBox(aidBox)) {
            throw new RouteException("AidBox does not exist in the route");
        }

        for (int i = 0; i < this.nAidBoxes; i++) {
            if (this.aidBoxes[i].equals(aidBox)) {
                for (int j = i; j < this.nAidBoxes - 1; j++) {
                    this.aidBoxes[j] = this.aidBoxes[j + 1];
                }

                this.nAidBoxes--;
            }

            return this.aidBoxes[i];
        }

        return null;
    }

    @Override
    public boolean containsAidBox(AidBox aidBox) {
        for (int i = 0; i < this.nAidBoxes; i++) {
            if (this.aidBoxes[i].equals(aidBox)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void replaceAidBox(AidBox aidBox, AidBox aidBox1) throws RouteException {
        if (aidBox == null || aidBox1 == null) {
            throw new RouteException("AidBox is null");
        }

        if (!this.containsAidBox(aidBox)){
            throw new RouteException("AidBox does not exist in the route");
        }

        if (this.containsAidBox(aidBox1)) {
            throw new RouteException("AidBox already exists in the route");
        }


        if (!aidBoxIsCompatible(aidBox1)) {
            throw new RouteException("AidBox does not have the required container types");
        }

        for (int i = 0; i < this.nAidBoxes; i++) {
            if (this.aidBoxes[i].equals(aidBox)) {
                this.aidBoxes[i] = aidBox1;
            }
        }
    }

    @Override
    public void insertAfter(AidBox aidBox, AidBox aidBox1) throws RouteException {
        if (aidBox == null || aidBox1 == null) {
            throw new RouteException("AidBox is null");
        }

        if (!this.containsAidBox(aidBox)){
            throw new RouteException("AidBox does not exist in the route");
        }

        if (this.containsAidBox(aidBox1)) {
            throw new RouteException("AidBox already exists in the route");
        }

        if (!aidBoxIsCompatible(aidBox1)) {
            throw new RouteException("AidBox does not have the required container types");
        }

        for (int i = 0; i < this.nAidBoxes; i++) {
            if (this.aidBoxes[i].equals(aidBox)) {
                for (int j = this.nAidBoxes; j > i; j--) {
                    this.aidBoxes[j] = this.aidBoxes[j - 1];
                }

                this.aidBoxes[i + 1] = aidBox1;
                this.nAidBoxes++;
            }
        }
    }

    private Container[] generateContainersDeepCopy(AidBox aidBox) {

        Container[] containersDeepCopy = new ContainerImp[aidBox.getContainers().length];

        for (int i = 0; i < aidBox.getContainers().length; i++) {

            Container container = new ContainerImp(aidBox.getContainers()[i].getCode(), aidBox.getContainers()[i].getCapacity(), aidBox.getContainers()[i].getType());
            MeasurementImp[] measurements = (MeasurementImp[]) aidBox.getContainers()[i].getMeasurements();

            for (int j = 0; j < measurements.length; j++) {
                try {
                    container.addMeasurement(measurements[j]);
                } catch (MeasurementException e) {
                    throw new RuntimeException(e);
                }
            }

            containersDeepCopy[i] = container;
        }

        return containersDeepCopy;
    }

    @Override
    public AidBox[] getRoute() {
        AidBox[] aidBoxesDeepCopy = new AidBoxImp[this.nAidBoxes];

        for (int i = 0; i < this.nAidBoxes; i++) {
            aidBoxesDeepCopy[i] = new AidBoxImp(this.aidBoxes[i].getCode(), this.aidBoxes[i].getZone());

            Container[] containers = generateContainersDeepCopy(this.aidBoxes[i]);

            for (int j = 0; j < containers.length; j++) {
                try {
                    aidBoxesDeepCopy[i].addContainer(containers[j]);
                } catch (ContainerException e) {
                    e.printStackTrace();
                }
            }
        }

        return aidBoxesDeepCopy;
    }

    @Override
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    @Override
    public double getTotalDistance() {
        double totalDistance = 0;


        for (int i = 0; i < this.nAidBoxes - 1; i++) {
            try {
                totalDistance += this.aidBoxes[i].getDistance(this.aidBoxes[i + 1]);
            } catch (AidBoxException e) {
                e.printStackTrace();
            }
        }

        return totalDistance;
    }

    @Override
    public double getTotalDuration() {
        double totalDuration = 0;

        for (int i = 0; i < this.nAidBoxes - 1; i++) {
            try {
                totalDuration += this.aidBoxes[i].getDuration(this.aidBoxes[i + 1]);
            } catch (AidBoxException e) {
                e.printStackTrace();
            }
        }

        return totalDuration;
    }

    @Override
    public Report getReport() {
        return this.report;
    }
}
