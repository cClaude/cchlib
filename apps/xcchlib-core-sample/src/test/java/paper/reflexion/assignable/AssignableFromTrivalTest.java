package paper.reflexion.assignable;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;


public class AssignableFromTrivalTest
{
    @Test
    public void test_isAssignableFrom_trivial_1()
    {
        final Class<?> type   = Bird.class;
        final boolean  actual = type.isAssignableFrom( Bird.class );

        assertThat( actual ).isTrue();

        // Typically this :
        @SuppressWarnings("unused")
        final
        Bird aBird = new Bird();
   }

    @Test
    public void test_isAssignableFrom_trivial_2()
    {
        // MyInterface object = new MyClass()
        final Class<?> type   = MyCar.class;
        final boolean  actual = Vehicle.class.isAssignableFrom( type );

        assertThat( actual ).isTrue();

        // Typically this :
        @SuppressWarnings("unused")
        final
        Vehicle aVehicle = new MyCar();
    }

    @Test
    public void test_isAssignableFrom_trivial_3()
    {
        final Class<?> type   = MyCar.class;
        final boolean  actual = Bird.class.isAssignableFrom( type );

        assertThat( actual ).isFalse();
        // Typically this :
        // Bird aBirdOrACar = new MyCar(); // Compilation error here
    }
}
