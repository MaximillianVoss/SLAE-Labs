public class Polynom {         //класс полинома
    private class Monom{      //класс монома
        int st;             //степень
        double k;           //коэффициент
        Monom next;          //ссылка на следующий моном
 
        Monom(int st, double k){         //конструктор монома
            this.k = k;
            this.st = st;
            next = null;
        }
 
        Monom(Monom monom){                // копирующий конструктор монома
            st = monom.st;
            k = monom.k;
            next = monom.next;
        }
    }
 
    Monom head;                          // первый моном полинома
    Polynom(int st, double k){
        head = new Monom(st, k);
    }       //конструктор полинома по моному
 
    Polynom(){
        head = null;
    }           //конструктор пустого полинома
 
    
 
    
  //public Polynom sum(){       // сложение двух полиномов 
	/*моном первого полинома (передаем ссылку) - к нему прибавляем
	  моном второго полинома (передаем ссылку) - его прибавляем
	  Создаем новый полином, в который будем копировать
	  
	  1.Заводим внешний цикл, в котором идем одновременно по двум полиномам (пока они не закончатся)
	  2.Сравниваем степени первого и второго
	 	2.1.Если первый больше чем второй - копируем его в новый полином и в первом переходим к след. эл-ту
	 	2.2.Если они равны - находим сумму коэфф, записываем в новый полином и переходим и в первом и во втором к след.моному
	 	2.3.Если второй больше чем первый - копируем моном второго, меняем голову 
	  3.Делаем это пока не кончится один из полиномов
	  4.По завершении одного из списков - другой копируется без всяких проверок  */
    
    
    public void sum(Polynom secondP){                          // сумма полиномов на первом полиноме
        Monom curFirst = head;                               // текущий моном первого полинома
        Monom curSecond = secondP.head;                      // текущий моном второго полинома
        Monom prevFirst = null;                              // предыдущий моном первого полинома (изначально нулевой, поскольку нет мономов перед первым)
 
        if (head == null || Math.abs(head.k) <= 1e-6){      // если первый моном нулевой или его коэффициент 0, то первый полином
            head = curSecond;                               // ссылается на второй
            return;
        }
        while (curFirst != null && curSecond != null){      // пока оба полинома существуют
            if (curFirst.st > curSecond.st){                // если степень монома первого полинома больше
                prevFirst = curFirst;                       // запоминанем предыдущий
                curFirst = curFirst.next;                   // двигаемся по первому полиному
            }
            else if (curFirst.st == curSecond.st){          // если степени равны, то складываем
                if (Math.abs(curFirst.k + curSecond.k) <= 1e-6){            // если после сложения коэффициент обнуляется
                    if (curFirst == head){                                  // и коэффициент обнулился в первом мономе первого полинома
                        head = head.next;                                   // сдвигаем первый полином
                    }
                    else {                                   // если обнулился произвольный моном в середине первого полинома,
                        prevFirst.next = curFirst.next;      // то "перепрыгиваем" через него, переприсваивая следующие элементы
 
                    }
 
                }
                else{                                        // если не обнуляется коэффициент
                    curFirst.k += curSecond.k;               // то просто складываем и запоминаем предыдущий
                    prevFirst = curFirst;
                }
 
                curFirst = curFirst.next;                    // двигаем первый и второй полиномы
                curSecond = curSecond.next;
            }
            else {                                           // если степень монома второго полинома больше
                if (curFirst == head){                       // если первый полином находится в самом начале, то копируем моном второго полинома
                    Monom copy = new Monom(curSecond);         // меняем голову (ставим моном второго полинома перед первым полиномом)
                    head = copy;                             // делаем текущий моном первого полинома следующим за мономом второго
                    head.next = curFirst;                    // запоминаем предыдущий
                    prevFirst = head;
                }
                else {                                       // если первый полином находится в середине
                    Monom copy = new Monom(curSecond);         // копируем моном второго полинома
                    prevFirst.next = copy;                   // вставляем скопированный моном перед текущим
                    copy.next = curFirst;                    // запоминаем предыдущий
                    prevFirst = copy;
                }
                curSecond = curSecond.next;                  // двигаем второй полином
            }
        }
        if (curSecond != null){                              // если второй полином не закончился, но закончился первый
            prevFirst.next = curSecond;                      // вставляем второй в конец первого
        }
    }
 
    
    
  //public void multipleX(double x){     // умножение полинома на число
  	/*Создаем новый полином, в который будем копировать
  	  1.Если число = 0, то весь полином обнуляется (рез-т умножения пустой)
  	  2.Пока полином не кончился - умножаем каждый из мономов полинома на число
  	  */
    
    public void multipleK(double k){                         // умножение полинома на коэффициент
        if (Math.abs(k) <= 1e-6) {                           // если коэффициент к = 0, то обнуляем полином
            head = null;
            return;
        }
        Monom cur = head;                                     // текущий моном полинома
        while (cur != null){                                 // пока полином не закончился умножаем каждый из коэффициентов полинома на к
            cur.k *= k;
            cur = cur.next;
        }
    }
 
    	
    
    public Polynom multiple(Polynom poly){                        
        Monom newMonom = null;                                
        Monom curNewMonom = null;                             
        Polynom newPoly = new Polynom();                          
        Monom newPrev = null;                                
        int st;                                             
 
        Monom curFirst = head;                               
        Monom curSecond;                                     
        while (curFirst != null){                           
            curSecond = poly.head;                          
            curNewMonom = newPoly.head;                      
 
            while (curSecond != null){                      
                st = curFirst.st + curSecond.st;            
                if (Math.abs(curFirst.k* curSecond.k) <= 1e-6){         
                    curSecond = curSecond.next;
                }
                else if (curNewMonom == null){                    // если новый полином еще не задан
                    newPoly.head = new Monom(curFirst.st + curSecond.st, curSecond.k * curFirst.k);      // задаем начальный моном нового полинома
                    curSecond = curSecond.next;             // двигаем второй полином
                    curNewMonom = newPoly.head;              // запоминаем новый текущий моном в новом полиноме
                }
                else {
                    if (curNewMonom.st == st){               // если посчитанная степень совпадает со степенью текущего монома в новом полиноме
                        if (curNewMonom.k + curFirst.k * curSecond.k == 0){              // и если сумма коэффициентов равно 0 (при приведении подобных)
                            if (curNewMonom == newPoly.head){                            // если новый моном находится в самом начале
                                curNewMonom = curNewMonom.next;                           // сдвигаем новый полином
                                newPoly.head = curNewMonom;
                            }
                            else {
                                newPrev.next = curNewMonom.next;                         // если сумма равно 0, а текущий моном нового полинома
                                curNewMonom = newPrev.next;                              // находится в середине, то делаем вставку после предыдущего
                            }
                        }
                        else {
                            curNewMonom.k += curFirst.k * curSecond.k;                   // приводи подобные
                        }
                        curSecond = curSecond.next;          // двигаем второй
                    }
                    else if(curNewMonom.next == null){        // если следующего монома в новом полиноме нет, делаем вставку нового монома в конец полинома
                        curNewMonom.next = new Monom(st, curFirst.k * curSecond.k);
                        newPrev = curNewMonom;                // запоминаем предыдущий
                        curNewMonom = curNewMonom.next;        // двигаем второй и новый полиномы
                        curSecond = curSecond.next;
                    }
                    else if (curNewMonom.st > st){            // если степень текущего монома в новом полиноме больше, полученной при умножении
                        newPrev = curNewMonom;                // двигаем новый полином
                        curNewMonom = curNewMonom.next;
                    }
                    else if (curNewMonom.st < st){            // если степень текущего монома в новом полиноме меньше, полученной при умножении
                        newMonom = new Monom(st, curFirst.k* curSecond.k);    // создаем новый моном, вставляем текущий моном нового полинома
                        newMonom.next = curNewMonom.next;      // за ним
                        curNewMonom.next = newMonom;
                        newPrev = curNewMonom;
                        curNewMonom = curNewMonom.next;        // двигаем новый и второй полиномы
                        curSecond = curSecond.next;
                    }
                }
            }
            curFirst = curFirst.next;                        // сдвигаем первый полином
        }
        return newPoly;                                      // возвращаем новый полином
    }
 
    
    
 
    
    public double getPoint (double x){                       // получение значения в точке
        Monom cur = head;                                     // сдвигаем полином в голову
        double result = 0;                                   // значение в точке, вычисляемое по правилу Горнера
        int st = cur.st;                                     // степень текущего монома
        while (cur != null){                       // пока текущий моном существует и его степень больше 0
            if (st == cur.st){                               // если степень равна степени текущего монома, то двигаем моном и увеличиваем сумму
                result += cur.k;
                cur = cur.next;
            }
            if (st != 0)
                result *=x;                                      // умножаем на значение в точке и умеьшаем степень
            st--;
        }
        if (st > 0){                                         // если осталась степень
            for (int i = 0; i < st; i++){
                result *=x;
            }
        }
        if (cur != null){                                    // если свободный член полинома существует, повышаем сумму
            result += cur.k;
        }
        return result;
    }
 
    
  
    public void print() {           //вывод полинома
        Monom curMonom = head;        // curMonom - текущий моном, изачально равный первому моному полинома
        if (head == null)           // если первый моном нулевой, то выходим
            return;
        System.out.printf("%15.6E",  head.k);               // выводим коэффициент первого элемента
        System.out.print("*x^" + head.st);                  // выводим степень первого элемента
        curMonom = curMonom.next;                             // двигаем моном по полиному (переходим к следующему)
        while (curMonom != null){                            // пока полином не закончился
            if (curMonom.k < 0){                             // если коэффициент отрицательный, то выводим -коэффициент + степень
                System.out.printf("%15.6E",  curMonom.k);
                System.out.print("*x^" + curMonom.st);
            }
            else {                                           // если коэффициент положительный, то выводим +коэффициент + степень
                System.out.print(" +");
                System.out.printf("%15.6E",  curMonom.k);
                System.out.print("*x^" + curMonom.st);
            }
            curMonom = curMonom.next;                         // двигаем моном по полиному
        }
    }
    
    
    public void changeBin(double k){                         // изменение бинома по коэффициенту к
        Monom cur = head;                                     // текущий моном полинома
        if (cur == null){                                    // если моном нулевой, то вставляем в голову х (монов с коэффициентом 1 и степенью 1)
            head = new Monom(1, 1);                     // следующий за ним моном - свободный член к
            head.next = new Monom(0, k);
        }
        else{                                                // если текущий полином существует, то следующий за ним моном - свободный член к
            cur = cur.next;
            cur.k = k;
        }
    }
}