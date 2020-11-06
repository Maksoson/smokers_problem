package smokers_problem;

import java.util.Random;
import static smokers_problem.Main.*;

/**
 * Класс посредника, выкладывающего 2 из 3 компонентов на стол
 */
public class Barman extends Thread {
    private final Random random = new Random();

    /**
     * Время сбора компонентов
     */
    private int collectingTime;

    /**
     * Работа потока
     */
    @Override
    public void run() {
        while (true) {
            try {
                emptyTable.acquire();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            int smoker = random.nextInt(3) + 1;
            String barmanName = "\"Bob (the barman)\"";
            if (smoker == 1) {              // Курить будет первый курильщик
                System.out.println(barmanName + " is now collecting tobacco and matches (for the first smoker)...");
                collectionOfComponents();
                System.out.println(barmanName + " laid out the tobacco and matches (" + collectingTime + " ms)");

                // На столе табак и спички
                tobaccoAndMatches.release();
            } else if (smoker == 2) {       // Курить будет второй курильщик
                System.out.println(barmanName + " is now collecting paper and matches (for the second smoker)...");
                collectionOfComponents();
                System.out.println(barmanName + " laid out the paper and matches (" + collectingTime + " ms)");

                // На столе бумага и спички
                paperAndMatches.release();
            } else if (smoker == 3) {       // Курить будет третий курильщик
                System.out.println(barmanName + " is now collecting tobacco and paper (for the third smoker)...");
                collectionOfComponents();
                System.out.println(barmanName + " laid out the tobacco and paper (" + collectingTime + " ms)");

                // На столе бумага и табак
                tobaccoAndPaper.release();
            }
        }
    }

    /**
     * Генерируем случайное время ожидания сбора компонентов
     */
    private void collectionOfComponents() {
        collectingTime = random.nextInt(maxCollectingTime - minCollectingTime) + minCollectingTime;

        try {
            sleep(collectingTime);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
