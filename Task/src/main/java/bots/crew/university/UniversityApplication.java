package bots.crew.university;

import bots.crew.university.services.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class UniversityApplication implements CommandLineRunner {

    private final CommandService commandService;

    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            List<String> commands = List.of("Who is head of department (.+)",
                    "Show (.+) statistics.",
                    "Show the average salary for the department (.+).",
                    "Show count of employee for (.+).",
                    "Global search by (.+).");
            System.out.println("Write please command");
            System.out.println("Example of commands:");
            for (String command : commands) {
                System.out.println(command);
            }
            String command = scanner.nextLine();
            commandService.writeCommand(command, commands);
            if ("exit".equalsIgnoreCase(command)) {
                System.out.println("Exit");
                break;
            }
        }

        scanner.close();
    }
}
