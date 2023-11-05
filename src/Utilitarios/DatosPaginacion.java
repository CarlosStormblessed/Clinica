package Utilitarios;

import java.util.List;

public interface DatosPaginacion <T> {
    int getTotalRowCount();
    List<T> getRows(int startIndex, int endIndex);
}
