import java.io.*;
import java.util.*;
import java.util.Scanner;

public class test{

public static void main (String[] args){
    Scanner input = new Scanner(System.in);
    int[] numbers = new int[10];

    for (int i = 0; i < numbers.length; i++){
        System.out.println("Please enter number");
        numbers[i] = input.nextInt();
    }
    //Prints Array for testing
    System.out.println("Array has: ");  
		for(int j = 0; j< numbers.length; j++){
			System.out.print(numbers[j]);
		}
    
	}
}
