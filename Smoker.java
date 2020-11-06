package smokers_problem;

import java.util.Random;
import java.util.concurrent.Semaphore;

import static smokers_problem.Main.*;

/**
 * Абстрактный класс курильщика
 */
public class Smoker extends Thread {
    private static final Random random = new Random();

    /**
     * Время скручивания сигареты
     */
    protected static int makeTime;

    /**
     * Время курения сигареты
     */
    protected static int smokeTime;

    /**
     * Имя курильщика
     */
    private final String name;

    /**
     * Семафор, указывающий на нужные курильщику компоненты
     */
    private final Semaphore componentsNeeded;

    /**
     * Smoker Constructor
     * @param componentsNeeded
     * @param name
     */
    public Smoker(Semaphore componentsNeeded, String name) {
        this.componentsNeeded = componentsNeeded;
        this.name = name;
    }

    /**
     * Скручивание сигареты
     */
    protected static void makeCigarette() {
        // Генерируем случайное время скручивания сигареты
        makeTime = random.nextInt(maxMakingTime - minMakingTime) + minMakingTime;
        startSleep(makeTime);
    }

    /**
     * Скуривание сигареты
     */
    protected static void smokeCigarette() {
        // Генерируем случайное время курения сигареты
        smokeTime = random.nextInt(maxSmokingTime - minSmokingTime) + minSmokingTime;
        startSleep(smokeTime);
    }

    /**
     * Скручивание и скуривание сигареты
     * @param name
     */
    protected static synchronized void makeAndSmokeCigarette(String name) {
        System.out.println(name + " is making a new cigarette...");
        makeCigarette();
        System.out.println(name + " make his cigarette (" + (makeTime) + " ms)");

        // После скручивания сигареты, стол освобождается
        emptyTable.release();

        System.out.println(name + " is smoking a cigarette...");
        smokeCigarette();
        System.out.println(name + " smoked his cigarette (" + (smokeTime) + " ms)");
    }

    /**
     * Ожидание
     */
    private static void startSleep(int time) {
        try {
            sleep(time);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Работа потока
     */
    @Override
    public void run() {
        while (true) {
            try {
                // "Блокируем" семафор по выложенным компонентам
                componentsNeeded.acquire();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            makeAndSmokeCigarette(name);
        }
    }
}
