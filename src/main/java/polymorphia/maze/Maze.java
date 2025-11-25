package polymorphia.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.slf4j.Logger;

import polymorphia.artifacts.IArtifact;
import polymorphia.characters.Character;

public class Maze {
    private List<Room> rooms;

    private Maze() {
    }

    public int size() {
        return rooms.size();
    }

    public String toString() {
        return String.join("\n\n", rooms.stream().map(Object::toString).toList());
    }

    public Boolean hasLivingCreatures() {
        return rooms.stream().anyMatch(Room::hasLivingCreatures);
    }

    public Boolean hasLivingAdventurers() {
        return rooms.stream().anyMatch(Room::hasLivingAdventurers);
    }

    public List<Character> getLivingCreatures() {
        List<Character> creatures = new ArrayList<>();
        for (Room room : rooms) {
            creatures.addAll(room.getLivingCreatures());
        }
        return creatures;
    }

    public List<Character> getLivingCharacters() {
        List<Character> characters = new ArrayList<>();
        for (Room room : rooms) {
            characters.addAll(room.getLivingCharacters());
        }
        return characters;
    }

    public boolean hasLivingCharacters() {
        return getLivingCharacters().stream().anyMatch(Character::isAlive);
    }

    public List<Character> getLivingAdventurers() {
        return getLivingCharacters().stream()
                .filter(Character::isAdventurer)
                .toList();
    }

    public List<Room> getRooms() {
        return List.copyOf(rooms);
    }

    public static Builder getNewBuilder(RoomFactory roomFactory) {
        return new Builder(roomFactory);
    }

    public Room getRoom(String roomName) {
        return rooms.stream()
                .filter(room -> room.getName().equals(roomName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No room with name " + roomName));
    }

    public static class Builder {
        static Logger logger = org.slf4j.LoggerFactory.getLogger(Builder.class);
        private final Random rand = new Random();
        public static String[] grid2x2RoomNames = new String[]{"Northwest", "Northeast", "Southwest", "Southeast"};
        public static String[] grid3x3RoomNames = new String[]{"Northwest", "North", "Northeast", "West", "Center", "East", "Southwest", "South", "Southeast"};

        final Maze maze = new Maze();
        Map<String, Room> roomMap = new HashMap<>();
        private Boolean distributeRandomly = true;
        private boolean useBidirectionalConnections = true;
        private int currentRoomIndex = 0;
        private final RoomFactory roomFactory;

        private Builder(RoomFactory roomFactory) {
            this.roomFactory = roomFactory;
        }

        private Room nextRoom() {
            if (distributeRandomly) {
                return getRandomRoom();
            }
            return maze.getRooms().get(currentRoomIndex++ % maze.getRooms().size());
        }

        private Room getRandomRoom() {
            return maze.rooms.get(rand.nextInt(maze.rooms.size()));
        }

        public Builder createGridOfRooms(int rows, int columns, String[] roomNames) {
            Room[][] roomGrid = new Room[rows][columns];
            List<Room> rooms = new ArrayList<>();
            // Notice -- don't use i and j. Use row and column -- they are better
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Room newRoom = roomFactory.createRoom(roomNames[row * columns + column]);
                    roomGrid[row][column] = newRoom;
                    rooms.add(newRoom);
                }
            }
            maze.rooms = rooms;

            // Now connect the rooms
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Room currentRoom = roomGrid[row][column];
                    Room neighbor;
                    if (row > 0) {
                        neighbor = roomGrid[row - 1][column];
                        currentRoom.addNeighbor(neighbor, useBidirectionalConnections);
                    }
                    if (column > 0) {
                        neighbor = roomGrid[row][column - 1];
                        currentRoom.addNeighbor(neighbor, useBidirectionalConnections);
                    }
                }
            }
            return this;
        }

        public Builder create2x2Grid() {
            return createGridOfRooms(2, 2, grid2x2RoomNames);
        }

        public Builder create3x3Grid() {
            return createGridOfRooms(3, 3, grid3x3RoomNames);
        }

        public Builder createFullyConnectedRooms(List<String> roomNames) {
            maze.rooms = new ArrayList<>();
            for (String roomName : roomNames) {
                Room currentRoom = roomFactory.createRoom(roomName);
                roomMap.put(currentRoom.getName(), currentRoom);
                maze.rooms.add(currentRoom);
            }
            fullyConnectRooms();
            return this;
        }

        public Builder createRooms(List<String> roomNames) {
            maze.rooms = new ArrayList<>();
            for (String roomName : roomNames) {
                Room currentRoom = roomFactory.createRoom(roomName);
                roomMap.put(currentRoom.getName(), currentRoom);
                maze.rooms.add(currentRoom);
            }
            return this;
        }

        public Builder fullyConnectRooms() {
            for (Room room : maze.rooms) {
                for (Room otherRoom : maze.rooms) {
                    room.addNeighbor(otherRoom, true);
                }
            }
            return this;
        }

        public Builder createConnectedRooms(Integer minConnections, Integer numRooms) {
            createRooms(numRooms);
            connectRooms(minConnections);
            return this;
        }

        public void connectRooms(Integer minConnections) {
            if (maze.size() < 2) {
                return;
            }
            int realMinimumConnections = Math.min(minConnections, Math.max(maze.size() - 1, 1));
            for (Room room : maze.rooms) {
                while (room.numberOfNeighbors() < realMinimumConnections) {
                    Room neighbor = nextRoom();
                    if (!room.equals(neighbor) && !room.hasNeighbor(neighbor)) {
                        room.addNeighbor(neighbor, useBidirectionalConnections);
                    }
                }
            }
        }

        public Builder add(Character character) {
            nextRoom().add(character);
            return this;
        }

        public Builder addCharacters(List<Character> characters) {
            for (Character character : characters) {
                nextRoom().add(character);
            }
            return this;
        }

        public Builder addArtifacts(List<IArtifact> artifacts) {
            for (IArtifact artifact : artifacts) {
                nextRoom().add(artifact);
            }
            return this;
        }

        public Maze build() {
            assert maze.size() > 0;
            for (Room room : maze.rooms) {
                if (room.numberOfNeighbors() == 0) {
                    logger.warn("Room {} has no neighbors. Connecting it to another room.", room.getName());
                    room.addNeighbor(nextRoom(), useBidirectionalConnections);
                }
            }
            if (!maze.hasLivingAdventurers()) {
                logger.error("No adventurers created. Terminating game.");
                throw new IllegalStateException("No adventurers created. Terminating game.");
            }
            return maze;
        }

        public Builder addToRoom(String roomName, Character character) {
            getRoom(roomName).add(character);
            return this;
        }

        public Builder addToRoom(String roomName, IArtifact artifact) {
            getRoom(roomName).add(artifact);
            return this;
        }

        public Room getRoom(String roomName) {
            return roomMap.get(roomName);
        }

        public Builder distributeSequentially() {
            distributeRandomly = false;
            return this;
        }

        public Builder distributeRandomly() {
            distributeRandomly = true;
            return this;
        }

        public Builder setConnectionDirection(Boolean useBidirectionalConnections) {
            this.useBidirectionalConnections = useBidirectionalConnections;
            return this;
        }

        public Builder createNRoomsWithMConnections(int numberOfRooms, int numberOfConnections) {
            createConnectedRooms(numberOfConnections, numberOfRooms);
            return this;
        }

        private Builder createRooms(int numberOfRooms) {
            maze.rooms = new ArrayList<>();
            IntStream.range(0, numberOfRooms).forEach(_ -> {
                Room currentRoom = roomFactory.createRoom();
                roomMap.put(currentRoom.getName(), currentRoom);
                maze.rooms.add(currentRoom);
            });
            return this;
        }
    }
}
