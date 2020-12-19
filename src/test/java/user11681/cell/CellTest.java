package user11681.cell;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class CellTest {
    static final Logger LOGGER = LogManager.getLogger("cell");
    static final ObjectList<String> results = new ObjectArrayList<>();
    static final int iterations = 100000;
    static final int tests = 100000;
    static Class<?> lambdaClass;

    final int finalField = 6;

    int testField;

    @Test
    void generateLambdas() {
        for (int i = 0; i < iterations; i++) {
            final Class<? extends Runnable> klass = ((Runnable) () -> {}).getClass();

            if (lambdaClass == null) {
                lambdaClass = klass;
            }

            assert lambdaClass == klass;

            System.out.println(true);
        }
    }

    static void test(final Object format, final Runnable test) {
        final long time = System.nanoTime();

        for (int i = 0; i < tests; i++) {
            test.run();
        }

        addResult(format, (System.nanoTime() - time) / tests);
    }

    static void addResult(final Object format, final Object... arguments) {
        results.add(String.format(format.toString(), arguments));
    }

    @AfterAll
    static void logResults() {
        for (final String result : results) {
            LOGGER.warn(result);
        }
    }

    @Test
    void accessField() {
        test("field: %s", () -> {
            for (int i = 0; i < iterations; i++) {
                this.testField += this.finalField;
            }
        });
    }

    @Test
    void accessVariable() {
        test("variable: %s", () -> {
            final int finalVariable = this.finalField;

            for (int i = 0; i < iterations; i++) {
                this.testField += finalVariable;
            }
        });
    }

    //    @Test
    void assign() {
        final long time = System.nanoTime();

        for (int i = 0; i < iterations; i++) {
            final int j = i;

            testField += j;
        }

        addResult("assign: %s", System.nanoTime() - time);
    }

    //    @Test
    void reassign() {
        final long time = System.nanoTime();
        int j;

        for (int i = 0; i < iterations; i++) {
            j = i;

            testField += j;
        }

        addResult("reassign: %s", System.nanoTime() - time);
    }
}