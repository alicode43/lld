
// The Facade Design Pattern

// The Facade Pattern is a structural design pattern that provides a simplified, higher-level interface to a complex set of classes, library, framework, or subsystem. It hides the underlying complexities, dependencies, and execution sequences from the client, making the subsystem easier to use and loosely coupled.

// Core Components & Roles
// Complex Subsystem: Multiple specialized classes that handle granular tasks (e.g., PowerSupply, CoolingSystem, CPU, Memory, HardDrive, BIOS, OperatingSystem).

// Facade (ComputerFacade): A wrapper class that knows which subsystem classes are responsible for a request and coordinates their operations in the correct order.

// Client (Main): Interacts exclusively with the simple ComputerFacade without needing direct access to the complex internal components.


// Main entry point - Copy-paste and run directly
public class Main {
    public static void main(String[] args) {
        // Client interacts only with the unified Facade interface
        ComputerFacade computer = new ComputerFacade();

        System.out.println("=== Starting Computer ===");
        computer.startComputer();

        System.out.println("\n=== Shutting Down Computer ===");
        computer.shutdownComputer();
    }
}

// -------------------------------------------------------------
// Subsystem Classes (Complex internal components)
// -------------------------------------------------------------

class PowerSupply {
    public void providePower() {
        System.out.println("Power Supply: Providing power to system...");
    }

    public void cutPower() {
        System.out.println("Power Supply: Power cut off.");
    }
}

class CoolingSystem {
    public void startFans() {
        System.out.println("Cooling System: Fans started.");
    }

    public void stopFans() {
        System.out.println("Cooling System: Fans stopped.");
    }
}

class CPU {
    public void initialize() {
        System.out.println("CPU: Initializing registers and cores...");
    }

    public void execute() {
        System.out.println("CPU: Executing startup instructions...");
    }

    public void stop() {
        System.out.println("CPU: Halting execution.");
    }
}

class Memory {
    public void selfTest() {
        System.out.println("Memory: Running self-test and clearing cache...");
    }

    public void clear() {
        System.out.println("Memory: Clearing RAM...");
    }
}

class HardDrive {
    public void spinUp() {
        System.out.println("Hard Drive: Spinning up drive plates...");
    }

    public byte[] readBootSector() {
        System.out.println("Hard Drive: Reading OS boot sector from sector 0...");
        return new byte[]{1, 0, 1, 1}; // Dummy boot data
    }

    public void spinDown() {
        System.out.println("Hard Drive: Spinning down.");
    }
}

class BIOS {
    public void boot(CPU cpu, Memory memory, HardDrive hardDrive) {
        System.out.println("BIOS: Bootstrapping hardware...");
        memory.selfTest();
        cpu.initialize();
        hardDrive.spinUp();
        byte[] bootSector = hardDrive.readBootSector();
        System.out.println("BIOS: Boot sector loaded successfully.");
    }
}

class OperatingSystem {
    public void load() {
        System.out.println("Operating System: Kernel loaded into memory. OS started.");
    }

    public void shutdown() {
        System.out.println("Operating System: Saving session and shutting down kernel.");
    }
}

// -------------------------------------------------------------
// Facade Class (Unified Gateway)
// -------------------------------------------------------------

class ComputerFacade {
    private final PowerSupply powerSupply;
    private final CoolingSystem coolingSystem;
    private final CPU cpu;
    private final Memory memory;
    private final HardDrive hardDrive;
    private final BIOS bios;
    private final OperatingSystem os;

    public ComputerFacade() {
        this.powerSupply = new PowerSupply();
        this.coolingSystem = new CoolingSystem();
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
        this.bios = new BIOS();
        this.os = new OperatingSystem();
    }

    // Simplified high-level operation to boot the entire system
    public void startComputer() {
        powerSupply.providePower();
        coolingSystem.startFans();
        bios.boot(cpu, memory, hardDrive);
        cpu.execute();
        os.load();
        System.out.println("Status: Computer booted successfully!");
    }

    // Simplified high-level operation to power down
    public void shutdownComputer() {
        os.shutdown();
        cpu.stop();
        hardDrive.spinDown();
        coolingSystem.stopFans();
        memory.clear();
        powerSupply.cutPower();
        System.out.println("Status: Computer shut down completely.");
    }
}
