// ==========================================
// 1. SUBSYSTEM COMPONENTS
// ==========================================

class PowerSupply {
    public void providePower() {
        System.out.println("Power Supply: Supplying power to components...");
    }
}

class CoolingSystem {
    public void startFans() {
        System.out.println("Cooling System: Fans started and cooling initialized.");
    }
}

class CPU {
    public void initialize() {
        System.out.println("CPU: Initializing registers and clock cycles...");
    }
}

class Memory {
    public void selfTest() {
        System.out.println("Memory: Running POST (Power-On Self Test)... Passed.");
    }
}

class HardDrive {
    public void spinUp() {
        System.out.println("Hard Drive: Spinning up platters / loading SSD controller...");
    }
}

class BIOS {
    public void boot(CPU cpu, Memory memory) {
        System.out.println("BIOS: Booting sequence initiated...");
        cpu.initialize();
        memory.selfTest();
        System.out.println("BIOS: Hardware check completed.");
    }
}

class OperatingSystem {
    public void load() {
        System.out.println("Operating System: Kernel loaded. System ready for user.");
    }
}

// ==========================================
// 2. FACADE CLASS
// ==========================================

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

    // Unified simplified method exposed to the client
    public void startComputer() {
        System.out.println("=== Starting Computer Boot Sequence ===");
        powerSupply.providePower();
        coolingSystem.startFans();
        bios.boot(cpu, memory);
        hardDrive.spinUp();
        os.load();
        System.out.println("=== Computer Booted Successfully! ===");
    }
}

// ==========================================
// 3. CLIENT / DRIVER CODE
// ==========================================

public class Main {
    public static void main(String[] args) {
        // The client only interacts with the Facade, knowing nothing about internal classes
        ComputerFacade computer = new ComputerFacade();
        computer.startComputer();
    }
}
