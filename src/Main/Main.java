package Main;

import com.java.labjob.VoteMachine;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new VoteMachine("localhost", 9886).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
