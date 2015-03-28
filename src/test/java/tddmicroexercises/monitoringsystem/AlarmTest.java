package tddmicroexercises.monitoringsystem;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import tddmicroexercises.monitoringsystem.Alarm;
import tddmicroexercises.monitoringsystem.Sensor;
import static org.mockito.Mockito.*;

public class AlarmTest {
    private static final double LOW_THRESHOLD = 17;
    private static final double HIGH_THRESHOLD = 21;

    @Test
    public void alarm_is_off_when_detected_value_in_safety_range() {
        Alarm alarm = new Alarm(sensorThatDetects(18.0), new SafetyRange(LOW_THRESHOLD,
                HIGH_THRESHOLD));

        alarm.check();

        assertThat(alarm.isAlarmOn(), is(false));
    }

    @Test
    public void alarm_is_on_when_detected_value_below_safety_range() {
        Alarm alarm = new Alarm(sensorThatDetects(16.0), new SafetyRange(LOW_THRESHOLD,
                HIGH_THRESHOLD));

        alarm.check();

        assertThat(alarm.isAlarmOn(), is(true));
    }

    @Test
    public void alarm_is_on_when_detected_value_over_safety_range() {
        Alarm alarm = new Alarm(sensorThatDetects(22.0), new SafetyRange(LOW_THRESHOLD,
                HIGH_THRESHOLD));

        alarm.check();

        assertThat(alarm.isAlarmOn(), is(true));
    }

    @Test
    public void alarm_should_ask_the_sensor_to_probe() {
        Sensor sensor = mock(Sensor.class);
        Alarm alarm = new Alarm(sensor, new SafetyRange(LOW_THRESHOLD, HIGH_THRESHOLD));

        alarm.check();

        verify(sensor).probe();
    }

    private Sensor sensorThatDetects(double samplePressure) {
        Sensor sensor = mock(Sensor.class);
        doReturn(samplePressure).when(sensor).probe();
        return sensor;
    }

}
