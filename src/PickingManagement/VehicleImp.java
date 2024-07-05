/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 * Turma: LEIT4
 */

package PickingManagement;

import Core.ContainerImp;
import com.estg.core.ContainerType;
import com.estg.pickingManagement.Vehicle;

public class VehicleImp implements Vehicle {

    private final int ARRAY_EXPANSION_FACTOR = 2;

    private boolean isActive = true;
    private String code;

    private int nContainers = 0;
    private ContainerImp[] containers = new ContainerImp[ARRAY_EXPANSION_FACTOR];

    public VehicleImp(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public double getCapacity(ContainerType containerType) {
        double capacity = 0;

        for (int i = 0; i < this.nContainers; i++) {
            capacity ++;
        }

        return capacity;
    }
}
