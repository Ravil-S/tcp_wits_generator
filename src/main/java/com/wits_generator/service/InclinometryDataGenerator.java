package com.wits_generator.service;

import com.wits_generator.DTO.InclinometryDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InclinometryDataGenerator {

    private  boolean isActive = true;

    private double inclinometryDepth=0;//"07083408" Глубина по показанию датчика – измеренная
    private double deptDelta=10; //длина участка обновления цикла

    private double inclinometryNS=0;// "07180" Положение по оси Север-Юг (m)
    private double inclinometryWE=0;//"07190" Положение по оси Восток-Запад (m)

    private String inclinometrySID="PLOSCHAD";// "0701PLOSCHAD\n" + //Идентификатор скважины
    private int inclinometrySID2=0;//"07020\n" + //Боковой ствол
    private int inclinometryMID=0;// "07032\n" + //Идентификатор записи
    private int inclinometryPID=0;//"07040\n" + //Идентификатор последовательности записей

    private Date statusLastTime = new Date();
    private Date deptLastTime = new Date();
    private int status=1;  //код деятельности
    private double speedStatusUpdate=3000; //время смены код деятельности мс
    private double speedDeptIncrease=0.1; //Cкорость увеличения глубины м/с

    public InclinometryDataGenerator() {    }

    public String getNewInclinometryData() {
        InclinometryDTO inclinometryDTO = new InclinometryDTO(inclinometrySID, inclinometrySID2,
                inclinometryMID, inclinometryPID, status);

        if (isActive== false) {return inclinometryDTO.toString();}

        double k = 0.1;
        double count = 10/0.1;
      //  inclinometryNS= inclinometryNS+(Math.random()-0.5) *k;
      //  inclinometryWE= inclinometryWE+(Math.random()-0.5) *k;

        inclinometryDTO.setInclinometryWE(Math.round(inclinometryWE*count)/count);
        inclinometryDTO.setInclinometryNS(Math.round(inclinometryNS*count)/count);
        inclinometryDTO.setInclinometryDepth(Math.round(inclinometryDepth*count)/count);
       // inclinometryDepth=inclinometryDepth+deptDelta+((Math.random()-0.5) *0.2*deptDelta);
        inclinometryMID++;

        return inclinometryDTO.toString();
    }

    public void stop() {isActive= false;}
    public void start() {isActive= true;}

    @Scheduled(initialDelay = 1000, fixedRate = 100)
    public void workCycle(){
        Date currentTime = new Date();
        if (isActive== true ) {
            if (currentTime.getTime() > (statusLastTime.getTime() + speedStatusUpdate)) {
                statusLastTime = new Date();
                if (status == 1) {
                    status = 100;
                } else if (status == 105) {
                    status = 1;
                } else if (status == 14) {
                    status = 0;
                    newDirection();
                }
                status++;
                System.out.println("status " + status);
            }
            increaseDept();
        }
    }

    public void increaseDept(){
        Date currentTime = new Date();

        if (status==2 || status==4 || status==7 || status==9) {
            long time=currentTime.getTime() - deptLastTime.getTime();
            double deltaDepth = speedDeptIncrease*(time/1000);
            inclinometryDepth = inclinometryDepth+deltaDepth+((Math.random()-0.5) *0.2*deltaDepth);
            deptLastTime = new Date();
            if (inclinometryDepth>6000) {restart();}
        }
    }

    public void newDirection(){
        double k = 0.1;
        double count = 10/0.1;
        inclinometryNS= inclinometryNS+(Math.random()-0.5) *k;
        inclinometryWE= inclinometryWE+(Math.random()-0.5) *k;
    }

   public void restart(){
           inclinometryDepth = 0;//"07083408" Глубина по показанию датчика – измеренная
           inclinometryNS = 0;// "07180" Положение по оси Север-Юг (m)
           inclinometryWE = 0;//"07190" Положение по оси Восток-Запад (m)
           inclinometryMID=0;// "07032\n" + //Идентификатор записи
           inclinometryPID++;//"07040\n" + //Идентификатор последовательности записей
            statusLastTime = new Date();
            deptLastTime = new Date();
            status=1;  //код деятельности
       }
}

    /*
1. Накручивание свечи на силовой верхний привод. СВЧ свинчивается со свечой и БИ.
101 Подача свечи на ось скважины.
102 Перемещение свечи от оси скважины в подсвечник/укладка в соответствующую гребенку на платформе.
103 Захват свечи в автоматическом элеваторе.
104 Смазка и очистка резьбы перед свинчиванием.
105 Прием и сопровождение одиночной бурильной или обсадной трубы на ось скважины.
2. Выход на режим. Для подачи бурового раствора запускаются насосы, с клиньев снимается БИ, после чего начинается вращение буровых
3. Подход к забою. по росту давления, что позволяет определить, что буровая колонна достигла забоя.
4.Бурение с вращением (роторным способом) с контролем таких параметров, как нагрузка на забой, перепад давления, крутящий момент и скорость проходки.
5. Отход от забоя на 1-2 метра. После достижения забоя происходит остановка вращения БИ.
6. Подъем-спуск БИ на расстояние 7-15 метров без вращения.
7. Направленное бурение без вращения буровой колонны и подход к забою.
Вращается только СВП (режим осцилляцииИ).
8. Отход от забоя на 1-2 метра
9. Выход на режим с вращением СВП и продолжение бурения с вращением.
10. Выработка нагрузки — регулировании
11. Проработка – движение вверх/вниз с вращением и циркуляцией бурового раствора.
12. Расхаживание (при необходимости) для удаления накопленных отходов с забоя.
13. Становление в клинья.
14. Остановка подачи бурового раствора, завершение бурения.

     */


/*
    final String WITS_TEXT ="&&\n" + //ЗАПИСЬ № 1: ОБЩЕЕ: ЗАПИСЬ ДАННЫХ ПО ВРЕМЕНИ
            "0101PLOSCHAD\n" + //Идентификатор скважины
            "01020\n" + //Боковой ствол
            "01031\n" + //Идентификатор записи
            "01040\n" + //Идентификатор последовательности записей
            "0105070624\n" + //Дата
            "0106122412\n" + //Время
            "01074\n" + //Код проводимой работы
            "01103407.24\n" + //Глубина скважины, измер.
            "011231.86\n" + //Положение талевого блока
            "01130\n" + //Глубина отставания
            "011412.95\n" + //	Вес на крюке, сред.
            "01160.05\n" + //Нагрузка на долото, сред.
            "01393406.87\n" + //Глубина отставания
            "!!\n" +
            "&&\n" + //ЗАПИСЬ № 2: ЗАПИСЬ ДАННЫХ ПО ГЛУБИНЕ
            "0201PLOSCHAD\n" + //Идентификатор скважины
            "02020\n" + //Боковой ствол
            "02032\n" + //Идентификатор записи
            "02040\n" + //Идентификатор последовательности записей
            "0205070624\n" + //Дата
            "0206122412\n" + //Время
            "02074\n" + //Код проводимой работы
            "02083522\n" + //Глубина забоя, общая
            "02102.12\n" + //Скорость проходки, сред.
            "02110.07\n" + //Нагрузка на долото, сред.
            "021252.93\n" + //Вес на крюке, сред.
            "021338.62\n" + //Давление на входе
            "02140.58\n" + //Момент на роторе на поверхности, сред.
            "02150\n" + //Обороты долота, средние на поверхности
            "021738.62\n" + //Вес бурового раствора на входе, сред.
            "021916.28\n" + //Расход бурового раствора на входе, сред.
            "022112.83\n" + //Расход бурового раствора на выходе, сред., в процентах
            "022251.28\n" + //Объем активных емкостей
            "02270\n" + //Экспонента бурения
            "!!\n" + //
            "&&\n" + //Запись № 7: Запись данных инклинометрии
            "0701PLOSCHAD\n" + //Идентификатор скважины
            "07020\n" + //Боковой ствол
            "07032\n" + //Идентификатор записи
            "07040\n" + //Идентификатор последовательности записей
            "0705070624\n" + //Дата
            "0706122412\n" + //Время
            "07074\n" + //Код проводимой работы
            "07083408\n" + //	Глубина по показанию датчика – измеренная
            "07093408\n" + //	Глубина по показанию датчика – вертикальная
            "07100\n" + //	Номер замеряемого интервала скважины
            "07113408\n" + //	Глубина скважины – измеренная
            "07121\n" + //Типы инклинометрических измерений
            "07130\n" + //  Вертикальное отклонение (град)
            "07140\n" + // Горизонтальное смещение ствола – некорректированное (град)
            "07150\n" + // Горизонтальное смещение ствола – корректированное (град)
            "07160\n" + // Данные магнитных измерений (град)
            "07170\n" + // Данные гравитационных измерений (град)
            "07180\n" + // Положение по оси Север-Юг (m)
            "07190\n" + // Положение по оси Восток-Запад (m)
            "07200\n" + // Угол кривизны ствола скважины (
            "07210\n" + // Тенденция смещения бурового инструмента
            "!!";
*////

