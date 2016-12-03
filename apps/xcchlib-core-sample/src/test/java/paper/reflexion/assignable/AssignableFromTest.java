package paper.reflexion.assignable;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class AssignableFromTest
{
    private final Vehicle vehicle = new MyCar();
    private final Car     car     = new Car();
    private final MyCar   myCar   = new MyCar();

    private final Class<? extends Vehicle> vehicleType = this.vehicle.getClass();
    private final Class<? extends Car>     carType     = this.car.getClass();
    private final Class<? extends MyCar>   myCarType   = this.myCar.getClass();

    @Test
    public void test_isAssignableFrom_Case1()
    {
        // Case 1 : otherVehicle is a Vehicle interface
        Vehicle otherVehicle;
        otherVehicle = this.vehicle;
        otherVehicle = this.car;
        otherVehicle = this.myCar;

        // Using type directly on left side
        assertThat( this.vehicleType.isAssignableFrom( Vehicle.class ) ).isFalse(); // expected true
        assertThat( this.vehicleType.isAssignableFrom( Car.class     ) ).isFalse(); // expected true
        assertThat( this.vehicleType.isAssignableFrom( MyCar.class   ) ).isTrue();

        // Using type directly on right side
        assertThat( Vehicle.class.isAssignableFrom( this.vehicle.getClass() ) ).isTrue();
        assertThat( Vehicle.class.isAssignableFrom( this.car    .getClass() ) ).isTrue();
        assertThat( Vehicle.class.isAssignableFrom( this.myCar  .getClass() ) ).isTrue();

        // Using dynamic computed types
        assertThat( otherVehicle.getClass().isAssignableFrom( this.vehicle.getClass() ) ).isTrue();
        assertThat( otherVehicle.getClass().isAssignableFrom( this.car    .getClass() ) ).isFalse(); // expected true
        assertThat( otherVehicle.getClass().isAssignableFrom( this.myCar  .getClass() ) ).isTrue();

        // Using pre-computed types
        assertThat( this.vehicleType.isAssignableFrom( this.vehicleType ) ).isTrue();
        assertThat( this.vehicleType.isAssignableFrom( this.carType     ) ).isFalse(); // expected true
        assertThat( this.vehicleType.isAssignableFrom( this.myCarType   ) ).isTrue();
    }

    @Test
    public void test_isAssignableFrom_Case1_Inv()
    {
        // Case 1 : otherVehicle is a Vehicle interface
        Vehicle otherVehicle;
        otherVehicle = this.vehicle;
        otherVehicle = this.car;
        otherVehicle = this.myCar;

        // Using type directly on left side
        assertThat( Vehicle.class.isAssignableFrom( this.vehicleType ) ).isTrue();
        assertThat( Car.class    .isAssignableFrom( this.vehicleType ) ).isTrue();
        assertThat( MyCar.class  .isAssignableFrom( this.vehicleType ) ).isTrue();

        // Using type directly on right side
        assertThat( this.vehicle.getClass().isAssignableFrom( Vehicle.class ) ).isFalse(); // expected true
        assertThat( this.car    .getClass().isAssignableFrom( Vehicle.class ) ).isFalse(); // expected true
        assertThat( this.myCar  .getClass().isAssignableFrom( Vehicle.class ) ).isFalse(); // expected true

        // Using dynamic computed types
        assertThat( this.vehicle.getClass().isAssignableFrom( otherVehicle.getClass() ) ).isTrue();
        assertThat( this.car    .getClass().isAssignableFrom( otherVehicle.getClass() ) ).isTrue();
        assertThat( this.myCar  .getClass().isAssignableFrom( otherVehicle.getClass() ) ).isTrue();

        // Using pre-computed types
        assertThat( this.vehicleType.isAssignableFrom( this.vehicleType ) ).isTrue();
        assertThat( this.carType    .isAssignableFrom( this.vehicleType ) ).isTrue();
        assertThat( this.myCarType  .isAssignableFrom( this.vehicleType ) ).isTrue();
    }

    @Test
    public void test_isAssignableFrom_Case2_all_KO()
    {
        // Case 2 : otherCar is a Car object
        Car otherCar = new Car();
        //  otherCar = this.vehicle; // - compilation error here
        otherCar = this.car;
        otherCar = this.myCar;

        // Using type directly on left side
        assertThat( otherCar.getClass().isAssignableFrom( Vehicle.class ) ).isFalse();
        assertThat( otherCar.getClass().isAssignableFrom( Car.class     ) ).isFalse(); // expected true
        assertThat( otherCar.getClass().isAssignableFrom( MyCar.class   ) ).isTrue();

        // Using type directly on right side
        assertThat( Car.class.isAssignableFrom( this.vehicle.getClass() ) ).isTrue(); // expected false
        assertThat( Car.class.isAssignableFrom( this.car    .getClass() ) ).isTrue();
        assertThat( Car.class.isAssignableFrom( this.myCar  .getClass() ) ).isTrue();

        // Using dynamic computed types
       assertThat( otherCar.getClass().isAssignableFrom( this.vehicle.getClass() ) ).isTrue(); // expected false
       assertThat( otherCar.getClass().isAssignableFrom( this.car    .getClass() ) ).isFalse(); // expected true
       assertThat( otherCar.getClass().isAssignableFrom( this.myCar  .getClass() ) ).isTrue();

       // Using pre-computed types
       assertThat( this.carType.isAssignableFrom( this.vehicleType ) ).isTrue(); // expected false
       assertThat( this.carType.isAssignableFrom( this.carType     ) ).isTrue();
       assertThat( this.carType.isAssignableFrom( this.myCarType   ) ).isTrue();
    }

    @Test
    public void test_isAssignableFrom_Case2_Inv_all_KO()
    {
        // Case 2 : otherCar is a Car object
        Car otherCar = new Car();
        //  otherCar = this.vehicle; // - compilation error here
        otherCar = this.car;
        otherCar = this.myCar;

        // Using type directly on left side
        assertThat( Vehicle.class.isAssignableFrom( otherCar.getClass() ) ).isTrue(); // expected false
        assertThat( Car.class    .isAssignableFrom( otherCar.getClass() ) ).isTrue();
        assertThat( MyCar.class  .isAssignableFrom( otherCar.getClass() ) ).isTrue();

        // Using type directly on right side
        assertThat( this.vehicle.getClass().isAssignableFrom( Car.class ) ).isFalse();
        assertThat( this.car    .getClass().isAssignableFrom( Car.class ) ).isTrue();
        assertThat( this.myCar  .getClass().isAssignableFrom( Car.class ) ).isFalse(); // expected true

        // Using dynamic computed types
       assertThat( this.vehicle.getClass().isAssignableFrom( otherCar.getClass() ) ).isTrue(); // expected false
       assertThat( this.car    .getClass().isAssignableFrom( otherCar.getClass() ) ).isTrue();
       assertThat( this.myCar  .getClass().isAssignableFrom( otherCar.getClass() ) ).isTrue();

       // Using pre-computed types
       assertThat( this.vehicleType.isAssignableFrom( this.carType ) ).isFalse();
       assertThat( this.carType    .isAssignableFrom( this.carType ) ).isTrue();
       assertThat( this.myCarType  .isAssignableFrom( this.carType ) ).isFalse(); // expected true
    }

    @Test
    public void test_isAssignableFrom_Case3()
    {
        // Case 3
        @SuppressWarnings("unused")
        MyCar copyOfMyCar;
        // copyOfMyCar = this.vehicle; // - compilation error here
        // copyOfMyCar = this.car; // - compilation error here
        copyOfMyCar = this.myCar;

        // Using type directly on left side
        assertThat( copyOfMyCar.getClass().isAssignableFrom( Vehicle.class ) ).isFalse();
        assertThat( copyOfMyCar.getClass().isAssignableFrom( Car.class     ) ).isFalse();
        assertThat( copyOfMyCar.getClass().isAssignableFrom( MyCar.class   ) ).isTrue();

        // Using type directly on right side
        assertThat( MyCar.class.isAssignableFrom( this.vehicle.getClass() ) ).isTrue(); // expected false
        assertThat( MyCar.class.isAssignableFrom( this.car    .getClass() ) ).isFalse();
        assertThat( MyCar.class.isAssignableFrom( this.myCar  .getClass() ) ).isTrue();

        // Using dynamic computed types
       assertThat( copyOfMyCar.getClass().isAssignableFrom( this.vehicle.getClass() ) ).isTrue(); // expected false
       assertThat( copyOfMyCar.getClass().isAssignableFrom( this.car    .getClass() ) ).isFalse();
       assertThat( copyOfMyCar.getClass().isAssignableFrom( this.myCar  .getClass() ) ).isTrue();

       // Using pre-computed types
       assertThat( this.myCarType.isAssignableFrom( this.vehicleType ) ).isTrue(); // expected false
       assertThat( this.myCarType.isAssignableFrom( this.carType     ) ).isFalse();
       assertThat( this.myCarType.isAssignableFrom( this.myCarType   ) ).isTrue();
    }

    @Test
    public void test_isAssignableFrom_Case3_Inv_all_KO()
    {
        // Case 3
        @SuppressWarnings("unused")
        MyCar copyOfMyCar;
        // copyOfMyCar = this.vehicle; // - compilation error here
        // copyOfMyCar = this.car; // - compilation error here
        copyOfMyCar = this.myCar;

        // Using type directly on left side
        assertThat( Vehicle.class.isAssignableFrom( copyOfMyCar.getClass() ) ).isTrue(); // Should be false ????
        assertThat( Car.class    .isAssignableFrom( copyOfMyCar.getClass() ) ).isTrue(); // Should be false ????
        assertThat( MyCar.class  .isAssignableFrom( copyOfMyCar.getClass() ) ).isTrue();

        // Using type directly on right side
        assertThat( this.vehicle.getClass().isAssignableFrom( MyCar.class ) ).isTrue(); // Should be false ????
        assertThat( this.car    .getClass().isAssignableFrom( MyCar.class ) ).isTrue(); // Should be false ????;
        assertThat( this.myCar  .getClass().isAssignableFrom( MyCar.class ) ).isTrue();

        // Using dynamic computed types
       assertThat( this.vehicle.getClass().isAssignableFrom( copyOfMyCar.getClass() ) ).isTrue(); // Should be false ????
       assertThat( this.car    .getClass().isAssignableFrom( copyOfMyCar.getClass() ) ).isTrue(); // Should be false ????
       assertThat( this.myCar  .getClass().isAssignableFrom( copyOfMyCar.getClass() ) ).isTrue();

       // Using pre-computed types
       assertThat( this.vehicleType.isAssignableFrom( this.myCarType ) ).isTrue(); // Should be false ????
       assertThat( this.carType    .isAssignableFrom( this.myCarType ) ).isTrue(); // Should be false ????
       assertThat( this.myCarType  .isAssignableFrom( this.myCarType ) ).isTrue();
    }
}
