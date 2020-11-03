package smokers_problem;

import java.util.concurrent.Semaphore;

/**
 * !!! Формулировка задачи !!!
 *
 * Изначально есть три заядлых курильщика, сидящих за столом. Каждому из них доступно бесконечное количество одного из трёх компонентов: у одного курильщика — табака, у второго — бумаги, у третьего — спичек.
 * Для того чтобы делать и курить сигары, необходимы все три компонента.
 * Также, кроме курильщиков, есть бармен, помогающий им делать сигареты: он недетерминированно выбирает двух курильщиков, берёт у них по одному компоненту из их запасов и кладёт их на стол.
 * Третий курильщик забирает ингредиенты со стола и использует их для изготовления сигареты, которую он курит некоторое время.
 * В это время бармен, завидев стол пустым, снова выбирает двух курильщиков случайным образом и кладёт их компоненты на стол. Процесс повторяется бесконечно.
 * Курильщики, по условию проблемы, честные: они не прячут компоненты, выданные барменом, — они лишь скручивают сигарету тогда, когда докурят предыдущую.
 * Если бармен кладёт, например, табак и бумагу на стол, пока поставщик спичек курит, то табак и бумага останутся нетронутыми на столе, пока курильщик со спичками не докурит сигарету и только затем не возьмёт табак и бумагу.
 */

public class Main {
    // Границы времени сбора компонентов барменом
    static public int minCollectingTime = 1000;
    static public int maxCollectingTime = 1500;

    // Границы времени скручивания сигареты курильщиком
    static public int minMakingTime = 3000;
    static public int maxMakingTime = 3500;

    // Границы времени курения сигареты
    static public int minSmokingTime = 4000;
    static public int maxSmokingTime = 5000;

    // Семафоры, указывающие на выложенные компоненты
    static public final Semaphore tobaccoAndMatches = new Semaphore(0);
    static public final Semaphore paperAndMatches = new Semaphore(0);
    static public final Semaphore tobaccoAndPaper = new Semaphore(0);
    // Семафор, отвечающий за заполненность стола бармена
    static public final Semaphore emptyTable = new Semaphore(1);


    public static void main(String[] args) {
        new Barman().start();

        new Smoker(tobaccoAndMatches, "\"Jake (the first smoker)\"").start();
        new Smoker(paperAndMatches, "\"Andrew (the second smoker)\"").start();
        new Smoker(tobaccoAndPaper, "\"Mike (the third smoker)\"").start();
    }
}
