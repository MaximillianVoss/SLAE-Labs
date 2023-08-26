public class Grid {
    private int count;                                          // количество точек в сетке
    private double []x;                                         // массив х
    private double []y;                                         // массив у
    private int last = -1;                                      // последний занятый элемент массива
 
    public Grid(int count){                                     // конструктор сетки по количеству точек
        this.count = count;                                     // создание соответствующих массивов
        x = new double[count];
        y = new double[count];
    }
 
    //инициализируем
    public void addPoint(double x, double y){                   // добавление новой точки с координатами х и у
        if (last + 1 < count){                                  // если следующий элемент не выходит за рамки сетки, то повышаем индекс последнего занятого
            last++;                                             // и добавляем точку
            this.x[last] = x;
            this.y[last] = y;
        }
    }
 
    public double getX(int index){
        return x[index];
    }           // возвращаем координату х по индексу index
 
    public double getY(int index){ return y[index]; }            // возвращаем координату у по индексу index
 
    public int getCount(){
        return count;
    }                      // возвращаем количество точек
 
    public int findEl(double x){                                 // поиск индекса по координате х
        int pos = -1;                                            // если точки нет на сетке, то вернем позицию -1
        for (int i = 0; i <= last; i++){                         // если точка есть на сетке, возвращаем ее индекс
            if (Math.abs(this.x[i] - x) <= 1e-6){
                pos = i;
                return pos;
            }
        }
        return pos;
    }
 
}