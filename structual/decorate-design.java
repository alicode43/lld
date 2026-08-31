// The Decorator Design Pattern

// The Decorator Pattern is a structural design pattern that lets you dynamically attach new behaviors or responsibilities to an object at runtime without altering its underlying class or modifying existing code. It provides a flexible, modular alternative to static inheritance (subclassing).


// The Problem: Class Explosion via Static Inheritance

// When designing extensible systems using traditional inheritance, adding optional features or layers creates a combinatorial problem.

// Consider a game character like Mario with optional power-ups: HeightUp, GunPower, StarPower, and FlyPower. If you create a concrete subclass for every possible variation, you are forced to define:


// MarioWithHeightUp

// MarioWithGunPower

// MarioWithHeightUpAndGunPower

// MarioWithFlyAndHeightUp

// MarioWithFlyAndHeightUpAndGunPower

// For N features, the number of required subclasses scales exponentially as $2^N$. This is known as Class Explosion. It leads to duplicate logic, fragile class hierarchies, and impossible maintenance whenever a new capability is introduced or modified.2. The Solution: Object Wrapping via the Decorator PatternInstead of creating dedicated subclasses for every permutation at compile time, the Decorator Pattern wraps the base object inside decorator objects dynamically at runtime.Each decorator acts like an outer skin around the core object, delegating base tasks down the chain and adding its own custom behavior before or after delegation.


// +-------------------------------------------------------------+
// |                     StarPowerDecorator                      |
// |  +-------------------------------------------------------+  |
// |  |                  GunPowerDecorator                    |  |
// |  |  +-------------------------------------------------+  |  |
// |  |  |                HeightUpDecorator                |  |  |
// |  |  |  +-------------------------------------------+  |  |  |
// |  |  |  |             Base Mario Object             |  |  |  |
// |  |  |  +-------------------------------------------+  |  |  |
// |  |  +-------------------------------------------------+  |  |
// |  +-------------------------------------------------------+  |
// +-------------------------------------------------------------+

  
  
// Main entry point - Copy-paste and run directly
public class Main {
    public static void main(String[] args) {
        // 1. Basic Mario character
        Character mario = new Mario();
        System.out.println("Base: " + mario.getAbilities());

        // 2. Wrap with Height-Up power
        mario = new HeightUpDecorator(mario);
        System.out.println("After Mushroom: " + mario.getAbilities());

        // 3. Wrap with Gun Power
        mario = new GunPowerDecorator(mario);
        System.out.println("After Fire Flower: " + mario.getAbilities());

        // 4. Wrap with Star Power (Limited Time)
        mario = new StarPowerDecorator(mario);
        System.out.println("After Super Star: " + mario.getAbilities());
    }
}

// 1. Component Interface
interface Character {
    String getAbilities();
}

// 2. Concrete Component (Base Object)
class Mario implements Character {
    @Override
    public String getAbilities() {
        return "Mario";
    }
}

// 3. Base Decorator (Abstract class implementing Character & holding Character reference)
abstract class CharacterDecorator implements Character {
    protected Character character;

    public CharacterDecorator(Character character) {
        this.character = character;
    }

    @Override
    public String getAbilities() {
        return character.getAbilities();
    }
}

// 4. Concrete Decorators (Power-ups)
class HeightUpDecorator extends CharacterDecorator {
    public HeightUpDecorator(Character character) {
        super(character);
    }

    @Override
    public String getAbilities() {
        return character.getAbilities() + " with HeightUp";
    }
}

class GunPowerDecorator extends CharacterDecorator {
    public GunPowerDecorator(Character character) {
        super(character);
    }

    @Override
    public String getAbilities() {
        return character.getAbilities() + " with Gun";
    }
}

class StarPowerDecorator extends CharacterDecorator {
    public StarPowerDecorator(Character character) {
        super(character);
    }

    @Override
    public String getAbilities() {
        return character.getAbilities() + " with StarPower (Limited Time)";
    }
}
