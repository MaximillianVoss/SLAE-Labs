package L3_4.Common;

import L3_4.Common.Grid;
import L3_4.Common.Polynom;

public class Newton {

    private double[] differences;                              // массива разностей
    private Polynom newton;                                        // полином Ньютона

    public Newton(Grid grid) {                                          // конструктор по сетке
        int length = grid.getCount();                           // количество элементов в сетке
        differences = new double[length];                       // задаем массив разностей
        double firstArrOfDif[] = new double[length];           // первый массив разностей (на данном этапе 0-ого порядка)
        double secondArrOfDif[] = new double[length];          // второй массив разностей (на данном этапе 1-ого порядка)

        for (int i = 0; i < length; i++) {                       // добавляем разности 0-ого порядка
            firstArrOfDif[i] = grid.getY(i);
        }
        differences[0] = firstArrOfDif[0];                      // копируем в массив разностей первую разность 0-ого порядка
        for (int i = 1; i < length; i++) {                       // от 1-ого порядка и до конца
            for (int j = 0; j < length - i; j++) {               // считаем массив разностей n-ого порядка по формуле, где i - номер порядка
                secondArrOfDif[j] = (firstArrOfDif[j] - firstArrOfDif[j + 1]) / (grid.getX(j) - grid.getX(j + i));
            }

            firstArrOfDif = secondArrOfDif;             // присваиваем первому массиву второй

            differences[i] = firstArrOfDif[0];          // добавляем разность i-ого порядка в основной массив
        }

        newton = new Polynom(0, differences[0]);       // создаем полином Ньютона по разности 0-ого порядка
        Polynom curBin = new Polynom();                      // текущий бином
        for (int i = 1; i < length; i++) {
            Polynom curPolynom = new Polynom(0, 1);       // создаем текущий полином на i-ом шаге
            for (int j = 0; j < i; j++) {               // заполняем полином произведением биномов
                curBin.changeBin(-grid.getX(j));
                curPolynom = curPolynom.multiple(curBin);
            }
            curPolynom.multipleK(differences[i]);         // умножаем полином на соответствующую разность
            newton.sum(curPolynom);                       // и прибавляем полученный полином к полиному Лагранжа
        }
    }

    public double findSol(double x) {                   // значение полинома в точке
        return newton.getPoint(x);
    }

    public void print() {                                // выводм полинома Ньютона
        newton.print();
    }

}