package L3_4.Common;

/**
 * Класс, представляющий полином, который состоит из мономов.
 */
public class Polynom {
    /**
     * Внутренний класс, представляющий моном - одночлен (элемент полинома).
     */
    private class Monomial {
        // Степень монома
        int degree;
        // Коэффициент монома
        double coefficient;
        // Ссылка на следующий моном в полиноме
        Monomial nextMonomial;

        /**
         * Конструктор монома.
         *
         * @param degree      степень монома
         * @param coefficient коэффициент монома
         */
        Monomial(int degree, double coefficient) {
            this.coefficient = coefficient;
            this.degree = degree;
            this.nextMonomial = null;
        }

        /**
         * Копирующий конструктор для монома.
         *
         * @param monomial моном, который нужно скопировать
         */
        Monomial(Monomial monomial) {
            this.degree = monomial.degree;
            this.coefficient = monomial.coefficient;
            this.nextMonomial = monomial.nextMonomial;
        }
    }

    // Первый моном полинома
    Monomial head;

    /**
     * Конструктор полинома по моному.
     *
     * @param degree      степень монома
     * @param coefficient коэффициент монома
     */
    public Polynom(int degree, double coefficient) {
        head = new Monomial(degree, coefficient);
    }

    /**
     * Конструктор пустого полинома.
     */
    public Polynom() {
        head = null;
    }


    //region Описание сложения двух полиномов
    /*
    public Polynom sum(){
	моном первого полинома (передаем ссылку) - к нему прибавляем
	  моном второго полинома (передаем ссылку) - его прибавляем
	  Создаем новый полином, в который будем копировать
	  1.Заводим внешний цикл, в котором идем одновременно по двум полиномам (пока они не закончатся)
	  2.Сравниваем степени первого и второго
	 	2.1.Если первый больше чем второй - копируем его в новый полином и в первом переходим к след. эл-ту
	 	2.2.Если они равны - находим сумму коэфф, записываем в новый полином и переходим и в первом и во втором к след.моному
	 	2.3.Если второй больше чем первый - копируем моном второго, меняем голову
	  3.Делаем это пока не кончится один из полиномов
	  4.По завершении одного из списков - другой копируется без всяких проверок
	  */
    //endregion

    /**
     * Суммирует текущий полином с переданным в параметрах.
     * Результат сохраняется в текущем полиноме.
     *
     * @param secondPolynom второй полином для сложения
     */
    public void sum(Polynom secondPolynom) {
        // Текущий моном первого полинома
        Monomial currentMonomialFirst = head;

        // Текущий моном второго полинома
        Monomial currentMonomialSecond = secondPolynom.head;

        // Предыдущий моном первого полинома
        // Изначально null, поскольку нет мономов перед первым
        Monomial previousMonomialFirst = null;

        // Если первый моном отсутствует или его коэффициент близок к нулю
        if (head == null || Math.abs(head.coefficient) <= 1e-6) {
            // то первый полином ссылается на второй
            head = currentMonomialSecond;
            return;
        }

        // Пока существуют оба полинома
        while (currentMonomialFirst != null && currentMonomialSecond != null) {
            // Если степень монома первого полинома больше
            if (currentMonomialFirst.degree > currentMonomialSecond.degree) {
                // Запоминаем предыдущий
                previousMonomialFirst = currentMonomialFirst;
                // Двигаемся по первому полиному
                currentMonomialFirst = currentMonomialFirst.nextMonomial;
            }
            // Если степени равны, то складываем
            else if (currentMonomialFirst.degree == currentMonomialSecond.degree) {
                // Если после сложения коэффициент обнуляется
                if (Math.abs(currentMonomialFirst.coefficient + currentMonomialSecond.coefficient) <= 1e-6) {
                    // И коэффициент обнулился в первом мономе первого полинома
                    if (currentMonomialFirst == head) {
                        // Сдвигаем первый полином
                        head = head.nextMonomial;
                    }
                    // Если обнулился произвольный моном в середине первого полинома,
                    else {
                        // То "перепрыгиваем" через него, переприсваивая следующие элементы
                        previousMonomialFirst.nextMonomial = currentMonomialFirst.nextMonomial;
                    }
                }
                // Если не обнуляется коэффициент
                else {
                    // Просто складываем и запоминаем предыдущий
                    currentMonomialFirst.coefficient += currentMonomialSecond.coefficient;
                    previousMonomialFirst = currentMonomialFirst;
                }

                // Двигаем первый и второй полиномы
                currentMonomialFirst = currentMonomialFirst.nextMonomial;
                currentMonomialSecond = currentMonomialSecond.nextMonomial;
            }
            // Если степень монома второго полинома больше
            else {
                // Если первый полином находится в самом начале, то копируем моном второго полинома
                if (currentMonomialFirst == head) {
                    // Копируем моном второго полинома и меняем голову
                    Monomial copy = new Monomial(currentMonomialSecond);
                    head = copy;
                    head.nextMonomial = currentMonomialFirst;

                    // Запоминаем предыдущий
                    previousMonomialFirst = head;
                }
                // Если первый полином находится в середине
                else {
                    // Копируем моном второго полинома
                    Monomial copy = new Monomial(currentMonomialSecond);

                    // Вставляем скопированный моном перед текущим
                    previousMonomialFirst.nextMonomial = copy;
                    copy.nextMonomial = currentMonomialFirst;

                    // Запоминаем предыдущий
                    previousMonomialFirst = copy;
                }

                // Двигаем второй полином
                currentMonomialSecond = currentMonomialSecond.nextMonomial;
            }
        }

        // Если второй полином не закончился, но закончился первый
        if (currentMonomialSecond != null) {
            // Вставляем второй в конец первого
            previousMonomialFirst.nextMonomial = currentMonomialSecond;
        }
    }


    //region Описание умножения полинома на число
    /*public void multipleX(double x){
  	Создаем новый полином, в который будем копировать
  	  1.Если число = 0, то весь полином обнуляется (рез-т умножения пустой)
  	  2.Пока полином не кончился - умножаем каждый из мономов полинома на число
  	  */
    //endregion

    /**
     * Умножение полинома на коэффициент.
     *
     * @param coefficient коэффициент, на который умножается полином
     */
    public void multipleByCoefficient(double coefficient) {
        // Если коэффициент близок к нулю, обнуляем полином и выходим из метода
        if (Math.abs(coefficient) <= 1e-6) {
            head = null;
            return;
        }

        // Устанавливаем текущий моном на начало полинома
        Monomial currentMonomial = head;

        // Проходим по всем мономам в полиноме
        while (currentMonomial != null) {
            // Умножаем коэффициент текущего монома на переданный коэффициент
            currentMonomial.coefficient *= coefficient;

            // Переходим к следующему моному
            currentMonomial = currentMonomial.nextMonomial;
        }
    }


    public Polynom multiply(Polynom poly) {
        Monomial newMonomial = null;
        Monomial curNewMonomial = null;
        Polynom newPoly = new Polynom();
        Monomial newPrev = null;
        int st;

        Monomial curFirst = head;
        Monomial curSecond;
        while (curFirst != null) {
            curSecond = poly.head;
            curNewMonomial = newPoly.head;

            while (curSecond != null) {
                st = curFirst.degree + curSecond.degree;
                if (Math.abs(curFirst.coefficient * curSecond.coefficient) <= 1e-6) {
                    curSecond = curSecond.nextMonomial;
                } else if (curNewMonomial == null) {                    // если новый полином еще не задан
                    newPoly.head = new Monomial(curFirst.degree + curSecond.degree, curSecond.coefficient * curFirst.coefficient);      // задаем начальный моном нового полинома
                    curSecond = curSecond.nextMonomial;             // двигаем второй полином
                    curNewMonomial = newPoly.head;              // запоминаем новый текущий моном в новом полиноме
                } else {
                    if (curNewMonomial.degree == st) {               // если посчитанная степень совпадает со степенью текущего монома в новом полиноме
                        if (curNewMonomial.coefficient + curFirst.coefficient * curSecond.coefficient == 0) {              // и если сумма коэффициентов равно 0 (при приведении подобных)
                            if (curNewMonomial == newPoly.head) {                            // если новый моном находится в самом начале
                                curNewMonomial = curNewMonomial.nextMonomial;                           // сдвигаем новый полином
                                newPoly.head = curNewMonomial;
                            } else {
                                newPrev.nextMonomial = curNewMonomial.nextMonomial;                         // если сумма равно 0, а текущий моном нового полинома
                                curNewMonomial = newPrev.nextMonomial;                              // находится в середине, то делаем вставку после предыдущего
                            }
                        } else {
                            curNewMonomial.coefficient += curFirst.coefficient * curSecond.coefficient;                   // приводи подобные
                        }
                        curSecond = curSecond.nextMonomial;          // двигаем второй
                    } else if (curNewMonomial.nextMonomial == null) {        // если следующего монома в новом полиноме нет, делаем вставку нового монома в конец полинома
                        curNewMonomial.nextMonomial = new Monomial(st, curFirst.coefficient * curSecond.coefficient);
                        newPrev = curNewMonomial;                // запоминаем предыдущий
                        curNewMonomial = curNewMonomial.nextMonomial;        // двигаем второй и новый полиномы
                        curSecond = curSecond.nextMonomial;
                    } else if (curNewMonomial.degree > st) {            // если степень текущего монома в новом полиноме больше, полученной при умножении
                        newPrev = curNewMonomial;                // двигаем новый полином
                        curNewMonomial = curNewMonomial.nextMonomial;
                    } else if (curNewMonomial.degree < st) {            // если степень текущего монома в новом полиноме меньше, полученной при умножении
                        newMonomial = new Monomial(st, curFirst.coefficient * curSecond.coefficient);    // создаем новый моном, вставляем текущий моном нового полинома
                        newMonomial.nextMonomial = curNewMonomial.nextMonomial;      // за ним
                        curNewMonomial.nextMonomial = newMonomial;
                        newPrev = curNewMonomial;
                        curNewMonomial = curNewMonomial.nextMonomial;        // двигаем новый и второй полиномы
                        curSecond = curSecond.nextMonomial;
                    }
                }
            }
            curFirst = curFirst.nextMonomial;                        // сдвигаем первый полином
        }
        return newPoly;                                      // возвращаем новый полином
    }


    public double getPoint(double x) {                       // получение значения в точке
        Monomial cur = head;                                     // сдвигаем полином в голову
        double result = 0;                                   // значение в точке, вычисляемое по правилу Горнера
        int st = cur.degree;                                     // степень текущего монома
        while (cur != null) {                       // пока текущий моном существует и его степень больше 0
            if (st == cur.degree) {                               // если степень равна степени текущего монома, то двигаем моном и увеличиваем сумму
                result += cur.coefficient;
                cur = cur.nextMonomial;
            }
            if (st != 0)
                result *= x;                                      // умножаем на значение в точке и умеьшаем степень
            st--;
        }
        if (st > 0) {                                         // если осталась степень
            for (int i = 0; i < st; i++) {
                result *= x;
            }
        }
        if (cur != null) {                                    // если свободный член полинома существует, повышаем сумму
            result += cur.coefficient;
        }
        return result;
    }


    public void print() {           //вывод полинома
        Monomial curMonomial = head;        // curMonom - текущий моном, изачально равный первому моному полинома
        if (head == null)           // если первый моном нулевой, то выходим
            return;
        System.out.printf("%15.6E", head.coefficient);               // выводим коэффициент первого элемента
        System.out.print("*x^" + head.degree);                  // выводим степень первого элемента
        curMonomial = curMonomial.nextMonomial;                             // двигаем моном по полиному (переходим к следующему)
        while (curMonomial != null) {                            // пока полином не закончился
            if (curMonomial.coefficient < 0) {                             // если коэффициент отрицательный, то выводим -коэффициент + степень
                System.out.printf("%15.6E", curMonomial.coefficient);
                System.out.print("*x^" + curMonomial.degree);
            } else {                                           // если коэффициент положительный, то выводим +коэффициент + степень
                System.out.print(" +");
                System.out.printf("%15.6E", curMonomial.coefficient);
                System.out.print("*x^" + curMonomial.degree);
            }
            curMonomial = curMonomial.nextMonomial;                         // двигаем моном по полиному
        }
    }


    public void changeBin(double k) {                         // изменение бинома по коэффициенту к
        Monomial cur = head;                                     // текущий моном полинома
        if (cur == null) {                                    // если моном нулевой, то вставляем в голову х (монов с коэффициентом 1 и степенью 1)
            head = new Monomial(1, 1);                     // следующий за ним моном - свободный член к
            head.nextMonomial = new Monomial(0, k);
        } else {                                                // если текущий полином существует, то следующий за ним моном - свободный член к
            cur = cur.nextMonomial;
            cur.coefficient = k;
        }
    }
}
