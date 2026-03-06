package anagrafica.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import anagrafica.entity.TypeVoyageOperation;

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
    
    public static <T> T firstElement(final List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public static LocalDate getEndOfWorkWeek(LocalDate startDate) {
        DayOfWeek day = startDate.getDayOfWeek();

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException(
                "La startDate non può essere un giorno del weekend: " + day
            );
        }

        int daysUntilFriday = DayOfWeek.FRIDAY.getValue() - day.getValue();
        return startDate.plusDays(daysUntilFriday);
    }
    
    
    public static Boolean calculatedPrice(final TypeVoyageOperation typeOperation) {
    	if("RICARICA".equals(typeOperation.getCode())) {
    		return Boolean.FALSE;
		}else if("NON_RICARICA".equals(typeOperation.getCode())) {
			return Boolean.FALSE;
		}else if("LASCIO".equals(typeOperation.getCode())) {
			return Boolean.FALSE;
		}else if("RITIRO".equals(typeOperation.getCode())) {
			return Boolean.TRUE;
		}else if("VENDO".equals(typeOperation.getCode())) {
			return Boolean.TRUE;
		}else if ("OMAGGIO".equals(typeOperation.getCode())) {
			return Boolean.FALSE;
		}
    	return Boolean.FALSE;
    }

    
}
