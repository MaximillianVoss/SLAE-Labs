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


    /**
     * Умножение текущего полинома на другой полином.
     *
     * @param anotherPolynom полином, на который умножается текущий полином
     * @return новый полином, который является результатом умножения
     */
    public Polynom multiply(Polynom anotherPolynom) {
        // Создание новых объектов
        Monomial newMonomial = null;
        Monomial currentNewMonomial = null;
        Polynom newPolynom = new Polynom();
        Monomial previousNewMonomial = null;
        int calculatedDegree;

        // Инициализация текущих мономов для первого и второго полинома
        Monomial currentFirstMonomial = head;
        Monomial currentSecondMonomial;

        // Проходим по всем мономам в первом полиноме
        while (currentFirstMonomial != null) {
            currentSecondMonomial = anotherPolynom.head;
            currentNewMonomial = newPolynom.head;

            // Проходим по всем мономам второго полинома
            while (currentSecondMonomial != null) {
                // Рассчитываем степень для нового монома
                calculatedDegree = currentFirstMonomial.degree + currentSecondMonomial.degree;

                // Если произведение коэффициентов меньше или равно пороговому значению, переходим к следующему моному второго полинома
                if (Math.abs(currentFirstMonomial.coefficient * currentSecondMonomial.coefficient) <= 1e-6) {
                    currentSecondMonomial = currentSecondMonomial.nextMonomial;
                }
                // Проверяем, был ли уже инициализирован новый полином
                else if (currentNewMonomial == null) {
                    newPolynom.head = new Monomial(calculatedDegree, currentSecondMonomial.coefficient * currentFirstMonomial.coefficient);
                    currentSecondMonomial = currentSecondMonomial.nextMonomial;
                    currentNewMonomial = newPolynom.head;
                }
                // Проходим по уже инициализированным элементам нового полинома и добавляем новые элементы при необходимости
                else {
                    // Степень текущего монома нового полинома равна вычисленной степени
                    if (currentNewMonomial.degree == calculatedDegree) {
                        if (currentNewMonomial.coefficient + currentFirstMonomial.coefficient * currentSecondMonomial.coefficient == 0) {
                            if (currentNewMonomial == newPolynom.head) {
                                currentNewMonomial = currentNewMonomial.nextMonomial;
                                newPolynom.head = currentNewMonomial;
                            } else {
                                previousNewMonomial.nextMonomial = currentNewMonomial.nextMonomial;
                                currentNewMonomial = previousNewMonomial.nextMonomial;
                            }
                        } else {
                            currentNewMonomial.coefficient += currentFirstMonomial.coefficient * currentSecondMonomial.coefficient;
                        }
                        currentSecondMonomial = currentSecondMonomial.nextMonomial;
                    }
                    // Следующего монома в новом полиноме нет, делаем вставку нового монома в конец полинома
                    else if (currentNewMonomial.nextMonomial == null) {
                        currentNewMonomial.nextMonomial = new Monomial(calculatedDegree, currentFirstMonomial.coefficient * currentSecondMonomial.coefficient);
                        previousNewMonomial = currentNewMonomial;
                        currentNewMonomial = currentNewMonomial.nextMonomial;
                        currentSecondMonomial = currentSecondMonomial.nextMonomial;
                    }
                    // Степень текущего монома в новом полиноме больше вычисленной степени
                    else if (currentNewMonomial.degree > calculatedDegree) {
                        previousNewMonomial = currentNewMonomial;
                        currentNewMonomial = currentNewMonomial.nextMonomial;
                    }
                    // Степень текущего монома в новом полиноме меньше вычисленной степени
                    else if (currentNewMonomial.degree < calculatedDegree) {
                        newMonomial = new Monomial(calculatedDegree, currentFirstMonomial.coefficient * currentSecondMonomial.coefficient);
                        newMonomial.nextMonomial = currentNewMonomial.nextMonomial;
                        currentNewMonomial.nextMonomial = newMonomial;
                        previousNewMonomial = currentNewMonomial;
                        currentNewMonomial = currentNewMonomial.nextMonomial;
                        currentSecondMonomial = currentSecondMonomial.nextMonomial;
                    }
                }
            }
            // Переходим к следующему моному первого полинома
            currentFirstMonomial = currentFirstMonomial.nextMonomial;
        }

        // Возвращаем новый полином
        return newPolynom;
    }


    /**
     * Вычисление значения полинома в заданной точке x, используя схему Горнера.
     *
     * @param x значение, в котором вычисляется полином
     * @return значение полинома в точке x
     */
    public double getPoint(double x) {
        Monomial currentMonomial = head; // Начинаем с головы списка мономов
        double result = 0; // Результат, который будет аккумулировать вычисленное значение

        // Степень текущего монома, инициализируем самой большой степенью (степенью головы)
        int currentDegree = currentMonomial.degree;

        // Проходим по мономам пока они существуют и их степень больше 0
        while (currentMonomial != null) {
            if (currentDegree == currentMonomial.degree) {
                // Если степень текущего монома равна текущей степени, увеличиваем результат
                result += currentMonomial.coefficient;
                // и переходим к следующему моному
                currentMonomial = currentMonomial.nextMonomial;
            }
            if (currentDegree != 0) {
                // Умножаем результат на x (если степень не равна 0) и уменьшаем степень
                result *= x;
                currentDegree--;
            }
        }

        // Если еще остались степени (при погрешностях в данных), умножаем результат на x для каждой оставшейся степени
        if (currentDegree > 0) {
            for (int i = 0; i < currentDegree; i++) {
                result *= x;
            }
        }

        // Если свободный член полинома существует, увеличиваем сумму на его значение
        if (currentMonomial != null) {
            result += currentMonomial.coefficient;
        }

        // Возвращаем вычисленное значение полинома в точке x
        return result;
    }


    /**
     * Вывод полинома в формате "коэффициент * x^степень".
     */
    public void print() {
        Monomial curMonomial = head; // Указатель на текущий моном, изначально равен первому моному полинома
        if (head == null) {
            // Если первый моном нулевой (полином пуст), прекращаем вывод
            return;
        }
        // Выводим коэффициент и степень первого монома
        System.out.printf("%15.6E", head.coefficient);
        System.out.print("*x^" + head.degree);
        curMonomial = curMonomial.nextMonomial; // Переходим к следующему моному
        // Продолжаем для остальных мономов полинома
        while (curMonomial != null) {
            if (curMonomial.coefficient < 0) {
                // Если коэффициент отрицательный, выводим его как есть
                System.out.printf("%15.6E", curMonomial.coefficient);
                System.out.print("*x^" + curMonomial.degree);
            } else {
                // Если коэффициент положительный, выводим его с плюсом
                System.out.print(" +");
                System.out.printf("%15.6E", curMonomial.coefficient);
                System.out.print("*x^" + curMonomial.degree);
            }
            curMonomial = curMonomial.nextMonomial; // Переходим к следующему моному
        }
    }

    /**
     * Изменение бинома по коэффициенту k.
     *
     * @param k коэффициент, на который изменяем бином
     */
    public void changeBin(double k) {
        Monomial cur = head; // Указатель на текущий моном, изначально равен первому моному полинома
        if (cur == null) {
            // Если текущий моном нулевой, вставляем в голову новый моном со степенью 1 и коэффициентом 1,
            // затем добавляем свободный член с коэффициентом k
            head = new Monomial(1, 1);
            head.nextMonomial = new Monomial(0, k);
        } else {
            // Если текущий моном существует, переходим к следующему и обновляем его коэффициент на k
            cur = cur.nextMonomial;
            cur.coefficient = k;
        }
    }

}
