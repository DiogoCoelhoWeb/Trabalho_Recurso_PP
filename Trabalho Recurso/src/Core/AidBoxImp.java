/*
* Nome: Diogo António Martins Coelho
* Número: 8230387
* Turma: LEIT4
*/
package Core;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;

import java.util.Objects;


public class AidBoxImp implements AidBox {

    private final int ARRAY_EXPANSION_FACTOR = 2;

    private String code;
    private String zone;

    private int nContainers = 0;
    private Container[] containers = new Container[ARRAY_EXPANSION_FACTOR];

    public AidBoxImp(String code, String zone) {
        this.code = code;
        this.zone = zone;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getZone() {
        return this.zone;
    }

    @Override
    public double getDistance(AidBox aidBox) throws AidBoxException {
        return 0;
    }

    @Override
    public double getDuration(AidBox aidBox) throws AidBoxException {
        return 0;
    }

    private void resizeContainerArray() {
        Container[] newContainers = new ContainerImp[this.containers.length * ARRAY_EXPANSION_FACTOR];

        for (int i = 0; i < this.containers.length; i++) {
            newContainers[i] = this.containers[i];
        }

        this.containers = newContainers;
    }

    @Override
    public boolean addContainer(Container container) throws ContainerException {
        if (this.nContainers == this.containers.length) {
            resizeContainerArray();
        }

        if (container == null){
            throw new ContainerException("Container is null");
        }

        for (int i = 0; i < this.nContainers; i++) {
            if (container.getType() == this.containers[i].getType()){
                throw new ContainerException("There is already a container with the " + container.getType() + " ItemType");
            }

            if (container.equals(this.containers[i])){
                return false;
            }
        }

        this.containers[nContainers] = container;
        this.nContainers++;

        return true;
    }

    @Override
    public Container getContainer(ContainerType containerType) {
        for(Container container : this.containers){
            if (container.getType() == containerType){
                return container;
            }
        }

        return null;
    }

    @Override
    public Container[] getContainers() {
        Container[] containersFull = new ContainerImp[this.nContainers];

        for (int i = 0; i < this.nContainers; i++) {
            containersFull[i] = this.containers[i];
        }

        return containersFull;
    }

    @Override
    public void removeContainer(Container container) throws AidBoxException {
        if (container == null){
            throw new AidBoxException("Container does not exist in the AidBox");
        }

        for (int i = 0; i < this.nContainers; i++) {
            if (this.containers[i].equals(container)) {
                this.containers[i] = this.containers[this.nContainers - 1];
                this.containers[this.nContainers - 1] = null;
                this.nContainers--;
            }
        }
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

        if (!(o instanceof AidBoxImp)) {
            return false;
        }

        AidBoxImp aidBoxImp = (AidBoxImp) o;

        return this.code.equals(aidBoxImp.code);
    }

    @Override
    public String toString() {
        String s = "";

        s += "AIDBOX " + this.code + " - " + this.zone + "\n";
        s += " - Containers:\n";

        if (this.nContainers > 0) {
            for (int i = 0; i < this.nContainers; i++) {
                s += this.containers[i].toString() + "\n";
            }
        } else {
            s += " - No containers\n";
        }

        return s;
    }
}
