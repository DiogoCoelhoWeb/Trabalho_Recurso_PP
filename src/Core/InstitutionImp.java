/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 * Turma: LEIT4
 */

package Core;

import PickingManagement.PickingMapImp;
import PickingManagement.VehicleImp;

import com.estg.core.*;
import com.estg.core.exceptions.*;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;

import java.time.LocalDateTime;

public class InstitutionImp implements com.estg.core.Institution{
    private final int ARRAY_EXPANSION_FACTOR = 2;

    private String name;

    private int nAidBoxes = 0;
    private AidBox[] aidBoxes = new AidBoxImp[ARRAY_EXPANSION_FACTOR];

    private int nVehicles = 0;
    private Vehicle[] vehicles = new VehicleImp[ARRAY_EXPANSION_FACTOR];

    private int nPickingMaps = 0;
    private PickingMap[] pickingMaps = new PickingMapImp[ARRAY_EXPANSION_FACTOR];

    public InstitutionImp(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void expandAidBoxesArray() {
        AidBox[] newAidBoxes = new AidBoxImp[this.aidBoxes.length * ARRAY_EXPANSION_FACTOR];

        for (int i = 0; i < this.aidBoxes.length; i++) {
            newAidBoxes[i] = this.aidBoxes[i];
        }

        this.aidBoxes = newAidBoxes;
    }

    private boolean checkValidContainerArray(Container[] containers) throws AidBoxException {

        int nContainerTypes = 0;
        ContainerType[] containerTypes = new ContainerType[containers.length];

        for (Container container : containers) {
            for (int i = 0; i < nContainerTypes; i++) {
                if (container.getType().equals(containerTypes[i])) {
                    return false;
                }
            }

            containerTypes[nContainerTypes] = container.getType();
            nContainerTypes++;
        }

        return true;
    }

    @Override
    public boolean addAidBox(AidBox aidBox) throws AidBoxException {
        if (this.nAidBoxes == this.aidBoxes.length) {
            expandAidBoxesArray();
        }

        if (aidBox == null) {
            throw new AidBoxException("AidBox is null");
        }

        if (!checkValidContainerArray(aidBox.getContainers())) {
            throw new AidBoxException("Aid box is Invalid");
        }

        for (int i = 0; i < this.nAidBoxes; i++) {
            if (this.aidBoxes[i].equals(aidBox)) {
                return false;
            }
        }

        this.aidBoxes[this.nAidBoxes] = aidBox;
        this.nAidBoxes++;

        return true;

    }

    private Container getContainer(Container container) {
        for (int i = 0; i < this.nAidBoxes; i++) {
            for (Container container1 : this.aidBoxes[i].getContainers()) {
                if (container1.equals(container)) {
                    return container1;
                }
            }
        }

        return null;
    }

    @Override
    public boolean addMeasurement(Measurement measurement, Container container) throws ContainerException, MeasurementException {
        if (container == null) {
            throw new ContainerException("Container doesn't exist");
        }

        Container aidBoxContainer = getContainer(container);

        if (aidBoxContainer == null){
            throw new ContainerException("Container doesn't exist");
        }

        try {
            aidBoxContainer.addMeasurement(measurement);
        } catch (MeasurementException e) {
            if (e.getMessage().equals("Measurement date is the same as the most recent measurement")) {
                return false;
            }

            if(e.getMessage().equals("Invalid value")) {
                throw new MeasurementException("Invalid value");
            }
        }

        return false;
    }

    @Override
    public AidBox[] getAidBoxes() {
        AidBox[] aidBoxesCopy = new AidBoxImp[this.nAidBoxes];

        for (int i = 0; i < this.nAidBoxes; i++) {
            aidBoxesCopy[i] = this.aidBoxes[i];
        }

        return aidBoxesCopy;
    }

    @Override
    public Container getContainer(AidBox aidBox, ContainerType containerType) throws ContainerException {
        if (aidBox == null) {
            throw new ContainerException("AidBox doesn't exist");
        }

        for (int i = 0; i < this.nAidBoxes; i++) {
            if(this.aidBoxes[i].equals(aidBox)) {
                if (aidBox.getContainer(containerType) == null) {
                    throw new ContainerException("Container doesn't exist");
                }
                return aidBox.getContainer(containerType);
            }
        }

        throw new ContainerException("AidBox doesn't exist");
    }

    @Override
    public Vehicle[] getVehicles() {
        Vehicle[] vehiclesDeepCopy = new VehicleImp[this.nVehicles];

        for (int i = 0; i < this.nVehicles; i++) {
            vehiclesDeepCopy[i] = new VehicleImp(this.vehicles[i].getCode());
        }

        return vehiclesDeepCopy;
    }

    private void expandVehiclesArray() {
        Vehicle[] newVehicles = new VehicleImp[this.vehicles.length * ARRAY_EXPANSION_FACTOR];

        for (int i = 0; i < this.vehicles.length; i++) {
            newVehicles[i] = this.vehicles[i];
        }

        this.vehicles = newVehicles;
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) throws VehicleException {
        if (this.nVehicles == this.vehicles.length) {
            expandVehiclesArray();
        }

        if (vehicle == null) {
            throw new VehicleException("Vehicle is null");
        }

        for (int i = 0; i < this.nVehicles; i++) {
            if (this.vehicles[i].equals(vehicle)) {
                return false;
            }
        }

        this.vehicles[this.nVehicles] = vehicle;
        this.nVehicles++;

        return true;
    }

    @Override
    public void disableVehicle(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            throw new VehicleException("Vehicle doesn't exist");
        }

        for (int i = 0; i < this.nVehicles; i++) {
            if (this.vehicles[i].equals(vehicle)) {

                VehicleImp instituitonVehicle = (VehicleImp) this.vehicles[i];

                if (instituitonVehicle.isActive()) {
                    instituitonVehicle.setActive(false);
                    return;
                }else {
                    throw new VehicleException("Vehicle is already disabled");
                }
            }
        }

        throw new VehicleException("Vehicle doesn't exist");
    }

    @Override
    public void enableVehicle(Vehicle vehicle) throws VehicleException {
        if (vehicle == null) {
            throw new VehicleException("Vehicle doesn't exist");
        }

        for (int i = 0; i < this.nVehicles; i++) {
            if (this.vehicles[i].equals(vehicle)) {

                VehicleImp instituitonVehicle = (VehicleImp) this.vehicles[i];

                if (!instituitonVehicle.isActive()) {
                    instituitonVehicle.setActive(true);
                    return;
                }else {
                    throw new VehicleException("Vehicle is already enabled");
                }
            }
        }

        throw new VehicleException("Vehicle doesn't exist");
    }

    @Override
    public PickingMap[] getPickingMaps() {
        PickingMap[] pickingMapsFull = new PickingMapImp[this.nPickingMaps];

        for (int i = 0; i < this.nPickingMaps; i++) {
            pickingMapsFull[i] = this.pickingMaps[i];
        }

        return this.pickingMaps;
    }

    private PickingMap[] shortenPickingMapsBetweenDatesArray(PickingMap[] pickingMapsBetweenDates, int nPickingMapsBetweenDates) {
        PickingMap[] newPickingMaps = new PickingMapImp[nPickingMapsBetweenDates];

        for (int i = 0; i < nPickingMapsBetweenDates; i++) {
            newPickingMaps[i] = pickingMapsBetweenDates[i];
        }

        return newPickingMaps;
    }

    @Override
    public PickingMap[] getPickingMaps(LocalDateTime localDateTime, LocalDateTime localDateTime1) {
        int nPickingMapsBetweenDates = 0;
        PickingMap[] pickingMapsBetweenDates = new PickingMapImp[this.nPickingMaps];

        for (int i = 0; i < this.nPickingMaps; i++) {
            if (this.pickingMaps[i].getDate().isAfter(localDateTime) && this.pickingMaps[i].getDate().isBefore(localDateTime1)) {
                pickingMapsBetweenDates[nPickingMapsBetweenDates] = this.pickingMaps[i];
                nPickingMapsBetweenDates++;
            }
        }

        pickingMapsBetweenDates = shortenPickingMapsBetweenDatesArray(pickingMapsBetweenDates, nPickingMapsBetweenDates);

        return pickingMapsBetweenDates;
    }

    @Override
    public PickingMap getCurrentPickingMap() throws PickingMapException {
        if (this.nPickingMaps == 0) {
            throw new PickingMapException("There are no picking maps");
        }

        PickingMap currentPickingMap = this.pickingMaps[0];

        for (int i = 1; i < this.nPickingMaps; i++) {
            if (this.pickingMaps[i].getDate().isAfter(currentPickingMap.getDate())) {
                currentPickingMap = this.pickingMaps[i];
            }
        }

        return currentPickingMap;
    }

    private void expandPickingMapsArray() {
        PickingMap[] newPickingMaps = new PickingMapImp[this.pickingMaps.length * ARRAY_EXPANSION_FACTOR];

        for (int i = 0; i < this.pickingMaps.length; i++) {
            newPickingMaps[i] = this.pickingMaps[i];
        }

        this.pickingMaps = newPickingMaps;
    }

    @Override
    public boolean addPickingMap(PickingMap pickingMap) throws PickingMapException {
        if (this.nPickingMaps == this.pickingMaps.length) {
            expandPickingMapsArray();
        }

        if (pickingMap == null) {
            throw new PickingMapException("PickingMap is null");
        }

        for (int i = 0; i < this.nPickingMaps; i++) {
            if (this.pickingMaps[i].equals(pickingMap)) {
                return false;
            }
        }

        this.pickingMaps[this.nPickingMaps] = pickingMap;
        this.nPickingMaps++;

        return true;
    }

    @Override
    public double getDistance(AidBox aidBox) throws AidBoxException {
        return 0;
    }
}
