import java.util.Scanner;

public class Program {
    private static final int MAX_STUDENT = 10;
    private static final int MAX_LESSONS_PER_WEEK = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] students = new String[MAX_STUDENT];
        String[] lessons = new String[MAX_LESSONS_PER_WEEK];
        String[] attendance = new String[50];
        String[] daysOfWeek = {"TU", "WE", "TH", "FR", "SA", "SU", "MO"};
        students = add(sc, students);
        lessons = add(sc, lessons);
        int countLessons = lessons.length;
        attendance = add(sc, attendance);
        sc.close();
        String[] sortLessons = sortLesson(lessons, daysOfWeek, countLessons);
        String[] fullSchedule = fullSchedule(sortLessons, daysOfWeek, countLessons);
        print(students, fullSchedule, attendance);
    }

    private static String[] sortLesson(String[] lessons, String[] daysOfWeek, int countLessons) {
        String[] sortedLessons = new String[lessons.length];
        int numSort = 0;
        int marker = 1;
        for (String s : daysOfWeek) {
            for (int i = 0; i < lessons.length; i++) {
                String[] words = lessons[i].split(" ");
                if (words[1].equals(s)) {
                    sortedLessons[numSort] = lessons[i];
                    numSort++;
                }
            }
        }
        String temp = "";
        while (marker > 0) {
            marker = 0;
            for (int i = 0; i < sortedLessons.length - 1; i++) {
                String[] words = sortedLessons[i].split(" ");
                String[] words2 = sortedLessons[i + 1].split(" ");
                if (words[1].equals(words2[1])) {
                    char[] word = words[0].toCharArray();
                    char[] word2 = words2[0].toCharArray();
                    if (word[0] > word2[0]) {
                        temp = sortedLessons[i];
                        sortedLessons[i] = sortedLessons[i + 1];
                        sortedLessons[i + 1] = temp;
                        marker++;
                    }
                }

            }
        }
        return sortedLessons;
    }

    private static String[] add(Scanner sc, String[] array) {
        int counter = 0;
        while (true) {
            String input = sc.nextLine();
            if (input.equals(".")) break;
            array[counter++] = input;
        }
        String[] result = new String[counter];
        for (int i = 0; i < counter; i++) {
            result[i] = array[i];
        }
        return result;
    }

    private static String[] fullSchedule(String[] sortLessons, String[] dayOfWeek, int countLessons) {
        String[] fullSchedule = new String[countLessons * 5];
        int week = 0;
        int dayOfMonth = 1;
        int index = 0; // Индекс для заполнения fullSchedule
        while (dayOfMonth <= 30) {
            for (int i = 0; i < dayOfWeek.length; i++) {
                if (dayOfMonth % (i + 1 + week) == 0) {
                    for (int j = 0; j < sortLessons.length; j++) {
                        String[] words = sortLessons[j].split(" ");
                        if (dayOfWeek[i].equals(words[1])) {
                            if (dayOfMonth > 9) {
                                fullSchedule[index] = (words[0] + ":00 " + dayOfWeek[i] + " " + dayOfMonth);
                            } else {
                                fullSchedule[index] = (words[0] + ":00 " + dayOfWeek[i] + "  " + dayOfMonth);
                            }
                            index++;
                        }
                    }
                    dayOfMonth++;
                    if (dayOfMonth > 30) break;
                }
            }
            week += 7;
        }
        String[] result = new String[index];
        for (int i = 0; i < index; i++) {
            result[i] = fullSchedule[i];
        }
        return result;
    }

    private static void print(String[] students, String[] fullSchedule, String[] attendance) {
        String[][] result = new String[students.length + 1][fullSchedule.length + 1];
        String nineSpace = "         ";
        String tenSpace = "          ";
        String eightSpace = "        ";
        for (int i = 0; i < students.length + 1; i++) {
            for (int j = 0; j < fullSchedule.length + 1; j++) {
                if (i == 0 && j == 0) result[i][j] = tenSpace;
                if (i == 0 && j != 0) result[i][j] = fullSchedule[j - 1];
                if (i != 0 && j == 0) result[i][j] = String.format("%10s", students[i - 1]);
                if (i != 0 && j != 0) result[i][j] = tenSpace;
            }
        }
        for (int k = 0; k < attendance.length; k++) {
            String[] infoVisit = attendance[k].split(" ");
            for (int i = 1; i < students.length + 1; i++) {
                String[] temp = result[i][0].split(" ");
                if (infoVisit[0].equals(temp[temp.length - 1])) {
                    for (int j = 1; j < fullSchedule.length + 1; j++) {
                        String[] dayInSchedule = result[0][j].split(" ");
                        if (infoVisit[2].equals(dayInSchedule[dayInSchedule.length - 1])) {
                            char[] timeInSchedule = dayInSchedule[0].toCharArray();
                            char[] timeInfoVisit = infoVisit[1].toCharArray();
                            if (timeInfoVisit[0] == timeInSchedule[0]) {
                                if ((infoVisit[3].equals("HERE"))) result[i][j] = nineSpace + "1";
                                if (infoVisit[3].equals("NOT_HERE")) result[i][j] = eightSpace + "-1";
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < students.length + 1; i++) {
            for (int j = 0; j < fullSchedule.length + 1; j++) {
                if (j == 0) System.out.print(result[i][j]);
                if (j != 0) System.out.print(result[i][j] + "|");
            }
            System.out.print('\n');
        }
    }
}