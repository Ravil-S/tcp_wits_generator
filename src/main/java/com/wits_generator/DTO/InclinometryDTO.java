package com.wits_generator.DTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InclinometryDTO {

    private String inclinometrySID;// "0701PLOSCHAD\n" + //Идентификатор скважины
    private int inclinometrySID2;//"07020\n" + //Боковой ствол
    private int inclinometryMID;// "07032\n" + //Идентификатор записи
    private int inclinometryPID;//"07040\n" + //Идентификатор последовательности записей

    private String inclinometryDate;//"07050\n" + //Дата
    private String inclinometryTime;//"07060\n" + //Время

    private double inclinometryDepth=0;//"07083408" Глубина по показанию датчика – измеренная
    private double inclinometryNS=0;// "07180" Положение по оси Север-Юг (m)
    private double inclinometryWE=0;//"07190" Положение по оси Восток-Запад (m)

    public InclinometryDTO(String SID, int SID2, int MID, int PID){
        SimpleDateFormat formaterD = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat formaterT = new SimpleDateFormat("HHmmss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        inclinometryDate= "0705"+formaterD.format(date);
        inclinometryTime= "0706"+formaterT.format(date);

        inclinometrySID=SID;
        inclinometrySID2=SID2;
        inclinometryMID = MID;
        inclinometryPID= PID;
    };

    @Override
    public String toString() {
        return  "&&\n"+
                "0701"+inclinometrySID + '\n' +
                "0702"+inclinometrySID2 + '\n' +
                "0703"+inclinometryMID + '\n' +
                "0704"+inclinometryPID + '\n' +
                inclinometryDate+ '\n' +
                inclinometryTime + '\n' +
                "0708"+inclinometryDepth + '\n' +
                "0718"+inclinometryNS+ '\n' +
                "0719"+inclinometryWE+ '\n' +
                "!!";
    }

    public void setInclinometryDepth(double inclinometryDepth) {
        this.inclinometryDepth = inclinometryDepth;
    }

    public void setInclinometryNS(double inclinometryNS) {
        this.inclinometryNS = inclinometryNS;
    }

    public void setInclinometryWE(double inclinometryWE) {
        this.inclinometryWE = inclinometryWE;
    }
}
