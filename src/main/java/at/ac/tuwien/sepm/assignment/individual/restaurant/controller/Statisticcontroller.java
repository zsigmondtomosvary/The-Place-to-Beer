package at.ac.tuwien.sepm.assignment.individual.restaurant.controller;

import at.ac.tuwien.sepm.assignment.individual.restaurant.DAO.InvoiceDAO;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;

public class Statisticcontroller {

    @FXML
    private CheckBox cbaf, cbhg, cbag,cbvs, cbhs, cbd, cbmonday, cbtuesday, cbwednesday, cbthursday, cbfriday, cbsaturday, cbsunday;

    @FXML
    private DatePicker dpfrom, dpto;

    @FXML
    private LineChart linechart;

    @FXML
    private BarChart barchart;

    private InvoiceDAO invoiceDAO;
/*
    @FXML
    protected void makeChart()
    {
        //LOG.info("Making chart...");

        linechart.getData().clear();
        barchart.getData().clear();
        linechart.setTitle("Umsatz");

        String [] weekdays = new String[]{ "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        if(dpfrom.getValue() == null || dpfrom.getValue().toString() == "" ||
            dpto.getValue() == null || dpto.getValue().toString() == "") {
            JOptionPane.showMessageDialog(null,"Please enter a Date");
        } else {
            LocalDate from = dpfrom.getValue();
            LocalDate to = dpto.getValue();

            if(from.isAfter(to)) {
                JOptionPane.showMessageDialog(null, "From date has to be before to date.");
            }

            List<Invoice> allinvoices = null;
            try {
                allinvoices = invoiceDAO.displayInvoice();
            } catch (IllegalArgumentException e) {
            }

            List<Products> productsInDate = new ArrayList<>();

            //defining the axes
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Earnings");
            xAxis.setLabel("Date");

            //defining a series
            XYChart.Series series = new XYChart.Series();
            XYChart.Series series_af = new XYChart.Series();
            XYChart.Series series_hg = new XYChart.Series();
            XYChart.Series series_ag = new XYChart.Series();
            XYChart.Series series_vs = new XYChart.Series();
            XYChart.Series series_hs = new XYChart.Series();
            XYChart.Series series_d = new XYChart.Series();


            series.setName("Every");
            series_af.setName("AF");
            series_hg.setName("HG");
            series_ag.setName("AG");
            series_vs.setName("VS");
            series_hs.setName("HS");
            series_d.setName("D");

            DateFormat formatter = new SimpleDateFormat("yyyy/dd/MM");

            List<LineChartData> listseries = new ArrayList<>();
            List<LineChartData> listseries_af = new ArrayList<>();
            List<LineChartData> listseries_hg = new ArrayList<>();
            List<LineChartData> listseries_ag = new ArrayList<>();
            List<LineChartData> listseries_vs = new ArrayList<>();
            List<LineChartData> listseries_hs = new ArrayList<>();
            List<LineChartData> listseries_d = new ArrayList<>();
            for (int i = 0; i < allinvoices.size(); i++) {

                    productsInDate.addAll(invoiceDAO.displayInvoiceProducts(allinvoices.get(i).getId()));

                    boolean added = false;
                    for (int k = 0; k < listseries.size(); k++) {
                        try {
                            Date seriesdate = formatter.parse(formatter.format(listseries.get(k).date));
                            Date vehicledate = formatter.parse(formatter.format((allinvoices.get(i).getFrom().getTime() < (Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant())).getTime() ?
                                Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()) : allinvoices.get(i).getFrom())));
                            if(vehicledate.equals(seriesdate)) {
                                for (int j = 0; j < allinvoices.get(i).getVehicles().size(); j++) {
                                    listseries.get(k).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                        allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                        Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                                    if(allinvoices.get(i).getVehicles().get(j).getLicences().contains(Vehicle.Licence.A)) listseries_a.get(k).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                        allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                        Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                                    );
                                    if(allinvoices.get(i).getVehicles().get(j).getLicences().contains(Vehicle.Licence.B)) listseries_b.get(k).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                        allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                        Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                                    );
                                    if(allinvoices.get(i).getVehicles().get(j).getLicences().contains(Vehicle.Licence.C)) listseries_c.get(k).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                        allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                        Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                                    if(allinvoices.get(i).getVehicles().get(j).getLicences().size() == 0) listseries_no.get(k).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                        allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                        Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                                }
                                added = true;
                            }
                        } catch (ParseException e) {
                        }
                    }
                    if(!added) //falls das datum in den charts noch nicht vorkommt füge es hinzu
                    {
                        Date vehicledate = null;
                        try {
                            vehicledate = formatter.parse(formatter.format(
                                (allinvoices.get(i).getFrom().getTime() < (Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant())).getTime() ?
                                    Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()) : allinvoices.get(i).getFrom())
                            ));
                            listseries.add(new LineChartData(vehicledate, 0));
                            listseries_a.add(new LineChartData(vehicledate, 0));
                            listseries_b.add(new LineChartData(vehicledate, 0));
                            listseries_c.add(new LineChartData(vehicledate, 0));
                            listseries_no.add(new LineChartData(vehicledate, 0));
                            for (int j = 0; j < allinvoices.get(i).getVehicles().size(); j++) {
                                listseries.get(listseries.size()-1).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                    allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                    Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                                );
                                if(allinvoices.get(i).getVehicles().get(j).getLicences().contains(Vehicle.Licence.A)) listseries_a.get(listseries.size()-1).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                    allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                    Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                                );
                                if(allinvoices.get(i).getVehicles().get(j).getLicences().contains(Vehicle.Licence.B)) listseries_b.get(listseries.size()-1).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                    allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                    Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                                );
                                if(allinvoices.get(i).getVehicles().get(j).getLicences().contains(Vehicle.Licence.C)) listseries_c.get(listseries.size()-1).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                    allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                    Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                                );
                                if(allinvoices.get(i).getVehicles().get(j).getLicences().size() == 0) listseries_no.get(listseries.size()-1).addAmount(allinvoices.get(i).getVehicles().get(j).getOverallprice(
                                    allinvoices.get(j).getFrom(), allinvoices.get(j).getTo(),
                                    Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                                );
                            }
                        } catch (ParseException e) {
                            showDialog("Error", e.getMessage());
                        }
                    }
                }
            }

            //addiere vortage auf
            for (int i = 0; i < listseries.size(); i++) {
                for (int j = 0; j < listseries.size(); j++) {
                    if(listseries.get(i).date.after(listseries.get(j).date))
                    {
                        listseries.get(i).amount += listseries.get(j).amount;
                        listseries_af.get(i).amount += listseries_af.get(j).amount;
                        listseries_b.get(i).amount += listseries_b.get(j).amount;
                        listseries_c.get(i).amount += listseries_c.get(j).amount;
                        listseries_no.get(i).amount += listseries_no.get(j).amount;
                    }
                }
            }

            Collections.sort(listseries);
            Collections.sort(listseries_c);
            Collections.sort(listseries_a);
            Collections.sort(listseries_b);
            Collections.sort(listseries_no);

            for (int i = 0; i < listseries.size(); i++) {
                series.getData().add(new XYChart.Data(listseries.get(i).date.toString(), listseries.get(i).amount));
            }
            for (int i = 0; i < listseries_c.size(); i++) {
                series_c.getData().add(new XYChart.Data(listseries_c.get(i).date.toString(), listseries_c.get(i).amount));
            }
            for (int i = 0; i < listseries_a.size(); i++) {
                series_a.getData().add(new XYChart.Data(listseries_a.get(i).date.toString(), listseries_a.get(i).amount));
            }
            for (int i = 0; i < listseries_b.size(); i++) {
                series_b.getData().add(new XYChart.Data(listseries_b.get(i).date.toString(), listseries_b.get(i).amount));
            }
            for (int i = 0; i < listseries_no.size(); i++) {
                series_no.getData().add(new XYChart.Data(listseries_no.get(i).date.toString(), listseries_no.get(i).amount));
            }


            stat_chart_booking.getData().add(series);
            if(stat_cb_a.isSelected()) stat_chart_booking.getData().add(series_a);
            if(stat_cb_b.isSelected()) stat_chart_booking.getData().add(series_b);
            if(stat_cb_c.isSelected()) stat_chart_booking.getData().add(series_c);
            if(stat_cb_no.isSelected()) stat_chart_booking.getData().add(series_no);


            int [] amount = new int[5];
            int [] amounta = new int[5];
            int [] amountb = new int[5];
            int [] amountc = new int[5];
            int [] amountno = new int[5];

            for (int i = 0; i < 5; i++)
            {
                amount[i] = 0;
                amounta[i] = 0;
                amountb[i] = 0;
                amountc[i] = 0;
                amountno[i] = 0;
            }

            Calendar c = Calendar.getInstance();
            for (int i = 0; i < listseries.size(); i++) {
                c.setTime(listseries.get(i).date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek >=2 && dayOfWeek <= 6) amount[dayOfWeek - 2] += listseries.get(i).vehiclecount;
            }

            for (int i = 0; i < listseries_a.size(); i++) {
                c.setTime(listseries_a.get(i).date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek >=2 && dayOfWeek <= 6) amounta[dayOfWeek - 2] += listseries_a.get(i).vehiclecount;
            }

            for (int i = 0; i < listseries_b.size(); i++) {
                c.setTime(listseries_b.get(i).date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek >=2 && dayOfWeek <= 6) amountb[dayOfWeek - 2] += listseries_b.get(i).vehiclecount;
            }

            for (int i = 0; i < listseries_c.size(); i++) {
                c.setTime(listseries_c.get(i).date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek >=2 && dayOfWeek <= 6) amountc[dayOfWeek - 2] += listseries_c.get(i).vehiclecount;
            }

            for (int i = 0; i < listseries_no.size(); i++) {
                c.setTime(listseries_no.get(i).date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek >=2 && dayOfWeek <= 6) amountno[dayOfWeek - 2] += listseries_no.get(i).vehiclecount;
            }

            XYChart.Series series1 = new XYChart.Series(), series2 = new XYChart.Series(), series3 = new XYChart.Series(), series4 = new XYChart.Series(), series5 = new XYChart.Series(), series6 = new XYChart.Series(), series7 = new XYChart.Series();
            series1.setName("Monday");
            series2.setName("Tuesday");
            series3.setName("Wednesday");
            series4.setName("Thursday");
            series5.setName("Friday");
            series6.setName("Saturday");
            series7.setName("Sunday");

            if(cbmonday.isSelected())
            {
                series1.getData().add(new XYChart.Data("Overall", amount[0]));
                if(cbaf.isSelected()) series1.getData().add(new XYChart.Data("AF", amounta[0]));
                if(cbhg.isSelected()) series1.getData().add(new XYChart.Data("HG", amountb[0]));
                if(cbag.isSelected()) series1.getData().add(new XYChart.Data("AG", amountc[0]));
                if(cbvs.isSelected()) series1.getData().add(new XYChart.Data("VS", amountno[0]));
                if(cbhs.isSelected()) series1.getData().add(new XYChart.Data("HS", amountno[0]));
                if(cbd.isSelected()) series1.getData().add(new XYChart.Data("D", amountno[0]));
                barchart.getData().add(series1);
            }
            if(cbtuesday.isSelected())             {
                series2.getData().add(new XYChart.Data("Overall", amount[1]));
                if(cbaf.isSelected()) series1.getData().add(new XYChart.Data("AF", amounta[1]));
                if(cbhg.isSelected()) series1.getData().add(new XYChart.Data("HG", amountb[1]));
                if(cbag.isSelected()) series1.getData().add(new XYChart.Data("AG", amountc[1]));
                if(cbvs.isSelected()) series1.getData().add(new XYChart.Data("VS", amountno[1]));
                if(cbhs.isSelected()) series1.getData().add(new XYChart.Data("HS", amountno[1]));
                if(cbd.isSelected()) series1.getData().add(new XYChart.Data("D", amountno[1]));
                barchart.getData().add(series2);
            }
            if(cbwednesday.isSelected())             {
                series3.getData().add(new XYChart.Data("Overall", amount[2]));
                if(cbaf.isSelected()) series1.getData().add(new XYChart.Data("AF", amounta[2]));
                if(cbhg.isSelected()) series1.getData().add(new XYChart.Data("HG", amountb[2]));
                if(cbag.isSelected()) series1.getData().add(new XYChart.Data("AG", amountc[2]));
                if(cbvs.isSelected()) series1.getData().add(new XYChart.Data("VS", amountno[2]));
                if(cbhs.isSelected()) series1.getData().add(new XYChart.Data("HS", amountno[2]));
                if(cbd.isSelected()) series1.getData().add(new XYChart.Data("D", amountno[2]));
                barchart.getData().add(series3);
            }
            if(cbthursday.isSelected())             {
                series4.getData().add(new XYChart.Data("Overall", amount[3]));
                if(cbaf.isSelected()) series1.getData().add(new XYChart.Data("AF", amounta[3]));
                if(cbhg.isSelected()) series1.getData().add(new XYChart.Data("HG", amountb[3]));
                if(cbag.isSelected()) series1.getData().add(new XYChart.Data("AG", amountc[3]));
                if(cbvs.isSelected()) series1.getData().add(new XYChart.Data("VS", amountno[3]));
                if(cbhs.isSelected()) series1.getData().add(new XYChart.Data("HS", amountno[3]));
                if(cbd.isSelected()) series1.getData().add(new XYChart.Data("D", amountno[3]));
                barchart.getData().add(series4);
            }
            if(cbfriday.isSelected())             {
                series5.getData().add(new XYChart.Data("Overall", amount[4]));
                if(cbaf.isSelected()) series1.getData().add(new XYChart.Data("AF", amounta[4]));
                if(cbhg.isSelected()) series1.getData().add(new XYChart.Data("HG", amountb[4]));
                if(cbag.isSelected()) series1.getData().add(new XYChart.Data("AG", amountc[4]));
                if(cbvs.isSelected()) series1.getData().add(new XYChart.Data("VS", amountno[4]));
                if(cbhs.isSelected()) series1.getData().add(new XYChart.Data("HS", amountno[4]));
                if(cbd.isSelected()) series1.getData().add(new XYChart.Data("D", amountno[4]));
                barchart.getData().add(series5);
            }
            if(cbsaturday.isSelected())             {
                series5.getData().add(new XYChart.Data("Overall", amount[5]));
                if(cbaf.isSelected()) series1.getData().add(new XYChart.Data("AF", amounta[5]));
                if(cbhg.isSelected()) series1.getData().add(new XYChart.Data("HG", amountb[5]));
                if(cbag.isSelected()) series1.getData().add(new XYChart.Data("AG", amountc[5]));
                if(cbvs.isSelected()) series1.getData().add(new XYChart.Data("VS", amountno[5]));
                if(cbhs.isSelected()) series1.getData().add(new XYChart.Data("HS", amountno[5]));
                if(cbd.isSelected()) series1.getData().add(new XYChart.Data("D", amountno[5]));
                barchart.getData().add(series5);
            }
            if(cbsunday.isSelected())             {
                series5.getData().add(new XYChart.Data("Overall", amount[6]));
                if(cbaf.isSelected()) series1.getData().add(new XYChart.Data("AF", amounta[6]));
                if(cbhg.isSelected()) series1.getData().add(new XYChart.Data("HG", amountb[6]));
                if(cbag.isSelected()) series1.getData().add(new XYChart.Data("AG", amountc[6]));
                if(cbvs.isSelected()) series1.getData().add(new XYChart.Data("VS", amountno[6]));
                if(cbhs.isSelected()) series1.getData().add(new XYChart.Data("HS", amountno[6]));
                if(cbd.isSelected()) series1.getData().add(new XYChart.Data("D", amountno[6]));
                barchart.getData().add(series5);
            }
        }
    }

    public class LineChartData implements Comparable<LineChartData>
    {
        public Date date;
        public double amount = 0;
        public int productcount = 0;

        public LineChartData(Date date, double amount) {
            this.date = date;
            this.amount = amount;
        }

        @Override
        public int compareTo(LineChartData o) {
            return date.compareTo(o.date);
        }

        public void addAmount(double amount) {
            this.amount += amount;
            productcount += 1;
        }
    }*/
}
