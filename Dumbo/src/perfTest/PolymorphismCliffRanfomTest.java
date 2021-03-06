package perfTest;

import java.lang.reflect.Constructor;
import java.util.Random;

public class PolymorphismCliffRanfomTest {
  private static final int UPTO = 100 * 1000 * 1000;

  public static void main(String[] args) throws Exception {
    int offset = Integer.parseInt(args[0]);

    Test[] tests = generateTestData(offset);

    printDescriptions(tests);

    System.out.println("Warmup");
    testAll(tests);

    System.out.println("Actual run");
    printHeader(tests);

    testAll(tests);
  }

  private static void testAll(Test[] tests) {
    for (int j = 0; j < 10; j++) {
      runTests(tests);
    }
  }

  private static void printDescriptions(Test[] tests) {
    System.out.println(tests[0].getClass().getSimpleName() +
        ": " + tests[0].description());
    System.out.println();
  }

  public static void runTests(Test[] tests) {
    long time = System.currentTimeMillis();
    test(tests);
    time = System.currentTimeMillis() - time;
    System.out.print(time + "\t");
    System.out.flush();
    System.out.println();
  }

  public static void test(Test[] sources) {
    Test t0 = makeRandomTest(sources);
    Test t1 = makeRandomTest(sources);
    Test t2 = makeRandomTest(sources);
    Test t3 = makeRandomTest(sources);
    Test t4 = makeRandomTest(sources);
    Test t5 = makeRandomTest(sources);
    Test t6 = makeRandomTest(sources);
    Test t7 = makeRandomTest(sources);
    Test t8 = makeRandomTest(sources);
    Test t9 = makeRandomTest(sources);
    for (int i = 0; i < UPTO / 10; i++) {
      t0.run();
      t1.run();
      t2.run();
      t3.run();
      t4.run();
      t5.run();
      t6.run();
      t7.run();
      t8.run();
      t9.run();
    }
  }

  private static Test makeRandomTest(Test[] sources) {
    return sources[((int) (Math.random() * sources.length))];
  }

  private static void printHeader(Test[] tests) {
    System.out.print(tests[0].getClass().getSimpleName());
    System.out.print('\t');
    System.out.println();
  }

  private static Test[] generateTestData(int offset)
      throws Exception {
    switch (offset) {
      default:
        throw new IllegalArgumentException("offset:" + offset);
      case 0:
        return fillSources(A1.class, C1.class, C1.class);
      case 1:
        return fillSources(A2.class, B2.class, C2.class);
      case 2:
        return fillSources(
            A3.class, B3.class, C3.class, D3.class);
      case 3:
        return fillSources(
            A4.class, B4.class, C4.class, D4.class, E4.class);
      case 4:
        return fillSources(
            A5.class, B5.class, C5.class, D5.class, E5.class,
            F5.class);
      case 5:
        return fillSources(
            A6.class, B6.class, C6.class, D6.class, E6.class,
            F6.class, G6.class);
    }
  }

  private static Test[] fillSources(
      Class<? extends Test> aClass,
      Class<?> bClass, Class<?>... bClasses)
      throws Exception {
    Test[] sources = new Test[1000];
    Random rand = new Random(0);
    Constructor<? extends Test> constr =
        aClass.getConstructor(bClass);
    for (int i = 0; i < sources.length; i++) {
      int offset = Math.abs(rand.nextInt() % bClasses.length);
      Object b = bClasses[offset].newInstance();
      sources[i] = constr.newInstance(b);
    }
    return sources;
  }
}
