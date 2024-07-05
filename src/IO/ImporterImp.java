/*
 * Nome: Diogo António Martins Coelho
 * Número: 8230387
 * Turma: LEIT4
 */

package IO;

import com.estg.core.Institution;
import com.estg.core.exceptions.InstitutionException;
import com.estg.io.HTTPProvider;
import com.estg.io.Importer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ImporterImp implements com.estg.io.Importer {

    HTTPProvider provider = new HTTPProvider();

    @Override
    public void importData(Institution institution) throws FileNotFoundException, IOException, InstitutionException {

    }
}
