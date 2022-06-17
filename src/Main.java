import java.util.Random;
import java.util.stream.IntStream;

import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;

import static org.distance.distance__m256d_h.C_DOUBLE;
import static org.distance.distance__m256d_h.aligned_alloc;
import static org.distance.distance__m256d_h.cosine;
import static org.distance.distance__m256d_h.euclidean;
import static org.distance.distance__m256d_h.manhattan;

public class Main {

    private static final long KILOBYTE = 1024;
    private static final long MEBIBYTE = KILOBYTE * 1024;
    private static final long L3_CACHE = 64 * MEBIBYTE;

    public static void main(String[] args) {

        final int iterations = Integer.parseInt(args[0]);

        final long alignment = C_DOUBLE.bitSize();
        final int elements = (int) ((int) L3_CACHE / alignment);

        final int arbitrarySeed = 3;
        final Random random = new Random(arbitrarySeed);

        try (final ResourceScope ignored = ResourceScope.newConfinedScope()) {

            final long sizeOfArray = alignment * elements;
            final MemoryAddress xArray = aligned_alloc(alignment, sizeOfArray);
            final MemoryAddress x1Array = aligned_alloc(alignment, sizeOfArray);

            for (int i = 0; i < elements; i++) {
                xArray.setAtIndex(C_DOUBLE, i, random.nextDouble());
                x1Array.setAtIndex(C_DOUBLE, i, random.nextDouble());
            }

            IntStream.rangeClosed(0, iterations)
                     .mapToDouble(i -> euclidean(xArray, x1Array, elements))
                     .average()
                     .ifPresent(result -> System.out.printf("METHOD:   EUCLIDEAN [ %f ]%n", result));

            IntStream.rangeClosed(0, iterations)
                     .mapToDouble(i -> cosine(xArray, x1Array, elements))
                     .average()
                     .ifPresent(result -> System.out.printf("METHOD:   COSINE [ %f ]%n", result));

            IntStream.rangeClosed(0, iterations)
                     .mapToDouble(i -> manhattan(xArray, x1Array, elements))
                     .average()
                     .ifPresent(result -> System.out.printf("METHOD:   MANHATTAN [ %f ]%n", result));

        }
    }
}