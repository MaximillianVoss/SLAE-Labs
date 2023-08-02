package L3_4.Common;

import L3_4.Common.Grid;
import L3_4.Common.Polynom;

public class Lagrange {

    private Polynom lagrangePolynom;                                      // полином Лагранжа

    public Lagrange(Grid grid) {                                            // конструктор Класса по сетке
        lagrangePolynom = new Polynom();                                  // создаем новый полином
        double temp;                                                // коэффициент, получаемый благодаря умножению f(x) на знаменатель
        Polynom curBin = new Polynom();                                   // текущий бином
        for (int i = 0; i < grid.getCount(); i++) {                  // до конца сетки считаем полином Лагранжа по формуле
            Polynom curPolynom = new Polynom(0, 1);                    // текущий полином, создаваемый на i-ом шаге
            temp = grid.getY(i);                                    // f(x)
            for (int j = 0; j < i; j++) {                            // до i-ого элемента
                temp /= (grid.getX(i) - grid.getX(j));              // делим f(x) на знаменатель
                curBin.changeBin(-grid.getX(j));                    // создаем бином и умножаем его на текущий полином, таким образом формируя текущий полином
                curPolynom = curPolynom.multiple(curBin);
            }
            for (int j = i + 1; j < grid.getCount(); j++) {
                temp /= (grid.getX(i) - grid.getX(j));              // делим f(x) на знаменатель
                curBin.changeBin(-grid.getX(j));                    // создаем бином и умножаем его на текущий полином
                curPolynom = curPolynom.multiple(curBin);
            }
            curPolynom.multipleK(temp);                                // умножаем текущий полином на полученный коэффициент (f(x)/(...)
            lagrangePolynom.sum(curPolynom);                              // складываем полученный полином

        }
    }

    public double findSol(double x) {
        return lagrangePolynom.getPoint(x);
    }           // значение полинома в точке

    public void print() {
        lagrangePolynom.print();
    }                                  // вывод полинома Лагранжа

}
