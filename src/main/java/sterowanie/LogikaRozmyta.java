package sterowanie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LogikaRozmyta {
    double[][] xAxisFuzzy = new double [4][201];
    double[][] yAxisFuzzy = new double [5][201];
    double[][] xAxisDirection = new double [3][41];
    double[][] xAxisDirectionCut = new double [2][41];
    double[][] yAxisDirection = new double [3][41];
    double[][] yAxisDirectionCut = new double [4][41];
    double outY=0, outX=0;
    public LogikaRozmyta()
    {
        fillLogic();
    }
    //Wypełnianie termów
    private void fillLogic()
    {
        for(int i=0;i<201;i++)
        {
            xAxisFuzzy[0][i] = i-100;
            yAxisFuzzy[0][i] = i-100;
            if(i<41)
            {
            xAxisDirection[0][i] = i-20;
            yAxisDirection[0][i] = i-20;
            }
        }
        for(int i=0;i<201;i++)
        {
            // Ustawianie termów RX
            
            //Z tyłu
            if(i>= 0 && i<90)
                xAxisFuzzy[1][i] = 1;
            else if(i>=90 && i<=100)
                xAxisFuzzy[1][i] = ((double)100-i)/10;
            else
                xAxisFuzzy[1][i] = 0;
            
            //Kolizja
            if(i>= 100 && i<=190)
                xAxisFuzzy[2][i] = 1;
            else if(i>190 && i<=200)
                xAxisFuzzy[2][i] = ((double)200-i)/10;
            else
                xAxisFuzzy[2][i] = 0;
            
            //Z przodu
            if(i>= 190 && i<=200)
                xAxisFuzzy[3][i] = ((double)i-190)/10;
            else
                xAxisFuzzy[3][i] = 0;
            
            // Ustawianie termów RY
            
            //Daleko NE
            if(i>= 0 && i<=40)
                yAxisFuzzy[1][i] = 1;
            else if(i>40 && i<= 50)
                yAxisFuzzy[1][i] = ((double)50-i)/10;
            else
                yAxisFuzzy[1][i] = 0;
            
            //Blisko NE
            if(i>= 40 && i<=50)
                yAxisFuzzy[2][i] = ((double)i-40)/10;
            else if(i>50 && i<=90)
                yAxisFuzzy[2][i] = 1;
            else if(i>90 && i<= 100)
                yAxisFuzzy[2][i] = ((double)100-i)/10;
            else
                yAxisFuzzy[2][i] = 0;
            
            //Blisko SE
            if(i>= 100 && i<=110)
                yAxisFuzzy[3][i] = ((double)i-100)/10;
            else if(i>110 && i<=150)
                yAxisFuzzy[3][i] = 1;
            else if(i>150 && i<= 160)
                yAxisFuzzy[3][i] = ((double)160-i)/10;
            else
                yAxisFuzzy[3][i] = 0;
            
            //Daleko SE
            if(i>= 150 && i<=160)
                yAxisFuzzy[4][i] = ((double)i-150)/10;
            else if(i>160 && i<=200)
                yAxisFuzzy[4][i] = 1;
            else
                yAxisFuzzy[4][i] = 0;
            
            if(i<41)
            {
                // Ustawianie termów sterowanie osią X
                
                //W tył
                if(i>= 0 && i<=10)
                    xAxisDirection[1][i] = 1;
                else if(i>10 && i<=20)
                    xAxisDirection[1][i] = ((double)20-i)/10;
                else
                    xAxisDirection[1][i] = 0;
                
                //W przód
                if(i>= 20 && i<=30)
                    xAxisDirection[2][i] = ((double)i-20)/10;
                else if(i>30 && i<=40)
                    xAxisDirection[2][i] = 1;
                else
                    xAxisDirection[2][i] = 0;
                // Ustawianie termów sterowanie osią Y
                
                //Do góry
                if(i>= 0 && i<=17)
                    yAxisDirection[1][i] = 1;
                else if(i>17 && i<=20)
                    yAxisDirection[1][i] = ((double)20-i)/3;
                else
                    yAxisDirection[1][i] = 0;
                
                //W dół
                if(i>= 20 && i<=23)
                    yAxisDirection[2][i] = ((double)i-20)/3;
                else if(i>23 && i<=40)
                    yAxisDirection[2][i] = 1;
                else
                    yAxisDirection[2][i] = 0;
            }
            
        }
    }
    public void Fuzzify(int x, int y)
    {
        //Rozmycie
        double Reg_RX_0 = xAxisFuzzy[1][x+100];
        double Reg_RX_1 = xAxisFuzzy[2][x+100];
        double Reg_RX_2 = xAxisFuzzy[3][x+100];
        
        //Blisko NE
        double Reg_RY_0 = yAxisFuzzy[2][y+100];
        //Daleko NE
        double Reg_RY_1 = yAxisFuzzy[1][y+100];
        //Blisko SE
        double Reg_RY_2 = yAxisFuzzy[3][y+100];
        //Daleko SE
        double Reg_RY_3 = yAxisFuzzy[4][y+100];
        
        //Reguły dla przemieszczenia po X
        double Reg_0 = Math.min(Reg_RX_0, Reg_RY_0);
        double Reg_1 = Math.min(Reg_RX_0, Reg_RY_1);
        double Reg_2 = Math.min(Reg_RX_0, Reg_RY_2);
        double Reg_3 = Math.min(Reg_RX_0, Reg_RY_3);
        
        double Reg_4 = Math.min(Reg_RX_1, Reg_RY_0);
        double Reg_5 = Math.min(Reg_RX_1, Reg_RY_1);
        double Reg_6 = Math.min(Reg_RX_1, Reg_RY_2);
        double Reg_7 = Math.min(Reg_RX_1, Reg_RY_3);
        
        double Reg_8 = Math.min(Reg_RX_2, Reg_RY_0);
        double Reg_9 = Math.min(Reg_RX_2, Reg_RY_1);
        double Reg_10 = Math.min(Reg_RX_2, Reg_RY_2);
        double Reg_11 = Math.min(Reg_RX_2, Reg_RY_3);
        
        //Reguły dla przemieszczenia po Y
        double RegYS_0 = Math.min(Reg_RX_1, Reg_RY_0);
        double RegYS_1 = Math.min(Reg_RX_1, Reg_RY_1);
        
        double RegYN_0 = Math.min(Reg_RX_1, Reg_RY_2);
        double RegYN_1 = Math.min(Reg_RX_1, Reg_RY_3);
        
        
        //
        List<Double> list = new ArrayList<>();
        list.add(Reg_0);
        list.add(Reg_1);
        list.add(Reg_2);
        list.add(Reg_3);
        list.add(Reg_4);
        list.add(Reg_5);
        list.add(Reg_6);
        list.add(Reg_7);
        list.add(Reg_8);
        list.add(Reg_9);
        list.add(Reg_10);
        list.add(Reg_11);
        //Aggregacja X
        double outReg = Collections.max(list);
        
        //Aggregacja Y
        double outRegYS = Math.max(RegYS_0,RegYS_1);
       double outRegYN = Math.max(RegYN_0,RegYN_1);
        //double outRegYS = RegYS_0;
       //double outRegYN = RegYN_0;
        //Obciecie X
        for(int i=0;i<41;i++)
        {
            xAxisDirectionCut[0][i] = xAxisDirection[0][i];
            xAxisDirectionCut[1][i] = Math.min(outReg,xAxisDirection[2][i]);
        }
        //Obciecie Y
        for(int i=0;i<41;i++)
        {        
            yAxisDirectionCut[0][i] = yAxisDirection[0][i];
            //Obciecie poszczegolnych zbiorow
            yAxisDirectionCut[1][i] = Math.min(outRegYS,yAxisDirection[1][i]);
            yAxisDirectionCut[2][i] = Math.min(outRegYN,yAxisDirection[2][i]);
            //Aggregacja
            yAxisDirectionCut[3][i] = Math.max(yAxisDirectionCut[1][i],yAxisDirectionCut[2][i]);
        }
        double outYV=0;
        double dzielnikYV=0;
        
        double out=0;
        double dzielnik=0;
        //Metoda srodka ciezkosci
        for(int i=0;i<41;i++)
        {
            out+= xAxisDirectionCut[1][i]*xAxisDirectionCut[0][i];
            dzielnik += xAxisDirectionCut[1][i];
            outYV += yAxisDirectionCut[3][i]*yAxisDirectionCut[0][i];
            dzielnikYV+= yAxisDirectionCut[3][i];
        }
        outX = (out==0) ? 0 : (out/dzielnik);
        outY = (outYV==0) ? 0 : (outYV/dzielnikYV);
        
    }
    public double GetDirectionX()
    {
        return outX;
    }
    public double GetDirectionY()
    {
        return outY;
    }
    
}
