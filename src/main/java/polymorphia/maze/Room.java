package polymorphia.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import polymorphia.artifacts.ArtifactType;
import polymorphia.artifacts.IArtifact;
import polymorphia.characters.Character;

public class Room {
    static private final Random rand = new Random();

    private final String name;
    private final List<Room> neighbors = new ArrayList<>();
    private final List<Character> characters = new ArrayList<>();
    private final List<IArtifact> artifacts = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Character> getLivingCreatures() {
        return getLivingCharacters().stream()
                .filter(Character::isCreature)
                .toList();
    }

    public List<Character> getLivingAdventurers() {
        return getLivingCharacters().stream()
                .filter(Character::isAdventurer)
                .toList();
    }

    public List<String> getContents() {
        List<String> characterStrings = new ArrayList<>(getLivingCharacters().stream()
                .map(Object::toString)
                .toList());

        for (IArtifact artifact : artifacts) {
            characterStrings.add(artifact.toString());
        }

        return characterStrings;
    }

    void addNeighbor(Room neighbor) {
        // Make sure we are never a neighbor of ourselves
        if (this != neighbor) {
            this.neighbors.add(neighbor);
        }
    }

    void addNeighbor(Room neighbor, boolean bidirectional) {
        this.addNeighbor(neighbor);
        if (bidirectional) {
            neighbor.addNeighbor(this);
        }
    }

    public String toString() {
        String representation = "\t" + name + ":\n\t\t";
        representation += String.join("\n\t\t", getContents());
        representation += "\n";
        return representation;
    }

    void add(Character character) {
        characters.add(character);
        character.enterRoom(this);
    }

    public Boolean hasLivingCreatures() {
        return getLivingCharacters().stream().anyMatch(Character::isCreature);
    }

    public Boolean hasLivingAdventurers() {
        return getLivingCharacters().stream().anyMatch(Character::isAdventurer);
    }

    public void remove(Character character) {
        characters.remove(character);
    }

    public Character getRandomAdventurer() {
        List<Character> adventurers = getLivingAdventurers();
        return adventurers.get(rand.nextInt(adventurers.size()));
    }

    public Room getRandomNeighbor() {
        if (neighbors.isEmpty()) {
            return null;
        }
        return neighbors.stream().toList().get(rand.nextInt(neighbors.size()));
    }

    public void enter(Character character) {
        add(character);
    }

    public List<Character> getLivingCharacters() {
        return characters.stream()
                .filter(Character::isAlive)
                .toList();
    }

    public boolean hasNeighbor(Room neighbor) {
        return neighbors.contains(neighbor);
    }

    public int numberOfNeighbors() {
        return neighbors.size();
    }

    public boolean hasNeighbors() {
        return !neighbors.isEmpty();
    }

    public boolean contains(Character character) {
        return characters.contains(character);
    }

    public boolean contains(IArtifact artifact) {
        return artifacts.contains(artifact);
    }

    public void add(IArtifact artifact) {
        artifacts.add(artifact);
    }

    public boolean hasFood() {
        return artifacts.stream()
                .anyMatch(artifact -> artifact.isType(ArtifactType.Food));
    }

    public Optional<IArtifact> getFood() {
        Optional<IArtifact> food = artifacts.stream()
                .filter(artifact -> artifact.isType(ArtifactType.Food))
                .findAny();
        food.ifPresent(artifacts::remove);
        return food;
    }

    public List<IArtifact> getFoodItems() {
        return artifacts.stream()
                .filter(artifact -> artifact.isType(ArtifactType.Food))
                .toList();
    }

    public Optional<Character> getCreature() {
        List<Character> creatures = getLivingCreatures();
        if (creatures.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(creatures.get(rand.nextInt(creatures.size())));
    }

    public Optional<IArtifact> getArtifact(ArtifactType artifactType) {
        return artifacts.stream()
                .filter(artifact -> artifact.isType(artifactType))
                .findAny();
    }

    public void remove(IArtifact artifact) {
        artifacts.remove(artifact);
    } //changed

    public Optional<IArtifact> getArmor() {
        return artifacts.stream()
                .filter(artifact -> artifact.isType(ArtifactType.Armor))
                .findAny();
    }

    public Optional<IArtifact> getStudentLoan() {
        return artifacts.stream()
                .filter(artifact -> artifact.isType(ArtifactType.StudentLoan))
                .findAny();
    }

    public void replaceCharacter(Character oldCharacter, Character newCharacter) {
        int index = -1;
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i) == oldCharacter) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            characters.set(index, newCharacter);
            newCharacter.enterRoom(this);
        } else {
            characters.removeIf(c -> c == oldCharacter || c.getName().equals(oldCharacter.getName()));
            add(newCharacter);
        }
    }
}
