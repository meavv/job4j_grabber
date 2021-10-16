package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {

    public static Connection con() throws Exception {
        Properties properties = readPropertiesTime();
        Class.forName(properties.get("driver").toString());
        return DriverManager.getConnection(properties.get("url").toString(),
                properties.get("login").toString(), properties.get("password").toString());
    }

    public static void main(String[] args) throws ClassNotFoundException {
            Properties properties = readPropertiesTime();
            try (Connection connection = con()) {
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                JobDataMap data = new JobDataMap();
                data.put("con", connection);
                JobDetail job = newJob(Rabbit.class).usingJobData(data).build();
                SimpleScheduleBuilder times = simpleSchedule()
                        .withIntervalInSeconds(Integer.parseInt(properties
                                .get("rabbit.interval").toString()))
                        .repeatForever();
                Trigger trigger = newTrigger()
                        .startNow()
                        .withSchedule(times)
                        .build();
                scheduler.scheduleJob(job, trigger);
                Thread.sleep(10000);
                scheduler.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try (Connection connection = con();
                    PreparedStatement statement =
                            connection.prepareStatement("insert into rabbit(created_date) values (?)")) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                statement.setTimestamp(1, timestamp);
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Properties readPropertiesTime() {
        Properties properties = new Properties();
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/rabbit.properties"))) {
            properties.load(bf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

}