// ==========================================
// 1. BASE COMPONENT INTERFACE
// ==========================================
interface Character {
    String getAbilities();
}

// ==========================================
// 2. CONCRETE COMPONENT (Base Mario)
// ==========================================
class Mario implements Character {
    @Override
    public String getAbilities() {
        return "Mario";
    }
}

// ==========================================
// 3. BASE DECORATOR (Abstract Decorator)
// ==========================================
abstract class CharacterDecorator implements Character {
    protected final Character character; // has-a relationship

    public CharacterDecorator(Character character) {
        this.character = character;
    }

    @Override
    public String getAbilities() {
        return character.getAbilities();
    }
}

// ==========================================
// 4. CONCRETE DECORATORS (Power-Ups)
// ==========================================

// Height Power-Up
class HeightUpDecorator extends CharacterDecorator {
    public HeightUpDecorator(Character character) {
        super(character);
    }

    @Override
    public String getAbilities() {
        return character.getAbilities() + " with Height-Up";
    }
}

// Gun Shooting Power-Up
class GunPowerDecorator extends CharacterDecorator {
    public GunPowerDecorator(Character character) {
        super(character);
    }

    @Override
    public String getAbilities() {
        return character.getAbilities() + " with Gun";
    }
}

// Star Power-Up (Temporary Invincibility)
class StarPowerDecorator extends CharacterDecorator {
    public StarPowerDecorator(Character character) {
        super(character);
    }

    @Override
    public String getAbilities() {
        return character.getAbilities() + " with Star Power (Limited Time)";
    }
}

// ==========================================
// 5. CLIENT / DRIVER CODE
// ==========================================
public class Main {
    public static void main(String[] args) {
        // 1. Start with base Mario
        Character mario = new Mario();
        System.out.println("1. Initial state: " + mario.getAbilities());

        // 2. Add Height-Up power-up
        mario = new HeightUpDecorator(mario);
        System.out.println("2. After Mushroom: " + mario.getAbilities());

        // 3. Add Gun power-up
        mario = new GunPowerDecorator(mario);
        System.out.println("3. After Fire Flower: " + mario.getAbilities());

        // 4. Add Star power-up
        mario = new StarPowerDecorator(mario);
        System.out.println("4. After Star Power: " + mario.getAbilities());

        System.out.println("\n--- Dynamic Composition Test ---");
        // Combine decorators directly in arbitrary order
        Character flyingGunMario = new GunPowerDecorator(new HeightUpDecorator(new Mario()));
        System.out.println("Composed Mario: " + flyingGunMario.getAbilities());
    }
}
