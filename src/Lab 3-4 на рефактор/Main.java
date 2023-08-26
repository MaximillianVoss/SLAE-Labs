public class Main {
 
    public static void main(String [] args){
 
        double a = 0;                       // а - начало интервала, b - конец интервала
        double b = 1;
        int n = 7;                          // количество точек в сетке
        Grid grid = new Grid(n);            // создаем сетку
        double step = (b - a)/ (n - 1);     // задаем шаг по сетке
        for (double i = a; i <= b; i+=step){
            grid.addPoint(i, function(i));          // добавляем новые точки в сетку
        }
 
        Lagrange langrage = new Lagrange(grid);             // создаем полином Лагранжа
        System.out.println("Полином в форме Лагранжа:");
        langrage.print();                                   // выводим его
        System.out.println();
        System.out.println();
 
        Newton newton = new Newton(grid);                   // создаем полином Ньютона
        System.out.println("Полином в форме Ньютона:");
        newton.print();                                     // выводим его
        System.out.println();
        System.out.println();
        
        printTable(grid, a, b, step/2, langrage, newton);              // выводим таблицу с х, у, f(x), и полиномом в точке х
    }
 
    public static void printTable(Grid grid, double a, double b, double step, Lagrange lagrange, Newton newton){      // вывод таблицы по Лагранжу
        System.out.printf("%20s %20s %20s %20s %20s", "X", "Y", "f(x)", "Ln(x)", "Nn(x)");
        System.out.println();
        for (double i = a; i <= b; i += step){                  // в заданном интервале [a,b] с шагом step
            if (grid.findEl(i) >= 0){                                       // если у есть на сетке, то выписываем полную строчку
                System.out.printf("%20.6E %20.6E %20.6E %20.6E %20.6E", i, grid.getY(grid.findEl(i)), function(i), lagrange.findSol(i), newton.findSol(i));
            }
            else {                                                          // если у не определен на сетке, то выписываем строчку без него
                System.out.format("%20.6E %20s %20.6E %20.6E %20.6E", i, "", function(i), lagrange.findSol(i), newton.findSol(i));
            }
            System.out.println();
        }
 
    }
 
    public static double function(double x){                     // значение функции в точке x
        //return 3.8332*x*x*x*x*x*x - 6.7677*x*x*x - 0.0023;
        return Math.sin(x*x/2);
        //return x*x;
    }
 
}