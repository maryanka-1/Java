package classes;

import java.util.StringJoiner;

public class Car {

        private String brand;
        private String model;
        private int maxSpeed;

        public Car() {
            this.brand= "Default brand";
            this.model = "Default model";
            this.maxSpeed = 0;
        }

        public Car(String firstName, String lastName, int height) {
            this.brand = firstName;
            this.model = lastName;
            this.maxSpeed = height;
        }

        public int upgrade(int value) {
            this.maxSpeed += value;
            return maxSpeed;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                    .add("brand='" + brand + "'")
                    .add("model='" + model + "'")
                    .add("max speed=" + maxSpeed)
                    .toString();
        }
    }


