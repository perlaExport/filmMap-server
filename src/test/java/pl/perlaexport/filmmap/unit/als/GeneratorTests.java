package pl.perlaexport.filmmap.unit.als;

import org.junit.jupiter.api.Test;
import pl.perlaexport.filmmap.als.Generator;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorTests {

    @Test
    public void generateRandomMatrixTest() {
        double[][] actual = Generator.generateRandomMatrix(10,10);

        assertNotNull(actual);
    }

    @Test
    public void generateRandomVectorTest() {
        int[] actual = Generator.generateRandomVector(10);

        assertNotNull(actual);
    }
}
