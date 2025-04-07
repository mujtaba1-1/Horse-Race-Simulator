class Main {
    public static void main(String[] args) {
        Horse horse = new Horse('T', "Thunder", 0.5);

        System.out.println(horse.getConfidence());
        
        horse.setConfidence(-0.5);
        System.out.println(horse.getConfidence());

        horse.setConfidence(1.6);
        System.out.println(horse.getConfidence());
    }
}