package anagrafica.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class MethodUtils {
    public static boolean isPartitaIvaValida(String piva) {
        if (piva == null) return false;

        // Deve avere esattamente 11 cifre
        if (!piva.matches("\\d{11}")) return false;

        int somma = 0;

        for (int i = 0; i < 11; i++) {
            int cifra = Character.getNumericValue(piva.charAt(i));

            if (i % 2 == 0) {
                // posizioni pari (0-based)
                somma += cifra;
            } else {
                // posizioni dispari
                cifra *= 2;
                if (cifra > 9) {
                    cifra -= 9;
                }
                somma += cifra;
            }
        }

        return somma % 10 == 0;
    }

    public static Pageable getPagination(final Integer offset, final Integer limit){
        return PageRequest.of(offset != null ? offset:0, limit!=null ? limit:10);
    }
}
