public class Pokemon {
    private String name;
    private String type1;
    private String ability;

    public Pokemon (String name, String type1, String ability) {
        this.name = name;
        this.type1 = type1;
        this.ability = ability;
    }

    public String getName() {
        return name;
    }

    public String getType1() {
        return type1;
    }

    public String getAbility() {
        return ability;
    }

    @Override
    public String toString() {
        return String.format("Nombre: %s\nTipo1 1: %s \nHabilidad: %s", name, type1, ability);
    }
}