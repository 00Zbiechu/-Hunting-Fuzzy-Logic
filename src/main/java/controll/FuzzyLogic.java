package controll;

import lombok.Data;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Data
public class FuzzyLogic {
    double[][] x = new double[4][201];
    double[][] y = new double[5][201];
    double[][] xDirection = new double[3][41];
    double[][] yDirection = new double[3][41];
    double[][] xDirectionCut = new double[2][41];
    double[][] yDirectionCut = new double[4][41];
    double outY = 0;
    double outX = 0;

    public FuzzyLogic() {
        fillLogic();

    }

    //Wypełnianie termów
    private void fillLogic() {

        for (int i = 0; i < 201; i++) {
            x[0][i] = i - 100;
            y[0][i] = i - 100;
            if (i < 41) {
                xDirection[0][i] = i - 20;
                yDirection[0][i] = i - 20;
            }
        }

        for (int i = 0; i < 201; i++) {
            // Ustawianie termów RX

            //Z tyłu
            if (i >= 0 && i < 90)
                x[1][i] = 1;
            else if (i >= 90 && i <= 100)
                x[1][i] =  (100 - i) / 10d;
            else
                x[1][i] = 0;

            //Kolizja
            if (i >= 100 && i <= 190)
                x[2][i] = 1;
            else if (i > 190 && i <= 200)
                x[2][i] = (200 - i) / 10d;
            else
                x[2][i] = 0;

            //Z przodu
            if (i >= 190 && i <= 200)
                x[3][i] = (i - 190) / 10d;
            else
                x[3][i] = 0;

            // Ustawianie termów RY

            //Daleko NE
            if (i >= 0 && i <= 40)
                y[1][i] = 1;
            else if (i > 40 && i <= 50)
                y[1][i] =  (50 - i) / 10d;
            else
                y[1][i] = 0;

            //Blisko NE
            if (i >= 40 && i <= 50)
                y[2][i] =  (i - 40) / 10d;
            else if (i > 50 && i <= 90)
                y[2][i] = 1;
            else if (i > 90 && i <= 100)
                y[2][i] =  100 - i / 10d;
            else
                y[2][i] = 0;

            //Blisko SE
            if (i >= 100 && i <= 110)
                y[3][i] = (i - 100) / 10d;
            else if (i > 110 && i <= 150)
                y[3][i] = 1;
            else if (i > 150 && i <= 160)
                y[3][i] =  160 - i / 10d;
            else
                y[3][i] = 0;

            //Daleko SE
            if (i >= 150 && i <= 160)
                y[4][i] =  (i - 150) / 10d;
            else if (i > 160 && i <= 200)
                y[4][i] = 1;
            else
                y[4][i] = 0;

            if (i < 41) {
                // Ustawianie termów sterowanie osią X

                //W tył
                if (i >= 0 && i <= 10)
                    xDirection[1][i] = 1;
                else if (i > 10 && i <= 20)
                    xDirection[1][i] =  (20 - i) / 10d;
                else
                    xDirection[1][i] = 0;

                //W przód
                if (i >= 20 && i <= 30)
                    xDirection[2][i] = (i - 20) / 10d;
                else if (i > 30 && i <= 40)
                    xDirection[2][i] = 1;
                else
                    xDirection[2][i] = 0;
                // Ustawianie termów sterowanie osią Y

                //Do góry
                if (i >= 0 && i <= 10)
                    yDirection[1][i] = 1;

                else if (i > 10 && i <= 20)
                    yDirection[1][i] =  (20 - i) / 3d;
                else
                    yDirection[1][i] = 0;

                //W dół

                if (i >= 20 && i <= 30)
                    yDirection[2][i] =  (i - 20) / 3d;
                else if (i > 30 && i <= 40)
                    yDirection[2][i] = 1;
                else
                    yDirection[2][i] = 0;
            }

        }
    }

    public void fuzzify(int x, int y) {
        //Rozmycie
        double IN_RX0 = this.x[1][x + 100];
        double IN_RX1 = this.x[2][x + 100];
        double IN_RX2 = this.x[3][x + 100];

        //Blisko NE
        double IN_RY0 = this.y[2][y + 100];
        //Daleko NE
        double IN_RY1 = this.y[1][y + 100];
        //Blisko SE
        double IN_RY2 = this.y[3][y + 100];
        //Daleko SE
        double IN_RY3 = this.y[4][y + 100];

        //Reguły dla przemieszczenia po X
        double Reg_0 = Math.min(IN_RX0, IN_RY0);
        double Reg_1 = Math.min(IN_RX0, IN_RY1);
        double Reg_2 = Math.min(IN_RX0, IN_RY2);
        double Reg_3 = Math.min(IN_RX0, IN_RY3);

        double Reg_4 = Math.min(IN_RX1, IN_RY0);
        double Reg_5 = Math.min(IN_RX1, IN_RY1);
        double Reg_6 = Math.min(IN_RX1, IN_RY2);
        double Reg_7 = Math.min(IN_RX1, IN_RY3);

        double Reg_8 = Math.min(IN_RX2, IN_RY0);
        double Reg_9 = Math.min(IN_RX2, IN_RY1);
        double Reg_10 = Math.min(IN_RX2, IN_RY2);
        double Reg_11 = Math.min(IN_RX2, IN_RY3);

        //Reguły dla przemieszczenia po Y - góra dół dla kolizji
        double RegYS_0 = Math.min(IN_RX1, IN_RY0);
        double RegYS_1 = Math.min(IN_RX1, IN_RY1);

        double RegYN_0 = Math.min(IN_RX1, IN_RY2);
        double RegYN_1 = Math.min(IN_RX1, IN_RY3);


        //
        List<Double> list = List.of(
                Reg_0, Reg_1, Reg_2, Reg_3, Reg_4, Reg_5, Reg_6, Reg_7, Reg_8, Reg_9, Reg_10, Reg_11);

        //Aggregacja X
        double outReg = Collections.max(list);

        //Aggregacja Y
        double outRegYS = Math.max(RegYS_0, RegYS_1);
        double outRegYN = Math.max(RegYN_0, RegYN_1);

        //Obciecie X
        for (int i = 0; i < 41; i++) {
            xDirectionCut[0][i] = xDirection[0][i];
            xDirectionCut[1][i] = Math.min(outReg, xDirection[2][i]);
        }
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

        double out = 0;
        double dzielnik = 0;

        //Metoda srodka ciezkosci

        for (int i = 0; i < 41; i++) {
            out += xDirectionCut[1][i] * xDirectionCut[0][i];
            dzielnik += xDirectionCut[1][i];
            outYV += yDirectionCut[3][i] * yDirectionCut[0][i];
            dzielnikYV += yDirectionCut[3][i];
        }
        outX = (out == 0) ? 0 : (out / dzielnik);
        outY = (outYV == 0) ? 0 : (outYV / dzielnikYV);

    }

}
