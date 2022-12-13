package controll;

import lombok.Data;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Data
public class FuzzyLogic {
    double[][] x = new double[2][201];
    double[][] y = new double[5][201];
    double[][] yDirection = new double[3][41];
    double[][] yDirectionCut = new double[4][41];
    double outY = 0;

    public FuzzyLogic() {
        fillLogic();

    }

    //Wypełnianie termów
    private void fillLogic() {

        for (int i = 0; i < 201; i++) {
            y[0][i] = i - 100;

            if (i < 41) {
                yDirection[0][i] = i - 20;
            }

            if (i > 99 && i < 201) {
                x[0][i] = i - 100;
            }
        }

        for (int i = 0; i < 201; i++) {

            // Ustawianie termów RX


            //Wilk jest z przodu
            if (i >= 100 && i <= 190)
                x[1][i] = 1;
            else if (i > 190 && i <= 200)
                x[1][i] = (200 - i) / 10d;
            else
                x[1][i] = 0;



            // Ustawianie termów RY

            //Daleko Północny Wschód
            if (i >= 0 && i <= 40)
                y[1][i] = 1;
            else if (i > 40 && i <= 50)
                y[1][i] =  (50 - i) / 10d;
            else
                y[1][i] = 0;

            //Blisko Północny Wschód
            if (i >= 40 && i <= 50)
                y[2][i] =  (i - 40) / 10d;
            else if (i > 50 && i <= 95)
                y[2][i] = 1;
            else if (i > 95 && i <= 105)
                y[2][i] =  105 - i / 10d;
            else
                y[2][i] = 0;

            //Blisko Południowy Wschód
            if (i >= 95 && i <= 105)
                y[3][i] = (i - 105) / 10d;
            else if (i > 105 && i <= 150)
                y[3][i] = 1;
            else if (i > 150 && i <= 160)
                y[3][i] =  160 - i / 10d;
            else
                y[3][i] = 0;

            //Daleko Południowy Wschód
            if (i >= 150 && i <= 160)
                y[4][i] =  (i - 150) / 10d;
            else if (i > 160 && i <= 200)
                y[4][i] = 1;
            else
                y[4][i] = 0;



            if (i < 41) {


                // Ustawianie termów sterowanie osią Y

                //Do góry
                if (i >= 0 && i <= 19)
                    yDirection[1][i] = 1;

                else if (i > 19 && i <= 23)
                    yDirection[1][i] =  (23 - i) / 4d;
                else
                    yDirection[1][i] = 0;

                //W dół

                if (i >= 19 && i <= 23)
                    yDirection[2][i] =  (i - 23) / 4d;
                else if (i > 23 && i <= 40)
                    yDirection[2][i] = 1;
                else
                    yDirection[2][i] = 0;
            }

        }
    }


    public void fuzzify(int x, int y) {

        //Rozmycie
        double IN_RX0 = this.x[1][x + 100];


        //Daleko NE
        double IN_RY1 = this.y[1][y + 100];
        //Blisko NE
        double IN_RY0 = this.y[2][y + 100];
        //Blisko SE
        double IN_RY2 = this.y[3][y + 100];
        //Daleko SE
        double IN_RY3 = this.y[4][y + 100];



        //Reguły dla przemieszczenia po Y - góra dół dla kolizji
        double RegYS_0 = Math.min(IN_RX0, IN_RY0);
        double RegYS_1 = Math.min(IN_RX0, IN_RY1);

        double RegYN_0 = Math.min(IN_RX0, IN_RY2);
        double RegYN_1 = Math.min(IN_RX0, IN_RY3);


        //Aggregacja Y
        double outRegYS = Math.max(RegYS_0, RegYS_1);
        double outRegYN = Math.max(RegYN_0, RegYN_1);



        //Obciecie Y
        for (int i = 0; i < 41; i++) {
            yDirectionCut[0][i] = yDirection[0][i];
            //Obciecie poszczegolnych zbiorow
            yDirectionCut[1][i] = Math.min(outRegYS, yDirection[1][i]);
            yDirectionCut[2][i] = Math.min(outRegYN, yDirection[2][i]);
            //Aggregacja
            yDirectionCut[3][i] = Math.max(yDirectionCut[1][i], yDirectionCut[2][i]);
        }


        double outYV = 0;
        double dzielnikYV = 0;


        //Metoda srodka ciezkosci

        for (int i = 0; i < 41; i++) {
            outYV += yDirectionCut[3][i] * yDirectionCut[0][i];
            dzielnikYV += yDirectionCut[3][i];
        }

        outY = (outYV == 0) ? 0 : (outYV / dzielnikYV);

    }

}
