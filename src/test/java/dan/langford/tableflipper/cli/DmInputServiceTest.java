package dan.langford.tableflipper.cli;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DmInputServiceTest {

    @Test
    public void test(){

        DmInputService dm = new DmInputService(new Scanner("dan"));

        String response = dm.ask("what is your name");

        assertEquals("dan",response);

        dm = new DmInputService(new Scanner("t\n1"));

        response = dm.promptFor(List.of("one","two","three"));

        assertEquals("three",response);
    }
}
