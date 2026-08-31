/**
 * ============================================================================
 * BRIDGE DESIGN PATTERN EXPLANATION & CODE
 * ============================================================================
 * 
 * WHAT IS THE BRIDGE PATTERN?
 * The Bridge Pattern is a structural design pattern that separates an 
 * "Abstraction" (high-level control layer) from its "Implementation" 
 * (low-level execution layer) so both can vary independently.
 * 
 * ----------------------------------------------------------------------------
 * 1. THE PROBLEM (CLASS EXPLOSION: M x N):
 * If we have M vehicle types (Sedan, SUV) and N engine types (Petrol, Diesel, Electric),
 * using normal inheritance creates M * N classes:
 *   - SedanWithPetrol, SedanWithDiesel, SedanWithElectric
 *   - SUVWithPetrol, SUVWithDiesel, SUVWithElectric
 * Adding 1 new engine forces us to create new subclasses for EVERY car type.
 * 
 * ----------------------------------------------------------------------------
 * 2. THE SOLUTION (BRIDGE VIA COMPOSITION: M + N):
 * Instead of subclassing, we split the system into two independent hierarchies:
 *   - Abstraction Hierarchy (High-level: Car -> Sedan, SUV)
 *   - Implementor Hierarchy (Low-level: Engine -> Petrol, Diesel, Electric)
 * 
 * The Car holds a reference to Engine (Car "has-a" Engine). 
 * This reference acts as the "BRIDGE".
 * Total classes needed: M + N (instead of M * N).
 * 
 * ----------------------------------------------------------------------------
 * 3. BRIDGE VS STRATEGY (INTENT DIFFERENCE):
 *   - Bridge (Structural): Separates two dimensions of code at design/build time 
 *     so both hierarchies can grow independently without class explosion.
 *   - Strategy (Behavioral): Swaps interchangeable algorithms dynamically at 
 *     runtime based on user choice (e.g., payment modes, sorting algorithms).
 * ============================================================================
 */

public class Main {
    public static void main(String[] args) {
        // Step 1: Create concrete Implementor objects (Engines)
        Engine petrolEngine = new PetrolEngine();
        Engine dieselEngine = new DieselEngine();
        Engine electricEngine = new ElectricEngine();

        // Step 2: Bridge any Abstraction (Car) with any Implementor (Engine)
        Car mySedan = new Sedan(petrolEngine);
        Car mySuvElectric = new SUV(electricEngine);
        Car mySuvDiesel = new SUV(dieselEngine);

        // Step 3: Execute high-level operations
        System.out.println("--- Bridge Pattern Output ---");
        mySedan.drive();
        System.out.println();

        mySuvElectric.drive();
        System.out.println();

        mySuvDiesel.drive();
    }
}

// ============================================================================
// HIERARCHY 1: IMPLEMENTOR (Low-Level Layer / Execution Details)
// ============================================================================

/**
 * Common interface for all low-level implementations.
 */
interface Engine {
    void start();
}

/**
 * Concrete Implementor A
 */
class PetrolEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Petrol Engine: Ignited via spark plug.");
    }
}

/**
 * Concrete Implementor B
 */
class DieselEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Diesel Engine: Ignited via high compression.");
    }
}

/**
 * Concrete Implementor C
 */
class ElectricEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Electric Engine: Powered on silently via battery.");
    }
}

// ============================================================================
// HIERARCHY 2: ABSTRACTION (High-Level Layer / User-Facing Interface)
// ============================================================================

/**
 * Base Abstraction: Holds the "Bridge" reference to the Implementor (Engine).
 */
abstract class Car {
    // THE BRIDGE: Composition reference to the Implementor interface
    protected Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public abstract void drive();
}

/**
 * Refined Abstraction 1
 */
class Sedan extends Car {
    public Sedan(Engine engine) {
        super(engine);
    }

    @Override
    public void drive() {
        engine.start(); // Delegates work across the bridge to the engine
        System.out.println("Driving a smooth Sedan on the highway.");
    }
}

/**
 * Refined Abstraction 2
 */
class SUV extends Car {
    public SUV(Engine engine) {
        super(engine);
    }

    @Override
    public void drive() {
        engine.start(); // Delegates work across the bridge to the engine
        System.out.println("Driving a rugged SUV across rough terrain.");
    }
}
