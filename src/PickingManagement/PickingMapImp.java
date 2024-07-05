/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 * Turma: LEIT4
 */

package PickingManagement;

import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Route;

import java.time.LocalDateTime;

public class PickingMapImp implements PickingMap {

    private LocalDateTime date;
    private Route[] routes;

    public PickingMapImp(LocalDateTime date, Route[] routes) {
        this.date = date;
        this.routes = fullCopyRoutes(routes);
    }

    public PickingMapImp(Route[] routes) {
        this.date = LocalDateTime.now();
        this.routes = fullCopyRoutes(routes);
    }

    private Route[] fullCopyRoutes(Route[] routes) {
        int nRoutes = 0;

        Route[] fullCopyRoutes;

        for (int i = 0; i < routes.length; i++) {
            if (routes[i] != null) {
                nRoutes++;
            }
        }

        fullCopyRoutes = new Route[nRoutes];

        for (int i = 0; i < routes.length; i++) {
            if (routes[i] != null) {
                fullCopyRoutes[i] = routes[i];
            }
        }

        return fullCopyRoutes;
    }

    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    @Override
    public Route[] getRoutes() {
        return this.routes;
    }
}
