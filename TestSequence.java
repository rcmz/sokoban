class TestSequence {
    public static void main(String[] args) {
        Sequence<Integer> sequence = new Tableau<>();

        System.out.println(sequence);

        sequence.insereTete(666);
        System.out.println(sequence);

        sequence.insereQueue(42);
        System.out.println(sequence);

        sequence.insereQueue(43);
        System.out.println(sequence);

        sequence.insereTete(333);
        System.out.println(sequence);

        int element = sequence.extraitTete();
        System.out.println(element);
        System.out.println(sequence);

        element = sequence.extraitTete();
        System.out.println(element);
        System.out.println(sequence);

        element = sequence.extraitTete();
        System.out.println(element);
        System.out.println(sequence);

        element = sequence.extraitTete();
        System.out.println(element);
        System.out.println(sequence);
    }
}
