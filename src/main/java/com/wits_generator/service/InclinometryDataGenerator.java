package com.wits_generator.service;

import com.wits_generator.DTO.InclinometryDTO;

public class InclinometryDataGenerator {

    private double inclinometryDepth=0;//"07083408" Глубина по показанию датчика – измеренная
   private double deptDelta=10;

    private double inclinometryNS=0;// "07180" Положение по оси Север-Юг (m)
    private double inclinometryWE=0;//"07190" Положение по оси Восток-Запад (m)

    private String inclinometrySID="PLOSCHAD";// "0701PLOSCHAD\n" + //Идентификатор скважины
    private int inclinometrySID2=0;//"07020\n" + //Боковой ствол
    private int inclinometryMID=0;// "07032\n" + //Идентификатор записи
    private int inclinometryPID=0;//"07040\n" + //Идентификатор последовательности записей

    public InclinometryDataGenerator() {    }

    public String getNewInclinometryData() {
        InclinometryDTO inclinometryDTO = new InclinometryDTO(inclinometrySID, inclinometrySID2,
                inclinometryMID, inclinometryPID);

        double k = 0.1;
        double count = 10/0.1;
        inclinometryNS= inclinometryNS+(Math.random()-0.5) *k;
        inclinometryWE= inclinometryWE+(Math.random()-0.5) *k;

        inclinometryDTO.setInclinometryWE(Math.round(inclinometryWE*count)/count);
        inclinometryDTO.setInclinometryNS(Math.round(inclinometryNS*count)/count);
        inclinometryDTO.setInclinometryDepth(Math.round(inclinometryDepth*count)/count);
        inclinometryDepth=inclinometryDepth+deptDelta+((Math.random()-0.5) *0.2*deptDelta);
        inclinometryMID++;

        if (inclinometryDepth>6000) {
            inclinometryDepth = 0;//"07083408" Глубина по показанию датчика – измеренная
            inclinometryNS = 0;// "07180" Положение по оси Север-Юг (m)
            inclinometryWE = 0;//"07190" Положение по оси Восток-Запад (m)

            inclinometryMID=0;// "07032\n" + //Идентификатор записи
            inclinometryPID=0;//"07040\n" + //Идентификатор последовательности записей
        }

        return inclinometryDTO.toString();
    }
}
