package se.branko.adventure.Game;

import se.branko.adventure.models.Burglar;
import se.branko.adventure.models.Entity;
import se.branko.adventure.models.Resident;

import java.util.Scanner;

public class Game {
    private Scanner scanner = new Scanner(System.in);
    private RoomType currentRoom = RoomType.LIVING_ROOM;
    private Resident resident;
    private Burglar burglar;
    private boolean hasPan = false;
    private boolean running = true;

    public Game(Resident resident, Burglar burglar) {
        this.resident = resident;
        this.burglar = burglar;
    }
    public boolean isRunning(){
        return running;
    }
    public void twoChoices(){
        System.out.println("What do you want to do?");
        System.out.println("1. Go back to living room");
        System.out.println("2. Exit game");
    }
    public void printWelcomeMessage(){
        System.out.println("\nYou are awakened by a loud noise coming from inside the house");
        System.out.println("You sit up on the couch where you accidentally fell asleep");
        System.out.println("You feel like you have to investigate what that sound was...");
    }

    public void livingRoom(){
        currentRoom = RoomType.LIVING_ROOM;
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("You are in the " + currentRoom.toString().replace("_", " ").toLowerCase() + ".");
        System.out.println("What do you want to do?");
        System.out.println("1. Check bedroom");
        System.out.println("2. Check kitchen");
        System.out.println("3. Check office");
        System.out.println("4. Check hallway");
        System.out.println("5. Exit game");
    }
    public void bedroom(){
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("You are in the " + currentRoom.toString().replace("_", " ").toLowerCase() + ".");
        System.out.println("Nothing unusual here, you see a bed and some cabinets with clothes all over the floor...");
        twoChoices();
    }
    public void kitchen(){
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("You are in the " + currentRoom.toString().replace("_", " ").toLowerCase() + ".");
        System.out.println("Not much to see or do here, everything seems to be in its place");
        twoChoices();
    }
    public void handleKitchen(){
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("You are in the " + currentRoom.toString().replace("_", " ").toLowerCase() + ".");
        System.out.println("You see a frying pan on the counter. Would you like to pick it up?");
        System.out.println("1. Yes, pick up the frying pan. It might come in handy...");
        System.out.println("2. No, leave it.");

        String input = getUserInput();

        switch (input) {
            case "1" -> {
                System.out.println("You picked up the frying pan.");
                hasPan = true;
                resident.setDamage(resident.getDamage()+ 3);
                System.out.println("Damage increased by 3!");
                System.out.println("Damage is now "+ resident.getDamage());
                System.out.println("\nPress ENTER to continue...");
                getUserInput();
            }
            case "2" ->
                System.out.println("You decided to leave the frying pan.");

            default -> System.out.println("Invalid input, type 1 or 2");
        }
    }

    public void office(){
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("You are in the " + currentRoom.toString().replace("_", " ").toLowerCase() + ".");
        System.out.println("You see a desk with a computer and phone on it.. Everything seems to be in order.");
        twoChoices();
    }
    public void handleOffice(){
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("You are in the " + currentRoom.toString().replace("_", " ").toLowerCase() + ".");
        System.out.println("You see a desk with a computer and phone on it");
        System.out.println("1. To call 911");
        System.out.println("2. Go back to living room");

        String input = getUserInput();

        switch (input) {
            case "1" -> {
                System.out.println("You call 911 and the police are on their way");
                System.out.println("You managed to win the fight and get the burglar arrested!");
                System.out.println("\n******** Mission Complete ********");
                running = false;
            }
            case "2" -> livingRoom();
        }
    }
    public void hallway(){
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("You are in the " + currentRoom.toString().replace("_", " ").toLowerCase() + ".");
        if(burglar.isConsious()) {
            System.out.println("You immediately spot a burglar going through your stuff!");
            System.out.println("You scream: HEY!! What the hell are you doing here!??");
            System.out.println("\nThe burglar does not seem to back off, and you engage in a fight!");
        }else{
            System.out.println("\nThe burglar is laying on the floor knocked out.");
        }
        while (resident.isConsious()&& burglar.isConsious()) {
            System.out.println("\nPress ENTER to strike");
            getUserInput();
            fightOneRound();
        }
        if(resident.isConsious()){

            System.out.println("\nAlert the authorities with the phone in the office before the burglar wakes up..");
            System.out.println("What do you want to do?");
            System.out.println("1. Go back to living room");
            System.out.println("2. Exit game");
        }else if(burglar.isConsious()){
            System.out.println("The burglar knocked you out and is now taking everything you have in your house.. :(");
            System.out.println("\n********GAME OVER********");
            running = false;
        }
    }
    public void executeAttack(Entity attacker, Entity defender){
        attacker.attack(defender);
        System.out.println(attacker.getRole()+ " attacks " + defender.getRole()+ " dealing "+ attacker.getDamage()+" damage");
        if(defender.isConsious()){
            System.out.println(defender.getRole()+" health is: "+ defender.getHealth());
        }else{
            System.out.println(defender.getRole()+" has been knocked out..");
        }
    }
    public void fightOneRound(){
        executeAttack(resident, burglar);
        if(burglar.isConsious()){
            executeAttack(burglar, resident);
        }
    }
    public boolean processInput(String input){
        if(currentRoom == RoomType.LIVING_ROOM){
        switch (input) {
            case "1" -> {
                currentRoom = RoomType.BEDROOM;
                bedroom();
            }
            case "2" -> {
                if(!hasPan){
                    currentRoom = RoomType.KITCHEN;
                    handleKitchen();
                }
                currentRoom = RoomType.KITCHEN;
                kitchen();
            }
            case "3" -> {
                if (burglar.isConsious()) {
                    currentRoom = RoomType.OFFICE;
                    office();
                }else {
                    currentRoom = RoomType.OFFICE;
                    handleOffice();
                }
            }
            case "4" -> {
                currentRoom = RoomType.HALLWAY;
                hallway();
            }
            case "5" -> {
                System.out.println("Exiting game...");
                return running = false;
            }
            default -> System.out.println("Invalid input, type a number between 1 and 5.");
        }
        }else{
            switch (input) {
                case "1" -> {
                    System.out.println("Go back to living room");
                    livingRoom();
                }
                case "2" -> {
                    System.out.println("Exiting game...");
                    return running = false;
                }
                default -> System.out.println("Invalid input, type 1 or 2");
            }
        }
        return true;
    }

    public String getUserInput(){
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        Resident resident = new Resident("Resident",12,3);
        Burglar burglar = new Burglar("Burglar", 12, 4);

        Game game = new Game(resident,burglar);
        game.printWelcomeMessage();
        game.livingRoom();
        while(game.isRunning()){
            String input = game.getUserInput();
            game.processInput(input);

        }
    }
}
